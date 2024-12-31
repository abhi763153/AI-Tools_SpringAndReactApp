package com.spring_ai.SpringAI_Backend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONObject;
@Service
public class ChatService {

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String apiKey;

    public String getResponse(String prompt) {
        String finalUrl = String.format("%s?key=%s", apiUrl, apiKey);

        // Build the JSON payload
        String requestBody = """
            { 
              "contents": [{
                "parts":[{"text": "%s"}]
              }]
            }
            """.formatted(prompt);

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Log the payload for debugging
        System.out.println("Final URL: " + finalUrl);
        System.out.println("Request Body: " + requestBody);

        // Create HTTP entity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Send the POST request
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(finalUrl, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject root = new JSONObject(response.getBody());
                String text = root.getJSONArray("candidates")
                        .getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text");
                return text;
            } else {
                return "Error: HTTP Status " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }
}
