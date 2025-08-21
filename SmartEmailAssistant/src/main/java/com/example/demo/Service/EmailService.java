package com.example.demo.Service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.Model.EmailRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmailService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;
    
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public EmailService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String generateEmailReply(EmailRequest er) {

        // Build the prompt
        String prompt = buildPrompt(er);

        // Craft request body
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt)
                        })
                }
        );

        // Make API call
        String res = webClient.post()
        		.uri(geminiApiUrl)
 // Base URL already configured in WebClientConfig
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Extract response
        return extractResponseContent(res);
    }

    private String extractResponseContent(String res) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(res);
            return rootNode.path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText();
        } catch (Exception e) {
        	e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    private String buildPrompt(EmailRequest er) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate a professional email reply. Please don't generate a subject line.");
        if (er.getTone() != null && !er.getTone().isEmpty()) {
            prompt.append(" Use a ").append(er.getTone()).append(" tone.");
        }
        prompt.append("\nOriginal email:\n").append(er.getEmailContent());
        return prompt.toString();
    }
}
