package de.dhbw.sms;

import java.io.Serializable;
import java.util.List;

public class OutputExpression implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	private String output;
	
	private String literals;
	
	private List<String> literalList;

	public List<String> getLiteralList() {
		return literalList;
	}

	public void setLiteralList(List<String> literalList) {
		this.literalList = literalList;
	}

	public String getOutput() {
		return output;
	}
	
	public void setOutput(String output) {
		this.output = output;
	}

	public String getLiterals() {
		return literals;
	}

	public void setLiterals(String literals) {
		this.literals = literals;
	}
}