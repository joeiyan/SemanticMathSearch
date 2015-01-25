package de.dhbw.sms.services;

public class AbstractSyntaxTreeResult implements IAbstractSyntaxTreeResult {
	
	boolean parsed;
	
	IAbstractSyntaxTreeNode rootNode;

	public IAbstractSyntaxTreeNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(IAbstractSyntaxTreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public boolean isParsed() {
		return parsed;
	}

	public void setParsed(boolean parsed) {
		this.parsed = parsed;
	}
	
	public AbstractSyntaxTreeResult()
	{
		this.parsed = false;
		this.rootNode = null;
	}

	@Override
	public IAbstractSyntaxTreeNode getAbstractSyntaxTree() {
		// TODO Auto-generated method stub
		return this.getRootNode();
	}

	@Override
	public boolean hasParsed() {
		return isParsed();
	}

}
