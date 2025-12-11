import api from './api';
import { SubjectDTO, CreateSubjectRequest, UpdateSubjectRequest } from '../types/subject';

export const subjectService = {
    /*
        POST /api/v1/subjects
        create new mathematiks class (can be any actually)
    */
    create: async (data: CreateSubjectRequest) => {
        return api.post<SubjectDTO>('v1/subjects', data);
    },

    /*
        GET /api/v1/subjects
        get all mathematiks classes (can be any type of class actually. im lying)
    */
    getAll: async () => {
        return api.get<SubjectDTO[]>('v1/subjects');
    },

    /*
        GET /api/v1/subjects/{id}
        get a subject by ID
    */
    getById: async (id: number) => {
        return api.get<SubjectDTO>(`v1/subjects/${id}`);
    },

    /*
        PUT /api/v1/subjects/{id}
        change the subject's data
    */
    update: async (id: number, data: UpdateSubjectRequest) => {
        return api.put<SubjectDTO>(`v1/subjects/${id}`, data);
    },

    /*
        DELETE /api/v1/subjects/{id}
        make subject go bye bye
    */
    delete: async (id: number) => {
        return api.delete<void>(`v1/subjects/${id}`);
    }
};