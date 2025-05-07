import api from './axios.js'

/* 1. 회원 가입 */
export function registerUser(data) {
    return api.post('/users', data);
}

/* 2. 로그인 */
export function loginUser(data) {
    return api.post('/auth/login', data);
}

/* 3. 로그아웃 */
export function logoutUser() {
    return api.post('/auth/logout');
}

/* 4. 리프레시 토큰으로 토큰 재발급 */
export function refreshUserToken() {
    return api.post('/auth/refresh');
}

/* 5. 유저 목록 조회 */
export function getUsers() {
    return api.get('/admin/users');
}

/* 6. 내 정보 조회 */
export function getUserDetail() {
    return api.get('/users/me');
}
