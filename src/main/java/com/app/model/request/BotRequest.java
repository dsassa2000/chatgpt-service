package com.app.model.request;

import java.io.Serializable;


public class BotRequest implements Serializable {
    private String question;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
    
    
}



