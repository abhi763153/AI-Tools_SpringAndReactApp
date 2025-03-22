import React, { useState } from "react";

function ChatComponent() {
    const [prompt, setPrompt] = useState('');
    const [texURL, setTexURL] = useState('');

    const askAI = async () => {
        try {
            const response = await fetch(`/api/text-to-speech?text=${encodeURIComponent(prompt)}`);
            if (!response.ok) throw new Error("Failed to fetch audio");

            const blob = await response.blob();
            const audioUrl = URL.createObjectURL(blob);
            setTexURL(audioUrl);
        } catch (error) {
            console.error("Error generating audio file : ", error)
        }
    };

    return (
        <div>
            <h2>Text to Speech</h2>
            <input
                type="text"
                value={prompt}
                onChange={(e) => setPrompt(e.target.value)}
                placeholder="Enter a prompt for AI"
            />
            <button onClick={askAI}>Generate Audio</button>
            <div className="output">
                {texURL && (
                    <audio controls>
                        <source src={texURL} type="audio/mpeg" />
                        Your browser does not support the audio element.
                    </audio>
                )}
            </div>
        </div>
    );
}

export default ChatComponent;