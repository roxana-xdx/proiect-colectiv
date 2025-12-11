
// if you are looking through this code and are going "heeeey wait a minute why do you keep switching camelCase and snake_case?!?!?!!!?!?"
// it is because conventions and backend matching. i do not apologize

export interface ScheduleDTO {
    id: number;
    teacher_id: number;
    subject_id: number;
    class_id: number;
    
    date: string; // "YYYY-MM-DD"
    start_hour: string;
    end_hour: string; // "HH:mm:ss" or "HH:mm"
}

export interface CreateScheduleRequest {
    teacher_id: number;
    subject_id: number;
    class_id: number;
    
    date: string; // "YYYY-MM-DD"
    start_hour: string;
    end_hour: string;
}

export interface UpdateScheduleRequest {

    // ur never gonna guess why all of these are optional. :^)

    teacher_id?: number;
    subject_id?: number;
    class_id?: number;
    date?: string;
    start_hour?: string;
    end_hour?: string;
}