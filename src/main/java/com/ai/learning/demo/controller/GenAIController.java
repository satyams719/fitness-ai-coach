package com.ai.learning.demo.controller;

import com.ai.learning.demo.model.UserProfile;
import com.ai.learning.demo.service.serviceInterface.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenAIController {

  ChatService chatService;

  public GenAIController(ChatService chatService){
    this.chatService = chatService;
  }

  @GetMapping("/ask-ai")
  public String getResponse(@RequestParam String prompt, @RequestBody UserProfile userProfile){
    return chatService.getResponse(prompt, userProfile);
  }

}
