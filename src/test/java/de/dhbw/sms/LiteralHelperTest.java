package de.dhbw.sms;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.dhbw.sms.services.IAbstractSyntaxTreeResult;
import de.dhbw.sms.services.InputParser;
import de.dhbw.sms.services.LiteralHelper;

public class LiteralHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		InputParser inputParser = new InputParser();   
		IAbstractSyntaxTreeResult parserResult = inputParser.parseExperssion("a*x^2+b*x+c");
		assertTrue(parserResult.hasParsed());
		List<String> result = LiteralHelper.getLiterals(parserResult.getAbstractSyntaxTree());
		
	}

}
