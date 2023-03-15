package com.app.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.config.ChatGptConfig;
import com.app.model.request.BotRequest;
import com.app.model.request.ChatGptRequest;
import com.app.model.response.ChatGptResponse;

@Service
public class BotServiceImpl implements BotService {

    private static RestTemplate restTemplate = new RestTemplate();

    //    Build headers
    public HttpEntity<ChatGptRequest> buildHttpEntity(ChatGptRequest chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
        return new HttpEntity<>(chatRequest, headers);
    }

    //    Generate response
    public ChatGptResponse getResponse(HttpEntity<ChatGptRequest> chatRequestHttpEntity) {
        ResponseEntity<ChatGptResponse> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.URL,
                chatRequestHttpEntity,
                ChatGptResponse.class);

        return responseEntity.getBody();
    }

    public ChatGptResponse askQuestion(BotRequest botRequest) {
    	ChatGptResponse response =  this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequest(
                                ChatGptConfig.MODEL,
                                botRequest.getQuestion(),
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TOP_P)));
    	return response;
    }
    // return just answer
    public ChatGptResponse ask(String botRequest) {
    	ChatGptResponse resp = this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequest(
                                ChatGptConfig.MODEL,
                                botRequest,
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TOP_P)));
    	
    	String answer = resp.getChoices().get(0).getText();
    	try {
			appendDataToCsvFile(botRequest, answer, "C:\\Users\\HP\\Desktop\\Classeur1.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return resp;
    }
    public void appendDataToCsvFile(String question,String answer, String filepath) throws IOException{
        File file = new File(filepath);
        boolean fileExists = file.exists();
        FileWriter fileWriter = new FileWriter(file, true);
        String header = "Question;Answer";
        if(fileExists) {
        	fileWriter.append(header);
        	fileWriter.append("\n");
        }
        fileWriter.append(question+";"+answer);    
        fileWriter.flush();
        fileWriter.close();
    }
}






