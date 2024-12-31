package com.spring_ai.SpringAI_Backend;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class ChatService {

    @Value("${spring.ai.gemini.api-key}")
    private String apiKey;

    private final String URL = "https://generativelanguage.googleapis.com/v1beta2/models/gemini-1.5-flash:generateContent?key=%s";

    public String getResponse(String prompt) throws JSONException {
        String finalUrl = String.format(URL, apiKey);

        // Create request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("prompt", prompt);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build the request
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        try {
            // Make the API call
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(finalUrl, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Parse the response
                JSONObject jsonResponse = new JSONObject(response.getBody());
                return jsonResponse.getString("response"); // Adjust based on actual API response structure
            } else {
                return "Failed to get response: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }
}
