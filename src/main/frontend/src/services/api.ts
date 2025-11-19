// src/services/api.js
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

// Add request interceptor for auth tokens if needed
api.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  error => Promise.reject(error)
);

export interface Student {
    id: number;
    name: string;
    age: number;
    classId?: number;
}


export const studentService = {
    getAll: () => api.get('/students'),
    getById: (id : number) => api.get(`/students/${id}`),
    create: (data : number) => api.post('/students', data),
    update: (id : number, data : number) => api.put(`/students/${id}`, data),
    delete: (id : number) => api.delete(`/students/${id}`)
};

export const teacherService = {
    getAll: () => api.get('/teachers'),
    getById: (id : number) => api.get(`/teachers/${id}`),
    create: (data : number) => api.post('/teachers', data),
    update: (id : number, data : number) => api.put(`/teachers/${id}`, data),
    delete: (id : number) => api.delete(`/teachers/${id}`)
};

export const classService = {
    getAll: () => api.get('/classes'),
    getById: (id : number) => api.get(`/classes/${id}`),
    create: (data : number) => api.post('/classes', data),
    update: (id : number, data : number) => api.put(`/classes/${id}`, data),
    delete: (id : number) => api.delete(`/classes/${id}`)
};

export const parentService = {
    getAll: () => api.get('/parents'),
    getById: (id : number) => api.get(`/parents/${id}`),
    create: (data : number) => api.post('/parents', data),
    update: (id : number, data : number) => api.put(`/parents/${id}`, data),
    delete: (id : number) => api.delete(`/parents/${id}`)
}

export default api;