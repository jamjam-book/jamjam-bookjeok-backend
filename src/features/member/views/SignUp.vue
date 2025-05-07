<template>
    <div class="signup-container">
        <h2 class="signup-title">회원가입</h2>
        <form class="signup-form" @submit.prevent="handleSubmit">
            <div class="form-group">
                <label for="name">이름</label>
                <input id="name" type="text" v-model="name" placeholder="이름을 입력해주세요." />
            </div>

            <div class="form-group">
                <label for="userId">아이디</label>
                <div class="input-with-btn">
                    <input
                            id="userId"
                            type="text"
                            v-model="userId"
                            placeholder="6~15자, 영문과 숫자만"
                            @focus="checkPasswordLength"
                            @input="checkPasswordLength"
                            @blur="onUserIdBlur"
                    />
                    <button type="button" class="check-btn">중복확인</button>
                </div>
            </div>

            <div class="form-group">
                <label for="password">비밀번호</label>
                <input id="password" type="password" v-model="password" placeholder="8자 이상" />
            </div>

            <div class="form-group">
                <input
                        id="passwordCheck"
                        type="password"
                        v-model="passwordCheck"
                        placeholder="비밀번호 재입력"
                />
                <p v-if="passwordCheck && password !== passwordCheck" class="error-message">
                    비밀번호가 일치하지 않습니다.
                </p>
            </div>

            <div class="form-group">
                <label for="nickname">닉네임</label>
                <div class="input-with-btn">
                    <input
                            id="nickname"
                            type="text"
                            v-model="nickname"
                            placeholder="2~10자"
                            @focus="checkPasswordLength"
                            @input="checkPasswordLength"
                            @blur="onNicknameBlur"
                    />
                    <button type="button" class="check-btn">중복확인</button>
                </div>
            </div>

            <div class="form-group">
                <label for="email">E-mail</label>
                <input
                        id="email"
                        type="email"
                        v-model="email"
                        placeholder="예) bookjeok@bookjeok.com"
                        @focus="checkPasswordLength"
                        @input="checkPasswordLength"
                />
            </div>

            <div class="form-group">
                <label for="phone">전화번호</label>
                <input
                        id="phone"
                        type="tel"
                        v-model="phone"
                        placeholder="‘-’ 제외"
                        @input="onPhoneInput"
                />
            </div>

            <div class="form-group">
                <label for="birth">생년월일</label>
                <input
                        id="birth"
                        type="text"
                        v-model="birth"
                        placeholder="생년월일 입력 (예:970816)"
                        @blur="onBirthBlur"
                />
            </div>

            <div class="form-group toggle-group">
                <label for="marketing">마케팅 정보 수신 여부</label>
                <label class="toggle-switch">
                    <input id="marketing" type="checkbox" v-model="marketing" />
                    <span class="slider"></span>
                </label>
            </div>

            <button type="submit" class="signup-btn">회원가입</button>
        </form>

        <Modal
                :visible="modalVisible"
                :message="modalMessage"
                @close="handleModalClose"
        />
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import Modal from '@/components/common/Modal2.vue'

const router = useRouter()

const name = ref('')
const userId = ref('')
const password = ref('')
const passwordCheck = ref('')
const nickname = ref('')
const email = ref('')
const phone = ref('')
const birth = ref('')
const marketing = ref(false)

const modalVisible = ref(false)
const modalMessage = ref('')
const isSignupComplete = ref(false)

// 전화번호: 숫자만 입력, 숫자 외 입력 시 모달
function onPhoneInput(e) {
    const onlyNumbers = e.target.value.replace(/[^0-9]/g, '')
    if (e.target.value !== onlyNumbers) {
        modalMessage.value = '올바른 양식으로 작성해주세요!'
        modalVisible.value = true
    }
    phone.value = onlyNumbers
}

// 생년월일: 6자리가 아니면 모달
function onBirthBlur(e) {
    if (birth.value.length !== 6) {
        modalMessage.value = '올바른 양식으로 작성해주세요!'
        modalVisible.value = true
    }
}

// 비밀번호 길이 체크 함수
function checkPasswordLength(e) {
    if (password.value.length > 0 && password.value.length < 8) {
        modalMessage.value = '비밀번호는 8자 이상이어야 합니다!'
        modalVisible.value = true
        if (e && e.target) e.target.blur()
        return false
    }
    return true
}

// 아이디 blur시 유효성 검사
function onUserIdBlur(e) {
    if (!checkPasswordLength(e)) return false
    const regex = /^[a-zA-Z0-9]{6,15}$/
    if (!regex.test(userId.value)) {
        modalMessage.value = '올바른 양식으로 작성해주세요!'
        modalVisible.value = true
        return false
    }
    return true
}

// 닉네임 blur시 유효성 검사
function onNicknameBlur(e) {
    if (!checkPasswordLength(e)) return false
    if (nickname.value.length < 2 || nickname.value.length > 10) {
        modalMessage.value = '올바른 양식으로 작성해주세요!'
        modalVisible.value = true
        return false
    }
    return true
}

