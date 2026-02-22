package test.java.mylang;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import main.java.mylang.interpreter.Interpreter;

class IntegrationTests {

	@Test
	public void testArithmeticProgram() {
	    String src = "print 1 + 2 * 3;";
	    String out = TestUtils.interpret(src);
	    assertEquals("7", out);
	}
}
