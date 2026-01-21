/*
    Matchy with <backend>/dto/TeacherDTO
*/
export interface TeacherDTO{
    teacherId: number;
    name: string;
    email: string;
}

/* 
    Interface for <backend>/dto/teacher/CreateTeacherRequest
*/
export interface CreateTeacherRequest {
    email: string;
}
