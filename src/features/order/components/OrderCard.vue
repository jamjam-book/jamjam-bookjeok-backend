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

        <div v-for="item in order.items" :key="item.id" class="order-item">
            <img :src="item.image" alt="도서 이미지">
            <div class="flex-grow-1">
                <div class="title">{{ item.title }}</div>
                <div class="quantity">{{ item.quantity }}개</div>
            </div>
            <div class="price">{{ item.price.toLocaleString() }}원</div>
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
}

.order-item {
    display: flex;
    border-top: 1px solid #dee2e6;
    padding-top: 16px;
    margin-bottom: 16px;
}

.order-item img {
    width: 110px;
    height: 136px;
    object-fit: cover;
    margin-right: 16px;
    border: 1px solid #dee2e6;
    border-radius: 4px;
}

.order-item .title {
    font-weight: bold;
    font-size: 16px;
}

.order-item .quantity {
    margin-top: 8px;
    font-size: 14px;
}

.order-item .price {
    font-weight: bold;
    font-size: 16px;
    text-align: right;
    align-self: flex-start;
}
</style>