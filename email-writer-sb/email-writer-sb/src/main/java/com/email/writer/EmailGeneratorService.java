package com.email.writer;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmailGeneratorService {
     
    private final ChatClient chatClient;

    public EmailGeneratorService(@Qualifier("openAiChatClient")ChatClient chatClientBuilder) {
        this.chatClient = chatClientBuilder;
    }

    public String generateEmailReply(EmailRequest emailRequest) {

        String prompt = buildPrompt(emailRequest);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    private String buildPrompt(EmailRequest emailRequest) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("Generate a professional email reply for the following email.\n\n");

        if (emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()) {
            prompt.append("Use a ")
                  .append(emailRequest.getTone())
                  .append(" tone.\n\n");
        }

        prompt.append("Original Email:\n");
        prompt.append(emailRequest.getEmailContent());

        return prompt.toString();
    }
}