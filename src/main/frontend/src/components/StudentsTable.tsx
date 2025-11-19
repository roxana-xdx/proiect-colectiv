import { useState } from "react";
import { Checkbox } from "./ui/checkbox.tsx";
import { Trash2 } from "lucide-react";

interface Student {
  id: number;
  name: string;
  nameParent?: string;
  class?: string;
  attendedSchool: boolean;
  actions: string;
}

export function StudentsTable() {
  const [students, setStudents] = useState<Student[]>([
    { id: 1, name: "Alexandra Montevardo-Howard", nameParent: "John Howard", class: "Grade 1", attendedSchool: false, actions: "" },
    { id: 2, name: "Alexandra Montevardo-Howard", nameParent: "Sarah Martinez", class: "Grade 2", attendedSchool: false, actions: "" },
    { id: 3, name: "Alexandra Montevardo-Howard", nameParent: "Michael Chen", class: "Grade 1", attendedSchool: false, actions: "" },
  ]);

const [mode, setMode] = useState<"add" | "edit" | null>(null);

  const [newStudent, setNewStudent] = useState({
    nameStudent: "",
    email: "",
    nameParent: "",
    class: "",
  });

  const [editStudent, setEditStudent] = useState({
    id: "",
    nameStudent: "",
    nameParent: "",
    class: "",
  });

  const handleCheckboxChange = (id: number, checked: boolean) => {
    setStudents(
      students.map((student) =>
        student.id === id ? { ...student, attendedSchool: checked } : student
      )
    );
  };

  const handleDelete = (id: number) => {
    setStudents(students.filter((student) => student.id !== id));
  };

   const handleAddStudent = () => {
    if (newStudent.nameStudent.trim()) {
      const newId = Math.max(...students.map(s => s.id), 0) + 1;
      const studentToAdd: Student = {
        id: newId,
        name: newStudent.nameStudent,
        nameParent: newStudent.nameParent,
        class: newStudent.class,
        attendedSchool: false,
        actions: "",
      };
      setStudents([...students, studentToAdd]);
      setNewStudent({ nameStudent: "", email: "", nameParent: "", class: "" });
    }
  };

  const handleEditStudent = () => {
    const studentId = parseInt(editStudent.id);
    if (studentId && !isNaN(studentId)) {
      setStudents(
        students.map((student) =>
          student.id === studentId
            ? {
                ...student,
                name: editStudent.nameStudent || student.name,
                nameParent: editStudent.nameParent || student.nameParent,
                class: editStudent.class || student.class,
              }
            : student
        )
      );
      setEditStudent({ id: "", nameStudent: "", nameParent: "", class: "" });
    }
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
              <th className="text-left px-6 py-4 text-gray-700">Student Name</th>
              <th className="text-left px-6 py-4 text-gray-700">Attended School</th>
              <th className="text-left px-6 py-4 text-gray-700">Actions</th>
            </tr>
          </thead>
          <tbody>
            {students.map((student, index) => (
              <tr key={student.id} className="border-b border-gray-100 hover:bg-gray-50">
                <td className="px-6 py-4 text-gray-700">{index + 1}</td>
                <td className="px-6 py-4 text-gray-700">{student.id}</td>
                <td className="px-6 py-4 text-gray-700">{student.name}</td>
                <td className="px-6 py-4">
                  <Checkbox
                    checked={student.attendedSchool}
                    onCheckedChange={(checked: boolean) =>
                      handleCheckboxChange(student.id, checked as boolean)
                    }
                  />
                </td>
                <td className="px-6 py-4">
                  <button
                    onClick={() => handleDelete(student.id)}
                    className="text-red-500 hover:text-red-700 transition-colors"
                    title="Delete student"
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
            onClick={() => setMode("add")}
            className={`px-5 py-2 rounded transition-colors ${
              mode === "add"
                ? "bg-[#b899d4] text-white"
                : "bg-[#c8b8d4] text-white hover:bg-[#b899d4]"
            }`}
          >
            Add
          </button>
          <button
            onClick={() => setMode("edit")}
            className={`px-5 py-2 rounded transition-colors ${
              mode === "edit"
                ? "bg-[#b899d4] text-white"
                : "bg-[#c8b8d4] text-white hover:bg-[#b899d4]"
            }`}
          >
            Edit
          </button>
        </div>
      </div>

      {/* Add New Student Form */}
      {mode === "add" && (
        <div className="bg-[#f8f5fa] rounded-lg p-6">
          <h3 className="text-gray-700 mb-4">Add new student:</h3>
          <div className="grid grid-cols-2 gap-4">
            <input
              type="text"
              placeholder="Name Student"
              value={newStudent.nameStudent}
              onChange={(e) =>
                setNewStudent({ ...newStudent, nameStudent: e.target.value })
              }
              className="px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
            <input
              type="email"
              placeholder="Email"
              value={newStudent.email}
              onChange={(e) => setNewStudent({ ...newStudent, email: e.target.value })}
              className="px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
            <input
              type="text"
              placeholder="Name Parent"
              value={newStudent.nameParent}
              onChange={(e) =>
                setNewStudent({ ...newStudent, nameParent: e.target.value })
              }
              className="px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
            <input
              type="text"
              placeholder="Class"
              value={newStudent.class}
              onChange={(e) => setNewStudent({ ...newStudent, class: e.target.value })}
              className="px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
          </div>

          <button
            onClick={handleAddStudent}
            className="mt-4 px-5 py-2 bg-[#b899d4] text-white rounded transition-colors hover:bg-[#a889c4]"
          >
            Add Student
          </button>
        </div>
      )}

      {/* Edit Student Form */}
      {mode === "edit" && (
        <div className="bg-[#f8f5fa] rounded-lg p-6">
          <h3 className="text-gray-700 mb-4">Edit student:</h3>
          <div className="grid grid-cols-2 gap-4">
            <input
              type="text"
              placeholder="Student ID"
              value={editStudent.id}
              onChange={(e) =>
                setEditStudent({ ...editStudent, id: e.target.value })
              }
              className="px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
            <input
              type="text"
              placeholder="New Class"
              value={editStudent.class}
              onChange={(e) => setEditStudent({ ...editStudent, class: e.target.value })}
              className="px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
            <input
              type="text"
              placeholder="New Name Student"
              value={editStudent.nameStudent}
              onChange={(e) =>
                setEditStudent({ ...editStudent, nameStudent: e.target.value })
              }
              className="px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700 col-span-2"
            />
            <input
              type="text"
              placeholder="New Name Parent"
              value={editStudent.nameParent}
              onChange={(e) =>
                setEditStudent({ ...editStudent, nameParent: e.target.value })
              }
              className="px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700 col-span-2"
            />
          </div>
          <button
            onClick={handleEditStudent}
            className="mt-4 px-5 py-2 bg-[#b899d4] text-white rounded transition-colors hover:bg-[#a889c4]"
          >
            Edit Student
          </button>
        </div>
      )}
    </div>
  );
}