import { useState, useEffect } from 'react'; 
import { Link, useNavigate } from "react-router-dom";
import { userService } from '../services/userService';

interface UserItem{
    name: string;
    email: string;
    // password: string;
}

export default function LandingPage() {
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");

  const [user, setUser] = useState<UserItem | null>(null);

    // --- FETCH DATA ---
  useEffect(() => {
      const data = localStorage.getItem("user");
      if (data) {
          setUser(JSON.parse(data));
      }
      else {
        navigate("/");
      }
  }, []);

  //  const fetchUsers = async () => {
  //   try {

  //     const response = await userService.getAll();

  //     const mappedData: UserItem[] = response.data.map((dto: any) => ({
  //       email: dto.email,
  //       name: dto.name,   
  //       // password: dto.password
  //     }));    
  //   } catch (error) {
  //     console.error("Error fetching users:", error);
  //   }
  // };

  if (!user) {
    return null;
  }

  return (
    <div className="min-h-screen bg-[#f5f5f5]">
      {/* Header */}
      <header className="bg-white border-b border-gray-200">
        <div className="flex items-center gap-8 px-6 py-4">
          <div className="text-2xl text-gray-700 px-6 py-2 border-b-4 border-blue-500">
            After School
          </div>
          {/* <nav className="flex gap-2">
            <button className="px-6 py-2 bg-[#e5e5e5] text-gray-700 rounded"> 
              News
            </button>
          </nav> */}
        </div>
      </header>

      {/* Main Content */}
      <div className="p-8">
        <div className="grid grid-cols-2 gap-8 max-w-6xl">
          {/* Left Column - Parent Info */}
          <div className="bg-[#ede9e5] p-8">
            <h2 className="text-gray-700 text-4xl mb-6 ">Welcome, {user.name}!</h2>
             
            {/* Avatar */}
            <div className="w-24 h-24 bg-[#d5d0ca] rounded mb-6 flex items-center justify-center">
              <svg width="60" height="60" viewBox="0 0 60 60" fill="none">
                <circle cx="30" cy="20" r="12" stroke="#6b6560" strokeWidth="2" fill="none"/>
                <path d="M10 50 Q10 35 30 35 Q50 35 50 50" stroke="#6b6560" strokeWidth="2" fill="none"/>
              </svg>
            </div>

            {/* Email */}
            <div className="mb-6">
              <label className="block text-gray-600 mb-2">E-mail</label>
              <input 
                type="email" 
                value={user.email}
                className="w-full px-3 py-2 bg-white border border-gray-300 rounded text-gray-700"
              />
            </div>

            {/* Phone Number
            <div className="mb-6">
              <label className="block text-gray-600 mb-2">Phone Number</label>
              <input 
                type="tel" 
                defaultValue="+40..."
                className="w-full px-3 py-2 bg-white border border-gray-300 rounded text-gray-700"
              />
            </div> */}

            {/* Edit Button */}
            <button className="px-8 py-2 bg-[#9bb4ce] text-gray-700 rounded">
              Edit
            </button>

            <button 
                onClick={() => {
                  localStorage.removeItem("authenticatedUser");
                  navigate("/");
                }}
                className="px-8 py-2 bg-gray-300 text-gray-700 rounded hover:bg-gray-400 transition-colors"
              >
                Logout
              </button>
          </div>
        </div>
      </div>
    </div>
  );
}
