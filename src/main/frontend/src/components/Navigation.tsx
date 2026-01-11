import { Link, useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
import { userService } from "../services/userService.ts";
import { UserType } from "../types/user";

const navItems = [
  { name: "Teachers", path: "/teachers", allowedTypes: ["ADMIN"] },
  { name: "Students", path: "/students", allowedTypes: ["ADMIN", "TEACHER"] },
  { name: "Classes", path: "/classes", allowedTypes: ["ADMIN", "TEACHER", "STUDENT", "PARENT"] },
  { name: "Parents", path: "/parents", allowedTypes: ["ADMIN", "TEACHER"] },
  { name: "News", path: "/news", allowedTypes: ["ADMIN", "TEACHER", "STUDENT", "PARENT"] },
];

 interface UserItem {
    email: string;
    name: string;
    type: UserType;
}

export default function Navigation() {
  const location = useLocation();
  const [user, setUser] = useState<UserItem | null>(null);
  const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
    fetchUser();
  }, []);

  const fetchUser = async () => {
    try {
      setIsLoading(true);
      const response = await userService.getAll();
      setUser(response.data);
    } catch (error) {
      console.error("Error fetching users:", error);
    } finally {
      setIsLoading(false);
    }
  };
  const visibleNavItems = navItems.filter((item) => {
    if (!user) return false;
    
    return item.allowedTypes.includes(user.type);
  });

  if (isLoading) return <div className="h-[79px] bg-[#F9F8F6]" />; 

  return (
    <div className="w-full bg-[#F9F8F6] px-4 md:px-8 lg:px-16 py-6">
      <div className="max-w-[1440px] mx-auto flex flex-col md:flex-row items-center justify-between gap-4">
        <Link 
          to="/news"
          className="flex items-center justify-center bg-[#EFE9E3] rounded-[20px] px-8 py-6 min-h-[79px]"
        >
          <h2 
            className="text-[#665B4E] font-bold tracking-[0.03em] leading-[117.504%]"
            style={{ fontSize: 'clamp(24px, 4vw, 40px)' }}
          >
            After School
          </h2>
        </Link>

       <nav className="flex flex-wrap items-center justify-center gap-3 md:gap-4">
          {visibleNavItems.map((item) => {
            const isActive = location.pathname === item.path;
            return (
              <Link
                key={item.path}
                to={item.path}
                className={`px-6 md:px-8 py-5 rounded-[20px] min-w-[140px] md:min-w-[170px] h-[70px] flex items-center justify-center text-2xl font-bold transition-colors ${
                  isActive
                    ? "bg-[#9CB0C9] text-[#F9F8F6]"
                    : "bg-[#EFE9E3] text-[#665B4E] hover:bg-[#E5DED6]"
                }`}
              >
                {item.name}
              </Link>
            );
          })}
        </nav>
      </div>
    </div>
  );
}
