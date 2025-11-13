import { useState } from "react";
import { Trash2 } from "lucide-react";

interface Class {
  id: number;
  name: string;
  students: number;
  teacher: string;
}

export function ClassesTable() {
  const [classes, setClasses] = useState<Class[]>([
    { id: 1, name: "Class 1", students: 25, teacher: "Mrs. Smith" },
    { id: 2, name: "Class 2", students: 15, teacher: "Mrs. John" },
    { id: 3, name: "Class 3", students: 27, teacher: "Mr. Smith" },
  ]);

  const [newClass, setNewClass] = useState({
    teacher: "",
    name: "",
  });

  const handleDelete = (id: number) => {
    setClasses(classes.filter((classItem) => classItem.id !== id));
  };

  const handleUpdate = (id: number) => {
    // This will be handled elsewhere as per requirements
    console.log("Update class with ID:", id);
  };

  return (
    <div className="space-y-6">
      {/* Table */}
      <div className="bg-white rounded-lg shadow-sm overflow-hidden">
        <table className="w-full">
          <thead>
            <tr className="border-b border-gray-200">
              <th className="text-left px-6 py-4 text-gray-700">#</th>
              <th className="text-left px-6 py-4 text-gray-700">ID</th>
              <th className="text-left px-6 py-4 text-gray-700">Class</th>
              <th className="text-left px-6 py-4 text-gray-700">Teacher</th>
              <th className="text-left px-6 py-4 text-gray-700">No. of Students</th>
              <th className="text-left px-6 py-4 text-gray-700">Update</th>
              <th className="text-left px-6 py-4 text-gray-700">Actions</th>
            </tr>
          </thead>
          <tbody>
            {classes.map((classItem, index) => (
              <tr key={classItem.id} className="border-b border-gray-100 hover:bg-gray-50">
                <td className="px-6 py-4 text-gray-700">{index + 1}</td>
                <td className="px-6 py-4 text-gray-700">{classItem.id}</td>
                <td className="px-6 py-4 text-gray-700">{classItem.name}</td>
                <td className="px-6 py-4 text-gray-700">{classItem.teacher}</td>
                <td className="px-6 py-4 text-gray-700">{classItem.students}</td>
                <td className="px-6 py-4">
                  <button
                    onClick={() => handleUpdate(classItem.id)}
                    className="px-4 py-1 bg-[#b899d4] text-white rounded hover:bg-[#a888c4] transition-colors"
                  >
                    Update
                  </button>
                </td>
                <td className="px-6 py-4">
                  <button
                    onClick={() => handleDelete(classItem.id)}
                    className="text-red-500 hover:text-red-700 transition-colors"
                    title="Delete class"
                  >
                    <Trash2 className="w-5 h-5" />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* Add Button */}
        <div className="flex justify-end gap-3 px-6 py-4 bg-gray-50">
          <button className="px-5 py-2 bg-[#b899d4] text-white rounded hover:bg-[#a888c4] transition-colors">
            Add
          </button>
        </div>
      </div>

      {/* Add New Class Form */}
      <div className="bg-[#f8f5fa] rounded-lg p-6">
        <h3 className="text-gray-700 mb-4">Add new Class:</h3>
        <div className="space-y-4">
          <input
            type="text"
            placeholder="Class' Teacher"
            value={newClass.teacher}
            onChange={(e) => setNewClass({ ...newClass, teacher: e.target.value })}
            className="w-full px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
          />
          <input
            type="text"
            placeholder="Name of Class"
            value={newClass.name}
            onChange={(e) => setNewClass({ ...newClass, name: e.target.value })}
            className="w-full px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
          />
        </div>
      </div>
    </div>
  );
}