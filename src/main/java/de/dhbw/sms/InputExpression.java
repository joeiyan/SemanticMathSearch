package de.dhbw.sms;

import java.io.Serializable;

public class InputExpression implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	private String input;

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
}
