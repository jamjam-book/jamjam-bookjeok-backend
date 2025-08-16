<script setup>
import {defineProps} from 'vue'
import {useRouter} from 'vue-router'

const props = defineProps({
    order: {
        type: Object,
        required: true
    }
})

const router = useRouter()

const getOrderDetail = () => {
    router.push({
        name: 'orderDetail',
        params: {orderId: props.order.orderId}
    })
}

const formatDate = (iso) => {
    const date = new Date(iso)
    return `${date.getFullYear()}.${(date.getMonth() + 1).toString().padStart(2, '0')}.${date.getDate().toString().padStart(2, '0')}.`
}

const IMAGE_BASE_URL = 'http://localhost:8080/images/';
</script>

<template>
    <div class="mb-5">
        <!-- 주문 상단 헤더 -->
        <div class="order-header">
            <span id="order-date-id">
                <span class="ordered-date">{{ formatDate(order.orderedAt) }}</span>
                <span class="order-id">[{{ order.orderId }}]</span>
            </span>
            <button class="btn" id="order-detail-btn" @click="getOrderDetail">주문 상세 내역</button>
        </div>

        <!-- 주문 항목 목록 -->
        <div v-for="item in order.items" :key="item.id" class="order-row">
            <img :src="item.imageUrl.startsWith('http') ? item.imageUrl : IMAGE_BASE_URL + item.imageUrl" alt="도서 이미지" class="book-image"/>

            <div class="info-row">
                <div class="header-row">
                    <div class="header">도서명</div>
                    <div class="header">수량</div>
                    <div class="header">금액</div>
                </div>
                <div class="content-row">
                    <div class="title">{{ item.bookName }}</div>
                    <div class="quantity">{{ item.quantity }}개</div>
                    <div class="price">
                        {{ item.totalPrice.toLocaleString() }}원
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
#order-date-id {
    font-size: 16px;
    font-weight: bold;
    display: flex;
    gap: 12px;
}

.order-id {
    white-space: nowrap;
}


#order-detail-btn {
    background-color: #F9F0DF;
    border-radius: 10px;
    font-weight: bold;
    width: 130px;
    height: 32px;
    margin-bottom: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0;
    line-height: 1;
}

.order-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 42px;
    margin-bottom: 12px;
    font-size: 14px;
    border-bottom: 2px solid black;
}

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