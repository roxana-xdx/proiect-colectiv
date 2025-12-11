import Navigation from "./Navigation.tsx";

export default function Students() {
  return (
    <div className="min-h-screen bg-[#F9F8F6]">
      <Navigation />
      <div className="max-w-[1440px] mx-auto px-4 md:px-8 lg:px-16 py-8">
        <div className="bg-[#EFE9E3] rounded-[30px] p-6 md:p-12 lg:p-16 min-h-[600px] flex items-center justify-center">
          <div className="text-center">
            <h1 className="text-4xl md:text-5xl font-bold text-[#665B4E] mb-4">
              Students
            </h1>
            <p className="text-xl text-[#665B4E]/80">
              COMING SOON TO A DEMO NEAR YOU.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
