import axios from "axios";

const api = axios.create({
    baseURL : 'http://localhost:8080/api/v1'
});

export const createPasswordResetLink = (payload) => {
    return api.post('/password/reset-link', payload, {
        headers: {
            'Content-Type': 'application/json'
        }
    });
};

export const resetPassword = (payload) => {
        return api.post('/password/reset', payload, {
        headers: {
            'Content-Type': 'application/json'
        }
    });
};