<script setup>

import FormInput from "@/features/member/components/FormInput.vue";

const memberIdFields = [{
    id: 'memberId',
    label: '아이디',
    placeholder: '가입한 아이디를 입력해주세요.',
    type: 'id'
}];

</script>

<template>
    <FormInput
            :fields="memberIdFields"
            button-text="비밀번호 재설정 이메일 발송"
    />
</template>

<style scoped>

</style>