function handleModalClose() {
    modalVisible.value = false
    if (isSignupComplete.value) {
        router.push('/login')
        isSignupComplete.value = false
    }
}

const handleSubmit = () => {
    // 필수값 빈칸 체크
    if (
            !name.value.trim() ||
            !userId.value.trim() ||
            !password.value.trim() ||
            !passwordCheck.value.trim() ||
            !nickname.value.trim() ||
            !email.value.trim() ||
            !phone.value.trim() ||
            !birth.value.trim()
    ) {
        modalMessage.value = '모든 항목을 입력해주세요!'
        modalVisible.value = true
        return
    }

    if (!checkPasswordLength()) return
    const isUserIdValid = onUserIdBlur({})
    const isNicknameValid = onNicknameBlur({})
    if (password.value !== passwordCheck.value) {
        modalMessage.value = '비밀번호가 일치하지 않습니다.'
        modalVisible.value = true
        return
    }
    if (isUserIdValid && isNicknameValid) {
        modalMessage.value = '회원가입이 완료되었습니다!'
        modalVisible.value = true
        isSignupComplete.value = true
    }
}
</script>

<style scoped>
/* ... 기존 스타일 그대로 ... */
.signup-container {
    max-width: 430px;
    margin: 56px auto 0 auto;
    padding: 0 0 48px 0;
    background: #fff;
}
.signup-title {
    text-align: center;
    font-size: 2rem;
    font-weight: 700;
    color: #391902;
    margin-bottom: 38px;
    letter-spacing: -1px;
}
.signup-form {
    display: flex;
    flex-direction: column;
    gap: 0;
}
.form-group {
    display: flex;
    flex-direction: column;
    margin-bottom: 18px;
    position: relative;
}
.form-group label {
    font-size: 15px;
    font-weight: 500;
    color: #391902;
    margin-bottom: 7px;
}
.input-with-btn {
    position: relative;
    width: 100%;
    display: flex;
    align-items: center;
}
.input-with-btn input {
    width: 100%;
    height: 38px;
    padding: 0 70px 0 14px;
    border: 1px solid #e5e5e5;
    border-radius: 6px;
    font-size: 15px;
    background: #fff;
    box-sizing: border-box;
    outline: none;
    transition: border 0.2s;
}
.input-with-btn input:focus {
    border-color: #d1bfa3;
}
.input-with-btn .check-btn {
    position: absolute;
    right: 8px;
    top: 50%;
    transform: translateY(-50%);
    height: 28px;
    padding: 0 10px;
    background: #f9f0df;
    color: #391902;
    border: 1px solid #e5e5e5;
    border-radius: 5px;
    font-size: 13px;
    font-weight: 500;
    cursor: pointer;
    transition: background 0.2s;
    line-height: 28px;
    white-space: nowrap;
    box-shadow: none;
    min-width: 52px;
    text-align: center;
}
.input-with-btn .check-btn:hover {
    background: #f2e1c4;
}
input[type="text"],
input[type="password"],
input[type="email"],
input[type="tel"] {
    width: 100%;
    height: 38px;
    padding: 0 14px;
    border: 1px solid #e5e5e5;
    border-radius: 6px;
    font-size: 15px;
    background: #fff;
    box-sizing: border-box;
    outline: none;
    transition: border 0.2s;
    font-weight: 400;
}
input:focus {
    border-color: #d1bfa3;
}
.error-message {
    color: #e74c3c;
    font-size: 12px;
    margin: 4px 0 0 2px;
    line-height: 1.4;
}
.toggle-group {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 32px;
    padding-right: 0;
}
.toggle-group label:first-child {
    margin-right: 0;
    min-width: unset;
    font-size: 15px;
    color: #391902;
}
.toggle-switch {
    position: relative;
    display: inline-block;
    width: 38px;
    height: 22px;
    vertical-align: middle;
    margin-left: auto;
}
.toggle-switch input {
    opacity: 0;
    width: 0;
    height: 0;
}
.slider {
    position: absolute;
    cursor: pointer;
    top: 0; left: 0; right: 0; bottom: 0;
    background-color: #e5e5e5;
    border-radius: 22px;
    transition: background 0.2s;
}
.slider:before {
    position: absolute;
    content: "";
    height: 16px;
    width: 16px;
    left: 3px;
    bottom: 3px;
    background-color: #fff;
    border-radius: 50%;
    transition: transform 0.2s;
    box-shadow: 0 1px 4px rgba(0,0,0,0.08);
}
.toggle-switch input:checked + .slider {
    background-color: #f9f0df;
}
.toggle-switch input:checked + .slider:before {
    transform: translateX(16px);
}
.signup-btn {
    width: 100%;
    height: 38px;
    background: #f9f0df;
    color: #391902;
    font-size: 16px;
    font-weight: 600;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    margin-top: 16px;
    transition: background 0.2s;
    box-shadow: none;
    letter-spacing: 0.5px;
}
.signup-btn:hover {
    background: #f2e1c4;
}
</style>
