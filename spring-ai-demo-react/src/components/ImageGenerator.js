import React, { useState } from "react";

function ImageGenerator() {
    const [prompt, setPrompt] = useState('');
    const [imageUrl, setImageUrl] = useState(''); // Store image URL or Blob

    const generateImage = async () => {
        try {
            // Make the GET request and receive the image as a Blob
            const response = await fetch(`http://localhost:8080/generate-image?prompt=${prompt}`);

            // Check if the response is successful
            if (response.ok) {
                const imageBlob = await response.blob(); // Get the response as Blob
                const imageObjectUrl = URL.createObjectURL(imageBlob); // Create a URL for the Blob

                // Set the image URL to the state
                setImageUrl(imageObjectUrl);
            } else {
                console.error("Error generating image: ", response.status);
            }
        } catch (error) {
            console.error("Error generating image: ", error);
        }
    };

    return (
        <div className="tab-content">
            <h2>Generate Image</h2>
            <input
                type="text"
                value={prompt}
                onChange={(e) => setPrompt(e.target.value)}
                placeholder="Enter prompt for image"
            />
            <button onClick={generateImage}>Generate Image</button> 


            <div className="image-grid">
                {/* Conditionally render the image if available */}
                {imageUrl && <img src={imageUrl} alt="Generated Image" />}
            </div>
        </div>
    );
}

export default ImageGenerator;
