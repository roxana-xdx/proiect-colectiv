import React, { useState } from 'react';
import { Bold, Italic, Type, ChevronDown, AlignLeft, Image } from 'lucide-react';
import { Link, useNavigate } from "react-router-dom";

export default function App() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const navigate = useNavigate();
const handleGoBack = () => {
    navigate("/news");
  };
  return (
    <div className="min-h-screen bg-[#e8dfd0] p-8">
      <div className="max-w-4xl mx-auto">
        {/* Header */}
        <div className="flex items-center justify-between mb-6">
          <button className="text-[#6b5744] flex items-center gap-2 hover:text-[#4a3d2f] transition-colors" onClick={handleGoBack}>
            &lt; Back to Dashboard
          </button>
          <div className="flex gap-3">
            <button className="px-5 py-2 bg-[#a89178] text-[#f5f0e8] rounded hover:bg-[#9a8168] transition-colors">
              Save as Draft
            </button>
            <button className="px-5 py-2 bg-[#8b7260] text-[#f5f0e8] rounded hover:bg-[#7a6250] transition-colors">
              Start Notes
            </button>
          </div>
        </div>

        {/* Editor Card */}
        <div className="bg-[#f5f0e8] rounded-lg shadow-lg p-8">
          {/* Toolbar */}
          <div className="flex items-center gap-4 mb-6 pb-4 border-b border-[#d4c4b0]">
            <button className="p-2 hover:bg-[#e8dfd0] rounded transition-colors text-[#6b5744]">
              <Bold size={18} />
            </button>
            <button className="p-2 hover:bg-[#e8dfd0] rounded transition-colors text-[#6b5744]">
              <Italic size={18} />
            </button>
            <button className="p-2 hover:bg-[#e8dfd0] rounded transition-colors text-[#6b5744]">
              <Type size={18} />
            </button>
            <button className="p-2 hover:bg-[#e8dfd0] rounded transition-colors text-[#6b5744]">
              <ChevronDown size={18} />
            </button>
            
            <div className="w-px h-6 bg-[#d4c4b0]"></div>
            
            <button className="p-2 hover:bg-[#e8dfd0] rounded transition-colors text-[#6b5744]">
              <AlignLeft size={18} />
            </button>
            <button className="px-3 py-1 bg-[#e8dfd0] rounded text-[#6b5744] hover:bg-[#d4c4b0] transition-colors">
              Manage
            </button>
            <button className="px-3 py-1 hover:bg-[#e8dfd0] rounded text-[#6b5744] transition-colors">
              Tags
            </button>
          </div>

          {/* Title Input */}
          <input
            type="text"
            placeholder="Add Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="w-full text-2xl mb-6 bg-transparent border-none outline-none placeholder-[#b5a692] text-[#4a3d2f]"
          />

          {/* Content Textarea */}
          <textarea
            placeholder="Add Text"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            className="w-full h-64 bg-transparent border-none outline-none resize-none placeholder-[#b5a692] text-[#6b5744]"
          />
        </div>
      </div>
    </div>
  );
}