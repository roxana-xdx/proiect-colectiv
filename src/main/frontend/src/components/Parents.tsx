import { useState } from "react";
import { Link } from "react-router-dom";
import Navigation from "./Navigation.tsx";

interface Parent {
  id: number;
  name: string;
  email: string;
}

const parentsData: Parent[] = [
  { id: 1, name: "Parent 1", email: "E-mail 1 - something @gmail.com" },
  { id: 2, name: "Parent 2", email: "E-mail 2 - something @gmail.com" },
  { id: 3, name: "Parent 3", email: "E-mail 3 - something @gmail.com" },
  { id: 4, name: "Parent 4", email: "E-mail 4 - something @gmail.com" },
  { id: 5, name: "Parent 5", email: "E-mail 5 - something @gmail.com" },
];

export default function Parents() {
  const [searchQuery, setSearchQuery] = useState("");

  const filteredParents = parentsData.filter(
    (parent) =>
      parent.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      parent.email.toLowerCase().includes(searchQuery.toLowerCase())
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
              Parents - Sorted a - z
            </h1>

            <div className="relative">
              <input
                type="text"
                placeholder="Search Parent"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full md:w-[288px] h-[42px] px-6 py-3 rounded-[20px] bg-[#F9F8F6] text-[#665B4E] text-base font-bold tracking-[0.03em] placeholder:text-[#665B4E] focus:outline-none focus:ring-2 focus:ring-[#9CB0C9] transition-all"
              />
            </div>
          </div>

          <div className="space-y-6 md:space-y-8">
            {filteredParents.map((parent) => (
              <div
                key={parent.id}
                className="flex flex-col md:flex-row md:items-center justify-between gap-4 md:gap-6"
              >
                <div className="flex flex-col md:flex-row md:items-center gap-2 md:gap-8 flex-1">
                  <div className="min-w-[200px] md:min-w-[300px]">
                    <p
                      className="text-[#665B4E]/80 font-bold tracking-[0.03em] leading-[117.504%]"
                      style={{ fontSize: "clamp(18px, 3vw, 24px)" }}
                    >
                      {parent.name}
                    </p>
                  </div>

                  <div className="flex-1">
                    <p
                      className="text-[#665B4E]/80 font-bold tracking-[0.03em] leading-[117.504%]"
                      style={{ fontSize: "clamp(18px, 3vw, 24px)" }}
                    >
                      {parent.email}
                    </p>
                  </div>
                </div>

                <Link
                  to={`/parents/${parent.id}`}
                  className="px-6 py-5 rounded-[20px] min-w-[140px] md:min-w-[170px] h-[70px] flex items-center justify-center text-2xl font-bold tracking-[0.03em] leading-[117.504%] bg-[#9CB0C9] text-white hover:bg-[#8BA0B8] transition-colors"
                >
                  View More
                </Link>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
