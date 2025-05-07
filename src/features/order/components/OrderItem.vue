<script setup>
import {computed, defineProps} from "vue";

const props = defineProps({
    item: {
        type: Object,
        required: true
    }
});

const IMAGE_BASE_URL = 'http://localhost:8080/images/';

const fullImageUrl = computed(() => {
    return props.item.image.startsWith('http')
            ? props.item.image
            : IMAGE_BASE_URL + props.item.image
})

console.log(fullImageUrl);
</script>

<template>
    <div class="order-row">
        <img :src="fullImageUrl" alt="도서 이미지" class="book-image"/>

        <div class="info-row">
            <div class="header-row">
                <div class="header">도서명</div>
                <div class="header">수량</div>
                <div class="header">금액</div>
            </div>
            <div class="content-row">
                <div class="title">{{ item.bookName }}</div>
                <div class="quantity">{{ item.quantity }}개</div>
                <div class="price">{{ (item.price * item.quantity).toLocaleString() }}원</div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.order-row {
    display: flex;
    align-items: flex-start;
    padding: 10px 0;
    border-bottom: 1px solid #dee2e6;
}

.book-image {
    width: auto;
    height: 160px;
    object-fit: cover;
    margin-right: 24px;
    border-radius: 4px;
}

.info-row {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.header-row {
    display: flex;
    justify-content: center;
    margin-top: 25px;
    margin-bottom: 4px;
}

.content-row {
    display: flex;
    margin-top: 10px;
    align-items: center;
}

.header:nth-child(1),
.title {
    width: 50%;
}

.header:nth-child(2),
.quantity {
    width: 25%;
}

.header:nth-child(3),
.price {
    width: 25%;
    padding-left: 60px;
}

.header {
    font-size: 14px;
    color: #999;
}

.title,
.quantity,
.price {
    font-size: 16px;
    font-weight: bold;
}
</style>