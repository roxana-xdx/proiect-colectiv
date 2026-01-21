import api from './api.ts';
import { ScheduleDTO, CreateScheduleRequest, UpdateScheduleRequest } from '../types/schedule';

export const scheduleService = {
    /*
        POST /api/v1/schedules
        Schedule a new class
    */
    create: async (data: CreateScheduleRequest) => {
        return api.post<ScheduleDTO>('v1/schedules', data);
    },

    /*
        GET /api/v1/schedules
        Gte all scheduled classes
    */
    getAll: async () => {
        return api.get<ScheduleDTO[]>('v1/schedules');
    },

    /*
        GET /api/v1/schedules/{id}
        Get a specific scheduled class
    */
    getById: async (id: number) => {
        return api.get<ScheduleDTO>(`v1/schedules/${id}`);
    },

    /*
        GET /api/v1/schedules/class/{classId}
        get timetable for specific class
    */
    getByClassId: async (classId: number) => {
        return api.get<ScheduleDTO[]>(`v1/schedules/class/${classId}`);
    },

    /*
        GET /api/v1/schedules/teacher/{teacherId}
        get timetable for specific prof
    */
    getByTeacherId: async (teacherId: number) => {
        return api.get<ScheduleDTO[]>(`v1/schedules/teacher/${teacherId}`);
    },

    /*
        PUT /api/v1/schedules/{id}
        update timetable/class entry
    */
    update: async (id: number, data: UpdateScheduleRequest) => {
        return api.put<ScheduleDTO>(`v1/schedules/${id}`, data);
    },

    /*
        DELETE /api/v1/schedules/{id}
        NO MORE SCHOOL. (for this very specific class)
    */
    delete: async (id: number) => {
        return api.delete<void>(`v1/schedules/${id}`);
    }
};