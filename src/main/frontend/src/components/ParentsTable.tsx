import { useState } from "react";
import { Trash2 } from "lucide-react";

interface Parent {
  id: number;
  name: string;
  email: string;
}

export function ParentsTable() {
  const [parents, setParents] = useState<Parent[]>([
    { id: 1, name: "Alexandra Montevardo-Howard", email: "alexandra@example.com" },
    { id: 2, name: "Alexandra Montevardo-Howard", email: "alexandra2@example.com" },
    { id: 3, name: "Alexandra Montevardo-Howard", email: "alexandra3@example.com" },
  ]);

  const [mode, setMode] = useState<"add" | "edit">("add");

  const [newParent, setNewParent] = useState({
    email: "",
    nameParent: "",
  });

  const [editParent, setEditParent] = useState({
    id: "",
    email: "",
    nameParent: "",
  });

  const handleDelete = (id: number) => {
    setParents(parents.filter((parent) => parent.id !== id));
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
              <th className="text-left px-6 py-4 text-gray-700">Parent Name</th>
              <th className="text-left px-6 py-4 text-gray-700">See More</th>
              <th className="text-left px-6 py-4 text-gray-700">Actions</th>
            </tr>
          </thead>
          <tbody>
            {parents.map((parent, index) => (
              <tr key={parent.id} className="border-b border-gray-100 hover:bg-gray-50">
                <td className="px-6 py-4 text-gray-700">{index + 1}</td>
                <td className="px-6 py-4 text-gray-700">{parent.id}</td>
                <td className="px-6 py-4 text-gray-700">{parent.name}</td>
                <td className="px-6 py-4">
                  <button className="px-4 py-1 bg-[#b899d4] text-white rounded hover:bg-[#a888c4] transition-colors">
                    View
                  </button>
                </td>
                <td className="px-6 py-4">
                  <button
                    onClick={() => handleDelete(parent.id)}
                    className="text-red-500 hover:text-red-700 transition-colors"
                    title="Delete parent"
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

      {/* Add New Parent Form */}
      {mode === "add" && (
        <div className="bg-[#f8f5fa] rounded-lg p-6">
          <h3 className="text-gray-700 mb-4">Add new parent:</h3>
          <div className="space-y-4">
            <input
              type="email"
              placeholder="Email"
              value={newParent.email}
              onChange={(e) => setNewParent({ ...newParent, email: e.target.value })}
              className="w-full px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
            <input
              type="text"
              placeholder="Name Parent"
              value={newParent.nameParent}
              onChange={(e) =>
                setNewParent({ ...newParent, nameParent: e.target.value })
              }
              className="w-full px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
          </div>
        </div>
      )}

      {/* Edit Parent Form */}
      {mode === "edit" && (
        <div className="bg-[#f8f5fa] rounded-lg p-6">
          <h3 className="text-gray-700 mb-4">Edit parent:</h3>
          <div className="space-y-4">
            <input
              type="text"
              placeholder="Parent ID"
              value={editParent.id}
              onChange={(e) =>
                setEditParent({ ...editParent, id: e.target.value })
              }
              className="w-full px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
            <input
              type="email"
              placeholder="New Email"
              value={editParent.email}
              onChange={(e) => setEditParent({ ...editParent, email: e.target.value })}
              className="w-full px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
            <input
              type="text"
              placeholder="New Name Parent"
              value={editParent.nameParent}
              onChange={(e) =>
                setEditParent({ ...editParent, nameParent: e.target.value })
              }
              className="w-full px-4 py-2 bg-[#e8dff0] border-none rounded placeholder-gray-500 text-gray-700"
            />
          </div>
        </div>
      )}
    </div>
  );
}