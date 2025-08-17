// auth.js (Pinia Store)
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import axios from 'axios'

/**
 * 안전한 JWT 디코드: 실패 시 null 반환
 */
function safeDecodeJwt(token) {
    try {
        const [, payloadB64] = token.split('.')
        if (!payloadB64) return null
        // 브라우저 환경의 atob()로 Base64 디코드
        const json = atob(payloadB64)
        return JSON.parse(json)
    } catch {
        return null
    }
}

export const useAuthStore = defineStore(
    'auth',
    () => {
        // --- state ---
        const accessToken = ref(null)      // string | null
        const userRole = ref(null)         // 'ADMIN' | 'MEMBER' | null
        const memberId = ref(null)         // string | null
        const expirationTime = ref(null)   // number(ms) | null

        // --- getters ---
        const isAuthenticated = computed(() => {
            return !!accessToken.value && Date.now() < (expirationTime.value || 0)
        })

        // --- actions ---
        /**
         * 정상 JWT로 로그인 상태 세팅
         * @param {string} at - JWT 액세스 토큰
         * @param {object} [options]
         * @param {boolean} [options.skipDecode=false] - true면 디코드 생략(개발용)
         * @param {string}  [options.role] - skipDecode일 때 적용할 역할
         * @param {number}  [options.expiresAt] - skipDecode일 때 만료 시각(ms)
         * @param {string}  [options.member] - 선택적 memberId
         */
        function setAuth(at, options = {}) {
            accessToken.value = at

            if (options.skipDecode) {
                // 개발용 강제 세팅: 디코드 없이 직접 주입
                userRole.value = options.role ?? 'MEMBER'
                expirationTime.value =
                    typeof options.expiresAt === 'number'
                        ? options.expiresAt
                        : Date.now() + 60 * 60 * 1000 // 기본 1시간
                if (options.member) memberId.value = options.member
            } else {
                // 정상 경로: JWT 디코드
                const payload = safeDecodeJwt(at)
                if (!payload || !payload.exp) {
                    // 토큰 형식 불량 → 초기화
                    clearAuth()
                    return
                }
                userRole.value = payload.role ?? 'MEMBER'
                expirationTime.value = payload.exp * 1000
                if (payload.memberId) memberId.value = payload.memberId
            }

            // persistedstate 플러그인이 관리하지만, 호환성 위해 저장
            localStorage.setItem('accessToken', at)
        }

        function clearAuth() {
            accessToken.value = null
            userRole.value = null
            memberId.value = null
            expirationTime.value = null
            localStorage.removeItem('accessToken')
        }

        async function login({ memberId: id, password }) {
            try {
                const response = await axios.post('/api/auth/login', { memberId: id, password })
                const token = response.data?.accessToken
                setAuth(token)            // 정상 JWT 경로
                memberId.value = id       // 백엔드에서 안 넣어줄 때 보강
                return true
            } catch (error) {
                console.error('로그인 실패:', error)
                return false
            }
        }

        async function signup({ name, memberId: id, password, nickname, email, phone, birth, marketing }) {
            try {
                await axios.post('/api/auth/signup', {
                    name, memberId: id, password, nickname, email, phone, birth, marketing
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

        /**
         * 🔧 개발용 강제 로그인 (JWT 없이도 UI를 로그인 상태로)
         * @param {{role?: 'ADMIN'|'MEMBER', minutes?: number, member?: string}} [opt]
         */
        function devForceLogin(opt = {}) {
            const minutes = Number(opt.minutes ?? 60)
            const expiresAt = Date.now() + minutes * 60 * 1000
            setAuth('dev-token', {
                skipDecode: true,
                role: opt.role ?? 'MEMBER',
                expiresAt,
                member: opt.member ?? 'user01',
            })
        }

        return {
            // state
            accessToken,
            userRole,
            memberId,
            expirationTime,
            // getters
            isAuthenticated,
            // actions
            setAuth,
            clearAuth,
            login,
            signup,
            logout,
            devForceLogin,
        }
    },
    {
        // pinia-plugin-persistedstate 사용 전제
        persist: true,
    }
)
