import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { userService } from "../services/userService.ts";

export default function Index() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Login attempt:", { email, password });

    try {
      const response = await userService.login({ email, password });

      console.log(response.data.name + " < nume");
      console.log(response.data.email + " < email");
      console.log(response.data.type + " < type");

      localStorage.setItem("user", JSON.stringify(response.data));

      navigate("/landingpage");
    } catch (error: any) {
      console.error("Login failed:", error);
      alert("Login failed. Please try again.");
      return;
    }

    // // hardcoded check
    // if (password === "123") {
    //   navigate("/landingpage");
    //   alert("correct guess buzzer");
    // } else {
    //   // *loud incorrect buzzer*
    //   alert("loud incorrect buzzer");
    // }
  };

  return (
    <div className="min-h-screen bg-white flex flex-col items-center justify-center px-4 py-8">
      <div className="w-full max-w-[397px] mb-8 md:mb-16">
        <h1 
          className="text-[#C9B59C] text-center font-bold tracking-[0.03em] leading-[117.504%]"
          style={{
            fontSize: 'clamp(48px, 8vw, 96px)',
            filter: 'drop-shadow(0 4px 1px #AE9C86)',
            textShadow: '0 4px 1px rgba(174, 156, 134, 0.5)'
          }}
        >
          After School
        </h1>
      </div>

      <div 
        className="w-full max-w-[721px] bg-[#F9F8F6] rounded-[48px] p-6 md:p-12 lg:p-16"
        style={{
          boxShadow: '0 2px 8px rgba(0, 0, 0, 0.05)'
        }}
      >
        <form onSubmit={handleSubmit} className="space-y-8 md:space-y-12">
          <div className="flex flex-col gap-2">
            <label 
              htmlFor="username"
              className="text-[#C9B59C] text-xl font-bold tracking-[0.03em] leading-[117.504%]"
              style={{
                textShadow: '0 1px 1px rgba(0, 0, 0, 0.25)'
              }}
            >
              Email
            </label>
            <input
              id="email"
              type="text"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Enter your email"
              className="w-full h-[60px] px-3 py-4 rounded-[10px] border border-[#C9B59C] bg-[#F9F8F6] text-base font-bold tracking-[0.03em] placeholder:text-[#9CB0C9] placeholder:font-bold focus:outline-none focus:ring-2 focus:ring-[#C9B59C] focus:border-transparent transition-all"
              style={{
                boxShadow: '0 1px 1px 0 #C9B59C'
              }}
            />
          </div>

          <div className="flex flex-col gap-2">
            <label 
              htmlFor="password"
              className="text-[#C9B59C] text-xl font-bold tracking-[0.03em] leading-[117.504%]"
              style={{
                textShadow: '0 1px 1px #C9B59C'
              }}
            >
              Password
            </label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Enter your password"
              className="w-full h-[60px] px-3 py-4 rounded-[10px] border border-[#C9B59C] bg-[#F9F8F6] text-base font-bold tracking-[0.03em] placeholder:text-[#9CB0C9] placeholder:font-bold focus:outline-none focus:ring-2 focus:ring-[#C9B59C] focus:border-transparent transition-all"
              style={{
                boxShadow: '0 1px 1px 0 #C9B59C'
              }}
            />
          </div>

          <div className="flex flex-col items-center gap-6 mt-12">
            <button
              type="submit"
              className="w-full max-w-[230px] h-[60px] bg-[#9CB0C9] text-[#F9F8F6] text-xl font-bold tracking-[0.03em] leading-[117.504%] rounded-[10px] hover:bg-[#8BA0B8] transition-colors focus:outline-none focus:ring-2 focus:ring-[#9CB0C9] focus:ring-offset-2"
              style={{
                boxShadow: '0 1px 1px 0 #C9B59C'
              }}
            >
              Log In
            </button>

            <p className="text-[#9CB0C9] text-base font-bold tracking-[0.03em] leading-[117.504%] text-center">
              Don't have an account?{" "}
              <Link 
                to="/signup" 
                className="underline hover:text-[#8BA0B8] transition-colors"
              >
                Sign Up!
              </Link>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
}