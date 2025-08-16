<template>
    <div class="main-login-wrap">
        <img class="main-login-logo" src="@/assets/images/logo.png" alt="Book적Book적 로고" />
        <div class="form-box">
            <form @submit.prevent="handleLogin">
                <div class="form-group">
                    <label for="memberId">아이디</label>
                    <input
                            id="memberId"
                            v-model="memberId"
                            type="text"
                            placeholder="아이디를 입력해주세요."
                    />
                </div>
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input
                            id="password"
                            v-model="password"
                            type="password"
                            placeholder="비밀번호를 입력해주세요."
                    />
                </div>
                <button class="login-button" type="submit">로그인</button>
            </form>
            <div class="links">
                <RouterLink to="/signup">회원가입</RouterLink>
                <RouterLink to="/find-id">아이디 찾기</RouterLink>
                <RouterLink to="/password/reset-link">비밀번호 찾기</RouterLink>
            </div>
        </div>

        <Modal
                :visible="modalVisible"
                :message="modalMessage"
                @close="modalVisible = false"
        />
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import Modal from '@/components/common/Modal2.vue'
import { useAuthStore } from '@/stores/auth'
import {loginUser} from "@/lib/user.js";

const memberId = ref('')
const password = ref('')
const router = useRouter()

// 모달 상태
const modalVisible = ref(false)
const modalMessage = ref('')

// Pinia 인증 스토어
const authStore = useAuthStore()

const handleLogin = async () => {
    try {
        const resp = await loginUser( {
            memberId: memberId.value,
            password: password.value
        })

        const at = resp.data.data.accessToken;
        // Pinia의 저장소에 access token 저장
        authStore.setAuth(at);
        await router.push('/')
    } catch (e) {
        modalMessage.value = '아이디 또는 비밀번호를 확인해주세요.';
        modalVisible.value = true;
    }
}
</script>

<style scoped>
/* ... 기존 스타일 그대로 ... */
.main-login-wrap {
    min-height: calc(100vh - 120px);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: flex-start;
    background: #fff;
    margin-top: 0;
    padding-top: 0;
}

.main-login-logo {
    width: 140px;
    height: auto;
    margin-bottom: 8px;
    display: block;
}

.form-box {
    width: 430px;
    margin-top: 0;
    padding-top: 0;
    background: none;
    border-radius: 0;
    box-shadow: none;
    padding: 0;
    display: flex;
    flex-direction: column;
    align-items: stretch;
}

.form-group {
    margin-bottom: 16px;
}

label {
    display: block;
    font-size: 15px;
    font-weight: 500;
    color: #391902;
    margin-bottom: 7px;
}

input {
    width: 100%;
    height: 38px;
    padding: 0 14px;
    border: 1px solid #e5e5e5;
    border-radius: 6px;
    font-size: 15px;
    background: #ffffff;
    color: #391902;
    box-sizing: border-box;
    outline: none;
    transition: border 0.2s;
}
input:focus {
    border-color: #d1bfa3;
}

.login-button {
    width: 100%;
    height: 38px;
    background-color: #f9f0df;
    color: #391902;
    font-size: 16px;
    font-weight: 600;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    margin-top: 4px;
    margin-bottom: 12px;
    transition: background-color 0.2s;
}
.login-button:hover {
    background-color: #f2e1c4;
}

.links {
    display: flex;
    justify-content: space-between;
    font-size: 13px;
    color: #391902;
    margin-top: 2px;
}
.links a {
    color: #391902;
    text-decoration: none;
    transition: text-decoration 0.2s;
}
.links a:hover {
    text-decoration: underline;
}

header {
    margin-bottom: 0;
    padding-bottom: 0;
}
</style>
