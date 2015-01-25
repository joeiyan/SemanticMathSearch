package de.dhbw.sms;

import java.io.Serializable;

public class OutputExpression implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	private String output;

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
}