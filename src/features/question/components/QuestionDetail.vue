<template>
    <div class="inquiry-item">
        <!-- 상단: 문의 상태 & 답변 보기 버튼 -->
        <div class="status-bar">
            <!-- 문의 번호 -->
            <div class="inquiry-number">No. {{ question.id }} ({{ question.date }})</div>
            <div class="status-text">
                {{ question.title }}
                <span
                        v-if="question.answer && !showAnswer"
                        class="answer-button"
                        @click="showAnswer = true"
                >답변보기
                </span>
            </div>
        </div>

        <!-- 문의 내용 -->
        <div class="inquiry-content">
            {{ question.content }}
        </div>

        <!-- 이미지 (조건부 렌더링) -->
        <div v-if="question.imageUrl" class="inquiry-image">
            <img class="questionImg" :src="`/src/assets${question.imageUrl}`" alt="문의 이미지" />
        </div>

        <!-- 작성일 -->
        <div class="inquiry-date"></div>

        <!-- 답변 내용 -->
        <div v-if="showAnswer" class="answer-wrapper">
            <div class="answer-box">
                <p class="answer-text">
                    {{ question.answer }}
                </p>
            </div>
            <div class="collapse-btn" @click="showAnswer = false">
                <div class="collapse-box">
                    <span class="collapse-text">답변 접기</span>
                </div>
            </div>
        </div>

        <!-- 우측 하단 수정/삭제 -->
        <div class="bottom-actions">
            <button class="action-button edit" @click="$emit('edit', question)">수정</button>
            <button v-if="!question.answer" class="action-button delete" @click="$emit('delete', question.id)">삭제</button>
        </div>
    </div>
</template>

<script setup>
import {ref} from "vue";

const showAnswer = ref(false);

const { question } = defineProps({
    question : Object
})
const emit = defineEmits(['edit', 'delete'])
</script>

<style scoped>
.inquiry-item {
    width: 580px;
    padding: 16px 20px;
    box-sizing: border-box;
    margin-bottom: 24px;
    font-family: "Inter", sans-serif;
    border: 1px solid #eee;
    border-radius: 12px;
    background-color: #fff;
}

.status-bar {
    margin-bottom: 12px;
}

.status-text {
    font-size: 14px;
    font-weight: 400;
    color: #000;
}

.answer-button {
    justify-content: flex-end;
    margin-left: 410px;
    font-size: 13px;
    font-weight: 400;
    color: #d9d9d9;
    cursor: pointer;
}

.inquiry-number {
    font-size: 13px;
    font-weight: 400;
    color: lightgray;
    margin-bottom: 8px;
}

.inquiry-content {
    font-size: 13px;
    color: #000;
    margin-bottom: 8px;
}

.inquiry-date {
    font-size: 17px;
    font-weight: 200;
    color: #b3b3b3;
    margin-bottom: 12px;
}

.inquiry-image {
    margin-top: 12px;
    text-align: center;
}

.inquiry-image img {
    max-width: 100%;
    height: auto;
    border-radius: 8px;
    border: 1px solid #eee;
}

/* 답변 영역 */
.answer-wrapper {
    margin-top: 12px;
}

.answer-box {
    background-color: #fcfcfc;
   /* border: 1px solid #d9d9d9;
    border-radius: 25px;*/
    padding: 20px;
}

.answer-text {
    font-size: 14px;
    font-weight: 400;
    color: #000;
    line-height: 1.5;
}

.collapse-btn {
    display: flex;
    justify-content: flex-end;
    margin-top: 10px;
}

.collapse-box {
    background-color: #fffbeb;
    padding: 10px;
    border-radius: 8px;
    cursor: pointer;
}

.collapse-text {
    font-size: 14px;
    font-weight: 400;
    color: #000;
}

.bottom-actions {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 20px;
}

.action-button {
    padding: 6px 12px;
    font-size: 13px;
    border-radius: 6px;
    border: none;
    cursor: pointer;
}

.action-button.edit {
    margin-left: 20px;
    font-size: 13px;
    font-weight: 400;
    color: #d9d9d9;
    cursor: pointer;
}

.action-button.delete {
    margin-left: 20px;
    font-size: 13px;
    font-weight: 400;
    color: #d9d9d9;
    cursor: pointer;
}

.questionImg {
    max-width: 300px;
    height: auto;
    max-height: 300px;
}
</style>