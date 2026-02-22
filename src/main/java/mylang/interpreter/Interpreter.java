package main.java.mylang.interpreter;
import java.util.*;

import main.java.resources.MyLangBaseVisitor;
import main.java.resources.MyLangParser;

public class Interpreter extends MyLangBaseVisitor<Object> {

    private final Map<String, MyLangParser.FuncDeclContext> functions = new HashMap<>();
    private final Deque<Map<String, Object>> stack = new ArrayDeque<>();
    private Object returnValue = null;
    private boolean returning = false;

    public Interpreter() {
        stack.push(new HashMap<>());
    }

    @Override
    public Object visitProgram(MyLangParser.ProgramContext ctx) {
        // collect functions
        for (var s : ctx.stmt())
            if (s.funcDecl() != null)
                functions.put(s.funcDecl().ID().getText(), s.funcDecl());

        // execute top-level statements
        for (var s : ctx.stmt())
            if (s.funcDecl() == null)
                visit(s);

        return null;
    }

    @Override
    public Object visitVarDecl(MyLangParser.VarDeclContext ctx) {
        Object val = ctx.expr() != null ? visit(ctx.expr()) : defaultValue(ctx.type());
        stack.peek().put(ctx.ID().getText(), val);
        return null;
    }

    @Override
    public Object visitAssign(MyLangParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        Object val = visit(ctx.expr());
        assign(id, val);
        return null;
    }

    private void assign(String id, Object val) {
        for (var frame : stack)
            if (frame.containsKey(id)) {
                frame.put(id, val);
                return;
            }
        throw new RuntimeException("Undefined variable " + id);
    }

    @Override
    public Object visitPrintStmt(MyLangParser.PrintStmtContext ctx) {
        Object v = visit(ctx.expr());
        System.out.println(v);
        return null;
    }

    @Override
    public Object visitBlock(MyLangParser.BlockContext ctx) {
        stack.push(new HashMap<>());
        for (var s : ctx.stmt()) {
            visit(s);
            if (returning) break;
        }
        stack.pop();
        return null;
    }

    @Override
    public Object visitIfStmt(MyLangParser.IfStmtContext ctx) {
        boolean cond = (Boolean) visit(ctx.expr());
        if (cond)
            visit(ctx.block(0));
        else if (ctx.block().size() > 1)
            visit(ctx.block(1));
        return null;
    }

    @Override
    public Object visitWhileStmt(MyLangParser.WhileStmtContext ctx) {
        while ((Boolean) visit(ctx.expr())) {
            visit(ctx.block());
            if (returning) break;
        }
        return null;
    }

    @Override
    public Object visitReturnStmt(MyLangParser.ReturnStmtContext ctx) {
        returning = true;
        returnValue = ctx.expr() != null ? visit(ctx.expr()) : null;
        return null;
    }

    @Override
    public Object visitFuncCall(MyLangParser.FuncCallContext ctx) {
        String name = ctx.ID().getText();
        var f = functions.get(name);
        if (f == null)
            throw new RuntimeException("Undefined function: " + name);

        // evaluate args
        List<Object> args = new ArrayList<>();
        if (ctx.args() != null)
            for (var e : ctx.args().expr())
                args.add(visit(e));

        // call
        stack.push(new HashMap<>());
        returning = false;

        // bind parameters
        if (f.params() != null) {
            for (int i = 0; i < f.params().param().size(); i++) {
                var p = f.params().param(i);
                stack.peek().put(p.ID().getText(), args.get(i));
            }
        }

        visit(f.block());

        stack.pop();

        Object rv = returnValue;
        returnValue = null;
        returning = false;

        return rv;
    }

    @Override
    public Object visitIdExpr(MyLangParser.IdExprContext ctx) {
        String id = ctx.ID().getText();
        for (var frame : stack)
            if (frame.containsKey(id))
                return frame.get(id);
        throw new RuntimeException("Undefined variable " + id);
    }

    private Object defaultValue(MyLangParser.TypeContext t) {
        if (t == null) return 0;
        return switch (t.getText()) {
            case "int" -> 0;
            case "float" -> 0.0;
            case "bool" -> false;
            default -> null;
        };
    }

    @Override
    public Object visitLiteralExpr(MyLangParser.LiteralExprContext ctx) {
        if (ctx.literal().INT() != null) return Integer.parseInt(ctx.literal().INT().getText());
        if (ctx.literal().FLOAT() != null) return Double.parseDouble(ctx.literal().FLOAT().getText());
        if (ctx.literal().BOOL() != null) return Boolean.parseBoolean(ctx.literal().BOOL().getText());
        return null;
    }

    @Override
    public Object visitAddSub(MyLangParser.AddSubContext ctx) {
        Object a = visit(ctx.expr(0));
        Object b = visit(ctx.expr(1));
        return ctx.op.getText().equals("+") ? add(a, b) : sub(a, b);
    }

    @Override
    public Object visitMulDiv(MyLangParser.MulDivContext ctx) {
        Object a = visit(ctx.expr(0));
        Object b = visit(ctx.expr(1));
        return ctx.op.getText().equals("*") ? mul(a, b) : div(a, b);
    }

    @Override
    public Object visitCompare(MyLangParser.CompareContext ctx) {
        Comparable a = (Comparable) visit(ctx.expr(0));
        Comparable b = (Comparable) visit(ctx.expr(1));
        return switch (ctx.op.getText()) {
            case "==" -> a.equals(b);
            case "!=" -> !a.equals(b);
            case "<" -> a.compareTo(b) < 0;
            case "<=" -> a.compareTo(b) <= 0;
            case ">" -> a.compareTo(b) > 0;
            case ">=" -> a.compareTo(b) >= 0;
            default -> false;
        };
    }

    @Override
    public Object visitLogical(MyLangParser.LogicalContext ctx) {
        boolean a = (Boolean) visit(ctx.expr(0));
        boolean b = (Boolean) visit(ctx.expr(1));
        return ctx.op.getText().equals("&&") ? (a && b) : (a || b);
    }

    @Override
    public Object visitUnaryMinus(MyLangParser.UnaryMinusContext ctx) {
        Object v = visit(ctx.expr());
        return (v instanceof Integer) ? -(Integer)v : -(Double)v;
    }

    @Override
    public Object visitNot(MyLangParser.NotContext ctx) {
        return !(Boolean) visit(ctx.expr());
    }

    private Object add(Object a, Object b) {
        return (a instanceof Integer && b instanceof Integer)
                ? (Integer)a + (Integer)b
                : toDouble(a) + toDouble(b);
    }

    private Object sub(Object a, Object b) {
        return (a instanceof Integer && b instanceof Integer)
                ? (Integer)a - (Integer)b
                : toDouble(a) - toDouble(b);
    }

    private Object mul(Object a, Object b) {
        return (a instanceof Integer && b instanceof Integer)
                ? (Integer)a * (Integer)b
                : toDouble(a) * toDouble(b);
    }

    private Object div(Object a, Object b) {
        return toDouble(a) / toDouble(b);
    }

    private double toDouble(Object v) {
        return (v instanceof Integer) ? ((Integer)v).doubleValue() : (Double)v;
    }
}