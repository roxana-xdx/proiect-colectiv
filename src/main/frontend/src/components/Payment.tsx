import { useState, useEffect } from "react";
import Navigation from "./Navigation.tsx";
import { paymentService } from "../services/paymentService.ts";
import { CreatePaymentRequest, PaymentStatus, PaymentMethod, UpdatePaymentRequest } from "../types/payments.ts";
import { UserType } from "../types/user.ts";

interface PaymentItem {
  id: number;
  parentId: number;
  parentEmail: string;
  amount: number;
  dueDate: string;
  createdAt: string;
  description: string;
  method: PaymentMethod;
  status: PaymentStatus;
}
interface UserItem {
  email: string;
  name: string;
  type: UserType;
}

export default function Payment() {
  const [payments, setPayments] = useState<PaymentItem[]>([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [user, setUser] = useState<UserItem | null>(null);

  const formatDateTime = (date: Date): string => {
    return date.toISOString().replace('T', ' ').substring(0, 19);
  };

  useEffect(() => { fetchPayments(); fetchUser(); }, []);
  const fetchUser = () => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  };
    const isAdmin = user?.type === "ADMIN";

  const fetchPayments = async () => {
    try {
      setIsLoading(true);
      const response = await paymentService.getAll();
      const mappedData: PaymentItem[] = response.data.map((dto: any) => ({
        id: dto.id,
        parentId: dto.parent_id,
        parentEmail: dto.parent_email,
        amount: dto.amount,
        dueDate: dto.due_date,
        createdAt: dto.payment_date || formatDateTime(new Date()),
        description: dto.description,
        method: dto.payment_method,
        status: dto.status || PaymentStatus.PENDING,
      }));
      setPayments(mappedData);
    } catch (error) {
      console.error("Error fetching payments:", error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleAddPayment = async (parentId: number, email: string, amount: number, dueDate: string, description: string) => {
    const formattedDueDate = dueDate.includes(' ') ? dueDate : `${dueDate} 00:00:00`;
    try {
      const payload: CreatePaymentRequest = {
        parent_id: parentId,
        amount: amount,
        due_date: formattedDueDate,
        description: description,
        payment_method: PaymentMethod.CASH
      };

      const response = await paymentService.create(payload);
      const newDTO = response.data;

      const newItem: PaymentItem = {
        id: newDTO.id,
        parentId: newDTO.parent_id,
        parentEmail: email,
        amount: newDTO.amount,
        dueDate: newDTO.due_date,
        createdAt: formatDateTime(new Date()),
        description: newDTO.description,
        method: newDTO.payment_method,
        status: newDTO.status
      };

      setPayments(prev => [...prev, newItem]);
      setIsAddModalOpen(false);
    } catch (error) {
      alert("Failed to create payment. Check parent ID.");
    }
  };

// const handleUpdatePayment = async (updatedData: PaymentItem) => {
//     try {
      
//       // lmao
//       alert("Update functionality is currently under development (To be implemented).");

//       /* // Logic to be enabled once backend is ready:
//       const requestPayload: UpdatePaymentRequest = { 
//           amount: updatedData.amount,
//           due_date: updatedData.dueDate,
//           description: updatedData.description,
//           payment_method: updatedData.method,
//           status: updatedData.status
//       };
//       await paymentService.update(updatedData.id, requestPayload);
//       */

//       // This allows the admin to see the changes in the list immediately - but wont persist yet
//       setPayments((prevPayments) =>
//         prevPayments.map((p) => (p.id === updatedData.id ? updatedData : p))
//       );

//     } catch (error) {
//       console.error("Failed to update payment:", error);
//       alert("Error updating payment - check console.");
//     }
//   };

  const handleDelete = async (id: number) => {
    try {
      await paymentService.delete(id);
      setPayments((prev) => prev.filter((p) => p.id !== id));
    } catch (error) {
      console.error("Delete failed:", error);
    }
  };

  const filteredPayments = payments.filter((p) =>
    p.parentEmail?.toLowerCase().includes(searchQuery.toLowerCase()) ||
    p.id.toString().includes(searchQuery)
  );

  

  const AddPaymentModal = ({ isOpen, onClose, onAdd }: any) => {
    const [email, setEmail] = useState("");
    const [pId, setPId] = useState("");
    const [amt, setAmt] = useState("");
    const [date, setDate] = useState("");
    const [desc, setDesc] = useState("Tuition Fee");

    if (!isOpen) return null;

    return (
      <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
        <div className="bg-white p-8 rounded-xl w-full max-w-md">
          <h3 className="text-2xl font-bold mb-4">New Payment</h3>
          <input placeholder="Parent ID" type="number" className="w-full p-2 border mb-3" value={pId} onChange={e => setPId(e.target.value)} />
          <input placeholder="Parent Email" className="w-full p-2 border mb-3" value={email} onChange={e => setEmail(e.target.value)} />
          <input placeholder="Amount" type="number" className="w-full p-2 border mb-3" value={amt} onChange={e => setAmt(e.target.value)} />
          <input type="date" className="w-full p-2 border mb-3" value={date} onChange={e => setDate(e.target.value)} />
          <input placeholder="Description" className="w-full p-2 border mb-4" value={desc} onChange={e => setDesc(e.target.value)} />
          <div className="flex justify-end gap-2">
            <button onClick={onClose} className="px-4 py-2 border rounded">Cancel</button>
            <button onClick={() => onAdd(Number(pId), email, Number(amt), date, desc)} className="px-4 py-2 bg-blue-500 text-white rounded">Create</button>
          </div>
        </div>
      </div>
    );
  };

  // const EditButton = ({ initialPaymentData, onUpdatePayment }: { initialPaymentData: PaymentItem, onUpdatePayment: (d: PaymentItem) => void }) => {
  //   const [open, setOpen] = useState(false);
  //   const [form, setForm] = useState(initialPaymentData);

  //   return (
  //     <>
  //       <button onClick={() => setOpen(true)} className="bg-[#9CB0C9] text-white px-4 py-2 rounded">Edit</button>
  //       {open && (
  //         <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
  //           <div className="bg-white p-8 rounded-xl w-full max-w-md text-black">
  //             <h3 className="text-xl font-bold mb-4">Edit Payment #{form.id}</h3>
  //             <label className="text-sm">Status</label>
  //             <select className="w-full p-2 border mb-3" value={form.status} onChange={e => setForm({...form, status: e.target.value as PaymentStatus})}>
  //               {Object.values(PaymentStatus).map(s => <option key={s} value={s}>{s}</option>)}
  //             </select>
  //             <label className="text-sm">Method</label>
  //             <select className="w-full p-2 border mb-4" value={form.method} onChange={e => setForm({...form, method: e.target.value as PaymentMethod})}>
  //               {Object.values(PaymentMethod).map(m => <option key={m} value={m}>{m}</option>)}
  //             </select>
  //             <div className="flex justify-end gap-2">
  //               <button onClick={() => setOpen(false)} className="px-4 py-2 border rounded">Cancel</button>
  //               <button onClick={() => { onUpdatePayment(form); setOpen(false); }} className="px-4 py-2 bg-blue-600 text-white rounded">Save</button>
  //             </div>
  //           </div>
  //         </div>
  //       )}
  //     </>
  //   );
  // };

  return (
    <div className="min-h-screen bg-[#F9F8F6]">
      <Navigation />
      <div className="max-w-[1440px] mx-auto px-4 py-8">
        <div className="bg-[#EFE9E3] rounded-[30px] p-6 md:p-12">
          <div className="flex justify-between items-center mb-12">
            <h1 className="text-[#665B4E] font-bold text-3xl">Payments Management</h1>
            <div className="flex gap-4">
              <button onClick={() => setIsAddModalOpen(true)} className="px-6 py-3 rounded-xl bg-[#9CB0C9] text-white font-bold">+ Add Payment</button>
              <input type="text" placeholder="Search by Email/ID..." value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)} className="px-6 py-3 rounded-xl focus:outline-none" />
            </div>
          </div>

          <div className="grid grid-cols-8 gap-4 border-b pb-4 mb-6 font-bold text-[#665B4E]">
            <div>ID</div>
            <div>Email</div>
            <div>Amount</div>
            <div>Due Date</div>
            <div>Method</div>
            <div>Status</div>
            {/* {isAdmin && <div>Actions</div>} */}
            {isAdmin && <div>Delete</div>}
          </div>
          {isLoading ? <div className="text-center py-10">Loading...</div> : (
            <div className="space-y-4">
              {filteredPayments.map((p) => (
                <div key={p.id} className="grid grid-cols-7 gap-4 items-center border-b pb-4 text-[#665B4E]">
                  <div className="font-mono text-sm">{p.id}</div>
                  <div className="truncate">{p.parentEmail}</div>
                  <div className="font-bold">${p.amount}</div>
                  <div className="text-sm">{p.dueDate.split(' ')[0]}</div>
                  <div className="text-xs bg-white/50 rounded px-2 py-1 w-fit">{p.method}</div>
                  <div className={`text-xs font-bold ${p.status === 'PAID' ? 'text-green-600' : 'text-orange-600'}`}>{p.status}</div>
                      {/* {isAdmin && (
                        <EditButton 
                          initialPaymentData={p} 
                          onUpdatePayment={handleUpdatePayment} 
                        />
                      )}   */}
                  <div>
                    {isAdmin && (
                      <button 
                        onClick={() => handleDelete(p.id)} 
                        className="bg-[#9CB0C9] text-white px-4 py-2 rounded text-xl font-bold mb-4"
                      >
                        Delete
                      </button>
                    )}
                  </div>
                </div>))}
        </div>
          )}
      <AddPaymentModal isOpen={isAddModalOpen} onClose={() => setIsAddModalOpen(false)} onAdd={handleAddPayment} />
        </div>
      </div>
    </div>
  );
}