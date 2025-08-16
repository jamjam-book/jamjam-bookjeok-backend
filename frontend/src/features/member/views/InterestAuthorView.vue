<script setup>
import {ref, onMounted, reactive} from 'vue'
import InterestAuthorList from '@/features/member/components/InterestAuthorList.vue'
import PagingBar from "@/components/common/PagingBar.vue";
import {getInterestAuthors} from "@/features/member/interestApi.js";
import {useRoute} from "vue-router";

const route = useRoute();
/* 멤버 아이디 수정해야 하는 부분 (주석 해제 해야 하는 부분)*/
const memberId = ref('user02');
// const memberId = ref(route.params.memberId);
const interestAuthors= ref([]);
const pagination = reactive({
    currentPage: 1,
    totalPage: 1,
    totalItems: 0
})


const fetchInterestAuthors = async (page = 1) => {
    try {
        const { data: wrapper } = await getInterestAuthors(
                { page },
                memberId.value
        );

        const respData = wrapper.data;
        interestAuthors.value = respData.interestAuthorList || [];
        Object.assign(pagination, respData.pagination ?? {});
        console.log('관심 작가 목록 조회', interestAuthors.value);
        console.log('페이지 조회', respData.pagination);
    } catch (e) {
        console.error('관심 작가 목록 조회 실패', e);
    }
}

onMounted(() => fetchInterestAuthors())
</script>

<template>
    <InterestAuthorList :interestAuthors="interestAuthors" />
    <PagingBar
            v-bind="pagination"
            @page-changed="fetchInterestAuthors"
    />
</template>
