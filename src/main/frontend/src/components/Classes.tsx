import { useState, useEffect } from "react";
import Navigation from "./Navigation.tsx";

import { classService } from "../services/schoolClassService.ts";
import { CreateSchoolClassRequest } from "../types/schoolClass.ts";
import { UserType } from "../types/user.ts";

interface ClassItem {
  id: number;
  className: string;
  teacher: string;
  // timetable: string;
}
interface UserItem {
  email: string;
  name: string;
  type: UserType;
}

export default function Classes() {
  const [classes, setClasses] = useState<ClassItem[]>([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [user, setUser] = useState<UserItem | null>(null);

  //  FETCH DATA 
  useEffect(() => {
    fetchClasses();
        fetchUser();

  }, []);

  const fetchClasses = async () => {
    try {
      setIsLoading(true);
      const response = await classService.getAll();

      const mappedData: ClassItem[] = response.data.map((dto) => ({
        id: dto.classId,
        className: dto.className,
        teacher: dto.homeroomTeacherId ? `Teacher ID: ${dto.homeroomTeacherId}` : "No Teacher",
        // timetable: "TBD", 
      }));

      setClasses(mappedData);
    } catch (error) {
      console.error("Error fetching classes:", error);
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

  const isAdmin = user?.type === "ADMIN";


  const handleAddClass = async (name: string, teacherId: number) => {
    try {
      const payload: CreateSchoolClassRequest = {
        class_name: name,
        homeroom_teacher_id: teacherId
      };

      const response = await classService.create(payload);
      const newClassDTO = response.data;

      const newClassItem: ClassItem = {
        id: newClassDTO.classId,
        className: newClassDTO.className,
        teacher: newClassDTO.homeroomTeacherId ? `Teacher ID: ${newClassDTO.homeroomTeacherId}` : "No Teacher",
        // timetable: "TBD"
      }; 

      setClasses([...classes, newClassItem]);
      setIsAddModalOpen(false);
      
    } catch (error) {
      console.error("Failed to create class:", error);
      alert("Error while adding - check console");
    }
  };

  const handleUpdateClass = async (updatedData: ClassItem) => {
    try {
      const teacherIdNum = parseInt(updatedData.teacher.replace("Teacher ID: ", "")) || 0;

      const requestPayload: CreateSchoolClassRequest = {
        class_name: updatedData.className,
        homeroom_teacher_id: teacherIdNum
      };

      await classService.update(updatedData.id, requestPayload);

      setClasses((prevClasses) =>
        prevClasses.map((c) => (c.id === updatedData.id ? updatedData : c))
      );
    } catch (error) {
      console.error("Failed to update class:", error);
      alert("Failed to save changes.");
    }
  };

  const filteredClasses = classes.filter(
    (classItem) =>
      classItem.className.toLowerCase().includes(searchQuery.toLowerCase()) ||
      classItem.teacher.toLowerCase().includes(searchQuery.toLowerCase())
      // classItem.timetable.toLowerCase().includes(searchQuery.toLowerCase())
  );

  interface AddClassModalProps {
    isOpen: boolean;
    onClose: () => void;
    onAdd: (name: string, teacherId: number) => void;
  }

  const AddClassModal: React.FC<AddClassModalProps> = ({ isOpen, onClose, onAdd }) => {
    const [name, setName] = useState("");
    const [teacherId, setTeacherId] = useState("");

    const handleSubmit = () => {
        if(!name) {
            alert("Class name is required");
            return;
        }

        const tId = parseInt(teacherId) || 0; 
        onAdd(name, tId);
        setName("");
        setTeacherId("");
    };

    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center z-50">
          <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md mx-4">
            <h3 className="text-3xl font-bold mb-6 text-gray-800">Add New Class</h3>

            <div className="mb-4">
              <label className="block text-lg font-medium text-gray-700 mb-2">Class Name</label>
              <input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg text-lg focus:ring-blue-500 focus:border-blue-500"
                placeholder="e.g. 10B Math"
              />
            </div>

            <div className="mb-6">
              <label className="block text-lg font-medium text-gray-700 mb-2">Teacher ID</label>
              <input
                type="number"
                value={teacherId}
                onChange={(e) => setTeacherId(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg text-lg focus:ring-blue-500 focus:border-blue-500"
                placeholder="e.g. 15"
              />
            </div>      
            <div className="flex justify-end space-x-4">
              <button onClick={onClose} className="px-9 py-3 rounded-xl min-w-[100px] md:min-w-[150px] h-[60px] text-lg font-semibold border-2 border-gray-300 text-gray-700 hover:bg-gray-100">Cancel</button>
              <button onClick={handleSubmit} className="px-9 py-3 rounded-[10px] min-w-[100px] md:min-w-[130px] h-[60px] flex items-center justify-center text-2xl font-bold border-none tracking-[0.03em] leading-[117.504%] bg-[#9CB0C9] text-white hover:bg-[#8BA0B8] transition-colors">
                Create Class
              </button>
            </div>
          </div>
        </div>
    );
  };

  interface EditClassModalProps {
    isOpen: boolean;
    onClose: () => void;
    classData: ClassItem;
    onSave: (updatedData: ClassItem) => void;
  }

  interface EditButtonProps {
    initialClassData: ClassItem;
    onUpdateClass: (updatedData: ClassItem) => void;
  }

  const EditButton: React.FC<EditButtonProps> = ({ initialClassData, onUpdateClass }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);

    const handleSave = (updatedData: ClassItem) => {
      onUpdateClass(updatedData);
      setIsModalOpen(false);
    };

    const EditClassModal: React.FC<EditClassModalProps> = ({ isOpen, onClose, classData, onSave }) => {
      const [formData, setFormData] = useState<ClassItem>(classData);

      useEffect(() => { setFormData(classData); }, [classData]);

      const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({ ...prevData, [name]: value }));
      };

      if (!isOpen) return null;

      return (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center z-50">
          <div className="bg-white p-8 rounded-xl shadow-2xl w-full max-w-md mx-4">
            <h3 className="text-3xl font-bold mb-6 text-gray-800"> Edit Class</h3>
            <div className="mb-4">
              <label className="block text-lg font-medium text-gray-700 mb-2">Class Name</label>
              <input name="className" value={formData.className} onChange={handleChange} className="w-full p-3 border border-gray-300 rounded-lg text-lg focus:ring-blue-500 focus:border-blue-500" />
            </div>
            <div className="mb-4">
              <label className="block text-lg font-medium text-gray-700 mb-2">Teacher (ID)</label>
              <input name="teacher" value={formData.teacher} onChange={handleChange} className="w-full p-3 border border-gray-300 rounded-lg text-lg focus:ring-blue-500 focus:border-blue-500" />
            </div>
            {/* <div className="mb-6">
              <label className="block text-lg font-medium text-gray-700 mb-2">Date & Time</label>
              <input name="timetable" value={formData.timetable} onChange={handleChange} readOnly className="w-full p-3 border border-gray-300 rounded-lg text-lg bg-gray-100 text-gray-500" />
            </div> */}
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
        <EditClassModal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} classData={initialClassData} onSave={handleSave} />
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
              Classes
            </h1>

            <div className="flex gap-4">
                            {isAdmin && (

              <button 
                onClick={() => setIsAddModalOpen(true)} 
                className="px-6 py-3 rounded-xl bg-[#9CB0C9] text-white font-bold"
              >
                + Add Class
              </button>
                            )}
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
            <div>Class Name</div>
            <div className="col-span-2">Teacher</div>
            {/* <div>Timetable</div> */}
            {isAdmin && <div>Actions</div>}
          </div>

          {isLoading ? (
            <div className="text-center py-10">Loading...</div>
          ) : (
            <div className="space-y-4">
              {filteredClasses.length === 0 ? (
                <div className="text-center text-gray-500 py-4">No classes found.</div>
              ) : (
                filteredClasses.map((classItem) => (
                  <div 
                    key={classItem.id} 
                    className="grid grid-cols-6 gap-6 items-center border-b pb-4 mb-6 font-bold text-[#665B4E]"
                  >
                    <div>{classItem.id}</div>
                    <div>{classItem.className}</div>
                    <div className="col-span-2 text-[#665B4E]/80">{classItem.teacher}</div>
                    {/* <div className="text-[#665B4E]/80">{classItem.timetable}</div> */}
                    <div>
                                            {isAdmin && (

                      <EditButton initialClassData={classItem} onUpdateClass={handleUpdateClass} /> )}
                    </div>
                  </div>
                ))
              )}
            </div>
          )}
        </div>
      </div>

      <AddClassModal 
        isOpen={isAddModalOpen} 
        onClose={() => setIsAddModalOpen(false)} 
        onAdd={handleAddClass} 
      />
    </div>
  );
}