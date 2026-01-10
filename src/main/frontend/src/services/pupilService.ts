import api from './api.ts';
import { PupilDTO, CreatePupilRequest, UpdatePupilRequest } from '../types/pupil';

export const pupilService = {
    /*
        POST /api/v1/pupils
        Create a pupil and assign it to a class and parent
    */
    create: async (data: CreatePupilRequest) => {
        return api.post<PupilDTO>('v1/pupils', data);
    },

    /*
        GET /api/v1/pupils
        Get all them pupils
    */
    getAll: async () => {
        return api.get<PupilDTO[]>('v1/pupils');
    },

    /*
        GET /api/v1/pupils/{id}
        Get a certain, specific troublemaker (pupil) by ID
    */
    getById: async (id: number) => {
        return api.get<PupilDTO>(`v1/pupils/${id}`);
    },

    /*
        GET /api/v1/pupils/email/{email}
        Get a certain, specific pupil by email (they're, like, 4th graders. who even uses emails at 10??)
    */
    getByEmail: async (email: string) => {
        return api.get<PupilDTO>(`v1/pupils/email/${email}`);
    },

    /*
        PUT /api/v1/pupils/{id}
        Updates a pupil's data
    */
    update: async (id: number, data: UpdatePupilRequest) => {
        return api.put<PupilDTO>(`v1/pupils/${id}`, data);
    },

    /*
        DELETE /api/v1/pupils/{id}
        Sends a pupil's profile to the shadow realm
    */
    delete: async (id: number) => {
        return api.delete<void>(`v1/pupils/${id}`);
    }
};