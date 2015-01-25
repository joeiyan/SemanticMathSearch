package de.dhbw.sms.services;

public interface IAbstractSyntaxTreeResult {
	
	IAbstractSyntaxTreeNode getAbstractSyntaxTree();
	
	boolean hasParsed();

}
