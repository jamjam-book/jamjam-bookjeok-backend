<template>
    <div class="author-books-page">
        <div class="breadcrumb">
            <RouterLink to="/interests">관심작가</RouterLink>&nbsp;/ {{ authorName }}
        </div>

        <h2 class="page-title">저자 <span class="highlight">{{ authorName }}</span>의 도서 {{ books.length }}건</h2>

        <div class="filter-buttons" v-if="books.length > 0">
            <button
                    v-for="type in sortOptions"
                    :key="type.value"
                    :class="{ active: sortType === type.value }"
                    @click="sortType = type.value"
            >
                {{ type.label }}
            </button>
        </div>

        <!-- 조회 결과 없음 -->
        <div class="empty-message" v-if="books.length === 0">
            조회된 도서가 없습니다.
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
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import BookCard from '@/features/book/components/BookCard.vue'
import { getBookList } from '@/features/book/api.js'

const route = useRoute()
const books = ref([])
const sortType = ref('latest')

const authorName = ref('')
const sortOptions = [
    { label: '최신순', value: 'latest' },
    { label: '가격 낮은 순', value: 'low' },
    { label: '가격 높은 순', value: 'high' },
    { label: '인기순', value: 'popular' }
]

const fetchBooks = async () => {
    if (!authorName.value) return
    try {
        const params = {
            keywordType: 'author',
            keyword: authorName.value
        }
        const res = await getBookList(params)
        books.value = res.data.data.content || []
        console.log(`books : ${books.value}`)
    } catch (e) {
        console.error('작가 도서 조회 실패:', e)
    }
}

const sortedBooks = computed(() => {
    return [...books.value].sort((a, b) => {
        switch (sortType.value) {
            case 'latest': return new Date(b.publishedAt) - new Date(a.publishedAt)
            case 'low': return a.price - b.price
            case 'high': return b.price - a.price
            case 'popular': return b.ordersCount - a.ordersCount
            default: return 0
        }
    })
})

// 초기 진입 및 쿼리 변경 시 검색 실행
onMounted(() => {
    authorName.value = route.query.keyword || ''
    fetchBooks()
})
watch(() => route.query.keyword, (newKeyword) => {
    authorName.value = newKeyword || ''
    fetchBooks()
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

.empty-message {
    padding: 2rem;
    text-align: center;
    font-size: 1.1rem;
    color: #777;
}
</style>

