import axios from "axios";

const api = axios.create({
    baseURL : 'http://localhost:8080/api/v1'
});

export const getPopularBooks = () => api.get(`/book/popular`)

export const getMainCategories = () => api.get(`/book/ca/main`)