// matches backend.dto.SubjectDTO
export interface SubjectDTO {
    id: number;
    name: string;
}

// matches backend.dto.subject.CreateSubjectRequest
export interface CreateSubjectRequest {
    name: string;
}

// matches backend.dto.subject.UpdateSubjectRequest
export interface UpdateSubjectRequest {
    // optional so we can partially upadte stuff
    name?: string; 
}