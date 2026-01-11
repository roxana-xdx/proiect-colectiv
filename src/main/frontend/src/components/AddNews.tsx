import React, { useState } from 'react';
import { Bold, Italic, Type, AlignLeft } from 'lucide-react';
import {  useNavigate } from "react-router-dom";
import { classAnnouncementService } from '../services/classAnnouncementService.ts';
import { CreateClassAnnouncementRequest } from '../types/classAnnouncement';

export default function App() {
const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [adminId, setAdminId] = useState(''); 
  const [classId, setClassId] = useState(''); 
  const [isSubmitting, setIsSubmitting] = useState(false);
  const navigate = useNavigate();
const handleGoBack = () => {
    navigate("/news");
  };

const handlePost = async () => {

    if (!adminId || !classId || !content.trim()) {
      alert("Please fill in the Admin ID, Class ID, and Content.");
      return;
    }

    try {
      setIsSubmitting(true);

      const payload: CreateClassAnnouncementRequest = {
        adminId: Number(adminId),
        classId: Number(classId),
        // Combining title and content into the message field
        message: title ? `Title: ${title}\n\n${content}` : content,
        date: new Date(),
      };

      await classAnnouncementService.create(payload);
      
      navigate("/news");
    } catch (error) {
      console.error("Failed to post news:", error);
      alert("Error posting announcement. Check the console for details.");
    } finally {
      setIsSubmitting(false);
    }
  };
  
  return (
    <div className="min-h-screen bg-[#e8dfd0] p-8">
      <div className="max-w-4xl mx-auto">
        <div className="flex items-center justify-between mb-6">
          <button className="text-[#6b5744] flex items-center gap-2 hover:text-[#4a3d2f] transition-colors" onClick={handleGoBack}>
            &lt; Back to Dashboard
          </button>
          {/* <div className="flex gap-3"> */}
            {/* <button className="px-5 py-2 bg-[#a89178] text-[#f5f0e8] rounded hover:bg-[#9a8168] transition-colors">
              Save as Draft
            </button> */}
            {/* <button className="px-5 py-2 bg-[#8b7260] text-[#f5f0e8] rounded hover:bg-[#7a6250] transition-colors">
              Start Notes
            </button> */}
           <button 
            onClick={handlePost}
            disabled={isSubmitting}
            className={`px-8 py-2 bg-[#8b7260] text-[#f5f0e8] rounded-full font-bold hover:bg-[#7a6250] transition-colors shadow-md ${
              isSubmitting ? "opacity-50 cursor-not-allowed" : ""
            }`}
          >
            {isSubmitting ? "Posting..." : "Post"}
          </button>
          {/* </div> */}
        </div>
<div className="bg-[#f5f0e8] rounded-lg shadow-lg p-8">
          
          {/* Inputs  */}
          <div className="grid grid-cols-2 gap-4 mb-6">
            <div>
              <label className="block text-xs font-bold text-[#6b5744] uppercase mb-1">Admin ID</label>
              <input
                type="number"
                placeholder="Enter your ID"
                value={adminId}
                onChange={(e) => setAdminId(e.target.value)}
                className="w-full p-2 bg-[#e8dfd0] rounded border-none outline-none text-[#4a3d2f]"
              />
            </div>
            <div>
              <label className="block text-xs font-bold text-[#6b5744] uppercase mb-1">Class ID</label>
              <input
                type="number"
                placeholder="Target Class ID"
                value={classId}
                onChange={(e) => setClassId(e.target.value)}
                className="w-full p-2 bg-[#e8dfd0] rounded border-none outline-none text-[#4a3d2f]"
              />
            </div>
          </div>

        <hr className="border-[#d4c4b0] mb-6" />

          {/* Toolbar */}
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
          
          {/* News Content */}
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