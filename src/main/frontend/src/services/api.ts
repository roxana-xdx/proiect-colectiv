import axios, { InternalAxiosRequestConfig } from 'axios';

const baseURL = process.env.REACT_APP_API_URL || "http://localhost:8080/api";
console.log("Axios Base URL is:", baseURL);

const api = axios.create({
  baseURL,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem('token');
    if (token && config.headers) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;
