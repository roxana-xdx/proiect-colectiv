import { useState } from "react";
import { StudentsTable } from "./components/StudentsTable.tsx";
import { ParentsTable } from "./components/ParentsTable.tsx";
import { ClassesTable } from "./components/ClassesTable.tsx";
import { TeachersTable } from "./components/TeachersTable.tsx";
import { AddNewsSection } from "./components/AddNewsSection.tsx";

import { BrowserRouter as Router, Route, Routes } from "react-router-dom";


type TabType = "students" | "parents" | "classes" | "teachers" | "addNews";

export default function App() {
  const [activeTab, setActiveTab] = useState<TabType>("students");

  const handleAnnouncementsClick = () => {
    // Will navigate to announcements page
    console.log("Navigate to Announcements page");
  };

  return (
    <div className="min-h-screen bg-[#f5f5f5]">
      {/* Header */}
      <div className="bg-white border-b border-gray-200">
        <div className="max-w-7xl mx-auto px-6 py-6">
          <div className="flex items-center justify-between">
            {/* Logo and Title */}
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 bg-[#c8e6dc] rounded-md flex items-center justify-center">
                <span className="text-gray-700">AS</span>
              </div>
              <div>
                <h1 className="text-gray-900">School Admin Panel</h1>
                <p className="text-sm text-gray-500">Attendance Tracking</p>
              </div>
            </div>

            {/* Navigation */}
            <div className="flex gap-3">
              <button
                onClick={() => setActiveTab("students")}
                className={`px-5 py-2 rounded transition-colors ${
                  activeTab === "students"
                    ? "bg-[#b899d4] text-white"
                    : "bg-[#e8dff0] text-gray-700 hover:bg-[#d4c4e3]"
                }`}
              >
                Students
              </button>
              <button
                onClick={() => setActiveTab("parents")}
                className={`px-5 py-2 rounded transition-colors ${
                  activeTab === "parents"
                    ? "bg-[#b899d4] text-white"
                    : "bg-[#e8dff0] text-gray-700 hover:bg-[#d4c4e3]"
                }`}
              >
                Parents
              </button>
              <button
                onClick={() => setActiveTab("classes")}
                className={`px-5 py-2 rounded transition-colors ${
                  activeTab === "classes"
                    ? "bg-[#b899d4] text-white"
                    : "bg-[#e8dff0] text-gray-700 hover:bg-[#d4c4e3]"
                }`}
              >
                Classes
              </button>
              <button
                onClick={() => setActiveTab("teachers")}
                className={`px-5 py-2 rounded transition-colors ${
                  activeTab === "teachers"
                    ? "bg-[#b899d4] text-white"
                    : "bg-[#e8dff0] text-gray-700 hover:bg-[#d4c4e3]"
                }`}
              >
                Teachers
              </button>
              <button
                onClick={() => setActiveTab("addNews")}
                className={`px-5 py-2 rounded transition-colors ${
                  activeTab === "addNews"
                    ? "bg-[#b899d4] text-white"
                    : "bg-[#e8dff0] text-gray-700 hover:bg-[#d4c4e3]"
                }`}
              >
                Add News
              </button>
              <button
                onClick={handleAnnouncementsClick}
                className="px-5 py-2 rounded transition-colors bg-[#e8dff0] text-gray-700 hover:bg-[#d4c4e3]"
              >
                Announcements
              </button>
            </div>
          </div>
        </div>
      </div>

      {/* Content Area */}
     <div className="max-w-7xl mx-auto px-6 py-8">
  <div className={activeTab === "students" ? "block" : "hidden"}>
    <StudentsTable />
  </div>

  <div className={activeTab === "parents" ? "block" : "hidden"}>
    <ParentsTable />
  </div>

  <div className={activeTab === "classes" ? "block" : "hidden"}>
    <ClassesTable />
  </div>

  <div className={activeTab === "teachers" ? "block" : "hidden"}>
    <TeachersTable />
  </div>

  <div className={activeTab === "addNews" ? "block" : "hidden"}>
    <AddNewsSection />
  </div>
</div>
    </div>
  );
}