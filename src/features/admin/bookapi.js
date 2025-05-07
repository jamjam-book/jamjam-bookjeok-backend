import axios from "axios";

const api = axios.create({
    baseURL : 'http://localhost:8888/api/v1/admin'
});

export const checkIsbn = isbn => api.post(`/book/check/${isbn}`)

export const getBookDetail = bookId => api.get(`/book/${bookId}`)

export const updateQuantity = ((bookId, quantity) =>
    api.put(`/book/${bookId}/mod`, null, {params: {quantity}}))

export const getBookList = params => api.get(`/book/list`, {params})

export const getCategoryList = () => api.get(`/book/ca/list`)