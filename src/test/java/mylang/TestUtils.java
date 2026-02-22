package test.java.mylang;


import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import main.java.mylang.interpreter.Interpreter;
import main.java.mylang.semantic.SemanticAnalyzer;
import main.java.resources.MyLangLexer;
import main.java.resources.MyLangParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestUtils {

    // --- Lex + Parse source code ---
    public static MyLangParser.ProgramContext parse(String src) {
        CharStream input = CharStreams.fromString(src);
        MyLangLexer lexer = new MyLangLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MyLangParser parser = new MyLangParser(tokens);
        return parser.program();
    }

    // --- Run full pipeline: parse + semantic analysis + interpret ---
    public static String interpret(String src) {
        // Step 1: Parse
        MyLangParser.ProgramContext tree = parse(src);

        // Step 2: Semantic analysis
        SemanticAnalyzer sem = new SemanticAnalyzer();
        sem.visit(tree);

        // Step 3: Capture interpreter output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        PrintStream old = System.out;
        System.setOut(ps);

        // Step 4: Run
        Interpreter interpreter = new Interpreter();
        interpreter.visit(tree);

        // Restore stdout
        System.setOut(old);

        return out.toString().trim();
    }

    // Expect interpreter to throw an error
    public static void expectSemanticError(String src) {
        try {
            MyLangParser.ProgramContext tree = parse(src);
            SemanticAnalyzer sem = new SemanticAnalyzer();
            sem.visit(tree);
        } catch (RuntimeException e) {
            return; // PASS
        }
        throw new AssertionError("Expected semantic error but code passed.");
    }

    // Expect parsing to fail
    public static void expectParseError(String src) {
        try {
            parse(src);
        } catch (Exception e) {
            return; // PASS
        }
        throw new AssertionError("Expected parse error but input parsed.");
    }
}