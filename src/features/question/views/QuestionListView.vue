<script setup>

import QuestionDetail from "@/features/question/components/QuestionDetail.vue";
import QuestionCategories from "@/features/question/components/QuestionCategories.vue";
import {useRouter} from "vue-router";
import PagingBar from "@/components/common/PagingBar.vue";
import {computed, ref} from "vue";

const router = useRouter();

const {questions, categories, list} = defineProps({
    questions: {
        type: Array,
        default: () => [
            {
                id: 1,
                title: '배송 문의',
                content: "배송 문의 드립니다.",
                imageUrl : "/images/cleancode.png",
                date : '2025-04-01',
                answer: "주문번호 다시 남겨 주시면 확인 후 답변 드리겠습니다.",
                categoryId :1
            },
            {
                id: 2,
                title: '주문 문의',
                content: "주문 문의드립니다.",
                date : '2025-04-02',
                answer: "주문번호 다시 남겨 주시면 확인 후 답변 드리겠습니다.",
                categoryId: 2
            },
            {
                id: 3,
                title: '회원 문의',
                date : '2025-04-03',
                content: "탈퇴 문의드립니다.",
                categoryId: 3
            },
        ]
    },
    categories: {
        type: Array,
        required: true,
        default: () => [
            {id: 1, name: '주문'},
            {id: 2, name: '회원'},
            {id: 3, name: '배송'},
        ]
    },
    list: {
        type: Boolean,
        default: true
    }
});

const editQuestion = () => {
    router.push("/questions/:questionId")
};

const deleteQuestion = () => {
    // delete api 호출
};

const goToQuestionForm = () => {
    router.push('/questions/regist')
}

const fetchQuestions = async() => {

}

const currentPage = ref(1);
const totalItems = computed(() => questions.length);
const totalPages = computed(() => Math.ceil(totalItems.value / 3));

</script>

<template>
    <div class="top-bar">
        <QuestionCategories :categories="categories" :list="list" @category-selected="fetchQuestions" />
        <button class="inquiry-button" @click="goToQuestionForm">문의하기</button>
    </div>
    <div class="questions-container">
        <QuestionDetail
                v-for="question in questions"
                :key="question.id"
                :question="question"
                @edit="editQuestion"
                @delete="deleteQuestion"
        />
        <PagingBar
                :total-items="totalItems"
                :total-pages="totalPages"
                :current-page="currentPage"
                @page-change="(page) => currentPage = page"
        />
    </div>
</template>

<style scoped>
/* 🔹 카테고리 + 문의하기 버튼 정렬 */
.top-bar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;
}

/* 🔸 오른쪽 버튼 */
.inquiry-button {
    color: #854d14;
    font-weight: 400;
    font-size: 14px;
    padding: 10px 20px;
    cursor: pointer;
    height: 44px;
    border: none;
    background-color: white;
}
.questions-container {
    display: flex;
    flex-direction: column;
    align-items: center; /* ← 중앙 정렬 핵심 */
    padding: 40px 0;
}
</style>