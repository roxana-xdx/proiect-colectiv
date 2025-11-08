import React, { useState, useRef, useEffect } from "react";

export default function App() {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);

  // cand dropdown-u ii deschis -> il inchide daca dai click altundeva
  useEffect(() => {

    function handleClickOutside(event) {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setIsDropdownOpen(false);
      }
    }

    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside); 
  }, []);

  const grades = [
    { label: "Grade 1", disabled: false },
    { label: "Grade 2", disabled: false },
    { label: "Grade 3", disabled: false },
    { label: "Grade 4", disabled: false },
  ];

  return (
    <div className="bg-[#EAF6F0] rounded-xl p-6 min-h-screen relative">
      <header className="flex items-center justify-between mb-6 relative">
        {/*AS*/}
        <image
          className="bg-[#d9fff5] px-4 py-3 rounded-lg  text-[#2f7f6b] text-3xl font-normal"
          aria-label="AS label"> AS 
        </image>

        {/* add news (nu face nimica xd) */}
        <div className="flex items-center gap-4">
          <button
            className="bg-[#B8A7C1] rounded-md px-4 py-2 text-white font-semibold text-sm"
            aria-label="Add News"> Add News
          </button>

          {/* button dropdown for grades */}
          <div className="relative inline-block text-left" ref={dropdownRef}>
            <button
              onClick={() => setIsDropdownOpen((prev) => !prev)}
              className="bg-[#3B7D78] text-white font-semibold rounded-md px-5 py-2 text-sm"
              aria-label="dropdown grade">
                Choose grade ▼
            </button>

            {isDropdownOpen && (
              <ul className="absolute mt-1 bg-[#d6e9e7] rounded-md w-36 text-center font-semibold text-[#153C3B]">
                {grades.map(({ label, disabled }, idx) => (
                  <li
                    key={idx}
                    className={`cursor-pointer py-2 px-3 border-b border-[#b0cbc9] last:border-none ${
                      disabled
                        ? "text-[#7d9d9b] cursor-not-allowed"
                        : "hover:bg-[#a9ccc7]"
                    }`}
                    onClick={() => {
                      if (!disabled) {
                        alert(`${label}`.toLowerCase());
                        setIsDropdownOpen(false);
                      }
                    }}
                  >
                    {label}
                  </li>
                ))}
              </ul>
            )}
          </div>
        </div>
      </header>

      <div className="bg-white rounded-xl p-6 shadow-md">
        <h2 className="m-0 text-[24px] font-semibold text-[#144A3B] mb-2">
          Grade 2 Students
        </h2>

        <div className="inline-block bg-[#EAF6F0] px-2 py-1 rounded-md text-[#1B6A56] text-[15px] font-medium mb-6">
          Teacher: Mr. Robert Wilson
        </div>

        <table className="w-full text-[15px] table-fixed">
          <thead>
            <tr className="text-left text-[#1B6A56] border-[#F1F7F4] font-semibold">
              <th className="p-3 w-[5%]">#</th>
              <th className="p-3 w-[10%]">ID</th>
              <th className="p-3 w-[50%]">Student Name</th>
              <th className="p-3 w-[10%]">Attended School</th>
              <th className="p-3 w-[8%]">Had Lunch</th>
              <th className="p-3 w-[6%]">Actions</th>
            </tr>
          </thead>
          <tbody>
            {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((i) => (
              <tr key={i} className="border-b border-[#F1F7F4]">
                <td className="p-3">{i}</td>
                <td className="p-3">{11-i}</td>
                <td className="p-3">Alexandra Monteverde-Howard</td>
                <td className="p-3 text-center">
                  <input type="checkbox" />
                </td>
                <td className="p-3 text-center">
                  <input type="checkbox" />
                </td>
                <td className="p-3 text-center cursor-pointer" title="Delete">
                  🗑
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
