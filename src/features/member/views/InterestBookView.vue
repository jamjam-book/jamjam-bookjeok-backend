<script setup>
import {ref, onMounted, reactive} from 'vue'
import InterestBookList from '@/features/member/components/InterestBookList.vue'
import PagingBar from "@/components/common/PagingBar.vue";
import InterestTab from "@/features/member/components/InterestTab.vue";

const interestBooks = ref([])

// 도서 정보 가져오기 : memberId를 매개변수에 넘겨줘야 함
const fetchInterestBooks = async () => {
    try {
        // 도서 정보 api로 가져오는 것으로 수정할 부분
        interestBooks.value = [
            {
                bookId: 1,
                bookName: '채식주의자',
                bookInfo: '문학상 수상작, 인간 본성에 대한 탐구',
                imageUrl: '/src/assets/images/book1.jpg',
                authorNames: ['한강']
            },
            {
                bookId: 2,
                bookName: '살인자의 기억법',
                bookInfo: '알츠하이머에 걸린 연쇄살인자의 회고록',
                imageUrl: '/src/assets/images/book2.jpg',
                authorNames: ['김영하']
            },
        ]
    } catch (e) {
        console.log('관심 도서 정보를 불러오지 못했습니다.', e);
    }
}

// 페이지 정보 넘기기
const pagination = reactive({
    currentPage: 1,
    totalPages: 1,
    totalItems: 0
});

onMounted(() => fetchInterestBooks())
</script>

<template>
    <InterestBookList :interestBooks="interestBooks" />
    <PagingBar
            v-bind="pagination"
            @page-changed="fetchInterestBooks"
    />
</template>
