import React, { useState } from 'react';
import './App.css';
import ImageGenerator from './components/ImageGenerator';
import ChatComponent from './components/ChatComponent';
import Transcriber from './components/Transcriber';
import TextToSpeech from './components/TextToSpeech.js';

function App() {
  const [activeTab, setActiveTab] = useState('image-generator');

  const handleTabChange = (tab) => {
    //alert(tab)
    setActiveTab(tab);
  };

  return (
    <div className="App">
      <button className={activeTab === 'image-generator' ? 'active' : ''}
       onClick={() => handleTabChange('image-generator')}>
        Image Generator
        </button>
      <button  className={activeTab === 'chat' ? 'active' : ''}
      onClick={() => handleTabChange('chat')}>
        Ask AI
        </button>

      <button  className={activeTab === 'transcriber' ? 'active' : ''}
      onClick={() => handleTabChange('transcriber')}>
        Transcriber
        </button>
      
      <button  className={activeTab === 'TextToSpeech' ? 'active' : ''}
      onClick={() => handleTabChange('TextToSpeech')}>
        Text To Speech
        </button>
      

        <div>
          {activeTab === 'image-generator' && <ImageGenerator/>}
          {activeTab === 'chat' && <ChatComponent/>}
          {activeTab === 'transcriber' && <Transcriber/>}
          {activeTab === 'TextToSpeech' && <TextToSpeech/>}
        </div>
    </div>
  );
}

export default App;
