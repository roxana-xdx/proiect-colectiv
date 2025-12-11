import { useState } from "react";
import Navigation from "./Navigation.tsx";

interface ClassItem {
  id: number;
  className: string;
  teacher: string;
  timetable: string;
}

const classesData: ClassItem[] = [
  {
    id: 1,
    className: "Class 1",
    teacher: "Teacher 1",
    timetable: "Date/Time 1",
  },
  {
    id: 2,
    className: "Class 2",
    teacher: "Teacher 2",
    timetable: "Date/Time 2",
  },
  {
    id: 3,
    className: "Class 3",
    teacher: "Teacher 3",
    timetable: "Date/Time 3",
  },
  {
    id: 4,
    className: "Class 4",
    teacher: "Teacher 4",
    timetable: "Date/Time 4",
  },
  {
    id: 5,
    className: "Class 5",
    teacher: "Teacher 5",
    timetable: "Date/Time 5",
  },
];

export default function ClassesAll() {
  const [searchQuery, setSearchQuery] = useState("");

  const filteredClasses = classesData.filter(
    (classItem) =>
      classItem.className.toLowerCase().includes(searchQuery.toLowerCase()) ||
      classItem.teacher.toLowerCase().includes(searchQuery.toLowerCase()) ||
      classItem.timetable.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <div className="min-h-screen bg-[#F9F8F6]">
      <Navigation />

      <div className="max-w-[1440px] mx-auto px-4 md:px-8 lg:px-16 py-8">
        <div className="bg-[#EFE9E3] rounded-[30px] p-6 md:p-12 lg:p-16">
          <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 mb-8 md:mb-12">
            <h1
              className="text-[#665B4E] font-bold tracking-[0.03em] leading-[117.504%]"
              style={{ fontSize: "clamp(24px, 4vw, 32px)" }}
            >
              Classes - Sorted a - z
            </h1>

            <div className="relative">
              <input
                type="text"
                placeholder="Search Class"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full md:w-[288px] h-[42px] px-6 py-3 rounded-[20px] bg-[#F9F8F6] text-[#665B4E] text-base font-bold tracking-[0.03em] placeholder:text-[#665B4E] focus:outline-none focus:ring-2 focus:ring-[#9CB0C9] transition-all"
              />
            </div>
          </div>

          <div className="hidden md:grid md:grid-cols-[300px_257px_300px_auto] gap-6 mb-6">
            <div
              className="text-[#665B4E] font-bold tracking-[0.03em] leading-[117.504%]"
              style={{ fontSize: "clamp(18px, 3vw, 24px)" }}
            >
              Class
            </div>
            <div
              className="text-[#665B4E] font-bold tracking-[0.03em] leading-[117.504%]"
              style={{ fontSize: "clamp(18px, 3vw, 24px)" }}
            >
              Teacher
            </div>
            <div
              className="text-[#665B4E] font-bold tracking-[0.03em] leading-[117.504%]"
              style={{ fontSize: "clamp(18px, 3vw, 24px)" }}
            >
              Timetable
            </div>
            <div></div>
          </div>

          <div className="space-y-4 md:space-y-6">
            {filteredClasses.map((classItem) => (
              <div
                key={classItem.id}
                className="grid grid-cols-1 md:grid-cols-[300px_257px_300px_auto] gap-4 md:gap-6 items-center"
              >
                <div className="md:col-span-1">
                  <p
                    className="text-[#665B4E]/80 font-bold tracking-[0.03em] leading-[117.504%]"
                    style={{ fontSize: "clamp(18px, 3vw, 24px)" }}
                  >
                    <span className="md:hidden text-[#665B4E] mr-2">Class:</span>
                    {classItem.className}
                  </p>
                </div>

                <div className="md:col-span-1">
                  <p
                    className="text-[#665B4E]/80 font-bold tracking-[0.03em] leading-[117.504%]"
                    style={{ fontSize: "clamp(18px, 3vw, 24px)" }}
                  >
                    <span className="md:hidden text-[#665B4E] mr-2">Teacher:</span>
                    {classItem.teacher}
                  </p>
                </div>

                <div className="md:col-span-1">
                  <p
                    className="text-[#665B4E]/80 font-bold tracking-[0.03em] leading-[117.504%]"
                    style={{ fontSize: "clamp(18px, 3vw, 24px)" }}
                  >
                    <span className="md:hidden text-[#665B4E] mr-2">Timetable:</span>
                    {classItem.timetable}
                  </p>
                </div>

                <div className="flex justify-end md:justify-start">
                  <button className="px-6 py-5 rounded-[20px] min-w-[140px] md:min-w-[170px] h-[70px] flex items-center justify-center text-2xl font-bold tracking-[0.03em] leading-[117.504%] bg-[#9CB0C9] text-white hover:bg-[#8BA0B8] transition-colors">
                    Edit
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
