package de.dhbw.sms;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.dhbw.sms.services.IAbstractSyntaxTreeNode;
import de.dhbw.sms.services.IAbstractSyntaxTreeResult;
import de.dhbw.sms.services.InputParser;

public class InputParserTest {
	
	InputParser objectUnderTest;
	
	@Before
    public void setUp() throws Exception {
		objectUnderTest = new InputParser();   
    }
	

	@Test
	public void testInputParserEmptyString() {
		IAbstractSyntaxTreeResult parserResult =objectUnderTest.parseExperssion("");
		assertTrue(parserResult != null);
		assertFalse(parserResult.hasParsed());
		assertTrue(parserResult.getAbstractSyntaxTree() == null);
	}
	
	@Test
	public void testInputPaserBlankTestString()
	{
		IAbstractSyntaxTreeResult parserResult =objectUnderTest.parseExperssion("   ");
		assertTrue(parserResult != null);
		assertFalse(parserResult.hasParsed());
		assertTrue(parserResult.getAbstractSyntaxTree() == null);
	}
	
	@Test
	public void testInputPaserPlusTestString()
	{
		IAbstractSyntaxTreeResult parserResult =objectUnderTest.parseExperssion("+");
		assertTrue(parserResult != null);
		assertTrue(parserResult.hasParsed());
		IAbstractSyntaxTreeNode root = parserResult.getAbstractSyntaxTree();
		assertTrue(root != null);
		assertTrue(root.getSymbol() == "+");
		assertTrue(root.getRhs() == null);
		assertTrue(root.getLhs() == null);
	}
	
	@Test
	public void testInputPaserSymbolPlusSymbolTestString()
	{
		IAbstractSyntaxTreeResult parserResult =objectUnderTest.parseExperssion("a+b");
		assertTrue(parserResult != null);
		assertTrue(parserResult.hasParsed());
		IAbstractSyntaxTreeNode root = parserResult.getAbstractSyntaxTree();
		assertTrue(root != null);
		assertTrue(root.getSymbol().equals("+"));
		assertTrue(root.getRhs() != null);
		assertTrue(root.getLhs() != null);
		
		IAbstractSyntaxTreeNode lhs = root.getLhs();
		assertTrue(lhs.getSymbol().equals("a"));
		assertTrue(lhs.getRhs() == null);
		assertTrue(lhs.getLhs() == null);
		
		IAbstractSyntaxTreeNode rhs = root.getRhs();
		assertTrue(rhs.getSymbol().equals("b"));
		assertTrue(rhs.getRhs() == null);
		assertTrue(rhs.getLhs() == null);
	}
	
	@Test
	public void testInputPaserBracketedExpressionTestString()
	{
		IAbstractSyntaxTreeResult parserResult =objectUnderTest.parseExperssion("(a+b)+(c+m)");
		assertTrue(parserResult != null);
		assertTrue(parserResult.hasParsed());
		IAbstractSyntaxTreeNode root = parserResult.getAbstractSyntaxTree();
		assertTrue(root != null);
		assertTrue(root.getSymbol().equals("+"));
		assertTrue(root.getRhs() != null);
		assertTrue(root.getLhs() != null);
		
		/*IAbstractSyntaxTreeNode lhs = root.getLhs();
		assertTrue(lhs.getSymbol().equals("a"));
		assertTrue(lhs.getRhs() == null);
		assertTrue(lhs.getLhs() == null);
		
		IAbstractSyntaxTreeNode rhs = root.getRhs();
		assertTrue(rhs.getSymbol().equals("b"));
		assertTrue(rhs.getRhs() == null);
		assertTrue(rhs.getLhs() == null);*/
	}
	
