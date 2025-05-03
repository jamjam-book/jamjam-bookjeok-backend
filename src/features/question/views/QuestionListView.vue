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
                imageUrl : "main_482791348279137620.20240609071045.jpg",
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
const selectedCategoryId = ref(null);

const goToEdit = () => {
    router.push("/members/questions/1/edit")
};

const deleteQuestion = () => {
    // delete api 호출
};

const goToRegister = () => {
    router.push('/members/questions/register')
}

const currentPage = ref(1);
const filteredQuestions = computed(() => {
  if (selectedCategoryId.value === null) return questions;
  return questions.filter(q => q.categoryId === selectedCategoryId.value);
});
const totalItems = computed(() => filteredQuestions.value.length);
const totalPages = computed(() => Math.ceil(totalItems.value / 3));
</script>

<template>
    <div class="top-bar">
      <QuestionCategories
          :categories="categories"
          :list="list"
          v-model="selectedCategoryId"
      />
        <button class="inquiry-button" @click="goToRegister">문의하기</button>
    </div>
    <div class="questions-container">
      <div v-if="filteredQuestions.length === 0" class="empty-message">
        등록된 문의사항이 없습니다.
      </div>
      <template v-else>
        <QuestionDetail
            v-for="question in filteredQuestions"
            :key="question.id"
            :question="question"
            @edit="goToEdit"
            @delete="deleteQuestion"
        />
        <PagingBar
            :total-items="totalItems"
            :total-pages="totalPages"
            :current-page="currentPage"
            @page-change="(page) => currentPage = page"
        />
      </template>
    </div>
</template>

<style scoped>
.top-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

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
  align-items: center;
  padding: 40px 0;
}

.empty-message {
  font-size: 16px;
  color: #888;
  padding: 60px 0;
}
</style>