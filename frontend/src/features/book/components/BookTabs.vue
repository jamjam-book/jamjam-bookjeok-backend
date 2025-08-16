<template>
    <div class="book-tabs">
        <div class="tab-buttons">
            <button :class="{ active: activeTab === 'info' }" @click="scrollTo('info')">상품 정보</button>
            <button v-if="reviews" :class="{ active: activeTab === 'review' }" @click="scrollTo('review')">리뷰 ({{ reviewCount }})</button>
            <button :class="{ active: activeTab === 'policy' }" @click="scrollTo('policy')">교환/환불/품절</button>
        </div>

        <div ref="info" class="tab-section">
            <BookDescription :book="book" />
        </div>

        <div ref="review" class="tab-section">
            <BookReviews
                    :reviews="reviews"
                    :ratingCounts="ratingCounts"
                    :isPurchaser="isPurchaser"
                    @submit-reviews="handleReviewSubmit"
            />
        </div>

        <div ref="policy" class="tab-section">
            <BookPolicy />
        </div>
    </div>
</template>

<script setup>
import {computed, ref} from 'vue';
import BookDescription from '@/features/book/components/BookDescription.vue';
import BookReviews from "@/features/book/components/BookReviews.vue";
import BookPolicy from "@/features/book/components/BookPolicy.vue";

const props = defineProps({
    book: Object,
    reviews: Array,
});

const reviewArray = computed(() => props.reviews?.reviews || []);
const reviewCount = computed(() => reviewArray.value.length);

const ratingCounts = computed(() => {
    const ratingArr = [0, 0, 0, 0, 0];

    if (!Array.isArray(reviewArray.value)) return ratingArr;

    reviewArray.value.forEach((review) => {
        const rating = Number(review.rating);
        if (rating >= 1 && rating <= 5) {
            ratingArr[rating - 1]++;
        }
    });

    return ratingArr;
});

const activeTab = ref('info');

const info = ref(null);
const review = ref(null);
const policy = ref(null);
const isPurchaser = ref(true);

const scrollTo = (tab) => {
    activeTab.value = tab;
    const target = { info, review, policy }[tab];
    target.value?.scrollIntoView({ behavior: 'smooth', block: 'start' });
};

const emit = defineEmits(['submit-reviews']);

const handleReviewSubmit = (payload) => {
    emit('submit-reviews', payload);
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
