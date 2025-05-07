<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import Modal2 from '@/components/common/Modal2.vue'
import Modal from '@/components/common/Modal.vue'

const router = useRouter()

const nickname = ref('닉네임 01')
const phone = ref('01012345678')
const email = ref('asdfasdf@naver.com')
const marketing = ref(false)

const modalVisible = ref(false)
const modalMessage = ref('')

const confirmModalVisible = ref(false)
const passwordModalVisible = ref(false)
const passwordInput = ref('')
const withdrawSuccessModalVisible = ref(false)
const passwordErrorModalVisible = ref(false)

const handleWithdraw = () => {
    confirmModalVisible.value = true
}
const handleConfirmWithdraw = () => {
    confirmModalVisible.value = false
    passwordInput.value = ''
    setTimeout(() => {
        passwordModalVisible.value = true
    }, 200)
}
const handleCancelWithdraw = () => {
    confirmModalVisible.value = false
}
const handlePasswordConfirm = () => {
    if (passwordInput.value === 'test1234') {
        passwordModalVisible.value = false
        setTimeout(() => {
            withdrawSuccessModalVisible.value = true
        }, 300)
    } else {
        passwordModalVisible.value = false
        setTimeout(() => {
            passwordErrorModalVisible.value = true
        }, 300)
    }
}
const closePasswordErrorModal = () => {
    passwordErrorModalVisible.value = false
    setTimeout(() => {
        passwordModalVisible.value = true
    }, 200)
}
const handleWithdrawSuccessClose = () => {
    withdrawSuccessModalVisible.value = false
    router.push('/')
}
const handleSubmit = (e) => {
    e.preventDefault()
    if (nickname.value.length < 2 || nickname.value.length > 10) {
        modalMessage.value = '닉네임은 2자리 이상 10자리 이하여야 합니다!'
        modalVisible.value = true
        return
    }
    modalMessage.value = '변경사항이 저장되었습니다!'
    modalVisible.value = true
}
</script>

<template>
    <main class="profile-main">
        <h2 class="profile-title">프로필 관리</h2>
        <form class="profile-form" @submit="handleSubmit" autocomplete="off">
            <div class="form-group">
                <label for="nickname">닉네임</label>
                <input id="nickname" type="text" v-model="nickname" placeholder="닉네임 01" autocomplete="off" />
            </div>
            <div class="form-group">
                <label for="phone">전화번호</label>
                <input id="phone" type="tel" v-model="phone" placeholder="01012345678" autocomplete="off" />
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input id="email" type="email" v-model="email" placeholder="asdfasdf@naver.com" autocomplete="off" />
            </div>
            <div class="form-group toggle-group">
                <label for="marketing">마케팅 정보 수신 여부</label>
                <label class="toggle-switch">
                    <input id="marketing" type="checkbox" v-model="marketing" />
                    <span class="slider"></span>
                </label>
            </div>
            <div class="withdraw-row">
                <button type="button" class="withdraw-link" @click="handleWithdraw">회원탈퇴</button>
            </div>
            <button type="submit" class="profile-btn">변경</button>
        </form>

        <!-- 정말로 탈퇴하시겠습니까? (확인/취소) -->
        <Modal
                :visible="confirmModalVisible"
                message="정말로 탈퇴하시겠습니까?"
                @confirm="handleConfirmWithdraw"
                @cancel="handleCancelWithdraw"
        />

        <!-- 비밀번호 입력 모달 (확인만, 메시지+input+확인버튼) -->
        <Modal2
                :visible="passwordModalVisible"
                message="비밀번호를 입력해주세요"
                @close="passwordModalVisible = false"
        >
            <div class="modal2-password-content">
                <input
                        v-model="passwordInput"
                        type="password"
                        placeholder="비밀번호"
                        class="modal2-password-input"
                        @keyup.enter="handlePasswordConfirm"
                />
                <button class="modal2-confirm-btn" @click="handlePasswordConfirm">확인</button>
            </div>
        </Modal2>

        <!-- 비밀번호 불일치 모달 (확인만) -->
        <Modal2
                :visible="passwordErrorModalVisible"
                message="비밀번호가 일치하지 않습니다."
                @close="closePasswordErrorModal"
        />

        <!-- 탈퇴처리 완료 모달 (확인만, 메인으로 이동) -->
        <Modal2
                :visible="withdrawSuccessModalVisible"
                message="탈퇴처리 되었습니다."
                @close="handleWithdrawSuccessClose"
        />

        <!-- 닉네임 등 일반 알림 모달 (확인만) -->
        <Modal2
                :visible="modalVisible"
                :message="modalMessage"
                @close="modalVisible = false"
        />
    </main>
