<script setup>
import {defineProps} from "vue";

const props = defineProps({
    item: {
        type: Object,
        required: true
    }
});
</script>

<template>
    <div class="order-row">
        <img :src="item.image" alt="도서 이미지" class="book-image"/>

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
    width: 110px;
    height: 136px;
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
}

.header {
    font-size: 14px;
    color: #999;
    width: 33.3%;
}

.title,
.quantity,
.price {
    font-size: 16px;
    font-weight: bold;
    width: 33.3%;
}
</style>