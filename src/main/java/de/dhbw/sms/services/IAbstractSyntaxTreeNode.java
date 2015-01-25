package de.dhbw.sms.services;

import java.util.List;

public interface IAbstractSyntaxTreeNode {
	IAbstractSyntaxTreeNode getLhs();
	
	IAbstractSyntaxTreeNode getRhs();
	
	boolean isBracketed() ;
	
	void rotate();
	
	String getSymbol();
}
