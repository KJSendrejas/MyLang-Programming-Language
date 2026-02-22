package test.java.mylang;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.mylang.semantic.SemanticAnalyzer;

class SemanticsTest {

	@Test
	public void testUndefinedVar() {
	    TestUtils.expectSemanticError("print x;");
	}
}
