package com.spring_ai.SpringAI_Backend;

import com.assemblyai.api.resources.transcripts.types.Transcript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestControllerAI {

    @Autowired
    private ChatService chatService;

    @Autowired
    ImageService imageService;

    @Autowired
    TranscriberService transcriberService;

    @GetMapping("/ask-ai") // Matches the endpoint "/ask-ai"
    public String getResponse(@RequestParam("prompt") String prompt) {
        return chatService.getResponse(prompt);
    }


    @GetMapping("/generate-image")
    public ResponseEntity<InputStreamResource> generateImage(@RequestParam("prompt") String prompt) {
        // Call the image service and return the image URL
        System.out.println(imageService.getImageUrl(prompt));
        return imageService.getImageUrl(prompt);
    }

    @GetMapping("/transcriber") // Matches the endpoint "/ask-ai"
    public String getResponse() throws Exception {
        return transcriberService.getResponse();
    }
}
