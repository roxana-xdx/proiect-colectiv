// matches backend.dto.AdminDTO
export interface AdminDTO {
    id: number;
    email: string;
    name: string;
}

// ditto backend.dto.admin.CreateAdminRequest
export interface CreateAdminRequest {
    email: string;
}