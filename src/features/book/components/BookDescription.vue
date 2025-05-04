<template>
    <div class="book-description" ref="info">
        <h4 class="section-title">상세 정보 (description)</h4>
        <p class="description" v-html="formattedDescription"></p>

        <div class="basic-info">
            <div class="info-row header">기본정보</div>
            <div class="info-row">
                <span class="label">ISBN</span>
                <span>{{ book.isbn }}</span>
            </div>
            <div class="info-row">
                <span class="label">출판사</span>
                <span>{{ book.publisher }}</span>
            </div>
            <div class="info-row">
                <span class="label">출판일</span>
                <span>{{ book.publishDate }}</span>
            </div>
        </div>
    </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
    book: Object
});

const formattedDescription = computed(() => {
    return props.book.description
            ? props.book.description.replace(/\n/g, '<br/>')
            : '';
});
</script>

<style scoped>
.book-description {
    padding: 40px 0;
    font-family: sans-serif;
}

.section-title {
    margin-bottom: 20px;
    font-size: 16px;
    color: #333;
}

.description {
    margin-bottom: 24px;
    line-height: 1.6;
    color: #444;
}

.basic-info {
    background-color: #f8f8f8;
    padding: 16px;
    border-radius: 16px;
}

.info-row {
    display: flex;
    justify-content: space-between;
    padding: 6px 0;
    font-size: 14px;
}

.info-row.header {
    font-weight: bold;
    border-bottom: 1px solid #ddd;
    padding-bottom: 8px;
    margin-bottom: 8px;
}

.label {
    color: #555;
}
</style>
