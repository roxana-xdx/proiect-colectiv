import { useState, useEffect } from 'react'; 
import { useNavigate } from "react-router-dom";
import { userService } from '../services/userService.ts';
import { feedbackService } from '../services/feedbackService.ts';
import { UserDTO, UpdateUserRequest, UserType } from '../types/user.ts';
import { PaymentDTO, PaymentStatus } from '../types/payments.ts';
import { FeedbackDTO } from '../types/feedback.ts';
import { pupilService } from '../services/pupilService.ts';
import { parentService } from '../services/parentService.ts';
import { paymentService } from '../services/paymentService.ts';
import { scheduleService } from '../services/scheduleService.ts';
import { subjectService } from '../services/subjectService.ts';
import { ScheduleDTO } from '../types/schedule.ts';

export default function LandingPage() {
  const navigate = useNavigate();
  
  const [user, setUser] = useState<UserDTO | null>(null);
  const [payments, setPayments] = useState<PaymentDTO[]>([]);
  const [feedbacks, setFeedbacks] = useState<FeedbackDTO[]>([]);
  const [schedules, setSchedules] = useState<ScheduleDTO[]>([]);
  const [subjects, setSubjects] = useState<{id: number, name: string}[]>([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [editFormData, setEditFormData] = useState({ name: '', password: '' });

  // get me info . . .
  useEffect(() => {
    const data = localStorage.getItem("user");
    if (data) {
      const parsedUser: UserDTO = JSON.parse(data);
      setUser(parsedUser);
      setEditFormData({ name: parsedUser.name, password: '' });
      
      const loadAllData = async () => {
        setIsLoading(true);
        try {
          // get the data for everyone except admins (they have access to all pages anyway xd)
          if (parsedUser.type !== UserType.ADMIN) {
            const [schRes, subRes] = await Promise.all([
              scheduleService.getAll(),
              subjectService.getAll()
            ]);
            setSchedules(schRes.data);
            setSubjects(subRes.data);
          }

          // for each role
          if (parsedUser.type === UserType.PARENT) {
            const parent = (await parentService.getByEmail(parsedUser.email)).data;
            const payRes = await paymentService.getByParentId(parent.id);
            setPayments(payRes.data);
          } else if (parsedUser.type === UserType.PUPIL) {
            const pupil = (await pupilService.getByEmail(parsedUser.email)).data;
            const feedRes = await feedbackService.getByPupilId(pupil.id);
            setFeedbacks(feedRes.data);
          }
        } catch (e) {
          console.error("Error loading landing page data:", e);
        } finally {
          setIsLoading(false);
        }
      };

      loadAllData();
    } else {
      navigate("/");
    }
  }, [navigate]);

  const getSubjectName = (id: number) => {
    return subjects.find(s => s.id === id)?.name || `ID: ${id}`;
  };

  const handleUpdateUser = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!user) return;
    try {
      const requestBody: UpdateUserRequest = {
        name: editFormData.name,
        ...(editFormData.password ? { password: editFormData.password } : {})
      };
      const response = await userService.update(user.email, requestBody);
      localStorage.setItem("user", JSON.stringify(response.data));
      setUser(response.data);
      setIsEditModalOpen(false);
      alert("Profile updated!");
    } catch (e) { console.error(e); }
  };

  if (!user) return null;

  // check if we should show the column on the right or not
  const showContentColumn = user.type !== UserType.ADMIN;

  return (
    <div className="min-h-screen bg-[#f5f5f5] font-sans">
      <header className="bg-white border-b border-gray-200">
        <div className="flex items-center gap-8 px-6 py-4 max-w-[1600px] mx-auto">
          <div className="text-2xl text-gray-700 px-6 py-2 border-b-4 border-blue-500 font-bold">After School</div>
          <nav className="flex gap-2">
            <button onClick={() => navigate("/news")} className="px-6 py-2 bg-[#e5e5e5] text-gray-700 rounded hover:bg-gray-300">Home</button>
          </nav> 
        </div>
      </header>

      <div className="p-8 max-w-[1600px] mx-auto">
        <div className={`grid grid-cols-1 ${showContentColumn ? 'xl:grid-cols-4' : 'max-w-xl mx-auto'} gap-8 transition-all`}>
          
          {/* profile card */}
          <div className="bg-[#ede9e5] p-8 rounded-[30px] h-fit shadow-sm">
            <div className="mb-4">
               <span className="px-3 py-1 bg-white/50 rounded-full text-[10px] font-black uppercase tracking-widest text-gray-500 border border-gray-300">
                {user.type}
               </span>
            </div>
            <h2 className="text-gray-700 text-3xl mb-6 font-bold leading-tight">Welcome, <br/>{user.name}!</h2>
            <div className="w-24 h-24 bg-[#d5d0ca] rounded-2xl mb-8 flex items-center justify-center">
              <svg width="60" height="60" viewBox="0 0 60 60" fill="none">
                <circle cx="30" cy="20" r="12" stroke="#6b6560" strokeWidth="2" fill="none"/><path d="M10 50 Q10 35 30 35 Q50 35 50 50" stroke="#6b6560" strokeWidth="2" fill="none"/>
              </svg>
            </div>
            <div className="mb-8">
              <label className="block text-gray-600 mb-2 font-semibold">Email</label>
              <div className="w-full px-4 py-3 bg-white border border-gray-300 rounded-xl text-gray-700 truncate shadow-sm">{user.email}</div>
            </div>
            <div className="flex flex-col gap-3">
              <button onClick={() => setIsEditModalOpen(true)} className="w-full py-3 bg-[#9bb4ce] text-white font-bold rounded-xl hover:bg-[#8aa3bd] transition-all shadow-md">Edit Profile</button>
              <button onClick={() => { localStorage.removeItem("user"); navigate("/"); }} className="w-full py-3 bg-gray-300 text-gray-700 font-bold rounded-xl hover:bg-gray-400 transition-colors">Logout</button>
            </div>
          </div>

          {/* column on the right i was talking about earlier */}
          {showContentColumn && (
            <div className="xl:col-span-3 space-y-8">
              
              {/* the top section (payment for parents / feedback for kids) */}
              {(user.type === UserType.PARENT || user.type === UserType.PUPIL) && (
                <div className="bg-[#EFE9E3] rounded-[30px] p-8 md:p-12 shadow-sm">
                  {user.type === UserType.PARENT && (
                    <>
                      <h1 className="text-[#665B4E] font-bold text-3xl mb-10">Payment Directory</h1>
                      <div className="space-y-4">
                        {payments.map(p => (
                          <div key={p.id} className="grid grid-cols-5 gap-4 items-center bg-white/40 p-5 rounded-2xl">
                            <div className="col-span-2 font-bold text-[#665B4E]">{p.description}</div>
                            <div className="text-xl font-bold text-gray-800">${p.amount}</div>
                            <div className="text-gray-600 text-sm">{p.due_date.split(' ')[0]}</div>
                            <div className="text-right"><span className="px-3 py-1 rounded-full text-[10px] font-black bg-white text-gray-600 border border-gray-200">{p.status}</span></div>
                          </div>
                        ))}
                      </div>
                    </>
                  )}

                  {user.type === UserType.PUPIL && (
                    <>
                      <h1 className="text-[#665B4E] font-bold text-3xl mb-10">Academic Feedback</h1>
                      {/* ... (Feedback mapping same as before) */}
                      <div className="space-y-4">
                        {feedbacks.map(f => (
                          <div key={f.id} className="grid grid-cols-5 gap-4 items-center bg-white/40 p-5 rounded-2xl">
                            <div className={`text-3xl font-black ${f.grade >= 5 ? 'text-green-600' : 'text-red-600'}`}>{f.grade}</div>
                            <div className="col-span-3 text-[#665B4E] font-medium italic">"{f.message}"</div>
                            <div className="text-right text-gray-500 text-xs font-bold">{f.date}</div>
                          </div>
                        ))}
                      </div>
                    </>
                  )}
                </div>
              )}

              {/* the bottom section (schedule for all of them) */}
              <div className="bg-[#EFE9E3] rounded-[30px] p-8 md:p-12 shadow-sm min-h-[300px]">
                <h1 className="text-[#665B4E] font-bold text-3xl mb-10">Class Schedule</h1>
                
                {isLoading ? (
                   <div className="text-center py-10 text-[#665B4E]">Loading timetable...</div>
                ) : (
                  <>
                    <div className="grid grid-cols-6 gap-4 border-b border-[#dcd4cb] pb-4 mb-6 font-bold text-[#665B4E] text-sm uppercase">
                      <div className="col-span-2">Subject</div>
                      <div>Date</div>
                      <div>Start</div>
                      <div>End</div>
                      <div className="text-right">Class</div>
                    </div>
                    <div className="space-y-4">
                      {schedules.map((s) => (
                        <div key={s.id} className="grid grid-cols-6 gap-4 items-center bg-white/40 p-5 rounded-2xl">
                          <div className="col-span-2 font-bold text-[#665B4E]">{getSubjectName(s.subject_id)}</div>
                          <div className="text-gray-600 text-sm">{s.date}</div>
                          <div className="font-mono text-sm">{s.start_hour}</div>
                          <div className="font-mono text-sm">{s.end_hour}</div>
                          <div className="text-right text-gray-500 font-bold">#{s.class_id}</div>
                        </div>
                      ))}
                    </div>
                  </>
                )}
              </div>
            </div>
          )}
        </div>
      </div>
      
      {/* edit modal */}
      {isEditModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 backdrop-blur-[2px]">
          <div className="bg-white rounded-[40px] p-10 w-full max-w-md shadow-2xl mx-4">
            <h2 className="text-3xl font-bold text-[#665B4E] mb-2">Edit Account</h2>
            <form onSubmit={handleUpdateUser} className="space-y-6 mt-6">
              <div>
                <label className="block text-xs font-bold text-gray-400 uppercase mb-2">Full Name</label>
                <input type="text" value={editFormData.name} onChange={(e) => setEditFormData({...editFormData, name: e.target.value})} className="w-full px-5 py-3 rounded-2xl border border-gray-200 outline-none" required />
              </div>
              <div>
                <label className="block text-xs font-bold text-gray-400 uppercase mb-2">New Password</label>
                <input type="password" placeholder="Leave blank to keep current" value={editFormData.password} onChange={(e) => setEditFormData({...editFormData, password: e.target.value})} className="w-full px-5 py-3 rounded-2xl border border-gray-200 outline-none" />
              </div>
              <div className="flex gap-4 pt-4">
                <button type="button" onClick={() => setIsEditModalOpen(false)} className="flex-1 py-4 bg-gray-100 text-gray-500 font-bold rounded-2xl">Cancel</button>
                <button type="submit" className="flex-1 py-4 bg-[#9CB0C9] text-white font-bold rounded-2xl shadow-lg">Save</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}