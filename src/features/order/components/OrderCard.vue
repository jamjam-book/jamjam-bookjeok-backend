<!-- components/order/OrderCard.vue -->
<script setup>
import { defineProps, defineEmits } from 'vue'
import { useRouter } from 'vue-router'

const props = defineProps({
    order: {
        type: Object,
        required: true
    }
})

const router = useRouter()
const emit = defineEmits(['detail'])

const getOrderDetail = () => {
    // 추후에 동적 ID 경로 적용 예정
    router.push(`/members/orders/${props.order.orderId}/order-detail`)
}
</script>

<template>
    <div class="mb-5">
        <div class="order-header">
            <span id="order-date-id">{{ order.date }} [{{ order.orderId }}]</span>
            <button class="btn" id="order-detail-btn" @click="getOrderDetail">주문 상세 내역</button>
        </div>

        <div v-for="item in order.items" :key="item.id" class="order-row">
            <img :src="item.image" alt="도서 이미지" class="book-image"/>

            <div class="info-row">
                <div class="header-row">
                    <div class="header">도서명</div>
                    <div class="header">수량</div>
                    <div class="header">금액</div>
                </div>
                <div class="content-row">
                    <div class="title">{{ item.title }}</div>
                    <div class="quantity">{{ item.quantity }}개</div>
                    <div class="price">
                        {{ (item.price * item.quantity).toLocaleString() }}원
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
.order-item img {
    width: 110px;
    height: 136px;
    object-fit: cover;
    margin-right: 16px;
    border-radius: 4px;
}

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