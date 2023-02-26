package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.request.BotRequest;
import com.app.model.response.ChatGptResponse;
import com.app.service.BotService;

@RestController
@RequestMapping("/api/v1/bot")

public class BotController {
	@Autowired
	private BotService botService;

	@PostMapping("/send")
	//@RequestMapping(value = "/send", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ChatGptResponse sendMessage(@RequestBody BotRequest botRequest) {
		return botService.askQuestion(botRequest);
	}
	@PostMapping("/sendQuestion")
	//@RequestMapping(value = "/send", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String sendQuestion(@RequestBody BotRequest botRequest) {
		return botService.ask(botRequest);
	}
}
