import { useState, useEffect } from "react";
import Navigation from "./Navigation.tsx";

// Service and Types
import { scheduleService } from "../services/scheduleService.ts";
import { subjectService } from "../services/subjectService.ts";
import { ScheduleDTO, CreateScheduleRequest } from "../types/schedule.ts";
import { UserType } from "../types/user.ts";

interface SubjectItem {
  id: number;
  name: string;
}

export default function Schedules() {
  const [schedules, setSchedules] = useState<ScheduleDTO[]>([]);
  const [subjects, setSubjects] = useState<SubjectItem[]>([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [user, setUser] = useState<{ email: string; type: UserType } | null>(null);

  useEffect(() => {
    const initializePage = async () => {
      setIsLoading(true);
      await Promise.all([fetchSchedules(), fetchSubjects()]);
      setIsLoading(false);
    };

    initializePage();

    const storedUser = localStorage.getItem("user");
    if (storedUser) setUser(JSON.parse(storedUser));
  }, []);

  const isAdmin = user?.type === "ADMIN";

  const fetchSchedules = async () => {
    try {
      const response = await scheduleService.getAll();
      setSchedules(response.data);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const fetchSubjects = async () => {
    try {
      const response = await subjectService.getAll();
      setSubjects(response.data);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const getSubjectName = (id: number) => {
    const subject = subjects.find((s) => s.id === id);
    return subject ? subject.name : `ID: ${id}`;
  };

  const handleAddSchedule = async (newSchedule: CreateScheduleRequest) => {
    try {
      const response = await scheduleService.create(newSchedule);
      setSchedules((prev) => [...prev, response.data]);
      setIsAddModalOpen(false);
    } catch (error) {
      console.error("Failed to create schedule:", error);
      alert("Error adding schedule.");
    }
  };

  const handleDelete = async (id: number) => {
    try {
      await scheduleService.delete(id);
      setSchedules((prev) => prev.filter((s) => s.id !== id));
    } catch (error) {
      console.error("Delete failed:", error);
    }
  };

  const filteredSchedules = schedules.filter(
    (s) => s.date.includes(searchQuery) || s.id.toString().includes(searchQuery)
  );

  return (
    <div className="min-h-screen bg-[#F9F8F6]">
      <Navigation />
      <div className="max-w-[1440px] mx-auto px-4 py-8">
        <div className="bg-[#EFE9E3] rounded-[30px] p-6 md:p-12">
          
          <div className="flex justify-between items-center mb-12">
            <h1 className="text-[#665B4E] font-bold text-3xl">Schedule Table</h1>
            <div className="flex gap-4">
              {isAdmin && (
                <button 
                  onClick={() => setIsAddModalOpen(true)} 
                  className="px-6 py-3 rounded-xl bg-[#9CB0C9] text-white font-bold hover:bg-[#8BA0B8] transition-colors"
                >
                  + Schedule a class
                </button>
              )}
              <input 
                placeholder="Search date..." 
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="px-6 py-3 rounded-xl focus:outline-none border border-gray-200"
              />
            </div>
          </div>

          {/* Header Row */}
          <div className="grid grid-cols-8 gap-4 items-center border-b pb-4 mb-6 font-bold text-[#665B4E]">
            <div>ID</div>
            <div>Teacher ID</div>
            <div>Subject</div>
            <div>Class ID</div>
            <div>Date</div>
            <div>Start</div>
            <div>End</div>
            {isAdmin && <div>Actions</div>}
         {/* { isAdmin&& <div> Delete</div>} */}
</div>
          {isLoading ? (
            <div className="text-center py-10 text-[#665B4E]">Loading...</div>
          ) : (
            <div className="space-y-4">
              {filteredSchedules.map((s) => (
                <div key={s.id} className="grid grid-cols-8 gap-4 items-center border-b pb-4 mb-6 text-[#665B4E]">
                  <div className="font-mono text-sm">#{s.id}</div>
                  <div>{s.teacher_id}</div>
                  <div className="font-semibold text-[#665B4E]">
                    {getSubjectName(s.subject_id)}
                  </div>
                  <div>{s.class_id}</div>
                  <div>{s.date}</div>
                  <div>{s.start_hour}</div>
                  <div>{s.end_hour}</div>
                  <div>
                    {isAdmin && (
                      <button 
                        onClick={() => handleDelete(s.id)} 
                        className="bg-[#9CB0C9] text-white px-4 py-2 rounded text-xl font-bold mb-4"
                      >
                        Delete
                      </button>
                    )}
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      <AddScheduleModal 
        isOpen={isAddModalOpen} 
        onClose={() => setIsAddModalOpen(false)} 
        onAdd={handleAddSchedule}
        subjects={subjects}
      />
    </div>
  );
}

interface AddModalProps {
  isOpen: boolean;
  onClose: () => void;
  onAdd: (data: CreateScheduleRequest) => void;
  subjects: SubjectItem[];
}

function AddScheduleModal({ isOpen, onClose, onAdd, subjects }: AddModalProps) {
  const [formData, setFormData] = useState({
    teacher_id: "",
    subject_id: "",
    class_id: "",
    date: "",
    start_hour: "",
    end_hour: ""
  });

  if (!isOpen) return null;

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onAdd({
      teacher_id: Number(formData.teacher_id),
      subject_id: Number(formData.subject_id),
      class_id: Number(formData.class_id),
      date: formData.date,
      start_hour: formData.start_hour,
      end_hour: formData.end_hour
    });
    setFormData({ teacher_id: "", subject_id: "", class_id: "", date: "", start_hour: "", end_hour: "" });
  };

  return (
    <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div className="bg-white rounded-2xl p-8 w-full max-w-md mx-4">
        <h2 className="text-2xl font-bold mb-6 text-[#665B4E]">Schedule New Class</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input 
            placeholder="Teacher ID" required type="number" 
            className="w-full border p-3 rounded-xl outline-none focus:ring-2 focus:ring-[#9CB0C9]" 
            value={formData.teacher_id} onChange={e => setFormData({...formData, teacher_id: e.target.value})} 
          />
          <select 
            required className="w-full border p-3 rounded-xl outline-none focus:ring-2 focus:ring-[#9CB0C9]"
            value={formData.subject_id} onChange={e => setFormData({...formData, subject_id: e.target.value})}
          >
            <option value="">Select Subject</option>
            {subjects.map(sub => (
              <option key={sub.id} value={sub.id}>{sub.name}</option>
            ))}
          </select>
          <input 
            placeholder="Class ID" required type="number" 
            className="w-full border p-3 rounded-xl outline-none focus:ring-2 focus:ring-[#9CB0C9]" 
            value={formData.class_id} onChange={e => setFormData({...formData, class_id: e.target.value})} 
          />
          <input 
            required type="date" 
            className="w-full border p-3 rounded-xl outline-none focus:ring-2 focus:ring-[#9CB0C9]" 
            value={formData.date} onChange={e => setFormData({...formData, date: e.target.value})} 
          />
          <div className="grid grid-cols-2 gap-4">
            <input 
              required type="time" placeholder="Start"
              className="border p-3 rounded-xl outline-none focus:ring-2 focus:ring-[#9CB0C9]" 
              value={formData.start_hour} onChange={e => setFormData({...formData, start_hour: e.target.value})} 
            />
            <input 
              required type="time" placeholder="End"
              className="border p-3 rounded-xl outline-none focus:ring-2 focus:ring-[#9CB0C9]" 
              value={formData.end_hour} onChange={e => setFormData({...formData, end_hour: e.target.value})} 
            />
          </div>
          <div className="flex justify-end gap-3 mt-6">
            <button type="button" onClick={onClose} className="px-6 py-2 text-gray-500 font-semibold">Cancel</button>
            <button type="submit" className="px-6 py-2 bg-[#9CB0C9] text-white rounded-xl font-bold shadow-md hover:bg-[#8BA0B8]">Save</button>
          </div>
        </form>
      </div>
    </div>
  );
}