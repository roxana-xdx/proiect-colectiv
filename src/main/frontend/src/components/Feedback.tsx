import React, { useState, useEffect } from "react";
import Navigation from "./Navigation.tsx";
import { feedbackService } from "../services/feedbackService.ts";
import { CreateFeedbackRequest } from "../types/feedback.ts";
import { UserType } from "../types/user.ts";

interface FeedbackItem {
  id: number;
  teacherId: number;
  pupilId: number;
  subjectId: number;
  message: string;
  date: string;
  grade: number;
}
interface UserItem {
  email: string;
  name: string;
  type: UserType;
}
export default function Feedbacks() {
  const [feedbacks, setFeedbacks] = useState<FeedbackItem[]>([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [user, setUser] = useState<UserItem | null>(null);

  useEffect(() => {
    fetchFeedbacks();
    fetchUser();
  }, []);

  const fetchFeedbacks = async () => {
    try {
      setIsLoading(true);
      const response = await feedbackService.getAll();
      const mappedData: FeedbackItem[] = response.data.map((dto: any) => ({
        id: dto.id,
        teacherId: dto.teacherId,
        pupilId: dto.pupilId,
        subjectId: dto.subjectId,
        message: dto.message,
        date: dto.date,
        grade: dto.grade,
      }));
      setFeedbacks(mappedData);
    } catch (error) {
      console.error("Error fetching feedbacks:", error);
      alert("Error when getting the feedbacks - check console");
    } finally {
      setIsLoading(false);
    }
  };

  const fetchUser = () => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  };

  const isAdmin = user?.type === "ADMIN" || "TEACHER";

  const handleAddFeedback = async (teacherId: number, pupilId: number, subjectId: number, message: string, grade: number) => {
    try {
      const payload: CreateFeedbackRequest = {
        teacherId: teacherId,
        pupilId: pupilId,
        subjectId: subjectId,
        message: message,
        grade: grade
      };
      const response = await feedbackService.create(payload);
      const newFeedbackDTO = response.data;
      const newFeedbackItem: FeedbackItem = {
        id: newFeedbackDTO.id,
        teacherId: newFeedbackDTO.teacherId,
        pupilId: newFeedbackDTO.pupilId,
        subjectId: newFeedbackDTO.subjectId,
        message: newFeedbackDTO.message,
        date: newFeedbackDTO.date,
        grade: newFeedbackDTO.grade
      }; 
      setFeedbacks([...feedbacks, newFeedbackItem]);
      setIsAddModalOpen(false);
    } catch (error) {
      console.error("Failed to create feedback:", error);
      alert("Error while creating feedback - check console");
    }
  };

  const handleUpdateFeedback = async (updatedData: FeedbackItem) => {
    try {
      const requestPayload: CreateFeedbackRequest = {
        teacherId: parseInt(updatedData.teacherId.toString()),
        pupilId: parseInt(updatedData.pupilId.toString()),
        subjectId: parseInt(updatedData.subjectId.toString()),
        message: updatedData.message,
        grade: updatedData.grade
      };
      await feedbackService.update(updatedData.id, requestPayload);
      setFeedbacks((prev) => prev.map((f) => (f.id === updatedData.id ? updatedData : f)));
    } catch (error) {
      console.error("Failed to update feedback:", error);
      alert("Failed to save changes.");
    }
  };
  
  const handleDelete = async (id: number) => {
    try {
      await feedbackService.delete(id);
      setFeedbacks((prev) => prev.filter((f) => f.id !== id));
    } catch (error) {
      console.error("Delete failed:", error);
    }
  };

  const filteredFeedbacks = feedbacks.filter((f) =>
    f.message.toLowerCase().includes(searchQuery.toLowerCase()) ||
    f.id.toString().includes(searchQuery)
  );

  const gridLayout = {
    display: "grid",
    gridTemplateColumns: "50px 80px 80px 80px 1fr 120px 70px 100px 100px",
    gap: "1rem",
    alignItems: "center"
  };

  const AddFeedbackModal = ({ isOpen, onClose, onAdd }: any) => {
    const [tId, setTId] = useState("");
    const [pId, setPId] = useState("");
    const [sId, setSId] = useState("");
    const [msg, setMsg] = useState("");
    const [grd, setGrd] = useState("");

    if (!isOpen) return null;
    return (
      <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center z-50">
        <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md mx-4">
          <h3 className="text-3xl font-bold mb-6 text-gray-800">Add New Feedback</h3>
          <input type="number" placeholder="Teacher ID" className="w-full p-3 border mb-4 rounded-lg" onChange={(e)=>setTId(e.target.value)} />
          <input type="number" placeholder="Pupil ID" className="w-full p-3 border mb-4 rounded-lg" onChange={(e)=>setPId(e.target.value)} />
          <input type="number" placeholder="Subject ID" className="w-full p-3 border mb-4 rounded-lg" onChange={(e)=>setSId(e.target.value)} />
          <input type="text" placeholder="Message" className="w-full p-3 border mb-4 rounded-lg" onChange={(e)=>setMsg(e.target.value)} />
          <input type="number" placeholder="Grade" className="w-full p-3 border mb-6 rounded-lg" onChange={(e)=>setGrd(e.target.value)} />
          <div className="flex justify-end space-x-4">
            <button onClick={onClose} className="px-9 py-3 rounded-xl border-2 border-gray-300">Cancel</button>
            <button onClick={() => onAdd(Number(tId), Number(pId), Number(sId), msg, Number(grd))} className="px-9 py-3 rounded-[10px] bg-[#9CB0C9] text-white font-bold">Submit</button>
          </div>
        </div>
      </div>
    );
  };

  const EditButton = ({ initialFeedbackData, onUpdateFeedback }: any) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [msg, setMsg] = useState(initialFeedbackData.message);
    if (!isModalOpen) return <button onClick={() => setIsModalOpen(true)} className="px-6 py-2 rounded-[20px] bg-[#9CB0C9] text-white font-bold">Edit</button>;
    return (
      <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center z-50">
        <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md mx-4">
          <h3 className="text-3xl font-bold mb-6 text-gray-800">Edit Feedback</h3>
          <input value={msg} onChange={(e) => setMsg(e.target.value)} className="w-full p-3 border mb-6 rounded-lg" />
          <div className="flex justify-end space-x-4">
            <button onClick={() => setIsModalOpen(false)} className="px-12 py-3 rounded-xl border-2 border-gray-300">Cancel</button>
            <button onClick={() => { onUpdateFeedback({ ...initialFeedbackData, message: msg }); setIsModalOpen(false); }} className="px-6 py-3 rounded-[10px] bg-[#9CB0C9] text-white font-bold">Save</button>
          </div>
        </div>
      </div>
    );
  };

  return (
    <div className="min-h-screen bg-[#F9F8F6]">
      <Navigation />
      <div className="max-w-[1440px] mx-auto px-4 py-8">
        <div className="bg-[#EFE9E3] rounded-[30px] p-6 md:p-12">
          <div className="flex justify-between items-center mb-12">
            <h1 className="text-[#665B4E] font-bold text-3xl">Feedbacks</h1>
            <div className="flex gap-4">
              <button onClick={() => setIsAddModalOpen(true)} className="px-6 py-3 rounded-xl bg-[#9CB0C9] text-white font-bold">+ Add Feedback</button>
              <input type="text" placeholder="Search..." value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)} className="px-6 py-3 rounded-xl focus:outline-none" />
            </div>
          </div>

          <div style={gridLayout} className="border-b pb-4 mb-6 font-bold text-[#665B4E]">
            <div>ID</div>
            <div>Teacher</div>
            <div>Pupil</div>
            <div>Subject</div>
            <div>Message</div>
            <div>Date</div>
            <div>Grade</div>
            <div>Edit</div>
            <div>Delete</div>
          </div>

          {isLoading ? (
            <div className="text-center py-10">Loading...</div>
          ) : (
            <div className="space-y-4">
              {filteredFeedbacks.length === 0 ? (
                <div className="text-center text-gray-500 py-4">No feedbacks found.</div>
              ) : (
                filteredFeedbacks.map((item) => (
                  <div key={item.id} style={gridLayout} className="border-b pb-4 mb-6 text-[#665B4E]">
                    <div>{item.id}</div>
                    <div>{item.teacherId}</div>
                    <div>{item.pupilId}</div>
                    <div>{item.subjectId}</div>
                    <div className="truncate pr-4" title={item.message}>{item.message}</div>
                    <div className="text-sm">{item.date.slice(0, 10)}</div>
                    <div className="font-bold">{item.grade}</div>
                    <div><EditButton initialFeedbackData={item} onUpdateFeedback={handleUpdateFeedback} /></div>
                    <div>
                      {isAdmin && (
                        <button onClick={() => handleDelete(item.id)} className="bg-[#9CB0C9] text-white px-4 py-2 rounded text-sm font-bold">
                          Delete
                        </button>
                      )}
                    </div>
                  </div>
                ))
              )}
            </div>
          )}
        </div>
      </div>
      <AddFeedbackModal isOpen={isAddModalOpen} onClose={() => setIsAddModalOpen(false)} onAdd={handleAddFeedback} />
    </div>
  );
}