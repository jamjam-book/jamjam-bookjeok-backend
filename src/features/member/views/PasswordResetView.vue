<script setup>
import { reactive } from 'vue';
import * as yup from 'yup';
import { useRoute, useRouter } from 'vue-router';
import { resetPassword } from '@/features/member/passwordApi.js';

const route = useRoute();
const router = useRouter();

const formData = reactive({
    passwordResetToken: route.query.token,
    newPassword: '',
    confirmPassword: ''
});

const errors = reactive({
    newPassword: '',
    confirmPassword: ''
});

const schema = yup.object({
    newPassword: yup
            .string()
            .min(8, '비밀번호는 최소 8자 이상이어야 합니다.'),
    confirmPassword: yup
            .string()
            .oneOf([yup.ref('newPassword')], '비밀번호가 일치하지 않습니다.')
});

const validateField = async (fieldName) => {
    try {
        await schema.validateAt(fieldName, formData);
        errors[fieldName] = '';
    } catch (err) {
        errors[fieldName] = err.message;
    }
};

const submitForm = async () => {
    try {
        errors.newPassword = '';
        errors.confirmPassword = '';

        await schema.validate(formData);

        const { passwordResetToken, newPassword } = formData;

        await resetPassword({ passwordResetToken, newPassword });

        await router.push('/login');
    } catch (err) {
        if (err instanceof yup.ValidationError) {
            err.inner.forEach((error) => {
                if (error.path && errors[error.path] !== undefined) {
                    errors[error.path] = error.message;
                }
            });
        } else {
            console.error('폼 제출 중 오류 발생:', err);
            alert('폼 제출 중 문제가 발생했습니다. 다시 시도해주세요.');
        }
    }
};
</script>


<template>
    <div class="auth-container">
        <div class="input-box">
            <div class="password-reset-group">
                <label for="newPassword" class="label">새 비밀번호</label>
                <input
                        id="newPassword"
                        type="password"
                        placeholder="새 비밀번호를 입력해주세요."
                        class="input"
                        v-model="formData.newPassword"
                        @input="validateField('newPassword')"
                        @click="validateField('newPassword')"
                />
                <span v-if="errors.newPassword" class="error">{{ errors.newPassword }}</span>

                <label for="confirmPassword" class="label">새 비밀번호 확인</label>
                <input
                        id="confirmPassword"
                        type="password"
                        placeholder="비밀번호를 다시 입력해주세요."
                        class="input"
                        v-model="formData.confirmPassword"
                        @input="validateField('confirmPassword')"
                        @click="validateField('confirmPassword')"
                />
                <span v-if="errors.confirmPassword" class="error">{{ errors.confirmPassword }}</span>
            </div>

            <button class="submit-button" @click="submitForm">
                비밀번호 변경하기
            </button>
        </div>
    </div>
</template>

<style scoped>
.auth-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 150px 150px 192px 150px;
}

.input-box {
    max-width: 540px;
    width: 150%;
    border: 1px solid #d9d9d9;
    border-radius: 8px;
    padding: 32px 24px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.password-reset-group {
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

.error {
    color: red;
    font-size: 14px;
    margin-top: 4px;
}
</style>