package test.java.mylang;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.resources.MyLangParser;

class ParserTest {

	@Test
	public void testSyntaxError() {
	    TestUtils.expectParseError("if (x 1) print 5;");
	}

}
