import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { userService } from "../services/userService.ts";
import { UserType } from "../types/user.ts";

export default function SignUp() {
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [role, setRole] = useState<UserType | "">("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    localStorage.clear();

    if (password !== confirmPassword) {
      alert("Passwords don't match!");
      return;
    }

    if (!role) {
      alert("Please select a role");
      return;
    }

    try {
      setLoading(true);

      await userService.register({
        email,
        password,
        name: username,
        type: role,
      });

      // redirect to login after successful registration
      navigate("/landingpage");
    } catch (error: any) {
      console.error("Registration failed:", error);
      alert("Error while registering an account - check console");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-white flex flex-col items-center justify-center px-4 py-8">
      <div className="w-full max-w-[397px] mb-8 md:mb-16">
        <h1
          className="text-[#C9B59C] text-center font-bold tracking-[0.03em]"
          style={{
            fontSize: "clamp(48px, 8vw, 96px)",
            filter: "drop-shadow(0 4px 1px #AE9C86)",
            textShadow: "0 4px 1px rgba(174, 156, 134, 0.5)",
          }}
        >
          After School
        </h1>
      </div>

      <div className="w-full max-w-[721px] bg-[#F9F8F6] rounded-[48px] p-6 md:p-12 lg:p-16">
        <form onSubmit={handleSubmit} className="space-y-6 md:space-y-8">

          <div className="flex flex-col gap-2">
            <label className="text-[#C9B59C] text-xl font-bold">Username</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              className="w-full h-[60px] px-3 rounded-[10px] border border-[#C9B59C]"
            />
          </div>

          <div className="flex flex-col gap-2">
            <label className="text-[#C9B59C] text-xl font-bold">Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="w-full h-[60px] px-3 rounded-[10px] border border-[#C9B59C]"
            />
          </div>

          <div className="flex flex-col gap-2">
            <label className="text-[#C9B59C] text-xl font-bold">Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="w-full h-[60px] px-3 rounded-[10px] border border-[#C9B59C]"
            />
          </div>

          <div className="flex flex-col gap-2">
            <label className="text-[#C9B59C] text-xl font-bold">
              Confirm Password
            </label>
            <input
              type="password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
              className="w-full h-[60px] px-3 rounded-[10px] border border-[#C9B59C]"
            />
          </div>

          <div className="flex flex-col gap-2">
            <label className="text-[#C9B59C] text-xl font-bold">Role</label>
            <select
              value={role}
              onChange={(e) => setRole(e.target.value as UserType)}
              required
              className="w-full h-[60px] px-3 rounded-[10px] border border-[#C9B59C]"
            >
              <option value="" disabled>
                Select a role
              </option>
              <option value={UserType.ADMIN}>Admin</option>
              <option value={UserType.TEACHER}>Teacher</option>
              <option value={UserType.PARENT}>Parent</option>
              <option value={UserType.PUPIL}>Pupil</option>
            </select>
          </div>

          <div className="flex flex-col items-center gap-6 mt-12">
            <button
              type="submit"
              disabled={loading}
              className="w-full max-w-[230px] h-[60px] bg-[#9CB0C9] text-[#F9F8F6] text-xl font-bold rounded-[10px]"
            >
              {loading ? "Signing Up..." : "Sign Up"}
            </button>

            <p className="text-[#9CB0C9] font-bold">
              Already have an account?{" "}
              <Link to="/" className="underline">
                Log In!
              </Link>
            </p>
          </div>

        </form>
      </div>
    </div>
  );
}
