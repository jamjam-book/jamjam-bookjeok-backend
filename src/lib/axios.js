import axios from 'axios'
import { useAuthStore } from '@/stores/auth'

// 공통 axios 인스턴스 생성
const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    headers: { 'Content-Type': 'application/json' },
    withCredentials: true, // HttpOnly 쿠키 등 사용 시 필요
})

// 요청 인터셉터: accessToken이 있으면 헤더에 자동 추가
api.interceptors.request.use((config) => {
    const authStore = useAuthStore()
    if (authStore.accessToken) {
        config.headers.Authorization = `Bearer ${authStore.accessToken}`
    }
    return config
})

// 응답 인터셉터: 토큰 만료시 재발급 처리
api.interceptors.response.use(
    (response) => response,
    async (error) => {
        const authStore = useAuthStore()
        const { config, response } = error

        if (!response) return Promise.reject(error)

        // refresh 요청 자체가 실패했다면 로그아웃 처리
        if (config.url.includes('/auth/refresh')) {
            await authStore.clearAuth()
            return Promise.reject(error)
        }

        // accessToken 만료로 인한 401 에러일 경우
        if (response.status === 401 && !config._retry) {
            config._retry = true

            try {
                // refresh 요청
                const refreshRes = await axios.post(
                    `${import.meta.env.VITE_API_BASE_URL}/auth/refresh`,
                    {},
                    { withCredentials: true }
                )
                const newToken = refreshRes.data.accessToken
                authStore.setAuth(newToken)

                // 원래 요청에 새 토큰 추가 후 재시도
                config.headers.Authorization = `Bearer ${newToken}`
                return api(config)
            } catch (refreshError) {
                await authStore.clearAuth()
                return Promise.reject(refreshError)
            }
        }

        return Promise.reject(error)
    }
)

export default api
