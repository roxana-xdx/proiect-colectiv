import { useState, useEffect } from "react";
import Navigation from "./Navigation.tsx"; 

import { subjectService } from "../services/subjectService.ts";
import { CreateSubjectRequest } from "../types/subject.ts";

interface SubjectItem {
  id: number;
  subjectName: string;
}

export default function Subjects() {
  const [subjects, setSubjects] = useState<SubjectItem[]>([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);

  //  FETCH DATA 
  useEffect(() => {
    fetchSubjects();
  }, []);

  const fetchSubjects = async () => {
    try {
      setIsLoading(true);
      const response = await subjectService.getAll();

      const mappedData: SubjectItem[] = response.data.map((dto) => ({
        id: dto.id,
         subjectName: dto.name,
      }));

      setSubjects(mappedData);
    } catch (error) {
      console.error("Error fetching subjects:", error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleAddSubject = async (subjectName: string, subjectId: number) => {
    try {
      const payload: CreateSubjectRequest = {
        name: subjectName,
        id: subjectId
      };

      const response = await subjectService.create(payload);
      const newSubjectDTO = response.data;
      const newSubjectItem: SubjectItem = {
        id: newSubjectDTO.id,
        subjectName: newSubjectDTO.name,
      }; 

      setSubjects([...subjects, newSubjectItem]);
      setIsAddModalOpen(false);
      
    } catch (error) {
      console.error("Failed to create subject:", error);
      alert("Failed to create subject. Check console for details.");
    }
  };

  // UPDATE 
  const handleUpdateSubject = async (updatedData: SubjectItem) => {
    try {
      const requestPayload: CreateSubjectRequest = {
        name: updatedData.subjectName,
        id: updatedData.id
      };

      await subjectService.update(updatedData.id, requestPayload);

      setSubjects((prevSubjects) =>
        prevSubjects.map((s) => (s.id === updatedData.id ? updatedData : s))
      );
    } catch (error) {
      console.error("Failed to update subject:", error);
      alert("Failed to save changes.");
    }
  };

  const filteredSubjects = subjects.filter(
    (subjectItem) =>
      subjectItem.subjectName.toLowerCase().includes(searchQuery.toLowerCase())
  );

  interface AddSubjectModalProps {
    isOpen: boolean;
    onClose: () => void;
    onAdd: (subjectName: string, subjectId: number) => void;
  }

  const AddSubjectModal: React.FC<AddSubjectModalProps> = ({ isOpen, onClose, onAdd }) => {
    const [name, setName] = useState("");
    const [subjectId, setSubjectId] = useState("");

    const handleSubmit = () => {
        if(!name) {
            alert("Subject name is required");
            return;
        }

        const tId = parseInt(subjectId) || 0; 
        onAdd(name, tId);
        setName("");
        setSubjectId("");
    };

    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center z-50">
          <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md mx-4">
            <h3 className="text-3xl font-bold mb-6 text-gray-800">Add New Subject</h3>

            <div className="mb-4">
              <label className="block text-lg font-medium text-gray-700 mb-2">Subject Name</label>
              <input
                type="text"
                value={subjectName}
                onChange={(e) => setName(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg text-lg focus:ring-blue-500 focus:border-blue-500"
                placeholder="e.g. 10B Math"
              />
            </div>
            <div className="flex justify-end space-x-4">
              <button onClick={onClose} className="px-9 py-3 rounded-xl min-w-[100px] md:min-w-[150px] h-[60px] text-lg font-semibold border-2 border-gray-300 text-gray-700 hover:bg-gray-100">Cancel</button>
              <button onClick={handleSubmit} className="px-9 py-3 rounded-[10px] min-w-[100px] md:min-w-[130px] h-[60px] flex items-center justify-center text-2xl font-bold border-none tracking-[0.03em] leading-[117.504%] bg-[#9CB0C9] text-white hover:bg-[#8BA0B8] transition-colors">
                Create Subject
              </button>
            </div>
          </div>
        </div>
    );
  };

  interface EditSubjectModalProps {
    isOpen: boolean;
    onClose: () => void;
    subjectData: SubjectItem;
    onSave: (updatedData: SubjectItem) => void;
  }

  interface EditButtonProps {
    initialSubjectData: SubjectItem;
    onUpdateSubject: (updatedData: SubjectItem) => void;
  }

  const EditButton: React.FC<EditButtonProps> = ({ initialSubjectData, onUpdateSubject }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);

    const handleSave = (updatedData: SubjectItem) => {
      onUpdateSubject(updatedData);
      setIsModalOpen(false);
    };

    const EditSubjectModal: React.FC<EditSubjectModalProps> = ({ isOpen, onClose, subjectData, onSave }) => {
      const [formData, setFormData] = useState<SubjectItem>(subjectData);

      useEffect(() => { setFormData(subjectData); }, [subjectData]);

      const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({ ...prevData, [name]: value }));
      };

      if (!isOpen) return null;

      return (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center z-50">
          <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md mx-4">
            <h3 className="text-3xl font-bold mb-6 text-gray-800"> Edit Subject</h3>
            <div className="mb-4">
              <label className="block text-lg font-medium text-gray-700 mb-2">Subject Name</label>
              <input name="subjectName" value={formData.subjectName} onChange={handleChange} className="w-full p-3 border border-gray-300 rounded-lg text-lg focus:ring-blue-500 focus:border-blue-500" />
            </div>
            <div className="flex justify-end space-x-4">
              <button onClick={onClose} className="px-12 py-3 rounded-xl min-w-[100px] md:min-w-[130px] h-[60px] text-lg font-semibold border-2 border-gray-300 text-gray-700 hover:bg-gray-100">Cancel</button>
              <button onClick={() => { onSave(formData); onClose(); }} className="px-6 py-3 rounded-[10px] min-w-[100px] md:min-w-[130px] h-[60px] flex items-center justify-center text-2xl font-bold border-none tracking-[0.03em] leading-[117.504%] bg-[#9CB0C9] text-white hover:bg-[#8BA0B8] transition-colors">Save Changes</button>
            </div>
          </div>
        </div>
      );
    };

    return (
      <>
        <button onClick={() => setIsModalOpen(true)} className="px-6 py-5 rounded-[20px] min-w-[140px] md:min-w-[170px] h-[70px] flex items-center justify-center text-2xl font-bold tracking-[0.03em] leading-[117.504%] bg-[#9CB0C9] text-white hover:bg-[#8BA0B8] transition-colors">
          Edit
        </button>
        <EditSubjectModal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} subjectData={initialSubjectData} onSave={handleSave} />
      </>
    );
  };

  return (
    <div className="min-h-screen bg-[#F9F8F6]">
      <Navigation />

      <div className="max-w-[1440px] mx-auto px-4 py-8">
        <div className="bg-[#EFE9E3] rounded-[30px] p-6 md:p-12">
          
          <div className="flex justify-between items-center mb-12">
            <h1 className="text-[#665B4E] font-bold text-3xl">
              Subjects - Sorted a - z
            </h1>

            <div className="flex gap-4">
              <button 
                onClick={() => setIsAddModalOpen(true)} 
                className="px-6 py-3 rounded-xl bg-[#9CB0C9] text-white font-bold"
              >
                + Add Subject
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

          <div className="grid grid-cols-6 gap-6 items-center border-b pb-4 mb-6 font-bold text-[#665B4E]">
            <div>ID</div>
            <div>Subject Name</div>
          </div>

          {isLoading ? (
            <div className="text-center py-10">Loading...</div>
          ) : (
            <div className="space-y-4">
              {filteredSubjects.length === 0 ? (
                <div className="text-center text-gray-500 py-4">No subjects found.</div>
              ) : (
                filteredSubjects.map((subjectItem) => (
                  <div 
                    key={subjectItem.id} 
                    className="grid grid-cols-6 gap-6 items-center border-b pb-4 mb-6 font-bold text-[#665B4E]"
                  >
                    <div>{subjectItem.id}</div>
                    <div>{subjectItem.subjectName}</div>
                    <div>
                      <EditButton initialSubjectData={subjectItem} onUpdateSubject={handleUpdateSubject} />
                    </div>
                  </div>
                ))
              )}
            </div>
          )}
        </div>
      </div>

      <AddSubjectModal 
        isOpen={isAddModalOpen} 
        onClose={() => setIsAddModalOpen(false)} 
        onAdd={handleAddSubject} 
      />
    </div>
  );
}