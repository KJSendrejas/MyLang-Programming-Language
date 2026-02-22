package main.java.resources;// Generated from MyLang.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MyLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MyLangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MyLangParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MyLangParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(MyLangParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(MyLangParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(MyLangParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#printStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStmt(MyLangParser.PrintStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(MyLangParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(MyLangParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MyLangParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#funcDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDecl(MyLangParser.FuncDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(MyLangParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(MyLangParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(MyLangParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code not}
	 * labeled alternative in {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot(MyLangParser.NotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(MyLangParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compare}
	 * labeled alternative in {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompare(MyLangParser.CompareContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literalExpr}
	 * labeled alternative in {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpr(MyLangParser.LiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryMinus}
	 * labeled alternative in {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinus(MyLangParser.UnaryMinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addSub}
	 * labeled alternative in {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(MyLangParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcCall}
	 * labeled alternative in {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCall(MyLangParser.FuncCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulDiv}
	 * labeled alternative in {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDiv(MyLangParser.MulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logical}
	 * labeled alternative in {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical(MyLangParser.LogicalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link MyLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpr(MyLangParser.IdExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(MyLangParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(MyLangParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyLangParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(MyLangParser.TypeContext ctx);
}