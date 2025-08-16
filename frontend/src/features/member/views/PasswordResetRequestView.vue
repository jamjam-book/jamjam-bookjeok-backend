<script setup>
import {reactive, ref} from 'vue';
import * as yup from "yup";
import { createPasswordResetLink } from "@/features/member/passwordResetApi.js";
import Modal from "@/components/common/Modal2.vue";

const buttonText = '비밀번호 재설정 이메일 발송';
const showModal = ref(false);
const modalMessage = ref('');
const formData = reactive({
    memberId: ''
});

// 없는 회원인 경우 뜨는 모달창 닫기
const handleModalClose = () => {
    showModal.value = false;
    formData.memberId = '';  // 입력한 아이디 초기화

    window.location.reload();
};

// 유효성 검사 스키마
const schema = yup.object({
    memberId: yup
            .string()
            .required('아이디 입력은 필수입니다.')
});

const submitForm = async () => {
    try {
        // 유효성 검사하기
        await schema.validate(formData);

        // 비밀번호 재설정 링크 링크 보내기
        await createPasswordResetLink(formData);

        showModal.value = true;
        modalMessage.value = '비밀번호 재설정 이메일을 발송했습니다.';
    } catch (err) {
        console.log(err.response.data.errorCode);
        if (err instanceof yup.ValidationError) {
            alert(err.errors[0]);
        }  else if (err.response && err.response.data.errorCode === '1001') {
            showModal.value = true;
            modalMessage.value = err.response.data.message;
        } else {
            console.error("폼 제출 중 오류 발생:", err);
            alert('폼 제출 중 문제가 발생했습니다. 다시 시도해 주세요.');
        }
    }
};

</script>

<template>
    <div class="auth-container">
        <div class="input-box">
            <div class="id-input-group">
                <label for="memberId" class="label">아이디</label>
                <input
                        id="memberId"
                        type="text"
                        placeholder="가입한 아이디를 입력해주세요."
                        class="input"
                        v-model="formData.memberId"
                />
            </div>

            <button class="submit-button" @click="submitForm">
                {{ buttonText }}
            </button>
        </div>
    </div>

    <Modal :visible="showModal" :message="modalMessage" @close="handleModalClose"/>
</template>

<style scoped>
.auth-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 150px 150px 192px 150px;
}

.input-box {
    max-width: 430px;
    width: 100%;
    border: 1px solid #d9d9d9;
    border-radius: 8px;
    padding: 32px 24px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.id-input-group {
    display: flex;
    flex-direction: column;
    gap: 8px;
}


.label {
    font-size: 15px;
    font-weight: 500;
    color: #1e1e1e;
}

.input {
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

.submit-button {
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
    transition: background-color 0.2s
}

.submit-button:hover {
    background-color: #f4e7cb;
}
</style>
