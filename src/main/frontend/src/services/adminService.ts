import api from './api.ts';
import { AdminDTO, CreateAdminRequest } from '../types/admin';

export const adminService = {
    /*
        POST /api/v1/admins
        make admin from user
    */
    create: async (data: CreateAdminRequest) => {
        return api.post<AdminDTO>('v1/admins', data);
    },

    /*
        GET /api/v1/admins
        get all sysadmins
    */
    getAll: async () => {
        return api.get<AdminDTO[]>('v1/admins');
    },

    /*
        GET /api/v1/admins/{id}
        get admin by id
    */
    getById: async (id: number) => {
        return api.get<AdminDTO>(`v1/admins/${id}`);
    },

    /*
        GET /api/v1/admins/by-email/{email}
        get admin by email
    */
    getByEmail: async (email: string) => {
        return api.get<AdminDTO>(`v1/admins/by-email/${email}`);
    },

    /*
        DELETE /api/v1/admins/{id}
        banish an admin from the metaphorical heaven of admindom
    */
    delete: async (id: number) => {
        return api.delete<void>(`v1/admins/${id}`);
    }
};