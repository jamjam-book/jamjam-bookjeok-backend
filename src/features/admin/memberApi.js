import axios from "axios";

const api = axios.create({
    baseURL : 'http://localhost:8080/api/v1'
});

export const getMembers = params => api.get("/admin/members", { params })

export const getMember = memberId => api.get(`/admin/members/${memberId}`)