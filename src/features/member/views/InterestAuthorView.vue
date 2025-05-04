<script setup>
import {ref, onMounted, reactive} from 'vue'
import InterestAuthorList from '@/features/member/components/InterestAuthorList.vue'
import PagingBar from "@/components/common/PagingBar.vue";
import InterestTab from "@/features/member/components/InterestTab.vue";

const interestAuthors = ref([])

// 작가 정보 가져오기 : memberId를 매개변수에 넘겨줘야 함
const fetchInterestAuthors = async () => {
    try {
        // 작가 정보 api로 가져오는 것으로 수정할 부분
        interestAuthors.value = [
            { authorId: 1, authorName: '한강', bookList: ['채식주의자', '소년이 온다'] },
            { authorId: 2, authorName: '김영하', bookList: ['살인자의 기억법', '여행의 이유'] },

        ]

    } catch (e) {
        console.log('데이터를 불러오지 못했습니다.', e);
    }
}

// 페이지 정보 넘기기
const pagination = reactive({
    currentPage: 1,
    totalPages: 1,
    totalItems: 0
});

onMounted(() => fetchInterestAuthors())
</script>

<template>
    <InterestTab/>
    <InterestAuthorList :interestAuthors="interestAuthors" />
    <PagingBar
            v-bind="pagination"
            @page-changed="fetchInterestAuthors"
    />
</template>
