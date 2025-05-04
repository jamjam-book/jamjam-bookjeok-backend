<template>
    <div class="author-other-books">
        <button
                v-if="books.length > booksPerPage"
                class="nav-button left"
                @click="prevPage"
                :disabled="currentPage === 1"
        >〈</button>

        <div class="book-list-wrapper">
            <div class="book-list">
                <div
                        v-for="book in paginatedBooks"
                        :key="book.id"
                        class="book-card"
                        @click="goToDetail(book.id)"
                >
                    <img :src="book.imageUrl || defaultImage" alt="도서 이미지" />
                    <p class="book-title">{{ book.title }}</p>
                </div>
            </div>
        </div>

        <button
                v-if="books.length > booksPerPage"
                class="nav-button right"
                @click="nextPage"
                :disabled="currentPage === totalPages"
        >〉</button>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const props = defineProps({
    books: {
        type: Array,
        default: () => []
    }
});

const currentPage = ref(1);
const booksPerPage = 5;
const defaultImage = '/images/placeholder.png';

const totalPages = computed(() => Math.ceil(props.books.length / booksPerPage));

const paginatedBooks = computed(() => {
    const start = (currentPage.value - 1) * booksPerPage;
    return props.books.slice(start, start + booksPerPage);
});

const nextPage = () => {
    if (currentPage.value < totalPages.value) currentPage.value++;
};

const prevPage = () => {
    if (currentPage.value > 1) currentPage.value--;
};

const goToDetail = (bookId) => {
    router.push(`/books/${bookId}`);
};
</script>

<style scoped>
.author-other-books {
    margin-top: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 12px;
}

.book-list-wrapper {
    display: flex;
    align-items: center;
}

.book-list {
    display: flex;
    gap: 16px;
}

.book-card {
    width: 120px;
    height: 180px;
    text-align: center;
    cursor: pointer;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    padding: 8px;
    box-sizing: border-box;
    border: 1px solid #eee;
    border-radius: 8px;
}

.book-card img {
    width: 100%;
    height: 140px;
    object-fit: cover;
    border-radius: 6px;
}

.book-title {
    margin-top: 4px;
    font-size: 12px;
    color: #333;
}

.nav-button {
    background-color: #fdf2e9;
    border: none;
    padding: 8px 10px;
    border-radius: 6px;
    cursor: pointer;
    font-size: 18px;
    height: 140px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.nav-button:disabled {
    opacity: 0.4;
    cursor: not-allowed;
}
</style>
