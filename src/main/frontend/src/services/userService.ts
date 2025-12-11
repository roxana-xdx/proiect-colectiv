import api from './api';
import { UserDTO, LoginRequest, RegisterRequest, UpdateUserRequest, UserType } from '../types/user';

export const userService = {
    /*
        POST /api/v1/users/register
        returns created userDTO
    */
    register: async (data: RegisterRequest) => {
        return api.post<UserDTO>('v1/users/register', data);
    },

    /*
        POST /api/v1/users/login
        returns user profile
    */
    login: async (data: LoginRequest) => {
        return api.post<UserDTO>('v1/users/login', data);
    },

    /*
        GET /api/v1/users
        returns all users
    */
    getAll: async () => {
        return api.get<UserDTO[]>('v1/users');
    },

    /*
        GET /api/v1/users/{email}
        uses encodeURIComponent for email because it has @ and .
    */
    getByEmail: async (email: string) => {
        return api.get<UserDTO>(`v1/users/${encodeURIComponent(email)}`);
    },

    /*
        GET /api/v1/users/{email}/type
        returns type (e.g. ADMIN, or whatever is defined at the top of ../types/user.ts)
    */
    getTypeByEmail: async (email: string) => {
        return api.get<UserType>(`v1/users/${encodeURIComponent(email)}/type`);
    },

    /*
        PUT /api/v1/users/{email}
        returns updated userDTO
    */
    update: async (email: string, data: UpdateUserRequest) => {
        return api.put<UserDTO>(`v1/users/${encodeURIComponent(email)}`, data);
    },

    /*
        DELETE /api/v1/users/{email}
        just deletes
    */
    delete: async (email: string) => {
        return api.delete<void>(`v1/users/${encodeURIComponent(email)}`);
    }
};