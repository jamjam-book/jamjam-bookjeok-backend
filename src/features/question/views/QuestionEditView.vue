<script setup>
import QuestionForm from "@/features/question/components/QuestionForm.vue";
</script>

<template>
  <div class="edit-page">
    <h2>문의 수정</h2>
    <QuestionForm
    :is-edit="true"
    :initial-data="{
      id: 1,
      title: '배송 문의',
      content: '배송 문의 드립니다.',
      imageUrl : 'main_482791348279137620.20240609071045.jpg',
      imageFileName: 'main_482791348279137620.20240609071045.jpg',
      date : '2025-04-01',
      answer: '주문번호 다시 남겨 주시면 확인 후 답변 드리겠습니다.',
      categoryId :1
    }"/>
  </div>
</template>

<style scoped>
.edit-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 40px 20px;
}

h2 {
  margin-bottom: 20px;
  font-size: 24px;
}
</style>