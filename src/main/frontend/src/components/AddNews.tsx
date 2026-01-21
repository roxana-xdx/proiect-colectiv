import React, { useState } from 'react';
import { Bold, Italic, Type, AlignLeft } from 'lucide-react';
import {  useNavigate } from "react-router-dom";
import { classAnnouncementService } from '../services/classAnnouncementService.ts';
import { CreateClassAnnouncementRequest } from '../types/classAnnouncement';

export default function App() {
const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [admin_id, setAdminId] = useState(''); 
  const [class_id, setClassId] = useState(''); 
  const [isSubmitting, setIsSubmitting] = useState(false);
  const navigate = useNavigate();
const handleGoBack = () => {
    navigate("/news");
  };

const handlePost = async () => {

    if (!admin_id || !class_id || !content.trim()) {
      alert("Fill in the Admin ID, Class ID, and content.");
      return;
    }

    try {
      setIsSubmitting(true);

      const payload: CreateClassAnnouncementRequest = {
        admin_id: Number(admin_id),
        class_id: Number(class_id),

        message: title ? `${title} | ${content}` : content,
        date: new Date().toISOString().slice(0, 10)
      };

      await classAnnouncementService.create(payload);
      
      navigate("/news");
    } catch (error) {
      console.error("Failed to post news:", error);
      alert("Error while posting - check console");
    } finally {
      setIsSubmitting(false);
    }
  };
  
  return (
    <div className="min-h-screen bg-[#e8dfd0] p-8">
      <div className="max-w-4xl mx-auto">
        <div className="flex items-center justify-between mb-6">
          <button className="text-[#6b5744] flex items-center gap-2 hover:text-[#4a3d2f] transition-colors" onClick={handleGoBack}>
            &lt; Home
          </button>
           <button 
            onClick={handlePost}
            disabled={isSubmitting}
            className={`px-8 py-2 bg-[#8b7260] text-[#f5f0e8] rounded-full font-bold hover:bg-[#7a6250] transition-colors shadow-md ${
              isSubmitting ? "opacity-50 cursor-not-allowed" : ""
            }`}
          >
            {isSubmitting ? "Posting..." : "Post"}
          </button>
        </div>
<div className="bg-[#f5f0e8] rounded-lg shadow-lg p-8">
          
          {/* input fields */}
          <div className="grid grid-cols-2 gap-4 mb-6">
            <div>
              <label className="block text-xs font-bold text-[#6b5744] uppercase mb-1">Admin ID</label>
              <input
                type="number"
                placeholder="Enter your ID"
                value={admin_id}
                onChange={(e) => setAdminId(e.target.value)}
                className="w-full p-2 bg-[#e8dfd0] rounded border-none outline-none text-[#4a3d2f]"
              />
            </div>
            <div>
              <label className="block text-xs font-bold text-[#6b5744] uppercase mb-1">Class ID</label>
              <input
                type="number"
                placeholder="Target Class ID"
                value={class_id}
                onChange={(e) => setClassId(e.target.value)}
                className="w-full p-2 bg-[#e8dfd0] rounded border-none outline-none text-[#4a3d2f]"
              />
            </div>
          </div>

        <hr className="border-[#d4c4b0] mb-6" />

          {/* fancy toolbar that may or may not do stuff */}
          <div className="flex items-center gap-4 mb-6 pb-4 border-b border-[#d4c4b0]">
            <button className="p-2 hover:bg-[#e8dfd0] rounded text-[#6b5744]"><Bold size={18} /></button>
            <button className="p-2 hover:bg-[#e8dfd0] rounded text-[#6b5744]"><Italic size={18} /></button>
            <button className="p-2 hover:bg-[#e8dfd0] rounded text-[#6b5744]"><Type size={18} /></button>
            <div className="w-px h-6 bg-[#d4c4b0]"></div>
            <button className="p-2 hover:bg-[#e8dfd0] rounded text-[#6b5744]"><AlignLeft size={18} /></button>
          </div>

          <input
            type="text"
            placeholder="Add Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="w-full text-2xl mb-6 bg-transparent border-none outline-none placeholder-[#b5a692] text-[#4a3d2f] font-bold"
          />
          
          <textarea
            placeholder="Add Text"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            className="w-full h-64 bg-transparent border-none outline-none resize-none placeholder-[#b5a692] text-[#6b5744] text-lg"
          />
        </div>
      </div>
    </div>
  );
}