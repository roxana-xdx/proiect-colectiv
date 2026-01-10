import { useState, useEffect } from "react";
import Navigation from "./Navigation.tsx";

import { teacherService } from "../services/teacherService.ts";
import { CreateTeacherRequest } from "../types/teacher.ts";


interface TeacherItem {
  id: number;
  name: string;
  email: string;
}

export default function Teachers() {
  const [teachers, setTeachers] = useState<TeacherItem[]>([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  

  useEffect(() => {
    fetchTeachers();
  }, []);

  const fetchTeachers = async () => {
    try {
      setIsLoading(true);
      const response = await teacherService.getAll();

      const mappedData: TeacherItem[] = response.data.map((dto: any) => ({
        id: dto.id,
        name: dto.name || "N/A",
        email: dto.email || "N/A",
        className: dto.className || "Unassigned"
      }));

      setTeachers(mappedData);
    } catch (error) {
      console.error("Error:", error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleAddTeacher = async (name: string, email: string, className: string) => {
    try {
      const payload: CreateTeacherRequest = { email };
      const response = await teacherService.create(payload);
      
      const newDTO = response.data as any;

      const newTeacherItem: TeacherItem = {
        id: newDTO.id,
        name: newDTO.name || name,
        email: newDTO.email || email,
      };

      setTeachers([...teachers, newTeacherItem]);
      setIsAddModalOpen(false);
      
    } catch (error) {
      console.error("Failed to create teacher:", error);
    }
  };

 const handleUpdateTeacher = async (updatedData: TeacherItem) => {
    try {

      const name = updatedData.name;

      const requestPayload: CreateTeacherRequest = {
        email: updatedData.email
      };

      await teacherService.update(updatedData.id, requestPayload);

      setTeachers((prevTeachers) =>
        prevTeachers.map((t) => (t.id === updatedData.id ? updatedData : t))
      );
    } catch (error) {
      console.error("Failed to update teachers:", error);
      alert("Failed to save changes.");
    }
  };

  const filteredTeachers = teachers.filter(
    (item) =>
      item.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      item.email.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const AddTeacherModal = ({ isOpen, onClose, onAdd }: any) => {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [className, setClassName] = useState("");

    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center z-50">
          <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md mx-4">
            <h3 className="text-3xl font-bold mb-6 text-gray-800">Add New Teacher</h3>
            <div className="space-y-4 mb-6">
              <input placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} className="w-full p-3 border rounded-lg" />
              <input placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} className="w-full p-3 border rounded-lg" />
              <input placeholder="Class" value={className} onChange={(e) => setClassName(e.target.value)} className="w-full p-3 border rounded-lg" />
            </div>
            <div className="flex justify-end space-x-4">
              <button onClick={onClose} className="px-6 py-2 border rounded-lg">Cancel</button>
              <button onClick={() => onAdd(name, email, className)} className="px-6 py-2 bg-[#9CB0C9] text-white rounded-lg">Create</button>
            </div>
          </div>
        </div>
    );
  };

  const EditButton = ({ initialData, onUpdate }: { initialData: TeacherItem, onUpdate: (data: TeacherItem) => void }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [formData, setFormData] = useState<TeacherItem>(initialData);

    return (
      <>
        <button onClick={() => setIsModalOpen(true)} className="px-6 py-3 rounded-xl bg-[#9CB0C9] text-white font-bold">
          Edit
        </button>
        {isModalOpen && (
          <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center z-50">
            <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md">
              <h3 className="text-2xl font-bold mb-4">Edit Teacher</h3>
              <div className="space-y-4 mb-6">
                <input value={formData.name} onChange={(e) => setFormData({...formData, name: e.target.value})} className="w-full p-3 border rounded-lg" />
                <input value={formData.email} onChange={(e) => setFormData({...formData, email: e.target.value})} className="w-full p-3 border rounded-lg" />

              </div>
              <div className="flex justify-end space-x-4">
                <button onClick={() => setIsModalOpen(false)} className="px-6 py-2 border rounded-lg">Cancel</button>
                <button onClick={() => { onUpdate(formData); setIsModalOpen(false); }} className="px-6 py-2 bg-[#9CB0C9] text-white rounded-lg">Save</button>
              </div>
            </div>
          </div>
        )}
      </>
    );
  };

  return (
    <div className="min-h-screen bg-[#F9F8F6]">
      <Navigation />
      <div className="max-w-[1440px] mx-auto px-4 py-8">
        <div className="bg-[#EFE9E3] rounded-[30px] p-6 md:p-12">
          
          <div className="flex justify-between items-center mb-12">
            <h1 className="text-[#665B4E] font-bold text-3xl">Teacher Directory</h1>
            <div className="flex gap-4">
              <button onClick={() => setIsAddModalOpen(true)} className="px-6 py-3 rounded-xl bg-[#9CB0C9] text-white font-bold">
                + Add Teacher
              </button>
              <input 
                placeholder="Search..." 
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="px-6 py-3 rounded-xl focus:outline-none"
              />
            </div>
          </div>

          <div className="grid grid-cols-6 gap-6 items-center border-b pb-4 mb-6 font-bold text-[#665B4E]">
            <div>ID</div>
            <div>Name</div>
            <div>Email</div>
            <div>Action</div>
          </div>

          {isLoading ? (
            <div className="text-center py-10">Loading...</div>
          ) : (
            <div className="space-y-4">
              {filteredTeachers.map((teacher) => (
                <div key={teacher.id} className="grid grid-cols-6 gap-6 items-center border-b pb-4 mb-6 font-bold text-[#665B4E]">
                  <div>{teacher.id}</div>
                  <div>{teacher.name}</div>
                  <div>{teacher.email}</div>
                  <div>
                    <EditButton initialData={teacher} onUpdate={handleUpdateTeacher} />
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      <AddTeacherModal 
        isOpen={isAddModalOpen} 
        onClose={() => setIsAddModalOpen(false)} 
        onAdd={handleAddTeacher} 
      />
    </div>
  );
}