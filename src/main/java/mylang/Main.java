package main.java.mylang;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import main.java.mylang.interpreter.Interpreter;
import main.java.mylang.semantic.SemanticAnalyzer;
import main.java.resources.MyLangLexer;
import main.java.resources.MyLangParser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.err.println("Usage: java -jar mylang.jar <program.ml>");
            System.exit(1);
        }

        String filename = args[0];
        String src = Files.readString(Paths.get(filename));

        CharStream input = CharStreams.fromString(src);
        MyLangLexer lexer = new MyLangLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MyLangParser parser = new MyLangParser(tokens);
        MyLangParser.ProgramContext tree = parser.program();

        SemanticAnalyzer sem = new SemanticAnalyzer();
        sem.visit(tree);

        Interpreter i = new Interpreter();
        i.visit(tree);
    }
}