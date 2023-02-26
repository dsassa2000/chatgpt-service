package com.app.model.response;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


public class ChatGptResponse implements Serializable {
    private String id;
    private String object;
    private String model;
    private LocalDate created;
    private List<Choice> choices;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public LocalDate getCreated() {
		return created;
	}
	public void setCreated(LocalDate created) {
		this.created = created;
	}
	public List<Choice> getChoices() {
		return choices;
	}
	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}
	public ChatGptResponse(String id, String object, String model, LocalDate created, List<Choice> choices) {
		super();
		this.id = id;
		this.object = object;
		this.model = model;
		this.created = created;
		this.choices = choices;
	}
	public ChatGptResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}




