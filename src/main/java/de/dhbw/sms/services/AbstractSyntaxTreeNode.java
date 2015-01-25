package de.dhbw.sms.services;

import java.util.List;

public class AbstractSyntaxTreeNode implements IAbstractSyntaxTreeNode {
	
	String symbol;
	
	AbstractSyntaxTreeNode rhs;
	
	boolean bracketed;
	
	@Override
	public boolean isBracketed() {
		return bracketed;
	}


	public void setBracketed(boolean bracketed) {
		this.bracketed = bracketed;
	}


	public void setRhs(AbstractSyntaxTreeNode rhs) {
		this.rhs = rhs;
	}


	public void setLhs(AbstractSyntaxTreeNode lhs) {
		this.lhs = lhs;
	}

	AbstractSyntaxTreeNode lhs;
	
	public AbstractSyntaxTreeNode()
	{
		this.symbol = "";
		this.rhs = null;
		this.lhs = null;
		this.bracketed = false;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	
	@Override
	public IAbstractSyntaxTreeNode getLhs()
	{
		return this.lhs;
	}
	
	@Override
	public IAbstractSyntaxTreeNode getRhs()
	{
		return this.rhs;
	}
	
	@Override
	public void rotate()
	{
		AbstractSyntaxTreeNode tempLhs = lhs;
		lhs = rhs;
		rhs = tempLhs;
		return;
	}

	@Override
	public String getSymbol() {
		return this.symbol;
	}

}
