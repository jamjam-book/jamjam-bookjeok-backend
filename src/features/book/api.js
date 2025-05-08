import axios from "axios";

const api = axios.create({
    baseURL : 'http://localhost:8080/api/v1'
});

export const getBookList = params => api.get(`/book/list`, {params})

export const getCategoryList = () => api.get(`/book/ca/list`)

export const getPriceRange = params => api.get(`/book/pricerange`, {params})

export const getBookDetail = bookId => api.get(`/book/${bookId}`)

export const getAuthorOtherBooks = params => api.post(`/book/author/others`, params)

export const getReviews = bookId => api.get(`/book/${bookId}/reviews`)

export const reviewRegister = ( bookId, params ) => api.post(`/book/${bookId}/review`, params)
