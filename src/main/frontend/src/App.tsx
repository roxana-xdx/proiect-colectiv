import "./global.css";

import { Toaster } from 'sonner';
import { TooltipProvider } from "./components/ui/tooltip.tsx";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Index from "./components/Index.tsx";
import SignUp from "./components/SignUp.tsx";
import LogIn from "./components/LogIn.tsx";
import News from "./components/News.tsx";
import NewsDetail from "./components/NewsDetail.tsx";
import Teachers from "./components/Teachers.tsx";
import Students from "./components/Students.tsx";
import Classes from "./components/Classes.tsx";
import ClassesAll from "./components/ClassesAll.tsx";
import Parents from "./components/Parents.tsx";
import ParentDetail from "./components/ParentDetail.tsx";
import NotFound from "./components/NotFound.tsx";
import AddNews from "./components/AddNews.tsx";
import LandingPage from "./components/LandingPage.tsx"
const queryClient = new QueryClient();



export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <TooltipProvider>
        <Toaster position = "bottom-right"/>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Index />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/login" element={<LogIn/>} />
            <Route path="/landingpage" element={ <LandingPage/>}/>
            <Route path="/news" element={<News />} />
            <Route path="/addnews" element={<AddNews/>} />
            <Route path="/news/:id" element={<NewsDetail />} />
            <Route path="/teachers" element={<Teachers />} />
          <Route path="/students" element={<Students />} />
          <Route path="/classes" element={<Classes />} />
          <Route path="/classes/all" element={<ClassesAll />} />
          <Route path="/parents" element={<Parents />} />
          <Route path="/parents/:id" element={<ParentDetail />} />
          {/* add all routes above this one. this is the "oh shit oh fuck" response */}
            <Route path="*" element={<NotFound />} />
          </Routes>
        </BrowserRouter>
      </TooltipProvider>
    </QueryClientProvider>
  );
}
