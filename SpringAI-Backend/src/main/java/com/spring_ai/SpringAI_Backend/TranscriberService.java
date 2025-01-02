package com.spring_ai.SpringAI_Backend;
import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TranscriberService {
    // Make sure to add com.assemblyai:assemblyai-java to your dependencies

    @Value("${assemblyai.api.key}")
    private String api_key;

    public String getResponse() throws Exception{

        AssemblyAI client = AssemblyAI.builder()
                .apiKey(api_key)
                .build();

        var params = TranscriptOptionalParams.builder()
                .speakerLabels(true)
                .build();

        // You can use a local file:
        /*
        Transcript transcript = aai.transcripts().transcribe(
                new File("./example.mp3"), params);
        */

        // Or use a publicly-accessible URL:
        String audioUrl = "https://assembly.ai/sports_injuries.mp3";
        Transcript transcript = client.transcripts().transcribe(audioUrl, params);

        if (transcript.getStatus().equals(TranscriptStatus.ERROR)) {
            System.err.println(transcript.getError().get());
            System.exit(1);
        }

        System.out.println(transcript.getText().get());

        transcript.getUtterances().get().forEach(utterance ->
                System.out.println("Speaker " + utterance.getSpeaker() + ": " + utterance.getText())
        );


        return transcript.getText().orElse("No transcription available");
    }

}


