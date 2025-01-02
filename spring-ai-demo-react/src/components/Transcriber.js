import React, {useState} from 'react'

function Transcriber() {
  return (
    <div>
        <h2>Transcriber</h2>
        <label htmlFor="audioFile">Choose an audio file : </label>
        <input type="file" id="audioFile" name="audioFile" accept="audio/*" />
        
    </div>
  )
}

export default Transcriber