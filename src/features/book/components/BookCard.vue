<template>
    <div class="book-card" @click="goToDetail">
        <div class="book-image">
            <img :src="book.imageUrl || defaultImage" alt="도서 이미지" />
        </div>
        <div class="book-info">
            <p class="book-title">{{ book.title }}</p>
            <p class="book-price">{{ formatPrice(book.price) }}</p>
        </div>
    </div>
</template>

<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const props = defineProps({
    book: {
        type: Object,
        required: true,
    },
});

const defaultImage = '/images/placeholder-book.png';

const formatPrice = (price) => {
    return `${price.toLocaleString()}원`;
};

const goToDetail = () => {
    router.push(`/books/${props.book.id}`);
};
</script>

<style scoped>
.book-card {
    background: #fff;
    border: 1px solid #eee;
    border-radius: 8px;
    padding: 12px;
    display: flex;
    flex-direction: column;
    align-items: center;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
    transition: transform 0.2s ease;
}

.book-card:hover {
    transform: translateY(-2px);
}

.book-image {
    width: 100%;
    height: 200px;
    background-color: #f6f6f6;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 12px;
    overflow: hidden;
    border-radius: 4px;
}

.book-image img {
    max-width: 100%;
    max-height: 100%;
    object-fit: cover;
}

.book-info {
    width: 100%;
    text-align: center;
}

.book-title {
    font-size: 15px;
    font-weight: 500;
    color: #333;
    margin-bottom: 4px;
}

.book-price {
    font-size: 14px;
    font-weight: bold;
    color: #854d14;
}
</style>
