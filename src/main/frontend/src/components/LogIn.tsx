import { useState } from 'react';

export default function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('Sign up submitted:', { username, password, role });
  };

  return (
    <div className="min-h-screen bg-[#2a2a2a] flex items-center justify-center p-4">
      <div className="w-full max-w-md">
        <div className="text-gray-500 text-sm mb-6 px-2">
          Log in Page
        </div>
        
        <div className="bg-white rounded-lg shadow-xl p-12">
          <h1 className="text-center text-[#c4a57b] mb-12">
            After<br />School
          </h1>

          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <label htmlFor="username" className="block text-sm text-gray-500 mb-2">
                Username
              </label>
              <input
                type="text"
                id="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="Enter your username or E-mail"
                className="w-full px-4 py-3 bg-gray-50 border border-gray-200 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-300 focus:border-transparent text-sm placeholder:text-gray-400"
              />
            </div>

            <div>
              <label htmlFor="password" className="block text-sm text-gray-500 mb-2">
                Password
              </label>
              <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Enter your password"
                className="w-full px-4 py-3 bg-gray-50 border border-gray-200 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-300 focus:border-transparent text-sm placeholder:text-gray-400"
              />
            </div>

            <div>
              <label htmlFor="role" className="block text-sm text-gray-500 mb-2">
                Role
              </label>
              <input
                type="text"
                id="role"
                value={role}
                onChange={(e) => setRole(e.target.value)}
                placeholder="Enter your role"
                className="w-full px-4 py-3 bg-gray-50 border border-gray-200 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-300 focus:border-transparent text-sm placeholder:text-gray-400"
              />
            </div>

            <div className="pt-4">
              <button
                type="submit"
                className="w-full bg-[#8ba3bb] hover:bg-[#7a92a9] text-white py-3 rounded-md transition-colors"
              >
                Sign Up
              </button>
            </div>

            <div className="text-center text-sm text-gray-500 pt-2">
              Already have an account?{' '}
              <a href="#" className="text-[#8ba3bb] hover:underline">
                Log in
              </a>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
