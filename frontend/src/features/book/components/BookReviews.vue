<template>
    <div class="book-review-section">
        <!-- 타이틀 영역 -->
        <div class="review-header">
            <h6>리뷰 ({{ totalReviews }})</h6>
            <button v-if="isPurchaser" class="write-button" @click="showForm = !showForm">
                {{ showForm ? '작성 취소' : '리뷰 작성' }}
            </button>
        </div>

        <!-- 리뷰 작성 폼 -->
        <div v-if="showForm" class="review-form">
            <textarea v-model="newReview" :placeholder="reviewPlaceholder" maxlength="100" />
            <div class="review-actions">
                <div class="rating-select">
                    <span
                            v-for="i in 5"
                            :key="i"
                            class="star"
                            :class="{ selected: rating >= i }"
                            @click="updateRating(i)"
                    >★</span>
                </div>
                <button class="submit-button" @click="submitReview">리뷰등록</button>
            </div>
        </div>

        <!-- 요약 영역 -->
        <div class="review-summary">
            <div class="summary-left">
                <div class="avg-rating">{{ averageRating.toFixed(1) }}</div>
                <div class="stars">
          <span
                  v-for="n in 5"
                  :key="n"
                  class="star"
                  :class="getStarClass(n, averageRating)"
          >★</span>
                </div>
                <p>({{ totalReviews }}개)</p>
            </div>

            <div class="summary-right">
                <div class="donut-wrapper">
                    <Doughnut :data="chartData" :options="options"/>
                </div>
            </div>
        </div>

        <!-- 리뷰 목록 -->
        <div    v-if="reviews"
                v-for="review in paginatedReviews"
                :key="review.id"
                class="review-item"
        >
            <div class="meta">{{ review.date }} &nbsp; {{ review.user }}</div>
            <div class="content">{{ review.content }}</div>
            <div class="stars small">
        <span
                v-for="i in 5"
                :key="i"
                class="star"
                :class="{ filled: review.rating >= i }"
        >★</span>
            </div>
        </div>

        <!-- 페이징 -->
        <PagingBar
                :currentPage="currentPage"
                :totalPage="totalPages"
                :totalItems="totalReviews"
                @page-changed="(page) => currentPage = page"
        />
    </div>
</template>

<script setup>
import {ref, computed, onMounted} from 'vue';
import { Doughnut } from 'vue-chartjs';
import {
    Chart as ChartJS,
    Title,
    Tooltip,
    Legend,
    ArcElement,
} from 'chart.js';
import ChartDataLabels from 'chartjs-plugin-datalabels';
import PagingBar from "@/components/common/PagingBar.vue";

ChartJS.register(Title, Tooltip, Legend, ArcElement, ChartDataLabels);

const props = defineProps({
    reviews: {
        type: Object,
        required: true
    },
    isPurchaser: {
        type: Boolean,
        default: false
    },
    ratingCounts: Array,
});

const reviewArray = computed(() => props.reviews?.reviews || []);

const showForm = ref(false);
const newReview = ref('');
const newRating = ref(0);
const rating = ref(0);
const currentPage = ref(1);
const reviewsPerPage = 5;
const reviewPlaceholder = '도서에 대한 리뷰를 100자 이내로 작성해주세요';

const totalReviews = computed(() => reviewArray.value.length);
const totalPages = computed(() => Math.ceil(totalReviews.value / reviewsPerPage));

const averageRating = computed(() => {
    if (!reviewArray.value.length) return 0;
    return reviewArray.value.reduce((acc, r) => acc + r.rating, 0) / reviewArray.value.length;
});

const paginatedReviews = computed(() => {
    const start = (currentPage.value - 1) * reviewsPerPage;
    return reviewArray.value.slice(start, start + reviewsPerPage) || [];
});


const chartData = computed(() => ({
    labels: ['1점', '2점', '3점', '4점', '5점'],
    datasets: [{
        data: [
            props.ratingCounts[0] || 0,
            props.ratingCounts[1] || 0,
            props.ratingCounts[2] || 0,
            props.ratingCounts[3] || 0,
            props.ratingCounts[4] || 0
        ],
        backgroundColor: ['#c08b59', '#d9a06b', '#e7b69c', '#f0cdbf', '#fdf2e9'],
        borderWidth: 0
    }]
}));

const options = {
    responsive: true,
    maintainAspectRatio: false,
    cutout: '60%',
    plugins: {
        legend: {
            position: 'bottom',
            labels: {
                color: '#666',
                font: { size: 12 }
            }
        },
        datalabels: {
            display: false
        }
    }
};

const getStarClass = (index, rating) => {
    if (rating >= index) return 'filled';
    if (rating >= index - 0.5) return 'half';
    return 'empty';
};

const emit = defineEmits(['submit-reviews']);

const updateRating = (cnt) => {
    rating.value = cnt;
    newRating.value = cnt;
}

const submitReview = () => {
    if (!newReview.value || !rating.value) return;

    emit('submit-reviews', {
        content : newReview.value,
        rating : newRating.value
    });

    showForm.value = false;
    newReview.value = '';
    rating.value = 0;
};
</script>

<style scoped>
.book-review-section {
    font-family: sans-serif;
    margin-top: 40px;
}
.review-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.write-button {
    background-color: #fdf2e9;
    border: none;
    border-radius: 8px;
    padding: 6px 12px;
    cursor: pointer;
    font-size: 13px;
}
.review-form {
    margin-top: 24px;
    margin-bottom: 24px;
}

.review-form textarea {
    width: 100%;
    height: 120px;
    padding: 12px;
    margin-bottom: 8px;
    border-radius: 12px;
    border: none;
    background-color: #f8f8f8;
}

.review-actions {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 8px;
}

.rating-select {
    display: flex;
    gap: 4px;
}

.submit-button {
    background-color: #fdf2e9;
    border: none;
    padding: 6px 12px;
    border-radius: 6px;
    cursor: pointer;
    font-size: 13px;
}

.rating-select .star {
    cursor: pointer;
    font-size: 20px;
    color: #ccc;
}
.rating-select .star.selected {
    color: gold;
}
.submit-button {
    float: right;
    background-color: #fdf2e9;
    border: none;
    padding: 6px 12px;
    border-radius: 6px;
    cursor: pointer;
    font-size: 13px;
}
.review-summary {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #f9f9f9;
    padding: 24px 32px;
    border-radius: 16px;
    margin: 20px 0;
    min-height: 160px;
}
.summary-left,
.summary-right {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    text-align: center;
}
.avg-rating {
    font-size: 28px;
    font-weight: bold;
    color: #854d14;
}
.stars .star {
    font-size: 20px;
    color: #ccc;
}
.stars .star.filled {
    color: gold;
}
.stars .star.half {
    background: linear-gradient(90deg, gold 50%, #ccc 50%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}
.donut-wrapper {
    width: 240px;
    height: 240px;
    position: relative;
}
.review-item {
    padding: 12px 0;
    border-bottom: 1px solid #eee;
}
.meta {
    font-size: 13px;
    color: #777;
    margin-bottom: 4px;
}
.content {
    font-size: 14px;
    margin-bottom: 6px;
}
.stars.small .star {
    font-size: 14px;
    color: #ccc;
}
.stars.small .star.filled {
    color: gold;
}
.pagination {
    display: flex;
    justify-content: center;
    gap: 4px;
    margin-top: 20px;
}
.pagination button {
    border: none;
    background: none;
    cursor: pointer;
    padding: 6px 10px;
    font-size: 13px;
    color: #333;
}
.pagination button.active {
    background-color: #fdf2e9;
    border-radius: 6px;
    font-weight: bold;
}
</style>
