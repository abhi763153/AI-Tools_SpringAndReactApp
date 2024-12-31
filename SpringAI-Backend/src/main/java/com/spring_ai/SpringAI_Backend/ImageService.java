package com.spring_ai.SpringAI_Backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class ImageService {

    @Value("${image.api.url}")
    private String imageApiUrl;

    public ResponseEntity<InputStreamResource> getImageUrl(String prompt) {
        try {
            // Encode the prompt to handle special characters
            String encodedPrompt = URLEncoder.encode(prompt, StandardCharsets.UTF_8);
            String url = String.format(imageApiUrl, encodedPrompt);

            // Fetch the image as a byte array
            RestTemplate restTemplate = new RestTemplate();
            byte[] imageBytes = restTemplate.getForObject(url, byte[].class);

            if (imageBytes == null || imageBytes.length == 0) {
                throw new RuntimeException("No image data received from the API");
            }

            // Convert the byte array into an InputStream
            InputStream inputStream = new ByteArrayInputStream(imageBytes);

            // Return the response with the appropriate Content-Type for display
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Change to IMAGE_PNG or other types if needed
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            // Log the error (if applicable) and return an error response
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}

