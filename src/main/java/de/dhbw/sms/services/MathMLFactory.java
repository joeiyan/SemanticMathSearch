package de.dhbw.sms.services;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MathMLFactory {
	
	public String getMathML(IAbstractSyntaxTreeNode root)
	{
		if(root != null)
		{
			String mathML = null;
			List<String> symbols = Arrays.asList("+", "-", "^", "=", "*", "/");
			for(String symbol : symbols)
			{
				mathML = testForSimpleRelations(root, symbol);
				if(mathML != null) break;
			}
			if(mathML != null)
				return mathML;
			return generateMathML(root);
				
		}
		return "<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mi>a</mi><mo>&#x2260;</mo><mn>0</mn></math>";
	}
	
	private String testForSimpleRelations(IAbstractSyntaxTreeNode root, String pattern)
	{
		if(root.getSymbol() == pattern && root.getLhs() == null && root.getRhs() == null)
			return "<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mo>"+pattern+"</mo></math>";
		return null;
	}
	
	public String generateMathML(IAbstractSyntaxTreeNode root)
	{
		String retVal = "";
		List<String> parts = new LinkedList<String>();
		String prefix = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\">";
		traverseTree(parts, root);
		for(String part : parts)
			retVal = retVal +part;
		String postfix = "</math>";
		return prefix+retVal+postfix;
	}
	
	public void traverseTree(List<String> parts, IAbstractSyntaxTreeNode node)
	{
		List<String> lhsParts = new LinkedList<String>();
		List<String> rhsParts = new LinkedList<String>();
		if(node.getLhs() == null && node.getRhs() == null)
		{
			parts.add(generateSymbol(node));
			return;
		}
		if(node.getLhs() != null)
			traverseTree(lhsParts, node.getLhs());
		if(node.getRhs() != null)
			traverseTree(rhsParts, node.getRhs());
		parts.add(generateNode(lhsParts, rhsParts, node));
		return;
	}
	
	public String generateNode(List<String> lhsParts,List<String> rhsParts, IAbstractSyntaxTreeNode node)
	{
		String retVal = "";
		if(node.getSymbol().equals("+")) retVal =generateAddition(lhsParts, rhsParts, node.isBracketed());
		if(node.getSymbol().equals("*")) retVal =generateMultiplication(lhsParts, rhsParts, node.isBracketed());
		if(node.getSymbol().equals("/")) retVal =generateDivision(lhsParts, rhsParts, node.isBracketed());
		if(node.getSymbol().equals("-")) retVal =generateSubtraction(lhsParts, rhsParts, node.isBracketed());
		if(node.getSymbol().equals("^")) retVal =generateExponentiation(lhsParts, rhsParts, node.isBracketed());
		if(node.getSymbol().equals("=")) retVal =generateEquality(lhsParts, rhsParts, node.isBracketed());
		return retVal;
	}
	
	public String generateAddition(List<String> lhsParts,List<String> rhsParts, boolean bracketed)
	{
		String retVal = "";
		for(String lhs : lhsParts)
			retVal= retVal + lhs;
		retVal= retVal +"<mo>+</mo>";
		for(String rhs : rhsParts)
			retVal= retVal + rhs;
		if(bracketed)
			retVal = generateGroup(retVal);
		return retVal;
	}
	
	public String generateSubtraction(List<String> lhsParts,List<String> rhsParts, boolean bracketed)
	{
		String retVal = "";
		for(String lhs : lhsParts)
			retVal= retVal +lhs;
		retVal= retVal +"<mo>-</mo>";
		for(String rhs : rhsParts)
			retVal= retVal +rhs;
		if(bracketed)
			retVal = generateGroup(retVal);
		return retVal;
	}
	
	public String generateMultiplication(List<String> lhsParts,List<String> rhsParts, boolean bracketed)
	{
		String retVal = "";
		for(String lhs : lhsParts)
			retVal= retVal +lhs;
		retVal= retVal +"<mo>&#x2062;</mo>";
		for(String rhs : rhsParts)
			retVal= retVal +rhs;
		if(bracketed)
			retVal = generateGroup(retVal);
		return retVal;
	}
	
	public String generateDivision(List<String> lhsParts,List<String> rhsParts, boolean bracketed)
	{
		String retVal = "";
		retVal= retVal +"<mfrac>";
		retVal= retVal +"<mrow>";
		for(String lhs : lhsParts)
			retVal= retVal +lhs;
		retVal= retVal +"</mrow>";
		retVal= retVal +"<mrow>";
		for(String rhs : rhsParts)
			retVal= retVal +rhs;
		retVal= retVal +"</mrow>";
		retVal= retVal +"</mfrac>";
		
		return retVal;
	}
	
	public String generateExponentiation(List<String> lhsParts,List<String> rhsParts, boolean bracketed)
	{
		String retVal = "";
		retVal= retVal +"<msup>";
		retVal= retVal +"<mrow>";
		for(String lhs : lhsParts)
			retVal= retVal +lhs;
		retVal= retVal +"</mrow>";
		retVal= retVal +"<mrow>";
		for(String rhs : rhsParts)
			retVal= retVal +rhs;
		retVal= retVal +"</mrow>";
		retVal= retVal +"</msup>";
		if(bracketed)
			retVal = generateGroup(retVal);
		return retVal;
	}
	
	
	public String generateExpression(IAbstractSyntaxTreeNode node)
	{
		String lhs = "";
		if(node.getLhs() != null)
			lhs = generateFactor(node.getLhs());
		else
			lhs = generateFactor(node);
		String prefix = "<mrow>";
        //while()		
		String postfix = "</mrow>";
		return prefix + lhs + postfix;
	}
	
	public String generateEquality(List<String> lhsParts,List<String> rhsParts, boolean bracketed)
	{
		String retVal = "";
		for(String lhs : lhsParts)
			retVal= retVal +lhs;
		retVal= retVal +"<mo>=</mo>";
		for(String rhs : rhsParts)
			retVal= retVal +rhs;
		return retVal;
	}
	
	public String generateFactor(IAbstractSyntaxTreeNode node)
	{
		if(node.isBracketed())
			return generateGroup("");
		else
			return generateSymbol(node);
	}
	
	public String generateGroup(String grouped)
	{
		return "<mo>(</mo>"+ grouped + "<mo>)</mo>";
	}
	
	public String generateSymbol(IAbstractSyntaxTreeNode node)
	{
		String retVal = "";
		String symbol = node.getSymbol();
		if(symbol.matches("-?\\d+(\\.\\d+)?"))
		{
			retVal = "<mn>"+symbol+"</mn>";
		}
		else
			retVal = "<mi>"+symbol+"</mi>";
		if(node.isBracketed())
			retVal = generateGroup(retVal);
		return retVal;
	}
}
