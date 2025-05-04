<template>
    <div class="book-info-header">
        <!-- 좌측 정보 영역 -->
        <div class="info-left">
            <div class="author">
                {{ book.author }}
                <button class="wish-button" :class="{ active: isAuthorLiked }" @click="toggleAuthorLike">
                    관심작가 ♥
                </button>
            </div>
            <div class="publisher">{{ book.publisher }} | {{ book.publishDate }}</div>
            <div class="rating">{{ averageRating.toFixed(1) }}</div>
            <div class="stars">
                <span
                        v-for="n in 5"
                        :key="n"
                        class="star"
                        :class="getStarClass(n, averageRating)"
                >★</span>
            </div>
            <div class="review-count">({{ reviewCount }}개)</div>
        </div>

        <!-- 가운데: 도서 이미지 및 제목 -->
        <div class="info-center">
            <h2 class="book-title">{{ book.title }}</h2>
            <div class="book-sub">{{ book.author }} | {{ book.category }}</div>
            <div class="book-image">
                <img :src="book.imageUrl || defaultImage" alt="도서 이미지" class="book-img" />
            </div>
        </div>

        <!-- 오른쪽: 구매 정보 -->
        <div class="info-right">
            <div class="purchase-box">
                <div class="price">{{ formatPrice(book.price) }}</div>
                <hr />

                <div class="quantity-box">
                    <span>수량</span>
                    <div class="quantity-controls">
                        <span>{{ quantity }}</span>
                        <button @click="increaseQuantity">+</button>
                        <button @click="decreaseQuantity">-</button>
                    </div>
                </div>

                <div class="total-price">
                    <span>총 금액</span>
                    <span>{{ formatPrice(totalPrice) }}</span>
                </div>

                <div class="actions">
                    <button class="gray" :class="{ active: isBookLiked }" @click="toggleBookLike">관심도서 ♥</button>
                    <button class="gray">장바구니</button>
                </div>

                <div class="order">
                    <button class="order-button">주문하기</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const props = defineProps({
    book: Object,
    reviews: Array,
});

const defaultImage = 'images/placeholder.png';
const quantity = ref(1);
const isAuthorLiked = ref(false);
const isBookLiked = ref(false);

const averageRating = computed(() => {
    if (!props.reviews.length) return 0;
    const sum = props.reviews.reduce((acc, r) => acc + r.rating, 0);
    return sum / props.reviews.length;
});

const getStarClass = (index, rating) => {
    if (rating >= index) return 'filled';
    if (rating >= index - 0.5) return 'half';
    return 'empty';
};
const reviewCount = ref(props.reviews.length);

const increaseQuantity = () => {
    if (quantity.value < 5) quantity.value++;
};

const decreaseQuantity = () => {
    if (quantity.value > 1) quantity.value--;
};

const toggleAuthorLike = () => {
    isAuthorLiked.value = !isAuthorLiked.value;
};

const toggleBookLike = () => {
    isBookLiked.value = !isBookLiked.value;
};

const totalPrice = computed(() => props.book.price * quantity.value);

const formatPrice = (price) => {
    return price.toLocaleString() + '원';
};
</script>

<style scoped>
.book-info-header {
    display: flex;
    justify-content: space-between;
    padding: 20px;
    font-family: sans-serif;
    gap: 40px;
}

.info-left {
    flex: 1.2;
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    font-size: 15px;
    color: #333;
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 16px;
    max-height: fit-content;
    max-width: 240px;
}

.info-center {
    flex: 1.5;
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
}

.info-right {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    max-width: 240px;
}

.book-title {
    font-size: 24px;
    margin-bottom: 8px;
}

.book-sub {
    font-size: 14px;
    color: #666;
    margin-bottom: 16px;
}

.book-image {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 12px;
}

.book-img {
    width: 180px;
    height: auto;
    object-fit: contain;
    border-radius: 8px;
    box-shadow: 0 0 4px rgba(0, 0, 0, 0.05);
}

.author {
    font-weight: bold;
    font-size: 16px;
    margin-bottom: 4px;
}

.wish-button {
    background-color: #ccc;
    color: #fff;
    border: none;
    padding: 4px 12px;
    border-radius: 16px;
    font-size: 12px;
    margin-left: 8px;
    cursor: pointer;
}

.wish-button.active {
    background-color: #854d14;
}

.gray.active {
    background-color: #854d14;
}

.publisher {
    color: #333;
    margin-bottom: 16px;
    font-size: 14px;
}

.rating {
    font-size: 28px;
    font-weight: bold;
    color: #854d14;
    margin-bottom: 4px;
}

.stars {
    font-size: 20px;
    color: lightgray;
    margin-bottom: 4px;
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

.review-count {
    font-size: 14px;
    color: #444;
}

.purchase-box {
    width: 100%;
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 16px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    align-items: stretch;
    gap: 12px;
}

.price {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 8px;
    text-align: left;
}

.quantity-box,
.total-price {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.quantity-controls {
    display: flex;
    gap: 8px;
    align-items: center;
}

.quantity-controls button {
    height: 24px;
    border: none;
    border-radius: 25%;
    background-color: #eee;
    cursor: pointer;
    font-size: 16px;
}

.actions {
    display: flex;
    justify-content: space-between;
    gap: 8px;
}

.gray {
    background-color: #ccc;
    color: #fff;
    border: none;
    padding: 10px;
    border-radius: 20px;
    flex: 1;
    cursor: pointer;
    font-size: 13px;
}

.order-button {
    background-color: #fdf2e9;
    padding: 10px;
    border: none;
    border-radius: 12px;
    font-weight: bold;
    cursor: pointer;
    width: 100%;
}
</style>
