/*
    Matches with <backend>/dto/ClassAnnouncementDTO
*/
export interface ClassAnnouncementDTO {
    announcementId: number;
    admin_id: number;
    class_id: number;
    message: string;
    date: string;
    adminEmail: string;
    adminName: string;
}


/* 
    Interface for <backend>/dto/classannouncement/CreateClassAnnouncementRequest
*/
export interface CreateClassAnnouncementRequest {
    admin_id: number;
    class_id: number;
    message: string;
    date: string;
}

/* 
    Interface for <backend>/dto/classannouncement/UpdateClassAnnouncementRequestd
*/
export interface UpdateClassAnnouncementRequest {
    message: string;
    date: string;
}