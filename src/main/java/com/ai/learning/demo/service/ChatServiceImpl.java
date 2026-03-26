package com.ai.learning.demo.service;

import static com.ai.learning.demo.utils.MessageConstants.message;
import static reactor.netty.http.HttpConnectionLiveness.log;

import com.ai.learning.demo.model.UserProfile;
import com.ai.learning.demo.service.serviceInterface.ChatService;
import java.util.List;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

  private final ChatModel chatModel;

  public ChatServiceImpl(ChatModel chatModel) {
    this.chatModel = chatModel;
  }

  public String getResponse(String userPrompt, UserProfile userProfile) {
    try {
      Prompt prompt = new Prompt(List.of(new SystemMessage(message), new UserMessage(buildUserContext(userProfile, userPrompt))),
          OpenAiChatOptions.builder().model("gpt-4o-mini").temperature(0.3).maxTokens(300).build());
      ChatResponse response = chatModel.call(prompt);
      return response.getResult().getOutput().getText();

    } catch (Exception e) {
      log.error("Error calling OpenAI API", e);
      return "Something went wrong. Please try again.";
    }
  }

  private String buildUserContext(UserProfile user, String question) {
    return String.format("""
              User Profile:
            - Age: %d
            - Weight: %d kg
            - Height: %s
            - Goal: %s
            - Workout Routine: %s
            - Diet Preference: %s
            - Budget: ₹%d/month
            
            User Question:
            %s
            """, user.getAge(), user.getWeight(), user.getHeight(), user.getGoal(),
        user.getWorkoutRoutine(), user.getDietPreference(), user.getBudget(), question);
  }

}
