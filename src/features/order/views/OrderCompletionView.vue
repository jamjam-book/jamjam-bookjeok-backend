<script setup>
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { ref, onMounted } from 'vue'
import OrderItem from '@/features/order/components/OrderItem.vue'
import MemberAddressInfo from '@/features/order/components/MemberAddressInfo.vue'
import PaymentInfo from '@/features/order/components/PaymentInfo.vue'

const router = useRouter()
const route = useRoute()

// 새로고침 대응용 fallback
const paymentKey = ref(route.query.paymentKey || sessionStorage.getItem('lastPaymentKey'))
const orderId = ref(route.query.orderId || sessionStorage.getItem('lastOrderId'))
const amount = ref(route.query.amount || sessionStorage.getItem('lastAmount'))

const items = ref([])
const totalPrice = ref(0)
const paymentMethod = ref('')

const loadFromSession = () => {
    const cached = sessionStorage.getItem(`payment:${paymentKey.value}`)
    if (!cached) return false

    const data = JSON.parse(cached)
    items.value = data.items
    totalPrice.value = data.totalPrice
    paymentMethod.value = data.paymentMethod
    return true
}

const fetchOrderCompletion = async () => {
    if (loadFromSession()) return

    try {
        const res = await axios.post(
                `${import.meta.env.VITE_API_BASE_URL}/payment/${paymentKey.value}/confirm`,
                {
                    orderId: orderId.value,
                    amount: Number(amount.value)
                }
        )

        const orderDetails = res.data.data.orderDetails

        const parsedItems = orderDetails.books.map(item => ({
            id: item.bookId,
            bookName: item.bookName,
            quantity: item.quantity,
            price: item.totalPrice / item.quantity,
            image: item.imageUrl
        }))

        items.value = parsedItems
        totalPrice.value = orderDetails.paymentDetail.totalAmount
        paymentMethod.value = orderDetails.paymentDetail.paymentMethod

        // 세션 저장 (새로고침 대비)
        sessionStorage.setItem(`payment:${paymentKey.value}`, JSON.stringify({
            items: parsedItems,
            totalPrice: orderDetails.paymentDetail.totalAmount,
            paymentMethod: orderDetails.paymentDetail.paymentMethod
        }))
        sessionStorage.setItem('lastPaymentKey', paymentKey.value)
        sessionStorage.setItem('lastOrderId', orderDetails.orderId)
        sessionStorage.setItem('lastAmount', amount.value)

        orderId.value = orderDetails.orderId
    } catch (e) {
        console.error('[ERROR] 결제 실패', e)
    }
}

onMounted(fetchOrderCompletion)

const goToOrders = () => router.push('/members/orders')
const goToMain = () => router.push('/')
</script>

<template>
    <div class="container py-5">
        <h2 class="text-center fw-bold">주문이 완료되었습니다.</h2>
        <div class="text-center fw-bold" id="order-no">주문 번호 : {{ orderId }}</div>

        <MemberAddressInfo/>

        <h4 id="order-info-title" class="fw-bold mb-3 py-3">주문 정보</h4>
        <OrderItem v-for="item in items" :key="item.id" :item="item"/>

        <PaymentInfo :totalPrice="totalPrice" :paymentMethod="paymentMethod"/>

        <div class="text-center" id="order-success-btn">
            <button class="btn" id="order-button" @click="goToOrders">주문 내역 이동</button>
            <button class="btn" id="main-button" @click="goToMain">메인 페이지 이동</button>
        </div>
    </div>
</template>

<style scoped>
.container {
    max-width: 900px;
    margin: 42px auto;
}

#order-success-btn {
    margin-top: 80px;
}

#order-no {
    color: red;
    margin: 42px;
    font-size: 16px;
}

#order-info-title {
    margin-top: 42px;
    border-bottom: 2px solid black;
}

#order-button {
    width: 200px;
    margin-right: 50px;
    font-weight: bold;
    box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.25);
}

#main-button {
    width: 200px;
    margin-left: 50px;
    font-weight: bold;
    background-color: #E2D1C5;
    box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.25);
}
</style>