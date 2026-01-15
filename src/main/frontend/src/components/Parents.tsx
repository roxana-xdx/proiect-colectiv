import { useState, useEffect } from "react";
import Navigation from "./Navigation.tsx";

import { parentService } from "../services/parentService.ts";
import { CreateParentRequest } from "../types/parent.ts";
import { UserType } from "../types/user.ts";

interface ParentItem {
  id: number;
  name: string;
  email: string;
}
interface UserItem {
  email: string;
  name: string;
  type: UserType;
}

interface AddParentModalProps {
  isOpen: boolean;
  onClose: () => void;
  onAdd: (name: string, email: string) => void;
}

const AddParentModal: React.FC<AddParentModalProps> = ({ isOpen, onClose, onAdd }) => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  const handleSubmit = () => {
    if (!name || !email) {
      alert("Both name and email are required");
      return;
    }
    onAdd(name, email);
    setName("");
    setEmail("");
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center z-50">
      <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md mx-4">
        <h3 className="text-3xl font-bold mb-6 text-gray-800">Add New Parent</h3>
        <div className="space-y-4 mb-6">
          <div>
            <label className="block text-lg font-medium text-gray-700 mb-2">Full Name</label>
            <input 
              placeholder="e.g. John Doe" 
              value={name} 
              onChange={(e) => setName(e.target.value)} 
              className="w-full p-3 border border-gray-300 rounded-lg text-lg" 
            />
          </div>
          <div>
            <label className="block text-lg font-medium text-gray-700 mb-2">Email Address</label>
            <input 
              placeholder="e.g. john@example.com" 
              value={email} 
              onChange={(e) => setEmail(e.target.value)} 
              className="w-full p-3 border border-gray-300 rounded-lg text-lg" 
            />
          </div>
        </div>
        <div className="flex justify-end space-x-4">
          <button onClick={onClose} className="px-9 py-3 rounded-xl min-w-[120px] h-[60px] text-lg font-semibold border-2 border-gray-300 text-gray-700 hover:bg-gray-100">
            Cancel
          </button>
          <button onClick={handleSubmit} className="px-9 py-3 rounded-[10px] min-w-[130px] h-[60px] flex items-center justify-center text-xl font-bold bg-[#9CB0C9] text-white hover:bg-[#8BA0B8] transition-colors">
            Create Parent
          </button>
        </div>
      </div>
    </div>
  );
};

  const EditButton = ({ initialData, onUpdate }: { initialData: ParentItem, onUpdate: (data: ParentItem) => void }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [formData, setFormData] = useState<ParentItem>(initialData);

    return (
      <>
        <button onClick={() => setIsModalOpen(true)} className="px-6 py-3 rounded-xl bg-[#9CB0C9] text-white font-bold">
          Edit
        </button>
        {isModalOpen && (
          <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center z-50">
            <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md">
              <h3 className="text-2xl font-bold mb-4">Edit Parent</h3>
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

export default function Parents() {
  const [parents, setParents] = useState<ParentItem[]>([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);

  const [user, setUser] = useState<UserItem | null>(null);
  

  useEffect(() => {
    fetchParents();
    fetchUser();
  }, []);

 const fetchUser = () => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  };

  // check if user is admin
  const isAdmin = user?.type === "ADMIN";

  const fetchParents = async () => {
    try {
      setIsLoading(true);
      const response = await parentService.getAll();
      const mappedData: ParentItem[] = response.data.map((dto: any) => ({
        id: dto.id,
        name: dto.name || "N/A",
        email: dto.email || "N/A",
      }));
      setParents(mappedData);
    } catch (error) {
      console.error("Error fetching parents:", error);
    } finally {
      setIsLoading(false);
    }
  };

  // const handleAddParent = async (name: string, email: string) => {
  //   try {
  //       const payload: CreateParentRequest = { 
  //       name: name, 
  //       email: email 
  //     } as any;      
  //     const response = await parentService.create(payload);
  //     const newDTO = response.data;

  //     const newParentItem: ParentItem = {
  //       id: newDTO.id,
  //       name: newDTO.name || name,
  //       email: newDTO.email || email,
  //     };

  //     setParents([...parents, newParentItem]);
  //     setIsAddModalOpen(false);
  //   } catch (error) {
  //     console.error("Failed to create parent:", error);
  //   }
  // };

  // const handleUpdateParent = async (updatedData: ParentItem) => {
  //   try {
  //       const requestPayload: CreateParentRequest = {
  //       name: updatedData.name,
  //       email: updatedData.email
  //     } as any;      
  //     await parentService.update(updatedData.id, requestPayload);

  //     setParents((prev) =>
  //       prev.map((p) => (p.id === updatedData.id ? updatedData : p))
  //     );
  //   } catch (error) {
  //     console.error("Failed to update parent:", error);
  //     alert("Failed to save changes.");
  //   }
  // };

  const filteredParents = parents.filter(
    (item) =>
      item.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      item.email.toLowerCase().includes(searchQuery.toLowerCase())
  );

 return (
    <div className="min-h-screen bg-[#F9F8F6]">
      <Navigation />
      <div className="max-w-[1440px] mx-auto px-4 py-8">
        <div className="bg-[#EFE9E3] rounded-[30px] p-6 md:p-12">
          
          <div className="flex justify-between items-center mb-12">
            <h1 className="text-[#665B4E] font-bold text-3xl">Parents Directory</h1>
            <div className="flex gap-4">
          {/*rendering based on if admin */}
              {/* {isAdmin && (
                <button 
                  onClick={() => setIsAddModalOpen(true)} 
                  className="px-6 py-3 rounded-xl bg-[#9CB0C9] text-white font-bold"
                >
                  + Add Parent
                </button>
              )} */}
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
            {/* {isAdmin && <div>Actions</div>} */}
          </div>

          {isLoading ? (
            <div className="text-center py-10">Loading...</div>
          ) : (
            <div className="space-y-4">
              {filteredParents.map((parent) => (
                <div key={parent.id} className="grid grid-cols-6 gap-6 items-center border-b pb-4 mb-6 font-bold text-[#665B4E]">
                  <div>{parent.id}</div>
                  <div>{parent.name}</div>
                  <div>{parent.email}</div>
                  {/* <div>
                      {isAdmin && (
                        <EditButton 
                          initialData={parent} 
                          onUpdate={handleUpdateParent} 
                        />
                      )}</div> */}
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      {/* <AddParentModal 
        isOpen={isAddModalOpen} 
        onClose={() => setIsAddModalOpen(false)} 
        onAdd={handleAddParent} 
      /> */}
    </div>
  );
}