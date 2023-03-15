package com.app.service;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.config.ChatGptConfig;
import com.app.model.request.BotRequest;
import com.app.model.request.ChatGptRequest;
import com.app.model.response.ChatGptResponse;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.ParseException;

public class ChatgptAgent extends Agent {

	 private ACLMessage message;
	 @Autowired
	 private BotService botService;
	 
	 public ChatgptAgent(ACLMessage message) {
	      this.message = message;
	  }
   
   protected void setup() {
       send(message);
    // Initialize botService here
       botService = new BotServiceImpl();
       // Define the behaviour
       CyclicBehaviour loop = new CyclicBehaviour(this) {
         private static final long serialVersionUID = 1L;
        
         @Override
         public void action() {
           ACLMessage aclMsg = receive();

           // Interpret the message
           if (aclMsg != null) {
        	   System.out.println(aclMsg.getContent());
           }

           block(); // Stop the behaviour until next message is received
         }
       };
       addBehaviour(loop);
     }
}
