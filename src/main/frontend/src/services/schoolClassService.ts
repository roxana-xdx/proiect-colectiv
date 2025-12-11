import api from './api.ts'; 
import { SchoolClassDTO, CreateSchoolClassRequest } from '../types/schoolClass';

export const classService = {
    /*
        GET /api/v1/classes
        Giveth me all classes 
     */
    getAll: async () => {
        return api.get<SchoolClassDTO[]>('/v1/classes');
    },

    /*
        GET /api/v1/classes/{id}
        Giveth me a class (by ID)
     */
    getById: async (id: number) => {
        return api.get<SchoolClassDTO>(`/v1/classes/${id}`);
    },

    /*
        GET /api/v1/classes/by-name/{name}
        Giveth me a class (by name now)
     */
    getByName: async (name: string) => {
        return api.get<SchoolClassDTO>(`/v1/classes/by-name/${name}`);
    },

    /*
        GET /api/v1/classes/by-teacher/{teacherId}
        Giveth me ALL classes (for a specific prof)
     */
    getByTeacherId: async (teacherId: number) => {
        return api.get<SchoolClassDTO[]>(`/v1/classes/by-teacher/${teacherId}`);
    },

    /*
        POST /api/v1/classes
        Spawn a new class
        (sends snake_case, receives camelCase data)
     */
    create: async (data: CreateSchoolClassRequest) => {
        return api.post<SchoolClassDTO>('/v1/classes', data);
    },

    /*
        PUT /api/v1/classes/{id}
        Update an existing class
    */
    update: async (id: number, data: CreateSchoolClassRequest) => {
        return api.put<SchoolClassDTO>(`/v1/classes/${id}`, data);
    },

    /*
        DELETE /api/v1/classes/{id}
        Delete a class (based on ID)
    */
    delete: async (id: number) => {
        return api.delete<void>(`/v1/classes/${id}`);
    }
};