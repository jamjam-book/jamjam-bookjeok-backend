<template>
    <div class="author-books-page">
        <div class="breadcrumb">
            <RouterLink to="/interests">관심작가</RouterLink>&nbsp;/ {{ authorName }}
        </div>

        <h2 class="page-title">저자 <span class="highlight">{{ authorName }}</span>의 도서 {{ books.length }}건</h2>

        <div class="filter-buttons">
            <button
                    v-for="type in sortOptions"
                    :key="type.value"
                    :class="{ active: sortType === type.value }"
                    @click="sortType = type.value"
            >
                {{ type.label }}
            </button>
        </div>

        <div class="book-grid">
            <BookCard
                    v-for="book in sortedBooks"
                    :key="book.id"
                    :book="book"
            />
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import BookCard from '@/features/book/components/BookCard.vue'

const route = useRoute()
const authorName = route.params.name || '천선란';

const sortType = ref('latest')

const sortOptions = [
    { label: '최신순', value: 'latest' },
    { label: '가격 낮은 순', value: 'low' },
    { label: '가격 높은 순', value: 'high' },
    { label: '인기순', value: 'popular' },
]

const books = ref([
    { id: 1, title: '아무튼, 디지몬', price: 15000, categoryId: 2, publishDate: '2024-05-31', imageUrl:'/images/main_482791348279137620.20240609071045.jpg'},
    { id: 2, title: '나인', price: 22000, categoryId: 3, publishDate: '2021-11-05', imageUrl:'/images/main_324803232480322263.20230725121109.jpg' },
    { id: 3, title: '노랜드', price: 18000, categoryId: 1, publishDate: '2022-06-22', imageUrl:'/images/main_329433932943397636.20230725121902.jpg' },
    { id: 4, title: '랑과 나의 사막', price: 17000, categoryId: 1, publishDate: '2022-10-25', imageUrl:'/images/main_354920835492080618.20250102071614.jpg' },
    { id: 5, title: '이끼숲', price: 21000, categoryId: 1, publishDate: '2023-05-02', imageUrl:'/images/main_396281739628177623.20230704084715.jpg' },
    { id: 6, title: '노을 건너기', price: 17500, categoryId: 1, publishDate: '2023-08-18', imageUrl:'/images/main_420729542072954632.20230905100926.jpg' },
    { id: 7, title: '어떤 물질의 사랑', price: 16200, categoryId: 2, publishDate: '2020-07-20', imageUrl:'/images/어떤 물질.jpg' },
    { id: 8, title: '천 개의 파랑', price: 15000, categoryId: 2, publishDate: '2020-08-19', imageUrl:'/images/천 개의 파랑.jpg' },
]);

const sortedBooks = computed(() => {
    return [...books.value].sort((a, b) => {
        switch (sortType.value) {
            case 'latest': return new Date(b.publishDate) - new Date(a.publishDate)
            case 'low': return a.price - b.price
            case 'high': return b.price - a.price
            case 'popular': return b.id - a.id
        }
    })
})
</script>

<style scoped>
.author-books-page {
    max-width: 1200px;
    margin: 40px auto;
    padding: 0 20px;
}
.breadcrumb {
    font-size: 13px;
    color: #666;
    margin-bottom: 20px;
}
.breadcrumb a {
    color: inherit;
    text-decoration: none;
}
.breadcrumb a:hover {
    text-decoration: underline;
    cursor: pointer;
}
.page-title {
    text-align: center;
    margin-bottom: 20px;
    font-size: 20px;
}
.page-title .highlight {
    color: #854d14;
    font-weight: bold;
}
.filter-buttons {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-bottom: 20px;
}
.filter-buttons button {
    padding: 6px 12px;
    font-size: 13px;
    border: none;
    background-color: #eee;
    border-radius: 16px;
    cursor: pointer;
}
.filter-buttons button.active {
    background-color: #854d14;
    color: #fff;
}
.book-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
    gap: 20px;
}
</style>

