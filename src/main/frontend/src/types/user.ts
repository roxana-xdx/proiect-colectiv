// matches User.Type in User.java
export enum UserType {
    ADMIN = 'ADMIN',
    TEACHER = 'TEACHER',
    PARENT = 'PARENT',
    PUPIL = 'PUPIL'
}

// matches backend.dto.UserDTO
export interface UserDTO {
    email: string;
    name: string;
    type: UserType;
}

// matches backend.dto.auth.LoginRequest
export interface LoginRequest {
    email: string;
    password: string;
}

// matches backend.dto.auth.RegisterRequest
export interface RegisterRequest {
    email: string;
    password: string;
    name: string;
    type: UserType;
}

// matches backend.dto.auth.UpdateRequest
export interface UpdateUserRequest {
    password?: string;
    name?: string;
}