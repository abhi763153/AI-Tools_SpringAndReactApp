package com.spring_ai.SpringAI_Backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//curl -X POST "https://api.elevenlabs.io/v1/text-to-speech/JBFqnCBsd6RMkjVDRZzb?output_format=mp3_44100_128" \
//        -H "xi-api-key: <apiKey>" \
//        -H "Content-Type: application/json" \
//        -d '{
//        "text": "The first move is what sets everything in motion.",
//        "model_id": "eleven_multilingual_v2"
//        }'

@Service
public class TextToSpeechService {

    private String API_URL = "https://api.elevenlabs.io/v1/text-to-speech/JBFqnCBsd6RMkjVDRZzb?output_format=mp3_44100_128";

    @Value("${eleven-lab.api-key}")
    private String API_KEY;

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<?> generateSpeech(String text) {

        String body = """
                {
                    "text": "%s",
                    "model_id": "eleven_multilingual_v2"
                 }
                """.formatted(text);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("xi-api-key", API_KEY);
        httpHeaders.set("Content-Type", "application/json");

        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<byte[]> response = restTemplate.exchange(API_URL, HttpMethod.POST, httpEntity, byte[].class);

        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null){
            HttpHeaders httpResHeader = new HttpHeaders();
            httpResHeader.set("Content-Type", "audio/mpeg");
            httpResHeader.set("Content-Disposition", "attachment; filename=\"speech.mp3\"");

            return new ResponseEntity<>(response.getBody(), httpResHeader, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
