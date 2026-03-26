package com.ai.learning.demo.service.serviceInterface;

import com.ai.learning.demo.model.UserProfile;

public interface ChatService {
  String getResponse(String prompt, UserProfile userProfile);
}
