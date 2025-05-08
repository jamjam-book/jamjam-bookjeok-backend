<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import MemberItem from "@/features/admin/components/MemberItem.vue";
import {getMember} from "@/features/admin/memberApi.js";

const route = useRoute()
const memberId = route.params.memberId;
const member = ref(null);
const isEditMode = ref(false);

// 핸드폰 번호 변환 format
const formatPhoneNumber = (number) => {
    if (!number) return '';
    return number
            .replace(/[^0-9]/g, '')
            .replace(/^(\d{3})(\d{3,4})(\d{4})$/, '$1-$2-$3');
};

// 날짜 변환 format
const formatDate = (datetime) => {
    if (!datetime) return '';
    return datetime.split('T')[0];
};

// 상태 변환 format
const formatConsent = (consent) => {
    return consent ? 'Y' : 'N';
};

onMounted(async () => {
    console.log(memberId);
    try{
        const respData = await getMember(memberId);
        member.value = respData.data.data.member;
        console.log("회원 정보를 조회했습니다.", respData.data.data.member);
        console.log("회원 정보를 조회했습니다.");
    } catch (e) {
        console.log('회원 정보를 불러오지 못했습니다.', e);
    }
})
</script>

<template>
    <div v-if="member" class="member-detail-wrapper">
        <div class="member-info-list">
            <MemberItem label="아이디" :value="member.memberId" />
            <MemberItem label="닉네임" :value="member.nickname" />
            <MemberItem label="이름" :value="member.memberName" />
            <MemberItem label="전화번호" :value="formatPhoneNumber(member.phoneNumber)" />
            <MemberItem label="이메일" :value="member.email" />
            <MemberItem label="마케팅 수신" :value="formatConsent(member.marketingConsent)" />
            <MemberItem label="계정 생성 날짜" :value="formatDate(member.createdAt)" />
            <MemberItem label="계정 상태">
                <template v-if="isEditMode">
                    <select v-model="member.activityStatus">
                        <option value="ACTIVE">ACTIVE</option>
                        <option value="DEACTIVATE">DEACTIVATE</option>
                        <option value="PERMANENT">PERMANENT</option>
                    </select>

                </template>
                <template v-else>
                    {{ member.activityStatus }}
                </template>
            </MemberItem>
        </div>
    </div>

    <button class="submit-btn"  @click="isEditMode = !isEditMode">
        {{ isEditMode ? '저장하기' : '수정하기' }}
    </button>
</template>

<style scoped>
.member-detail-wrapper {
    max-width: 720px;
    margin: 40px auto;
    padding: 30px;
    background-color: #fff;
    border-radius: 16px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}

.member-info-list {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.submit-btn {
    margin: 30px auto 0;
    display: block;
    background-color: #fcf4e6;
    border: none;
    padding: 14px 24px;
    border-radius: 10px;
    font-weight: bold;
    font-size: 16px;
    cursor: pointer;
}

.submit-btn:hover {
    background-color: #f7e4c7;
}
</style>