	@Test
	public void testInputPaserMultipleTermsExpressionTestString()
	{
		IAbstractSyntaxTreeResult parserResult =objectUnderTest.parseExperssion("a*x^2+b*x+c)");//(x+x^(1/2))
		assertTrue(parserResult != null);
		assertTrue(parserResult.hasParsed());
		IAbstractSyntaxTreeNode root = parserResult.getAbstractSyntaxTree();
		assertTrue(root != null);
		assertTrue(root.getSymbol().equals("+"));
		assertTrue(root.getRhs() != null);
		assertTrue(root.getLhs() != null);
		
		IAbstractSyntaxTreeNode lhsPlus = root.getLhs();
		assertTrue(lhsPlus.getSymbol().equals("+"));
		assertTrue(lhsPlus.getRhs() != null);
		assertTrue(lhsPlus.getLhs() != null);
		
		IAbstractSyntaxTreeNode rhs = root.getRhs();
		assertTrue(rhs.getSymbol().equals("b"));
		assertTrue(rhs.getRhs() == null);
		assertTrue(rhs.getLhs() == null);
	}
	
	@Test
	public void testInputPaserMultipleBracketedTermsExpressionTestString()
	{
		IAbstractSyntaxTreeResult parserResult =objectUnderTest.parseExperssion("(x+x^(1/2))");//(x+x^(1/2))
		assertTrue(parserResult != null);
		assertTrue(parserResult.hasParsed());
		IAbstractSyntaxTreeNode root = parserResult.getAbstractSyntaxTree();
		assertTrue(root != null);
		assertTrue(root.getSymbol().equals("+"));
		assertTrue(root.getRhs() != null);
		assertTrue(root.getLhs() != null);
		
		IAbstractSyntaxTreeNode lhsPlus = root.getLhs();
		assertTrue(lhsPlus.getSymbol().equals("+"));
		assertTrue(lhsPlus.getRhs() != null);
		assertTrue(lhsPlus.getLhs() != null);
		
		IAbstractSyntaxTreeNode rhs = root.getRhs();
		assertTrue(rhs.getSymbol().equals("b"));
		assertTrue(rhs.getRhs() == null);
		assertTrue(rhs.getLhs() == null);
	}
	
	@Test
	public void testInputPaserSymbolTestString()
	{
		IAbstractSyntaxTreeResult parserResult =objectUnderTest.parseExperssion("a");
		assertTrue(parserResult != null);
		assertTrue(parserResult.hasParsed());
		IAbstractSyntaxTreeNode root = parserResult.getAbstractSyntaxTree();
		assertTrue(root != null);
	}
	
	@Test
	public void testInputPaserSymbolPlusTestString()
	{
		IAbstractSyntaxTreeResult parserResult =objectUnderTest.parseExperssion("a+");
		assertTrue(parserResult != null);
		assertFalse(parserResult.hasParsed());
		IAbstractSyntaxTreeNode root = parserResult.getAbstractSyntaxTree();
		assertTrue(root == null);
	}
	
	@Test
	public void testInputPaserPlusSymbolTestString()
	{
		IAbstractSyntaxTreeResult parserResult =objectUnderTest.parseExperssion("+a");
		assertTrue(parserResult != null);
		assertFalse(parserResult.hasParsed());
		IAbstractSyntaxTreeNode root = parserResult.getAbstractSyntaxTree();
		assertTrue(root == null);
	}
	
	@Test
	public void testInputTokenizePattern()
	{
		List<String> input = Arrays.asList("(a+b)");
		List<String> output = objectUnderTest.tokenizeByPattern(input, "\\(");
		assertTrue(output.size() == 2);
		String subPart1 = output.get(0);
		String subPart2 = output.get(1);
		assertTrue(subPart1.equals("("));
		assertTrue(subPart2.equals("a+b)"));
	}
	
	@Test
	public void testInputTokenizeInput()
	{
		List<String> symbols = Arrays.asList("\\+", "-", "\\^", "=", "\\*", "\\/", "sin", "cos", "sqrt", "\\(", "\\)");
		
		String input = "(a+b)";
		List<String> output = objectUnderTest.tokenizeInput(input, symbols);
		assertTrue(output.size() == 5);
		String subPart1 = output.get(0);
		String subPart2 = output.get(1);
		String subPart3 = output.get(2);
		String subPart4 = output.get(3);
		String subPart5 = output.get(4);
		assertTrue(subPart1.equals("("));
		assertTrue(subPart2.equals("a"));
		assertTrue(subPart3.equals("+"));
		assertTrue(subPart4.equals("b"));
		assertTrue(subPart5.equals(")"));
	}

}
