import { useState, useEffect } from "react";
import Navigation from "./Navigation.tsx";

// Service and Types
import { pupilService } from "../services/pupilService.ts";
import { CreatePupilRequest, UpdatePupilRequest } from "../types/pupil.ts";

// This interface is for our Frontend UI state
interface PupilItem {
  id: number;
  email: string;
  name: string;
  class_id: number;
  parent_id: number;
}

export default function Students() {
  const [pupils, setpupils] = useState<PupilItem[]>([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);

  // --- FETCH DATA ---
  useEffect(() => {
    fetchpupils();
  }, []);

  const fetchpupils = async () => {
    try {
      setIsLoading(true);
      const response = await pupilService.getAll();

      const mappedData: PupilItem[] = response.data.map((dto) => ({
        id: dto.id,
        name: dto.name || "N/A",
        email: dto.email || "N/A",
        class_id: dto.class_id || -1,
        parent_id: dto.parent_id || -1
      }));

      setpupils(mappedData);
    } catch (error) {
      console.error("Error:", error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleAddpupil = async (email:string, name:string, class_id: number, parent_id: number) => {
    try {
      const payload: CreatePupilRequest = { 
        email : email, 
        class_id: class_id, 
        parent_id: parent_id
      };

      const response = await pupilService.create(payload);
      
      const newDTO = response.data;

      const newPupilItem: PupilItem = {
        id: newDTO.id,
        email: newDTO.email || email,
        name: newDTO.name || name,
        class_id: newDTO.class_id || 1,
        parent_id: newDTO.parent_id || 1
      };

      setpupils([...pupils, newPupilItem]);
      setIsAddModalOpen(false);
      
    } catch (error) {
      console.error("Failed to create pupil:", error);
    }
  };

  const handleUpdatepupil = async (updatedData: PupilItem) => {
    try {
      const requestPayload: UpdatePupilRequest = {
        email: updatedData.email
      } as any;

      await pupilService.update(updatedData.id, requestPayload);

      setpupils((prev) =>
        prev.map((t) => (t.id === updatedData.id ? updatedData : t))
      );
    } catch (error) {
      console.error("Failed to update pupil:", error);
      alert("Failed to save changes.");
    }
  };

  const filteredpupils = pupils.filter(
    (item) =>
      item.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      item.email.toLowerCase().includes(searchQuery.toLowerCase())
  );

  // interface EditPupilModalProps {
  //   isOpen: boolean;
  //   onClose: () => void;
  //   pupilData: PupilItem;
  //   onSave: (updatedData: PupilItem) => void;
  // }

  // interface EditButtonProps {
  //   initialPupilData: PupilItem;
  //   onUpdatePupil: (updatedData: PupilItem) => void;
  // }

  interface AddPupilModalProps {
    isOpen: boolean;
    onClose: () => void;
    onAdd: (email: string, name: string, class_id: number, parent_id: number) => void;
  }


  const AddPupilModal: React.FC<AddPupilModalProps> = ({ isOpen, onClose, onAdd }) => {
    const [email, setEmail] = useState("");
    const [name, setName] = useState("");
    const [class_id, setClassID] = useState("");
    const [parent_id, setParentID] = useState("");

        const handleSubmit = () => {
        if(!name || !email) {
            alert("All fields are required");
            return;
        }

        // convert string input to number for backend
        const cID = parseInt(class_id) || -1; 
        const pID = parseInt(parent_id) || -1;
        onAdd(email, name, cID, pID);

        // reset form
        setEmail("");
        setName("");
        setClassID("");
        setParentID("");
    };

    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center z-50">
          <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md mx-4">
            <h3 className="text-3xl font-bold mb-6 text-gray-800">Add new pupil</h3>

            <div className="mb-4">
              <label className="block text-lg font-medium text-gray-700 mb-2">Pupil Email</label>
              <input
                type="text"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg text-lg focus:ring-blue-500 focus:border-blue-500"
                placeholder="pupil@email.com"
              />
            </div>

            <div className="mb-6">
              <label className="block text-lg font-medium text-gray-700 mb-2">Pupil Name</label>
              <input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg text-lg focus:ring-blue-500 focus:border-blue-500"
                placeholder="Legal Name"
              />
            </div>  

            <div className="mb-8">
              <label className="block text-lg font-medium text-gray-700 mb-2">Class ID</label>
              <input
                type="number"
                value={class_id}
                onChange={(e) => setClassID(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg text-lg focus:ring-blue-500 focus:border-blue-500"
                placeholder="3"
              />
            </div>     

            <div className="mb-10">
              <label className="block text-lg font-medium text-gray-700 mb-2">Parent ID</label>
              <input
                type="number"
                value={parent_id}
                onChange={(e) => setParentID(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg text-lg focus:ring-blue-500 focus:border-blue-500"
                placeholder="4"
              />
            </div> 

            <div className="flex justify-end space-x-4">
              <button onClick={onClose} className="px-9 py-3 rounded-xl min-w-[100px] md:min-w-[150px] h-[60px] text-lg font-semibold border-2 border-gray-300 text-gray-700 hover:bg-gray-100">Cancel</button>
              <button onClick={handleSubmit} className="px-9 py-3 rounded-[10px] min-w-[100px] md:min-w-[130px] h-[60px] flex items-center justify-center text-2xl font-bold border-none tracking-[0.03em] leading-[117.504%] bg-[#9CB0C9] text-white hover:bg-[#8BA0B8] transition-colors">
                Create Pupil
              </button>
            </div>
          </div>
        </div>
    );
  };

  const EditButton = ({ initialData, onUpdate }: { initialData: PupilItem, onUpdate: (data: PupilItem) => void }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [formData, setFormData] = useState<PupilItem>(initialData);

    return (
      <>
        <button onClick={() => setIsModalOpen(true)} className="px-6 py-3 rounded-xl bg-[#9CB0C9] text-white font-bold">
          Edit
        </button>
        {isModalOpen && (
          <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center z-50">
            <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md">
              <h3 className="text-2xl font-bold mb-4">Edit pupil</h3>
              <div className="space-y-4 mb-6">
                <input value={formData.name} onChange={(e) => setFormData({...formData, name: e.target.value})} className="w-full p-3 border rounded-lg" />
                <input value={formData.email} onChange={(e) => setFormData({...formData, email: e.target.value})} className="w-full p-3 border rounded-lg" />
                <input value={formData.class_id} onChange={(e) => setFormData({...formData, class_id: parseInt(e.target.value)})} className="w-full p-3 border rounded-lg" />
                <input value={formData.parent_id} onChange={(e) => setFormData({...formData, parent_id: parseInt(e.target.value)})} className="w-full p-3 border rounded-lg" />
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
          
          {/*<div className="flex justify-between items-center mb-12">
            <h1 className="text-[#665B4E] font-bold text-3xl">Pupil Directory</h1>
            <div className="flex gap-4">
              <button onClick={() => setIsAddModalOpen(true)} className="px-6 py-3 rounded-xl bg-[#9CB0C9] text-white font-bold">
                + Add pupil
              </button>
              <input 
                placeholder="Search..." 
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="px-6 py-3 rounded-xl focus:outline-none"
              />
            </div>
          </div>*/}

        <div className="flex w-full items-center justify-between">
          <h1 className="text-[#665B4E] font-bold text-3xl">Students Directory</h1>
          <input
            placeholder="Search..." 
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="px-6 py-3 rounded-xl focus:outline-none border border-gray-200" 
              />
        </div>
          <div className="grid grid-cols-6 gap-6 items-center border-b pb-4 mb-6 font-bold text-[#665B4E]">
            <div>ID</div>
            <div>Email</div>
            <div>Name</div>
            <div>Class ID</div>
            <div>Parent ID</div>
            <div>Actions</div>
          </div>

          {isLoading ? (
            <div className="text-center py-10">Loading...</div>
          ) : (
            <div className="space-y-4">
              {filteredpupils.map((pupil) => (
                <div key={pupil.id} className="grid grid-cols-6 gap-6 items-center border-b pb-4 mb-6 font-bold text-[#665B4E]">
                  <div>{pupil.id}</div>
                  <div>{pupil.email}</div>
                  <div>{pupil.name}</div>
                  <div>{pupil.class_id}</div>
                  <div>{pupil.parent_id}</div>
                  <div> <EditButton initialData={pupil} onUpdate={handleUpdatepupil} /> </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      <AddPupilModal 
        isOpen={isAddModalOpen} 
        onClose={() => setIsAddModalOpen(false)} 
        onAdd={handleAddpupil} 
      />
    </div>
  );
}