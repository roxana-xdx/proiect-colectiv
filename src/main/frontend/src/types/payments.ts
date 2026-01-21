
// editors note : i LOVE money. hell yeah

export enum PaymentStatus {
    PENDING = 'PENDING',
    PAID = 'PAID',
    OVERDUE = 'OVERDUE',
    CANCELLED = 'CANCELLED'
}

export enum PaymentMethod {
    CASH = 'CASH',
    CARD = 'CARD',
    BANK_TRANSFER = 'BANK_TRANSFER'
}

// if you are looking through these you know the drill. matches backend.dto.PaymentDTO..
export interface PaymentDTO {
    id: number;
    amount: number; 
    payment_date: string; 
    due_date: string; // "yyyy-MM-dd HH:mm:ss" 
    status: PaymentStatus;
    description: string;
    parent_id: number;
    payment_method: PaymentMethod;
    parent_email: string; // i only care about the bank details . . . . . . . . 
}

// do i need to say it
export interface CreatePaymentRequest {
    amount: number;
    due_date: string; // "yyyy-MM-dd HH:mm:ss"
    description: string;
    parent_id: number;
    payment_method: PaymentMethod;
}

export interface UpdatePaymentRequest {
    // all optional fields for partial updates
    amount?: number;
    due_date?: string;
    description?: string;
}