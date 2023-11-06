package com.example.chatgptbotapplication.controller;

import  com.example.chatgptbotapplication.dto.ChatGPTRequest;
import  com.example.chatgptbotapplication.dto.ChatGptResponse;
import  com.example.chatgptbotapplication.dto.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Autowired
    private RestTemplate template;
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @PostMapping("/chat")
    @ResponseBody
    public String chat(@RequestBody String prompt){
        ChatGPTRequest request=new ChatGPTRequest(model, prompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }
}
