package de.dhbw.sms.services;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class InputParser {
	public IAbstractSyntaxTreeResult parseExperssion(String input)
	{
		IAbstractSyntaxTreeNode rootNode = parse(input);
		AbstractSyntaxTreeResult result = new AbstractSyntaxTreeResult();
		result.setParsed(rootNode != null);
		result.setRootNode(rootNode);
		return result;
	}
	
	static List<String> symbols = Arrays.asList("+", "-", "^", "=", "*", "/", "sin", "cos", "sqrt", "(", ")");
	
	private IAbstractSyntaxTreeNode parse(String input)
	{
		AbstractSyntaxTreeNode root = null;
		input = input.replace(" ", "");
		input = input.replace("\t", "");
		if(input.length() == 0)  return null;
		for(String symbol : symbols)
		{
			root = testForSimpleRelation(input, symbol);
			if(root != null) break;
		}
		if(root == null)
		{
			List<String> literals = Arrays.asList("\\+", "-", "\\^", "=", "\\*", "\\/", "sin", "cos", "sqrt", "\\(", "\\)");
			List<String> tokens = tokenizeInput(input, literals);
			root = parse(tokens);
		}
		
		return root;
	}
	
	private AbstractSyntaxTreeNode testForSimpleRelation(String input, String pattern)
	{
		AbstractSyntaxTreeNode root = null;
		if(input.startsWith(pattern) && input.length() == 1)
		{	
			root = new AbstractSyntaxTreeNode();
			root.setSymbol(pattern);
		}
		return root;
	}
	
	public List<String> tokenizeInput(String input, List<String> symbols)
	{
		List<String> baseList = Arrays.asList(input);
		
		for(String symbol : symbols)
		{
			baseList = tokenizeByPattern(baseList, symbol);
		}
		
		return baseList;
	}
	
	public List<String> tokenizeByPattern(List<String> input, String pattern)
	{
		List<String> output = new LinkedList(); 
		for(String part:input)
		{
			String[] subParts = part.split("((?<="+pattern+")|(?="+pattern+"))");
			for(String subPart: subParts)
			{
				output.add(subPart);
			}
			
		}
		return output;
	}
	
	public AbstractSyntaxTreeNode parse(List<String> tokens)
	{
		return parseExpression(tokens, null);
	}
	
	public AbstractSyntaxTreeNode parseExpression(List<String> tokens, AbstractSyntaxTreeNode parent)
	{
		AbstractSyntaxTreeNode lhsTerm = parseTerm(tokens, null);
		if(lhsTerm == null) return null;
		if(tokens.size() == 0) return lhsTerm;
		String nextToken = tokens.get(0);
		AbstractSyntaxTreeNode expression = null;
		while(nextToken.equals("+")||
				nextToken.equals("-")|| 
				nextToken.equals("="))
		{
			tokens.remove(0);
			AbstractSyntaxTreeNode rhsTerm = parseTerm(tokens, null);
			if(rhsTerm == null) return null;
			expression = new AbstractSyntaxTreeNode();
			expression.setSymbol(nextToken);
			expression.setLhs(lhsTerm);
			expression.setRhs(rhsTerm);
			lhsTerm = expression;
			if(tokens.size()==0) break;
			nextToken = tokens.get(0);
		}
		if(expression == null) return lhsTerm;
		return expression; 
	}
	
	public AbstractSyntaxTreeNode parseTerm(List<String> tokens, AbstractSyntaxTreeNode parent)
	{
		AbstractSyntaxTreeNode lhsFactor = parseExponentiation(tokens, null);
		if(lhsFactor == null) return null;
		if(tokens.size() == 0) return lhsFactor;
		String nextToken = tokens.get(0);
		AbstractSyntaxTreeNode term = null;
		while(nextToken.equals("*")||nextToken.equals("/"))
		{
			tokens.remove(0);
			AbstractSyntaxTreeNode rhsFactor = parseExponentiation(tokens, null);
			if(rhsFactor == null) return null;
			term = new AbstractSyntaxTreeNode();
			term.setSymbol(nextToken);
			term.setLhs(lhsFactor);
			term.setRhs(rhsFactor);
			lhsFactor = term;
			if(tokens.size()==0) break;
			nextToken = tokens.get(0);
		}
		if(term == null) return lhsFactor;
		return term; 
	}
	
	public AbstractSyntaxTreeNode parseExponentiation(List<String> tokens, AbstractSyntaxTreeNode parent)
	{
		AbstractSyntaxTreeNode lhsFactor = parseFactor(tokens, null);
		if(lhsFactor == null) return null;
		if(tokens.size() == 0) return lhsFactor;
		String nextToken = tokens.get(0);
		AbstractSyntaxTreeNode term = null;
		while(nextToken.equals("^"))
		{
			tokens.remove(0);
			AbstractSyntaxTreeNode rhsFactor = parseFactor(tokens, null);
			if(rhsFactor == null) return null;
			term = new AbstractSyntaxTreeNode();
			term.setSymbol(nextToken);
			term.setLhs(lhsFactor);
			term.setRhs(rhsFactor);
			lhsFactor = term;
			if(tokens.size()==0) break;
			nextToken = tokens.get(0);
		}
		if(term == null) return lhsFactor;
		return term; 
	}
	
	public AbstractSyntaxTreeNode parseFactor(List<String> tokens, AbstractSyntaxTreeNode parent)
	{
		AbstractSyntaxTreeNode literal = parseLiteral(tokens, null);
		if(literal != null) return literal;
		AbstractSyntaxTreeNode group = parseGroup(tokens, null);
		if(group != null) return group;
		return null;
	}
	
	public AbstractSyntaxTreeNode parseGroup(List<String> tokens, AbstractSyntaxTreeNode parent)
	{
		if(tokens.size() == 0) return null;
		String lhBracket = tokens.get(0);
		if(lhBracket.equals("("))
		{
			tokens.remove(0);
			AbstractSyntaxTreeNode expression = parseExpression(tokens, null);
			if(tokens.size() == 0) return null;
			String rhBracket = tokens.get(0);
			if(rhBracket.equals(")"))
			{
				tokens.remove(0);
				if(expression!= null)expression.setBracketed(true);
				return expression;
			}
		}
		return null;
	}
	
	public AbstractSyntaxTreeNode parseLiteral(	List<String> tokens, 
												AbstractSyntaxTreeNode parent)
	{
		if(tokens.size() == 0) return null;
		if(!IsSymbol(tokens.get(0)))
		{
			AbstractSyntaxTreeNode literal = new AbstractSyntaxTreeNode();
			literal.setSymbol(tokens.get(0));
			tokens.remove(0);
			return literal;
		}
		return null;
	}
	
	public boolean IsSymbol(String s)
	{
		for(String symbol : symbols)
		{
			if(symbol.equals(s)) return true;
		}
		return false;
	}
}
