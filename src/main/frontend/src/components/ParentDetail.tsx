import { Link, useParams } from "react-router-dom";
import Navigation from "./Navigation.tsx";

const parentsData: Record<string, { name: string; email: string }> = {
  "1": { name: "Parent 1", email: "E-mail 1 - something @gmail.com" },
  "2": { name: "Parent 2", email: "E-mail 2 - something @gmail.com" },
  "3": { name: "Parent 3", email: "E-mail 3 - something @gmail.com" },
  "4": { name: "Parent 4", email: "E-mail 4 - something @gmail.com" },
  "5": { name: "Parent 5", email: "E-mail 5 - something @gmail.com" },
};

export default function ParentDetail() {
  const { id } = useParams();
  const parent = parentsData[id || "1"];

  if (!parent) {
    return (
      <div className="min-h-screen bg-[#F9F8F6] flex items-center justify-center">
        <p className="text-2xl text-[#665B4E]">Parent not found</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#F9F8F6]">
      <Navigation />

      <div className="max-w-[1440px] mx-auto px-4 md:px-8 lg:px-16 py-8">
        <div className="bg-[#EFE9E3] rounded-[30px] p-6 md:p-12 lg:p-16">
          <Link
            to="/parents"
            className="inline-block text-[#665B4E]/80 font-bold tracking-[0.03em] hover:text-[#665B4E] transition-colors mb-8 text-xl"
          >
            ← Back to Parents
          </Link>

          <h1 className="text-4xl md:text-5xl font-bold text-[#665B4E] mb-6">
            {parent.name}
          </h1>

          <div className="space-y-4">
            <div>
              <h2 className="text-2xl font-bold text-[#665B4E] mb-2">Email</h2>
              <p className="text-xl text-[#665B4E]/80">{parent.email}</p>
            </div>

            <div className="mt-8">
              <h2 className="text-2xl font-bold text-[#665B4E] mb-4">
                Additional Information
              </h2>
              <p className="text-lg text-[#665B4E]/80">
                MORE CONTENT... COMING SOON TO A DEMO NEAR YOU.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
