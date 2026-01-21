import api from './api.ts';
import { 
    FeedbackDTO, 
    CreateFeedbackRequest, 
    UpdateFeedbackRequest 
} from '../types/feedback';

export const feedbackService = {
    /*
        POST /api/v1/feedbacks
        add feedback
    */
    create: async (data: CreateFeedbackRequest) => {
        return api.post<FeedbackDTO>('v1/feedbacks', data);
    },

    /*
        GET /api/v1/feedbacks
        get all feedbacks
    */
    getAll: async () => {
        return api.get<FeedbackDTO[]>('v1/feedbacks');
    },

    /*
        GET /api/v1/feedbacks/{id}
        get feedback by id
    */
    getById: async (id: number) => {
        return api.get<FeedbackDTO>(`v1/feedbacks/${id}`);
    },

    /*
        GET /api/v1/feedbacks/by-teacher/{teacherId}
        get feedback from one specific teacher
    */
    getByTeacherId: async (teacherId: number) => {
        return api.get<FeedbackDTO[]>(`v1/feedbacks/by-teacher/${teacherId}`);
    },

    /*
        GET /api/v1/feedbacks/by-pupil/{pupilId}
        get feedback that got got by one student
    */
    getByPupilId: async (pupilId: number) => {
        return api.get<FeedbackDTO[]>(`v1/feedbacks/by-pupil/${pupilId}`);
    },

    /*
        GET /api/v1/feedbacks/by-subject/{subjectId}
        Obține feedback-ul primit la o materie
    */
    getBySubjectId: async (subjectId: number) => {
        return api.get<FeedbackDTO[]>(`v1/feedbacks/by-subject/${subjectId}`);
    },

    /*
        GET /api/v1/feedbacks/by-pupil/{pupilId}/sorted
        Obține feedback-ul elevului sortat după notă (descrescător)
    */
    getByPupilIdSorted: async (pupilId: number) => {
        return api.get<FeedbackDTO[]>(`v1/feedbacks/by-pupil/${pupilId}/sorted`);
    },

    /*
        GET /api/v1/feedbacks/by-teacher/{teacherId}/subject/{subjectId}
        Obține feedback-ul dat de un profesor la o materie
    */
    getByTeacherAndSubject: async (teacherId: number, subjectId: number) => {
        return api.get<FeedbackDTO[]>(`v1/feedbacks/by-teacher/${teacherId}/subject/${subjectId}`);
    },

    /*
        PUT /api/v1/feedbacks/{id}
        Actualizează o înregistrare de feedback
    */
    update: async (id: number, data: UpdateFeedbackRequest) => {
        return api.put<FeedbackDTO>(`v1/feedbacks/${id}`, data);
    },

    /*
        DELETE /api/v1/feedbacks/{id}
        Șterge o înregistrare de feedback
    */
    delete: async (id: number) => {
        return api.delete<void>(`v1/feedbacks/${id}`);
    }
};