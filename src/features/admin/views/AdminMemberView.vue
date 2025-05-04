<script setup>
import AdminList from "@/features/admin/components/AdminList.vue";
import PagingBar from "@/components/common/PagingBar.vue";
import AdminNav from "@/components/layout/AdminNav.vue";
import SearchBar from "@/components/common/SearchBar.vue";
import {onMounted, reactive, ref} from "vue";

const members = ref([])

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

/*이 부분은 api로 가져오는 부분! */
const tableRows = [
    {
        memberUid: 1,
        memberId: "user01",
        memberName: "김철수",
        email: "chulsoo@example.com",
        nickname: "철수",
        phoneNumber: "010-1234-5678",
        createdAt: "2024-05-01",
        marketingConsent: "Y",
        activityStatus: "activation"
    },
    {
        memberUid: 2,
        memberId: "user02",
        memberName: "이영희",
        email: "younghee@example.com",
        nickname: "영희",
        phoneNumber: "010-2345-6789",
        createdAt: "2024-05-02",
        marketingConsent: "N",
        activityStatus: "activation"
    },
    {
        memberUid: 3,
        memberId: "user03",
        memberName: "박민수",
        email: "minsoo@example.com",
        nickname: "민수",
        phoneNumber: "010-3456-7890",
        createdAt: "2024-05-03",
        marketingConsent: "Y",
        activityStatus: "activation"
    },
    {
        memberUid: 4,
        memberId: "user04",
        memberName: "최지은",
        email: "jieun@example.com",
        nickname: "지은",
        phoneNumber: "010-4567-8901",
        createdAt: "2024-05-04",
        marketingConsent: "Y",
        activityStatus: "activation"
    },
    {
        memberUid: 5,
        memberId: "user05",
        memberName: "정우성",
        email: "woosung@example.com",
        nickname: "우성",
        phoneNumber: "010-5678-9012",
        createdAt: "2024-05-05",
        marketingConsent: "N",
        activityStatus: "activation"
    },
    {
        memberUid: 6,
        memberId: "user06",
        memberName: "한지민",
        email: "jimin@example.com",
        nickname: "지민",
        phoneNumber: "010-6789-0123",
        createdAt: "2024-05-06",
        marketingConsent: "Y",
        activityStatus: "activation"
    },
    {
        memberUid: 7,
        memberId: "user07",
        memberName: "김범수",
        email: "beomsu@example.com",
        nickname: "범수",
        phoneNumber: "010-7890-1234",
        createdAt: "2024-05-07",
        marketingConsent: "Y",
        activityStatus: "activation"
    },
    {
        memberUid: 8,
        memberId: "user08",
        memberName: "이하늬",
        email: "hani@example.com",
        nickname: "하늬",
        phoneNumber: "010-8901-2345",
        createdAt: "2024-05-08",
        marketingConsent: "N",
        activityStatus: "activation"
    },
    {
        memberUid: 9,
        memberId: "user09",
        memberName: "서준호",
        email: "junho@example.com",
        nickname: "준호",
        phoneNumber: "010-9012-3456",
        createdAt: "2024-05-09",
        marketingConsent: "Y",
        activityStatus: "activation"
    },
    {
        memberUid: 10,
        memberId: "user10",
        memberName: "홍길동",
        email: "hong@example.com",
        nickname: "길동",
        phoneNumber: "010-0123-4567",
        createdAt: "2024-05-10",
        marketingConsent: "N",
        activityStatus: "activation"
    }, {
        memberUid: 11,
        memberId: "user10",
        memberName: "홍길동",
        email: "hong@example.com",
        nickname: "길동",
        phoneNumber: "010-0123-4567",
        createdAt: "2024-05-10",
        marketingConsent: "N",
        activityStatus: "activation"
    }
];

const itemsPerPage = 10;

const pagination = reactive({
    currentPage: 1,
    totalPages: 1,
    totalItems: 0
})

// 회원 목록 페이징 처리하기
const fetchMembers= (page = 1) => {
    const start = (page - 1) * itemsPerPage
    const end = start + itemsPerPage

    members.value = tableRows.slice(start, end)
    pagination.currentPage = page
    pagination.totalItems = tableRows.length
    pagination.totalPages = Math.ceil(tableRows.length / itemsPerPage)

    // 버튼 클릭시 최상단으로 올라갈 수 있게 조정
    window.scrollTo(0,0)
}

onMounted(() => fetchMembers())
</script>

<template>
    <div id="table-wrapper">
        <!-- searchBar 내 버전으로 만들기-->
        <SearchBar/>
        <AdminList :headers="tableHeaders" :rows="members" :key-map="tableKeyMap"/>
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
