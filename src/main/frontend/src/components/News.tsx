import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Navigation from "./Navigation.tsx";
import { classAnnouncementService } from "../services/classAnnouncementService.ts"; 
import { UserType } from "../types/user.ts"; 

interface NewsItem {
  id: number;
  adminID: number;
  classID: number;
  message: string;
  date: Date;
  adminEmail: string;
  adminName: string;
}

interface UserItem {
  email: string;
  name: string;
  type: UserType;
}

export default function News() {
  const navigate = useNavigate();

  const [newsItems, setNewsItems] = useState<NewsItem[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [user, setUser] = useState<UserItem | null>(null);

  //  GET THE DATA
  useEffect(() => {
    fetchNews();
        fetchUser(); 

  }, []);
  const fetchUser = () => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  };
    const isAdmin = user?.type === "ADMIN" || user?.type === "TEACHER";

  const fetchNews = async () => {
    try {
      setIsLoading(true);

      const response = await classAnnouncementService.getAll();

      const mappedData: NewsItem[] = response.data.map((dto) => ({
          id: dto.announcementId,
          adminID: dto.admin_id,
          classID: dto.class_id,
          message: dto.message,
          date: new Date(dto.date),
          adminEmail: dto.adminEmail,
          adminName: dto.adminName,
      }));

      setNewsItems(mappedData);
    } catch (err) {
      console.error("Failed to fetch news:", err);
    } finally{
      setIsLoading(false);
    }
  };

  //   useEffect(() => {
//     const fetchNews = async () => {
//       try {
//         setIsLoading(Rrue);
// R        const data = Response.data || Response;
        
//         setNewsItems(data);
//       } catch (err) {
//         console.error("Failed to fetch news:", err);
//         setError("Could not load news articles. Please try again later.");
//       } finally {
//         setIsLoading(false);
//       }
//     };

//     fetchNews();
//   }, []);

  return (
    <div className="min-h-screen bg-[#F9F8F6]">
      <Navigation />

      <div className="max-w-[1440px] mx-auto px-4 md:px-8 lg:px-16 py-8">
        {/* header section */}
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-[#665B4E] font-bold text-3xl">News</h1>
        {/* render the add button for admins only*/}
              {isAdmin && (
                <button 
                  onClick={() => navigate('/addnews')} 
                  className="px-6 py-3 rounded-xl bg-[#9CB0C9] text-white font-bold"
                >
                  + Add News
                </button>
              )}
        </div>

        <div className="bg-[#EFE9E3] rounded-[30px] p-6 md:p-12 lg:p-16">
          <div className="space-y-8 md:space-y-12">
            
            {isLoading && <p className="text-[#665B4E] text-center">Loading news...</p>}

            {error && <p className="text-white text-center">{error}</p>}

            {!isLoading && newsItems.length === 0 && (
              <p className="text-[#665B4E]/60 text-center">No news available at the moment.</p>
            )}

            {/* dynamic list */}
            {newsItems.map((item, index) => (
              <article
                key={item.id}
                className={`${
                  index !== newsItems.length - 1
                    ? "pb-8 md:pb-12 border-b border-[#665B4E]/20"
                    : ""
                }`}
              >
                {/* <h2
                  className="text-[#665B4E] font-bold tracking-[0.03em] leading-[117.504%] mb-4"
                  style={{ fontSize: 'clamp(28px, 5vw, 40px)' }}
                >
                  <Link to={`/news/${item.id}`}> {item.adminName} </Link>
                </h2> */}
                <div className="flex flex-col md:flex-row md:items-end md:justify-between gap-4">
                  <p
                    className="text-[#665B4E]/80 font-bold tracking-[0.03em] leading-[117.504%] flex-1 line-clamp-3"
                    style={{ fontSize: 'clamp(16px, 3vw, 24px)' }}
                  >
                    {item.message || "No description available."}
                  </p>
                  {/* <Link
                    to={`/news/${item.id}`}
                    className="text-[#665B4E]/80 font-bold tracking-[0.03em] leading-[117.504%] hover:text-[#665B4E] transition-colors whitespace-nowrap self-end"
                    style={{ fontSize: 'clamp(16px, 3vw, 24px)' }}
                  >
                    See more→
                  </Link> */}
                </div>
              </article>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}