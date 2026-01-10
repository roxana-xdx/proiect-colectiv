import api from './api.ts';
import { PaymentDTO, CreatePaymentRequest, UpdatePaymentRequest } from '../types/payments';

export const paymentService = {
    /*
        POST /api/v1/payments
        give us money
    */
    create: async (data: CreatePaymentRequest) => {
        return api.post<PaymentDTO>('v1/payments', data);
    },

    /*
        GET /api/v1/payments
        find out how much money we got
    */
    getAll: async () => {
        return api.get<PaymentDTO[]>('v1/payments');
    },

    /*
        GET /api/v1/payments/overdue
        find out who we need to send hitmen after
    */
    getOverdue: async () => {
        return api.get<PaymentDTO[]>('v1/payments/overdue');
    },

    /*
        GET /api/v1/payments/recent
        find who our top 5 most recent generous donors are
    */
    getRecent: async () => {
        return api.get<PaymentDTO[]>('v1/payments/recent');
    },

    /*
        GET /api/v1/payments/parent/{parentId}
        see ho wmuch money we got from one specific individual
    */
    getByParentId: async (parentId: number) => {
        return api.get<PaymentDTO[]>(`v1/payments/parent/${parentId}`);
    },

    /*
        GET /api/v1/payments/parent/{parentId}/history
        get a history of all payments made by a specific parent
    */
    getParentHistory: async (parentId: number) => {
        return api.get<PaymentDTO[]>(`v1/payments/parent/${parentId}/history`);
    },

    /*
        GET /api/v1/payments/parent/{parentId}/pending
        get all paymens that a parent needs to pay us
    */
    getParentPending: async (parentId: number) => {
        return api.get<PaymentDTO[]>(`v1/payments/parent/${parentId}/pending`);
    },

    /*
        GET /api/v1/payments/parent/{parentId}/total-paid
        i feel like this is ditto from getByParentId but alright
    */
    getParentTotalPaid: async (parentId: number) => {
        return api.get<number>(`v1/payments/parent/${parentId}/total-paid`);
    },

    /*
        GET /api/v1/payments/parent/{parentId}/total-pending
        ditto again lol?
    */
    getParentTotalPending: async (parentId: number) => {
        return api.get<number>(`v1/payments/parent/${parentId}/total-pending`);
    },

    /*
        POST /api/v1/payments/{id}/pay
        confirm that money has been deposited directly into our bank accounts
    */
    markAsPaid: async (id: number) => {
        return api.post<void>(`v1/payments/${id}/pay`);
    },

    /*
        PUT /api/v1/payments/{id}
        update payment details
    */
    update: async (id: number, data: UpdatePaymentRequest) => {
        return api.put<PaymentDTO>(`v1/payments/${id}`, data);
    },

    /*
        DELETE /api/v1/payments/{id}
        delete a payment. :(
    */
    delete: async (id: number) => {
        return api.delete<void>(`v1/payments/${id}`);
    }
};