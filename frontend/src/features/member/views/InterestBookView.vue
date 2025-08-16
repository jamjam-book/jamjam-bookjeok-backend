<script setup>
import {ref, onMounted, reactive} from 'vue'
import InterestBookList from '@/features/member/components/InterestBookList.vue'
import PagingBar from "@/components/common/PagingBar.vue";
import {getInterestBooks} from "@/features/member/interestApi.js";
import {useRoute} from "vue-router";

const route = useRoute();
/* 멤버 아이디 수정해야 하는 부분 (주석 해제 해야 하는 부분)*/
const memberId = ref('user02');
const interestBooks = ref([])

// 페이지 정보 넘기기
const pagination = reactive({
    currentPage: 1,
    totalPage: 1,
    totalItems: 0
});

// 도서 정보 가져오기 : memberId를 매개변수에 넘겨줘야 함
const fetchInterestBooks = async (page = 1) => {
    try {
        const { data: wrapper } = await getInterestBooks(
                {page},
                memberId.value
        );

        const respData = wrapper.data;
        interestBooks.value = respData.interestBookList || [];
        Object.assign(pagination, respData.pagination ?? {});
        console.log('관심 도서 목록을 조회했습니다.')
        console.log('관심 도서 목록 ', respData.interestBookList)
    } catch (e) {
        console.log('관심 도서 정보를 불러오지 못했습니다.', e);
    }
}


onMounted(() => fetchInterestBooks())
</script>

<template>
    <InterestBookList :interestBooks="interestBooks" />
    <PagingBar
            v-bind="pagination"
            @page-changed="fetchInterestBooks"
    />
</template>