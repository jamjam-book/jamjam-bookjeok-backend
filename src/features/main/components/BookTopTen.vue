<template>
    <section class="weekly-top-wrapper">
        <h2 class="weekly-top-title">금주의 베스트 셀러 TOP10</h2>
        <div class="weekly-top-container">
            <div class="more-link" @click="goToBookList">더보기 +</div>
            <div class="slider-inner" v-if="topBooks">
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
    </section>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import BookCard from '@/features/book/components/BookCard.vue'
import {getPopularBooks} from "@/features/main/api.js";
import {useRouter} from "vue-router";

const router = useRouter();

const topBooks = ref([]);

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

const goToBookList = () => {
    router.push('/books')
}

const fetchTopTen = async () => {
    try {
        const res = await getPopularBooks();

        if(res.data && res.data.success) {
            topBooks.value = res.data.data;
        } else {
            console.log('인기도서 조회에 실패했습니다.')
        }
    } catch (e) {
        console.log('인기 도서 조회에 실패했습니다. : ', e);
    }
}

onMounted(() => {
    startAutoSlide()
    fetchTopTen();
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
    margin-bottom: 12px;
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
