import React, { useState } from "react";

function Transcriber() {
  const [transcript, setTranscript] = useState("");
  const [file, setFile] = useState(null);
  const [loading, setLoading] = useState(false); // Loading state

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const getResponse = async () => {
    if (!file) {
      alert("Please select an audio file before transcribing.");
      return; // Prevent further execution
    }
    const maxSize = 20 * 1024 * 1024; // 20 MB in bytes
    if (file.size > maxSize) {
        alert("File size exceeds the maximum limit of 20MB.");
        return false;
    }

    const formData = new FormData(); // Correct capitalization
    formData.append("audioFile", file);

    try {
      setLoading(true); // Start loading
      const response = await fetch("/api/transcriber", {
        method: "POST",
        body: formData,
      });

      if (!response.ok) {
        throw new Error("Failed to transcribe the audio file.");
      }

      const data = await response.text();
      setTranscript(data);
    } catch (error) {
      console.error(error);
      alert(`An error occurred: ${error.message}`);
    } finally {
      setLoading(false); // Stop loading
    }
  };

  return (
    <div>
      <h2>Transcriber</h2>
      <label htmlFor="audioFile">Choose an audio file:</label>
      <input
        type="file"
        id="audioFile"
        name="audioFile"
        accept="audio/*"
        onChange={handleFileChange}
      />

      <button onClick={getResponse} disabled={loading}>
        {loading ? "Transcribing..." : "Transcribe"}
      </button>

      <h3>Transcript:</h3>
      <p>{transcript || "No transcript available yet."}</p>
    </div>
  );
}

export default Transcriber;
