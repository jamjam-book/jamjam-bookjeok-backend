<template>
    <div class="book-tabs">
        <div class="tab-buttons">
            <button :class="{ active: activeTab === 'info' }" @click="scrollTo('info')">상품 정보</button>
            <button :class="{ active: activeTab === 'review' }" @click="scrollTo('review')">리뷰 ({{ reviewCount }})</button>
            <button :class="{ active: activeTab === 'policy' }" @click="scrollTo('policy')">교환/환불/품절</button>
        </div>

        <div ref="info" class="tab-section">
            <BookDescription :book="book" />
        </div>

        <div ref="review" class="tab-section">
            <BookReviews :reviews="book.reviews" :ratingCounts="ratingCounts" :isPurchaser="true"/>
        </div>

        <div ref="policy" class="tab-section">
            <BookPolicy />
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import BookDescription from '@/features/book/components/BookDescription.vue';
import BookReviews from "@/features/book/components/BookReviews.vue";
import BookPolicy from "@/features/book/components/BookPolicy.vue";

const props = defineProps({
    book: Object,
});

const reviewCount = ref(props.book.reviews.length);

const ratingCounts = [1, 1, 1, 0, 7]

const activeTab = ref('info');

const info = ref(null);
const review = ref(null);
const policy = ref(null);

const scrollTo = (tab) => {
    activeTab.value = tab;
    const target = { info, review, policy }[tab];
    target.value?.scrollIntoView({ behavior: 'smooth', block: 'start' });
};
</script>

<style scoped>
.book-tabs {
    margin-top: 40px;
    font-family: sans-serif;
}

.tab-buttons {
    display: flex;
    gap: 20px;
    margin-bottom: 20px;
}

.tab-buttons button {
    background: none;
    border: none;
    font-size: 14px;
    color: #aaa;
    cursor: pointer;
}

.tab-buttons button.active {
    color: #333;
    font-weight: bold;
    border-bottom: 2px solid #854d14;
}

.tab-section {
    padding: 40px 0;
}
</style>
