package de.dhbw.sms.services;

import java.util.LinkedList;
import java.util.List;
//import java.util.function.Predicate;

public class LiteralHelper {
	public static List<String> getLiterals(IAbstractSyntaxTreeNode node)
	{
		List<String> retList = new LinkedList<String>();
		traverseTree(retList, node);
		return retList;
	}
	
	public static List<String> getNonNumericLiterals(IAbstractSyntaxTreeNode node)
	{
		List<String> retList = new LinkedList<String>();
		List<String> literals = new LinkedList<String>();
		traverseTree(literals, node);
		for(String p : literals)
		{
			if(!p.matches("-?\\d+(\\.\\d+)?"))
			{
				retList.add(p);
			}
		}
		return retList;
	}
	
	public static void traverseTree(List<String> symbols, 
			IAbstractSyntaxTreeNode node)
	{
		if(node.getLhs() == null && node.getRhs() == null)
		{
			if(!symbols.contains(node.getSymbol()))
				symbols.add(node.getSymbol());
			return;
		}
		if(node.getLhs() != null)
			traverseTree(symbols, node.getLhs());
		if(node.getRhs() != null)
			traverseTree(symbols, node.getRhs());
		return;
	}

}
