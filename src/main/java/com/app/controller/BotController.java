package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.request.BotRequest;
import com.app.model.response.ChatGptResponse;
import com.app.service.BotService;
import com.app.service.ChatgptAgent;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

@RestController
@RequestMapping("/api/v1/bot")
@CrossOrigin(origins = "*")
public class BotController {
	@Autowired
	private BotService botService;

	@PostMapping("/send")
	//@RequestMapping(value = "/send", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ChatGptResponse sendMessage(@RequestBody BotRequest botRequest) {
		return botService.askQuestion(botRequest);
	}
    @PostMapping("/start")
    public ChatGptResponse start(@RequestBody BotRequest questionRequest) throws StaleProxyException{
        // Start JADE platform
    	Runtime runtime = jade.core.Runtime.instance();
        runtime.setCloseVM(true);

        Profile profile = new ProfileImpl("127.0.0.1", 1099, null);
        profile.setParameter(Profile.GUI, "true");

        AgentContainer mainContainer = runtime.createMainContainer(profile);

        ACLMessage messageToChatgptAgent = new ACLMessage(ACLMessage.INFORM);
        messageToChatgptAgent.setContent(questionRequest.getQuestion());
        messageToChatgptAgent.addReceiver(new AID("ChatgptAgent", AID.ISLOCALNAME));
        mainContainer.acceptNewAgent("ChatgptAgent", new ChatgptAgent(messageToChatgptAgent)).start();
        	
        return  botService.ask(messageToChatgptAgent.getContent());
      

    }
}
