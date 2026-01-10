import api from './api.ts';
import { ClassAnnouncementDTO, CreateClassAnnouncementRequest, UpdateClassAnnouncementRequest } from '../types/classAnnouncement';

export const classAnnouncementService = {
    /*
        POST /api/v1/announcements
        Create new announcement
    */
    create: async (data: CreateClassAnnouncementRequest) => {
        return api.post<ClassAnnouncementDTO>('v1/announcements', data);
    },

    /*
        GET /api/v1/announcements
        Get ALL the annoucements
    */
    getAll: async () => {
        return api.get<ClassAnnouncementDTO[]>('v1/announcements');
    },

    /*
        GET /api/v1/announcements/{id}
        Get an announcement by ID
    */
    getById: async (id: number) => {
        return api.get<ClassAnnouncementDTO>(`v1/announcements/${id}`);
    },

    /*
        GET /api/v1/announcements/class/{classId}
        Get announcements for a specific class
    */
    getByClassId: async (classId: number) => {
        return api.get<ClassAnnouncementDTO[]>(`v1/announcements/class/${classId}`);
    },

    /*
        GET /api/v1/announcements/admin/{adminId}
        Get announcements posted by a specific admin
    */
    getByAdminId: async (adminId: number) => {
        return api.get<ClassAnnouncementDTO[]>(`v1/announcements/admin/${adminId}`);
    },

    /*
        PUT /api/v1/announcements/{id}
        Update announcemend  
    */
    update: async (id: number, data: UpdateClassAnnouncementRequest) => {
        return api.put<ClassAnnouncementDTO>(`v1/announcements/${id}`, data);
    },

    /*
        DELETE /api/v1/announcements/{id}
        Delete an announcement (by ID)
    */
    delete: async (id: number) => {
        return api.delete<void>(`v1/announcements/${id}`);
    }
};