<template>
    <section class="weekly-top-wrapper">
        <h2 class="weekly-top-title">금주의 베스트 셀러 TOP10</h2>
        <div class="weekly-top-container">
            <div class="slider-inner">
                <button
                        class="arrow left"
                        :disabled="currentIndex === 0"
                        @click="prevSlide"
                >
                    〈
                </button>
                <div
                        class="slider-window"
                        @touchstart="handleTouchStart"
                        @touchend="handleTouchEnd"
                >
                    <div
                            class="slider-track"
                            :style="{ transform: `translateX(-${currentIndex * (cardWidth + gap)}px)` }"
                    >
                        <BookCard
                                v-for="book in topBooks"
                                :key="book.id"
                                :book="book"
                        />
                    </div>
                </div>
                <button
                        class="arrow right"
                        :disabled="currentIndex >= maxSlideIndex"
                        @click="nextSlide"
                >
                    〉
                </button>
            </div>
        </div>
        <div class="more-link">더보기 +</div>
    </section>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import BookCard from '@/features/book/components/BookCard.vue'

const topBooks = ref([
    { id: 1, title: '아무튼, 디지몬', price: 15000, categoryId: 2, publishDate: '2024-05-31', imageUrl:'/images/main_482791348279137620.20240609071045.jpg'},
    { id: 2, title: '나인', price: 22000, categoryId: 3, publishDate: '2021-11-05', imageUrl:'/images/main_324803232480322263.20230725121109.jpg' },
    { id: 3, title: '노랜드', price: 18000, categoryId: 1, publishDate: '2022-06-22', imageUrl:'/images/main_329433932943397636.20230725121902.jpg' },
    { id: 4, title: '랑과 나의 사막', price: 17000, categoryId: 1, publishDate: '2022-10-25', imageUrl:'/images/main_354920835492080618.20250102071614.jpg' },
    { id: 5, title: '이끼숲', price: 21000, categoryId: 1, publishDate: '2023-05-02', imageUrl:'/images/main_396281739628177623.20230704084715.jpg' },
    { id: 6, title: '노을 건너기', price: 17500, categoryId: 1, publishDate: '2023-08-18', imageUrl:'/images/main_420729542072954632.20230905100926.jpg' },
    { id: 7, title: '어떤 물질의 사랑', price: 16200, categoryId: 2, publishDate: '2020-07-20', imageUrl:'/images/어떤 물질.jpg' },
    { id: 8, title: '천 개의 파랑', price: 15000, categoryId: 2, publishDate: '2020-08-19', imageUrl:'/images/천 개의 파랑.jpg' },
    { id: 9, title: '거꾸로 읽는 세계사', price: 22000, categoryId: 3, publishDate: '2020-08-19', imageUrl:'/images/main_324438732443877025.20230509165419.jpg' },
    { id: 10, title: '행성어 서점', price: 22000, categoryId: 2, publishDate: '2020-08-19', imageUrl:'/images/main_324721232472125024.20240302074840.jpg' },
])

const currentIndex = ref(0)
const visibleCount = 5
const cardWidth = 180
const gap = 24

const maxSlideIndex = computed(() => {
    return Math.max(0, topBooks.value.length - visibleCount)
})

const prevSlide = () => {
    stopAutoSlide()
    if (currentIndex.value > 0) currentIndex.value--
}

const nextSlide = () => {
    stopAutoSlide()
    if (currentIndex.value < maxSlideIndex.value) currentIndex.value++
}

let startX = 0
const handleTouchStart = (e) => {
    stopAutoSlide()
    startX = e.touches[0].clientX
}

const handleTouchEnd = (e) => {
    const endX = e.changedTouches[0].clientX
    const diff = endX - startX

    if (Math.abs(diff) > 50) {
        if (diff < 0) nextSlide()
        else prevSlide()
    }
}

let intervalId = null
const startAutoSlide = () => {
    intervalId = setInterval(() => {
        if (currentIndex.value < maxSlideIndex.value) {
            currentIndex.value++
        } else {
            currentIndex.value = 0
        }
    }, 3000)
}

const stopAutoSlide = () => {
    if (intervalId) {
        clearInterval(intervalId)
        intervalId = null
    }
}

onMounted(() => {
    startAutoSlide()
})

onBeforeUnmount(() => {
    stopAutoSlide()
})
</script>

<style scoped>
.weekly-top-wrapper {
    margin: 40px 0;
    padding: 0 40px;
}

.weekly-top-title {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 12px;
    color: #5a3921;
    padding-left: 8px;
}

.weekly-top-container {
    background-color: #fbefd9;
    padding: 32px 40px;
    border-radius: 30px;
    overflow: hidden;
}

.slider-inner {
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
}

.slider-window {
    overflow: hidden;
    max-width: calc((180px + 24px) * 5 - 24px);
}

.slider-track {
    display: flex;
    transition: transform 0.3s ease;
    width: max-content;
    gap: 24px;
}

.arrow {
    background-color: white;
    border: none;
    font-size: 24px;
    width: 32px;
    height: 32px;
    border-radius: 50%;
    cursor: pointer;
    z-index: 2;
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
}

.arrow.left {
    left: -16px;
}

.arrow.right {
    right: -16px;
}

.arrow:disabled {
    opacity: 0.3;
    cursor: default;
}

.more-link {
    margin-top: 12px;
    text-align: right;
    font-weight: bold;
    cursor: pointer;
    padding-right: 8px;
    color: #5a3921;
}

::v-deep(.book-card) {
    width: 180px;
    flex-shrink: 0;
    box-sizing: border-box;
}
</style>
