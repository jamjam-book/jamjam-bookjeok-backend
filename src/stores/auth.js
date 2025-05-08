// auth.js (Pinia Store)
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import axios from 'axios'

export const useAuthStore = defineStore('auth', () => {
    const accessToken = ref(null)
    const userRole = ref(null)
    const expirationTime = ref(null)

    const isAuthenticated = computed(() =>
        !!accessToken.value && Date.now() < (expirationTime.value || 0)
    )

    function setAuth(at) {
        accessToken.value = at
        try {
            const payload = JSON.parse(atob(at.split('.')[1]))
            userRole.value = payload.role
            expirationTime.value = payload.exp * 1000
            localStorage.setItem('accessToken', at)
        } catch (e) {
            clearAuth()
        }
    }

    function clearAuth() {
        accessToken.value = null
        userRole.value = null
        expirationTime.value = null
        localStorage.removeItem('accessToken')
    }

    async function login({memberId, password}) {
        try {
            const response = await axios.post('/api/auth/login', {memberId, password})
            const token = response.data.accessToken
            setAuth(token)
            return true
        } catch (error) {
            console.error('로그인 실패:', error)
            return false
        }
    }

    async function signup({name, memberId, password, nickname, email, phone, birth, marketing}) {
        try {
            await axios.post('/api/auth/signup', {
                name, memberId, password, nickname, email, phone, birth, marketing
            })
            return true
        } catch (error) {
            console.error('회원가입 실패:', error)
            return false
        }
    }

    function logout() {
        clearAuth()
    }

    return {
        accessToken,
        userRole,
        expirationTime,
        isAuthenticated,
        setAuth,
        clearAuth,
        login,
        signup,
        logout,
    }
}, {
    persist: true
})
