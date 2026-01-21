import api from './api.ts';
import { TeacherDTO, CreateTeacherRequest } from '../types/teacher';

export const teacherService = {
    /*
        GET /api/v1/teachers
        Retrieve a list of all teachers
    */
    getAll: async () => {
        // Returns an array of TeacherDTO
        return api.get<TeacherDTO[]>('/v1/teachers');
    },

    /*
        GET /api/v1/teachers/{id}
        Get teacher by ID
    */
    getById: async (id: number) => {
        return api.get<TeacherDTO>(`/v1/teachers/${id}`);
    },

    /*
        GET /api/v1/teachers/by-email/{email}
        Get a teacher by email
    */
    getByEmail: async (email: string) => {
        return api.get<TeacherDTO>(`/v1/teachers/by-email/${email}`);
    },

    /*
        POST /api/v1/teachers
        Creates teacher (needs email)
    */
    create: async (data: CreateTeacherRequest) => {
        return api.post<TeacherDTO>('/v1/teachers', data);
    },

    /*
        DELETE /api/v1/teachers/{id}
        Delete teacher by ID
    */
    delete: async (id: number) => {
        return api.delete<void>(`/v1/teachers/${id}`);
    },

    update: async (id: number, data: CreateTeacherRequest) => {
        return api.put<TeacherDTO>(`/v1/teachers/${id}`, data);
    },
    
};