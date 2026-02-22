package main.java.mylang.semantic;

import java.util.ArrayList;
import java.util.List;

import main.java.resources.*;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.*;

public class SemanticAnalyzer extends MyLangBaseVisitor<Type> {

    private Scope currentScope = new Scope(null);
    public final List<String> errors = new ArrayList<>();

    private void error(String msg) {
        errors.add("Semantic error: " + msg);
    }

    @Override
    public Type visitVarDecl(MyLangParser.VarDeclContext ctx) {
        String name = ctx.ID().getText();
        Type t = ctx.type() != null ? getType(ctx.type()) : Type.INT;

        try {
            currentScope.define(new Symbol(name, t));
        } catch (RuntimeException e) {
            error(e.getMessage());
        }

        if (ctx.expr() != null) {
            Type rhs = visit(ctx.expr());
            if (rhs != t) error("Type mismatch in declaration of " + name);
        }
        return null;
    }

    @Override
    public Type visitAssign(MyLangParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        Symbol s = currentScope.resolve(id);
        if (s == null) {
            error("Undefined variable " + id);
            return null;
        }
        Type rhs = visit(ctx.expr());
        if (rhs != s.type) error("Type mismatch in assignment to " + id);
        return null;
    }

    @Override
    public Type visitBlock(MyLangParser.BlockContext ctx) {
        currentScope = new Scope(currentScope);
        for (var s : ctx.stmt()) visit(s);
        currentScope = currentScope.getParent();
        return null;
    }

    @Override
    public Type visitFuncDecl(MyLangParser.FuncDeclContext ctx) {
        String name = ctx.ID().getText();
        List<Type> paramTypes = new ArrayList<>();

        if (ctx.params() != null) {
            for (var p : ctx.params().param()) {
                paramTypes.add(getType(p.type()));
            }
        }

        Type ret = ctx.type() != null ? getType(ctx.type()) : Type.VOID;

        try {
            currentScope.define(new Symbol(name, ret, paramTypes));
        } catch (RuntimeException e) {
            error(e.getMessage());
        }

        // enter function scope
        currentScope = new Scope(currentScope);

        // define parameters
        if (ctx.params() != null) {
            for (var p : ctx.params().param()) {
                currentScope.define(new Symbol(
                        p.ID().getText(),
                        getType(p.type())
                ));
            }
        }

        visit(ctx.block());

        currentScope = currentScope.getParent();

        return null;
    }

    @Override
    public Type visitFuncCall(MyLangParser.FuncCallContext ctx) {
        String name = ctx.ID().getText();
        Symbol f = currentScope.resolve(name);

        if (f == null || !f.isFunction()) {
            error("Undefined function " + name);
            return Type.INT;
        }

        List<Type> givenArgs = new ArrayList<>();
        if (ctx.args() != null) {
            for (var e : ctx.args().expr()) givenArgs.add(visit(e));
        }

        if (givenArgs.size() != f.params.size())
            error("Argument count mismatch calling " + name);

        for (int i = 0; i < Math.min(givenArgs.size(), f.params.size()); i++) {
            if (givenArgs.get(i) != f.params.get(i))
                error("Argument type mismatch in call to " + name);
        }

        return f.type;
    }

    @Override
    public Type visitLiteralExpr(MyLangParser.LiteralExprContext ctx) {
        if (ctx.literal().INT() != null) return Type.INT;
        if (ctx.literal().FLOAT() != null) return Type.FLOAT;
        if (ctx.literal().BOOL() != null) return Type.BOOL;
        return null;
    }

    @Override
    public Type visitIdExpr(MyLangParser.IdExprContext ctx) {
        Symbol s = currentScope.resolve(ctx.ID().getText());
        if (s == null) error("Undefined variable " + ctx.ID().getText());
        return s != null ? s.type : Type.INT;
    }

    private Type getType(MyLangParser.TypeContext t) {
        return switch (t.getText()) {
            case "int" -> Type.INT;
            case "float" -> Type.FLOAT;
            case "bool" -> Type.BOOL;
            default -> Type.VOID;
        };
    }
}