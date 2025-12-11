export interface PupilDTO {
    id: number;
    email: string;
    name: string;
    // uses snake_case to match Java class_id. yet again
    class_id: number;
    parent_id: number;
}

// matches backend.dto.pupil.CreatePupilRequest
export interface CreatePupilRequest {
    email: string;
    class_id: number;
    parent_id: number;
}

// matches backend.dto.pupil.UpdatePupilRequest
export interface UpdatePupilRequest {
    // optional fields in case i only wish to update one of the IDs and not both. hopefully this is :thumbs_up:
    class_id?: number;
    parent_id?: number;
}