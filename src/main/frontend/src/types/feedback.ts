export interface FeedbackDTO {
    id: number;
    teacherId: number;
    pupilId: number;
    subjectId: number;
    message: string;
    date: string; // string in this case. what
    grade: number;
}

export interface CreateFeedbackRequest {
    teacherId: number;
    pupilId: number;
    subjectId: number;
    message: string;
    grade: number;
}

export interface UpdateFeedbackRequest {
    message?: string;
    grade?: number;
}