<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

import MemberAddressInfo from "@/features/order/components/MemberAddressInfo.vue"
import OrderDetailCard from "@/features/order/components/OrderDetailCard.vue"
import PaymentInfo from "@/features/order/components/PaymentInfo.vue"

const route = useRoute()
const router = useRouter()

const memberId = route.params.memberId
const orderId = route.params.orderId

const filteredOrder = ref(null)
const totalPrice = ref(0)
const paymentMethod = ref('')

const formatDate = (iso) => {
    const date = new Date(iso)
    return `${date.getFullYear()}.${(date.getMonth() + 1).toString().padStart(2, '0')}.${date.getDate().toString().padStart(2, '0')}.`
}

const fetchOrderDetail = async () => {
    try {
        const res = await axios.get(`${import.meta.env.VITE_API_BASE_URL}/members/${memberId}/orders/${orderId}/order-detail`)
        const data = res.data.data

        if (!data) {
            console.warn('주문 정보를 찾을 수 없습니다.')
            return
        }

        filteredOrder.value = {
            orderId: data.orderId,
            orderedAt: data.orderedAt,
            items: data.books.map((book, index) => ({
                id: index,
                image: book.imageUrl,
                title: book.bookName,
                quantity: book.quantity,
                price: book.totalPrice / book.quantity
            }))
        }

        totalPrice.value = data.paymentDetail.totalAmount
        paymentMethod.value = data.paymentDetail.paymentMethod
    } catch (err) {
        console.error("주문 상세 조회 실패", err)
    }
}

onMounted(fetchOrderDetail)

const goToOrders = () => {
    router.push(`/members/${memberId}/orders`)
}
</script>

<template>
    <div class="container">
        <h3 class="fw-bold mb-4">주문 상세 내역</h3>

        <div v-if="filteredOrder" class="order-meta-box">
            <div class="meta-item">{{ formatDate(filteredOrder.orderedAt) }}</div>
            <div class="meta-divider"></div>
            <div class="meta-item fw-bold">주문 번호 : {{ filteredOrder.orderId }}</div>
        </div>

        <MemberAddressInfo />

        <OrderDetailCard v-if="filteredOrder" :order="filteredOrder" />
        <div v-else>해당 주문을 찾을 수 없습니다.</div>

        <PaymentInfo :totalPrice="totalPrice" :paymentMethod="paymentMethod" />

        <div class="button-wrapper">
            <button class="btn" id="order-button" @click="goToOrders">주문 내역 이동</button>
        </div>
    </div>
</template>

<style scoped>
.container {
    width: 900px;
    margin-top: 42px;
}

.button-wrapper {
    display: flex;
    justify-content: center;
    margin-top: 100px;
    margin-bottom: 80px;
}

#order-button {
    width: 200px;
    font-weight: bold;
    background-color: #E2D1C5;
    box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.25);
}

.order-meta-box {
    width: 900px;
    background-color: #E2D1C5;
    border-radius: 10px;
    padding: 10px 20px;
    display: inline-flex;
    align-items: center;
    font-weight: bold;
    font-size: 14px;
    color: #000;
    box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
}

.order-meta-box {
    color: #444;
}

.meta-divider {
    height: 16px;
    width: 1px;
    background-color: #6c757d;
    margin: 0 16px;
}

.order-meta-box,
.order-meta-box {
    white-space: nowrap;
}
</style>