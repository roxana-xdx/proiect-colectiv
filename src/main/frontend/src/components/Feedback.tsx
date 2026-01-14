import React, { useState, useEffect } from "react";
import Navigation from "./Navigation.tsx";
import { feedbackService } from "../services/feedbackService.ts";
import { CreateFeedbackRequest } from "../types/feedback.ts";

// look again at date handling ;; maybe i have forgotten something

interface FeedbackItem {
  id: number;
  teacherId: number;
  pupilId: number;
  subjectId: number;
  message: string;
  date: string;
  grade: number;
}

export default function Feedbacks() {
  const [feedbacks, setFeedbacks] = useState<FeedbackItem[]>([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);

  // FETCH DATA 
  useEffect(() => {
    fetchFeedbacks();
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
    } finally {
      setIsLoading(false);
    }
  };

  const handleAddFeedback = async (teacherId: number, pupilId: number, subjectId: number, message: string, date: string, grade: number) => {
    try {
      const payload: CreateFeedbackRequest = {
        teacherId: teacherId,
        pupilId: pupilId,
        subjectId: subjectId,
        message: message,
        // date: date 
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
      alert("Failed to create feedback. Check console for details.");
    }
  };

  /* // UPDATE LOGIC
  const handleUpdateFeedback = async (updatedData: FeedbackItem) => {
    try {
        const teacherIdNum = parseInt(updatedData.teacherId.toString()) || 0;
        const pupilIdNum = parseInt(updatedData.pupilId.toString()) || 0;
        const subjectIdNum = parseInt(updatedData.subjectId.toString()) || 0;

      const requestPayload: CreateFeedbackRequest = {
        teacherId: teacherIdNum,
        pupilId: pupilIdNum,
        subjectId: subjectIdNum,
        message: updatedData.message,
        // date: updatedData.date,
        grade: updatedData.grade
      };

      await feedbackService.update(updatedData.id, requestPayload);

      setFeedbacks((prevFeedbacks) =>
        prevFeedbacks.map((f) => (f.id === updatedData.id ? updatedData : f))
      );
    } catch (error) {
      console.error("Failed to update feedback:", error);
      alert("Failed to save changes.");
    }
  };
  */

  const filteredFeedbacks = feedbacks.filter(
    (feedbackItem) =>
        feedbackItem.id.toString().includes(searchQuery.toLowerCase()) ||
        feedbackItem.teacherId.toString().includes(searchQuery.toLowerCase()) ||
        feedbackItem.pupilId.toString().includes(searchQuery.toLowerCase()) ||
        feedbackItem.subjectId.toString().includes(searchQuery.toLowerCase()) ||
        feedbackItem.message.toLowerCase().includes(searchQuery.toLowerCase()) ||
        feedbackItem.date.toLowerCase().includes(searchQuery.toLowerCase()) ||
        feedbackItem.grade.toString().includes(searchQuery.toLowerCase())
  );

  interface AddFeedbackModalProps {
    isOpen: boolean;
    onClose: () => void;
    onAdd: (teacherId: number, pupilId: number, subjectId: number, message: string, date: string, grade: number) => void;
  }

  const AddFeedbackModal: React.FC<AddFeedbackModalProps> = ({ isOpen, onClose, onAdd }) => {
    const [teacherId, setTeacherId] = useState("");
    const [pupilId, setPupilId] = useState("");
    const [subjectId, setSubjectId] = useState("");
    const [message, setMessage] = useState("");
   // const [date, setDate] = useState("");
    const [grade, setGrade] = useState("");

    const handleSubmit = () => {
        if(!teacherId || !pupilId || !subjectId || !message || !grade) {
            alert("All fields are required");
            return;
        }

        const tId = parseInt(teacherId) || 0; 
        const pId = parseInt(pupilId) || 0;
        const sId = parseInt(subjectId) || 0;
        const g = parseInt(grade) || 0;
        
        onAdd(tId, pId, sId, message, date, g);
        
        // FIX: Removed duplicate calls
        setTeacherId("");
        setPupilId("");
        setSubjectId("");
        setMessage("");
      //  setDate("");
        setGrade("");
    };

    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center z-50">
          <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md mx-4">
            <h3 className="text-3xl font-bold mb-6 text-gray-800">Add New Feedback</h3>

            <div className="mb-4">
              <label className="block text-lg font-medium text-gray-700 mb-2">Teacher ID</label>
              <input
                type="number"
                value={teacherId}
                onChange={(e) => setTeacherId(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg"
              />
            </div>

            <div className="mb-4">
              <label className="block text-lg font-medium text-gray-700 mb-2">Pupil ID</label>
              <input
                type="number"
                value={pupilId}
                onChange={(e) => setPupilId(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg"
              />
            </div>      
            <div className="mb-4">
              <label className="block text-lg font-medium text-gray-700 mb-2">Subject ID</label>
              <input
                type="number"
                value={subjectId}
                onChange={(e) => setSubjectId(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg"
              />
            </div>
            <div className="mb-4">
              <label className="block text-lg font-medium text-gray-700 mb-2">Message</label>
              <input
                type="text"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg"
              />
            </div>
            {/* <div className="mb-4">
              <label className="block text-lg font-medium text-gray-700 mb-2">Date</label>
              <input
                type="date"
                value={date}
                onChange={(e) => setDate(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg"
              />
            </div> */}
            <div className="mb-6">
              <label className="block text-lg font-medium text-gray-700 mb-2">Grade</label>
              <input
                type="number"
                value={grade}
                onChange={(e) => setGrade(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg"
              />
            </div>     
            <div className="flex justify-end space-x-4">
              <button onClick={onClose} className="px-9 py-3 rounded-xl border-2 border-gray-300">Cancel</button>
              <button onClick={handleSubmit} className="px-9 py-3 rounded-[10px] bg-[#9CB0C9] text-white font-bold">
                Submit Feedback
              </button>
            </div>
          </div>
        </div>
    );
  };

  /*
  // EDIT COMPONENTS
  interface EditFeedbackModalProps {
    isOpen: boolean;
    onClose: () => void;
    feedbackData: FeedbackItem;
    onSave: (updatedData: FeedbackItem) => void;
  }

  interface EditButtonProps {
    initialFeedbackData: FeedbackItem;
    onUpdateFeedback: (updatedData: FeedbackItem) => void;
  }

  const EditButton: React.FC<EditButtonProps> = ({ initialFeedbackData, onUpdateFeedback }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);

    const handleSave = (updatedData: FeedbackItem) => {
      onUpdateFeedback(updatedData);
      setIsModalOpen(false);
    };

    const EditFeedbackModal: React.FC<EditFeedbackModalProps> = ({ isOpen, onClose, feedbackData, onSave }) => {
      const [formData, setFormData] = useState<FeedbackItem>(feedbackData);
      useEffect(() => { setFormData(feedbackData); }, [feedbackData]);

      const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({ ...prevData, [name]: value }));
      };

      if (!isOpen) return null;

      return (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center z-50">
          <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md mx-4">
            <h3 className="text-3xl font-bold mb-6 text-gray-800"> Edit Feedback</h3>
             <div className="mb-4">
              <label className="block text-lg font-medium text-gray-700 mb-2">Message</label>
              <input name="message" value={formData.message} onChange={handleChange} className="w-full p-3 border border-gray-300 rounded-lg" />
            </div>
            <div className="flex justify-end space-x-4">
              <button onClick={onClose} className="px-12 py-3 rounded-xl border-2 border-gray-300">Cancel</button>
              <button onClick={() => { onSave(formData); onClose(); }} className="px-6 py-3 rounded-[10px] bg-[#9CB0C9] text-white font-bold">Save Changes</button>
            </div>
          </div>
        </div>
      );
    };

    return (
      <>
        <button onClick={() => setIsModalOpen(true)} className="px-6 py-2 rounded-[20px] bg-[#9CB0C9] text-white font-bold">
          Edit
        </button>
        <EditFeedbackModal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} feedbackData={initialFeedbackData} onSave={handleSave} />
      </>
    );
  };
  */

  return (
    <div className="min-h-screen bg-[#F9F8F6]">
      <Navigation />

      <div className="max-w-[1440px] mx-auto px-4 py-8">
        <div className="bg-[#EFE9E3] rounded-[30px] p-6 md:p-12">
          
          <div className="flex justify-between items-center mb-12">
            <h1 className="text-[#665B4E] font-bold text-3xl">Feedbacks</h1>

            <div className="flex gap-4">
              <button 
                onClick={() => setIsAddModalOpen(true)} 
                className="px-6 py-3 rounded-xl bg-[#9CB0C9] text-white font-bold"
              >
                + Add Feedback
              </button>
              
              <input
                type="text"
                placeholder="Search..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="px-6 py-3 rounded-xl focus:outline-none"
              />
            </div>
          </div>

          {/* FIX: Header grid spans adjusted to match data */}
          <div className="grid grid-cols-8 gap-4 items-center border-b pb-4 mb-6 font-bold text-[#665B4E]">
            <div>ID</div>
            <div>Teacher</div>
            <div>Pupil</div>
            <div>Subject</div>
            <div className="col-span-2">Message</div>
            <div>Date</div>
            <div>Grade</div>
          </div>

          {isLoading ? (
            <div className="text-center py-10">Loading...</div>
          ) : (
            <div className="space-y-4">
              {filteredFeedbacks.length === 0 ? (
                <div className="text-center text-gray-500 py-4">No feedbacks found.</div>
              ) : (
                filteredFeedbacks.map((feedbackItem) => (
                  <div 
                    key={feedbackItem.id} 
                    className="grid grid-cols-8 gap-4 items-center border-b pb-4 mb-6 text-[#665B4E]"
                  >
                    <div>{feedbackItem.id}</div>
                    <div>{feedbackItem.teacherId}</div>
                    <div>{feedbackItem.pupilId}</div>
                    <div>{feedbackItem.subjectId}</div>
                    <div className="col-span-2">{feedbackItem.message}</div>
                    <div>{feedbackItem.date}</div>
                    <div>{feedbackItem.grade}</div>
                    {/* EDIT BUTTON
                      <div>
                        <EditButton initialFeedbackData={feedbackItem} onUpdateFeedback={handleUpdateFeedback} />
                      </div>
                    */}
                  </div>
                ))
              )}
            </div>
          )}
        </div>
      </div>

      <AddFeedbackModal 
        isOpen={isAddModalOpen} 
        onClose={() => setIsAddModalOpen(false)} 
        onAdd={handleAddFeedback} 
      />
    </div>
  );
}