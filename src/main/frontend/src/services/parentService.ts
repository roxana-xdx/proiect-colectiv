import api from './api';
import { ParentDTO, CreateParentRequest } from '../types/parent';

export const parentService = {
    /*
        POST /api/v1/parents
        Create parent
    */
    create: async (data: CreateParentRequest) => {
        return api.post<ParentDTO>('v1/parents', data);
    },

    /*
        GET /api/v1/parents
        Acquire a list of all parents
    */
    getAll: async () => {
        return api.get<ParentDTO[]>('v1/parents');
    },

    /*
        GET /api/v1/parents/{id}
        Get one *specific* parent (by id)
    */
    getById: async (id: number) => {
        return api.get<ParentDTO>(`v1/parents/${id}`);
    },

    /*
        GET /api/v1/parents/by-email/{email}
        Get one *specific* parent (by email)
    */
    getByEmail: async (email: string) => {
        return api.get<ParentDTO>(`v1/parents/by-email/${email}`);
    },

    /*
        DELETE /api/v1/parents/{id}
        Thanos Snap a parent's profile
    */
    delete: async (id: number) => {
        return api.delete<void>(`v1/parents/${id}`);
    }
};