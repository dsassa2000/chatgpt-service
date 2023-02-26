package com.app.service;

import com.app.config.ChatGptConfig;
import com.app.model.request.BotRequest;
import com.app.model.request.ChatGptRequest;
import com.app.model.response.ChatGptResponse;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    	String answer = response.getChoices().get(0).getText();
    	String question = botRequest.getQuestion();
    	String [] rowData = {question,answer};
    	appendToCsv("C:\\Users\\HP\\Desktop\\Classeur1.csv",rowData);
    	return response;
    }
    // return just answer
    public String ask(BotRequest botRequest) {
    	ChatGptResponse resp = this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequest(
                                ChatGptConfig.MODEL,
                                botRequest.getQuestion(),
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TOP_P)));
    	
    	String answer = resp.getChoices().get(0).getText();
    	String question = botRequest.getQuestion();
    	String [] rowData = {question,answer};
    	appendToCsv("C:\\Users\\HP\\Desktop\\Classeur1.csv",rowData);
    	
    	return answer;
    }
    // write response to csv file
    public void appendToCsv(String filename, String[] rowData) {
        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            String[] header = {"Question", "Answer"};
            csvWriter.writeNext(header);
            fileWriter.append(String.join(";", rowData));
            fileWriter.append("\n");
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






