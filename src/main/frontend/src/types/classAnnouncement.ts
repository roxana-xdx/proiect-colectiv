/*
    Matches with <backend>/dto/ClassAnnouncementDTO
*/
export interface ClassAnnouncementDTO {
    announcementId: number;
    adminId: number;
    classId: number;
    message: string;
    date: Date;
    adminEmail: string;
    adminName: string;
}


/* 
    Interface for <backend>/dto/classannouncement/CreateClassAnnouncementRequest
*/
export interface CreateClassAnnouncementRequest {
    adminId: number;
    classId: number;
    message: string;
    date: Date;
}

/* 
    Interface for <backend>/dto/classannouncement/UpdateClassAnnouncementRequest
*/
export interface UpdateClassAnnouncementRequest {
    message: string;
    date: Date;
}