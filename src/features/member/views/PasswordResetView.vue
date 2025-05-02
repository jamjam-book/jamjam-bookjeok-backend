<script setup>
import FormInput from "@/features/member/components/FormInput.vue";

const passwordFields = [
        { id: 'new-password', label: '새 비밀번호', placeholder: '새로운 비밀번호를 입력해주세요.', type: 'password' },
        { id: 'confirm-password', label: '새 비밀번호 확인', placeholder: '비밀번호를 재입력 해주세요.', type: 'password' }
];
</script>

<template>
    <FormInput
            :fields="passwordFields"
            button-text="비밀번호 변경하기"
    />
</template>

<style scoped>

</style>
