<script setup>
import QuestionForm from "@/features/question/components/QuestionForm.vue";
</script>

<template>
  <div class="create-page">
    <h2>문의 등록</h2>
    <QuestionForm/>
  </div>
</template>

<style scoped>
.create-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 40px 20px;
}

h2 {
  margin-bottom: 20px;
  font-size: 24px;
}
</style>
