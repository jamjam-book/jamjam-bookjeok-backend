<script setup>
import AdminList from "@/features/admin/components/AdminList.vue";
import PagingBar from "@/components/common/PagingBar.vue";
import {onMounted, reactive, ref} from "vue";
import {getMembers} from "@/features/admin/memberApi.js";
import MemberSearchBar from "@/features/admin/components/MemberSearchBar.vue";
import AdminMemberList from "@/features/admin/components/AdminMemberList.vue";

const tableHeaders = [
    "아이디", "이름", "이메일", "닉네임", "전화번호", "계정 생성 날짜", "마케팅 수신", "계정 상태"
];

const tableKeyMap = {
    "아이디": "memberId",
    "이름": "memberName",
    "이메일": "email",
    "닉네임": "nickname",
    "전화번호": "phoneNumber",
    "계정 생성 날짜": "createdAt",
    "마케팅 수신": "marketingConsent",
    "계정 상태": "activityStatus"
};

// 멤버 전체 목록
const members= ref([]);
// 페이징 처리
const pagination = reactive({
    currentPage: 1,
    totalPage: 1,
    totalItems: 0
})
// 검색 param
const searchParams = reactive({
    memberId: null,
    nickname : ''
})

const fetchMembers = async (page = 1) => {
    try {
        // page와 검색 파라미터 넘겨주기
        const {data: wrapper} = await getMembers({
            page,
            memberId: searchParams.memberId,
            nickname: searchParams.nickname
        });

        // 데이터 응답받기
        const respData = wrapper.data;
        members.value = respData.memberList || [];
        Object.assign(pagination, respData.pagination ?? {});
        console.log('회원 목록 조회', members.value);
        console.log('페이지 조회', respData.pagination);
    } catch (e) {
        console.log('회원 목록 조회 실패', e);
    }
}

// 검색시 사용하는 함수
const onSearch = params => {
    Object.assign(searchParams, params);
    pagination.currentPage = 1;
    fetchMembers();
}

onMounted(() => fetchMembers())
</script>

<template>
    <div id="table-wrapper">
        <MemberSearchBar @search="onSearch"/>
        <AdminMemberList :headers="tableHeaders" :rows="members" :keyMap="tableKeyMap"/>
    </div>
    <PagingBar
            v-bind="pagination"
            @page-changed="fetchMembers"
    />
</template>

<style scoped>
#table-wrapper {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    gap: 20px;
    margin-top: 40px;
}
</style>
