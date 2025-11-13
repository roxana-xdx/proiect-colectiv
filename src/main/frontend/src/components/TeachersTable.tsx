import { useState } from "react";
import { Trash2 } from "lucide-react";

interface Teacher {
  id: number;
  name: string;
  class: string;
  email: string;
}

export function TeachersTable() {
  const [teachers, setTeachers] = useState<Teacher[]>([
    { id: 1, name: "Mrs. Emily Johnson", class: "Grade 1", email: "emily.j@school.com" },
    { id: 2, name: "Mr. David Smith", class: "Grade 2", email: "david.s@school.com" },
    { id: 3, name: "Ms. Rebecca Davis", class: "Grade 3", email: "rebecca.d@school.com" },
  ]);

  const [showAddForm, setShowAddForm] = useState(false);
  const [showEditForm, setShowEditForm] = useState(false);

  const [newTeacher, setNewTeacher] = useState({
    name: "",
    email: "",
  });

  const [editTeacher, setEditTeacher] = useState({
    id: "",
    name: "",
    email: "",
    class: "",
  });

  const handleDelete = (id: number) => {
    setTeachers(teachers.filter((teacher) => teacher.id !== id));
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
              <th className="text-left px-6 py-4 text-gray-700">Teacher Name</th>
              <th className="text-left px-6 py-4 text-gray-700">Class</th>
              <th className="text-left px-6 py-4 text-gray-700">Email</th>
              <th className="text-left px-6 py-4 text-gray-700">Actions</th>
            </tr>
          </thead>
          <tbody>
            {teachers.map((teacher, index) => (
              <tr key={teacher.id} className="border-b border-gray-100 hover:bg-gray-50">
                <td className="px-6 py-4 text-gray-700">{index + 1}</td>
                <td className="px-6 py-4 text-gray-700">{teacher.id}</td>
                <td className="px-6 py-4 text-gray-700">{teacher.name}</td>
                <td className="px-6 py-4 text-gray-700">{teacher.class}</td>
                <td className="px-6 py-4 text-gray-700">{teacher.email}</td>
                <td className="px-6 py-4">
                  <button
                    onClick={() => handleDelete(teacher.id)}
                    className="text-red-500 hover:text-red-700 transition-colors"
                    title="Delete teacher"
                  >
                    <Trash2 className="w-5 h-5" />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* Action Buttons */}
        <div className="flex justify-end gap-3 px-6 py-4 bg-gray-50">
          <button
            onClick={() => setShowAddForm(!showAddForm)}
            className="px-5 py-2 bg-[#b899d4] text-white rounded hover:bg-[#a888c4] transition-colors"
          >
            Add
          </button>
          <button
            onClick={() => setShowEditForm(!showEditForm)}
            className="px-5 py-2 bg-[#c8b8d4] text-white rounded hover:bg-[#b8a8c4] transition-colors"
          >
            Edit
          </button>
        </div>
      </div>

      {/* Add New Teacher Form */}
      {showAddForm && (
        <div className="bg-[#f8f5fa] rounded-lg p-6">
          <h3 className="text-gray-700 mb-4">Add new teacher:</h3>
          <div className="space-y-4">
            <input
              type="text"
              placeholder="Teacher Name"
              value={newTeacher.name}
              onChange={(e) => setNewTeacher({ ...newTeacher, name: e.target.value })}
              className="w-full px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
            <input
              type="email"
              placeholder="Email"
              value={newTeacher.email}
              onChange={(e) => setNewTeacher({ ...newTeacher, email: e.target.value })}
              className="w-full px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
          </div>
        </div>
      )}

      {/* Edit Teacher Form */}
      {showEditForm && (
        <div className="bg-[#f8f5fa] rounded-lg p-6">
          <h3 className="text-gray-700 mb-4">Edit teacher:</h3>
          <div className="space-y-4">
            <input
              type="text"
              placeholder="Teacher ID"
              value={editTeacher.id}
              onChange={(e) => setEditTeacher({ ...editTeacher, id: e.target.value })}
              className="w-full px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
            <input
              type="text"
              placeholder="New Name"
              value={editTeacher.name}
              onChange={(e) => setEditTeacher({ ...editTeacher, name: e.target.value })}
              className="w-full px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
            <input
              type="email"
              placeholder="New Email"
              value={editTeacher.email}
              onChange={(e) => setEditTeacher({ ...editTeacher, email: e.target.value })}
              className="w-full px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
            <input
              type="text"
              placeholder="Class Assigned (optional)"
              value={editTeacher.class}
              onChange={(e) => setEditTeacher({ ...editTeacher, class: e.target.value })}
              className="w-full px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
          </div>
        </div>
      )}
    </div>
  );
}