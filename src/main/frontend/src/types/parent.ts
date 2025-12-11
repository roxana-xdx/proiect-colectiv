export interface ParentDTO {
    id: number;
    email: string;
    name: string;
}

export interface CreateParentRequest {
    email: string;
}