</template>

<style scoped>
.profile-main {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 60px 0 0 0;
}

.profile-title {
    width: 430px;
    margin: 0 auto 32px auto;
    font-size: 22px;
    font-weight: 700;
    color: #391902;
    text-align: left;
    letter-spacing: -1px;
}

.profile-form {
    width: 430px;
    margin: 0 auto;
    background: #fff;
    border-radius: 10px;
    border: 1.5px solid #e5e5e5;
    padding: 40px 32px 32px 32px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    gap: 0;
    box-shadow: 0 2px 8px rgba(120, 80, 40, 0.04);
    margin-bottom: 60px;
    position: relative;
    align-items: flex-start;
}
.form-group {
    display: flex;
    flex-direction: column;
    margin-bottom: 18px;
    width: 100%;
}
.toggle-group {
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    margin-top: 18px;
    margin-bottom: 0;
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
.form-group label {
    font-size: 15px;
    font-weight: 500;
    color: #391902;
    margin-bottom: 7px;
    letter-spacing: -0.5px;
}
.form-group input[type="text"],
.form-group input[type="tel"],
.form-group input[type="email"] {
    width: 100%;
    height: 38px;
    padding: 0 14px;
    border: 1.3px solid #e5e5e5;
    border-radius: 6px;
    font-size: 15px;
    background: #faf9f8;
    color: #391902;
    box-sizing: border-box;
    outline: none;
    transition: border 0.2s;
    font-weight: 400;
    letter-spacing: -0.2px;
}
.form-group input:focus {
    border-color: #d1bfa3;
}
.form-group input:disabled {
    background: #f5f5f5;
    color: #b8a898;
}
.form-group input::placeholder {
    color: #b8a898;
    font-weight: 400;
    letter-spacing: -0.2px;
}
/* 여기만 변경: 슬라이더와 회원탈퇴 사이 공백 넉넉히 */
.withdraw-row {
    width: 100%;
    display: flex;
    justify-content: flex-end;
    margin-bottom: 8px;
    margin-top: 32px; /* 기존 -8px → 32px로 넉넉하게 */
}
.withdraw-link {
    background: none;
    border: none;
    font-size: 12px;
    color: #b8a898;
    cursor: pointer;
    user-select: none;
    transition: color 0.2s;
    padding: 0;
    margin: 0;
}
.withdraw-link:hover {
    color: #a0886a;
    text-decoration: underline;
}
.profile-btn {
    margin-top: 16px;
    width: 100px;
    height: 38px;
    background: #6d381a;
    color: #fff;
    font-size: 16px;
    font-weight: 600;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    transition: background 0.2s;
    box-shadow: none;
    letter-spacing: 0.5px;
    align-self: center;
}
.profile-btn:hover {
    background: #391902;
}
.modal2-password-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 10px;
}
.modal2-password-input {
    width: 90%;
    height: 38px;
    padding: 0 14px;
    border: 1.3px solid #e5e5e5;
    border-radius: 6px;
    font-size: 15px;
    background: #faf9f8;
    color: #391902;
    box-sizing: border-box;
    outline: none;
    transition: border 0.2s;
    font-weight: 400;
    letter-spacing: -0.2px;
    margin-bottom: 18px;
}
.modal2-password-input:focus {
    border-color: #d1bfa3;
}
.modal2-confirm-btn {
    width: 100px;
    height: 32px;
    background: #6d381a;
    color: #fff;
    font-size: 15px;
    font-weight: 500;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background 0.2s;
}
.modal2-confirm-btn:hover {
    background: #391902;
}
</style>
