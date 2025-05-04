<script setup>
import {useRoute, useRouter} from 'vue-router'
import OrderItem from "@/features/order/components/OrderItem.vue";
import MemberAddressInfo from "@/features/order/components/MemberAddressInfo.vue";
import PaymentInfo from "@/features/order/components/PaymentInfo.vue";

const router = useRouter()
const route = useRoute()

const orderId = route.query.orderId
const amount = route.query.amount
const paymentKey = route.query.paymentKey
const paymentType = route.query.paymentType

// 💡 실제 주문 데이터는 paymentKey나 orderId로 API를 통해 조회해야 합니다.
// 지금은 테스트용 임시 데이터:
const items = [
    {id: 1, title: '도메인 주도 개발 시작하기', price: 25000, quantity: 1, selected: true, image: '/src/assets/images/ddd.png'},
    {
        id: 2,
        title: 'Clean Code(클린 코드)',
        price: 30000,
        quantity: 2,
        selected: true,
        image: '/src/assets/images/cleancode.png'
    }
]

const totalPrice = items.reduce((sum, item) => sum + item.price * item.quantity, 0)

const goToOrders = () => router.push('/members/orders')
const goToMain = () => router.push('/')
</script>

<template>
    <div class="container py-5">
        <h2 class="text-center fw-bold">주문이 완료되었습니다.</h2>
        <div class="text-center fw-bold" id="order-no">주문 번호 : {{ orderId }}</div>

        <!-- 회원 정보 -->
        <MemberAddressInfo/>

        <!-- 주문 정보 -->
        <h4 id="order-info-title" class="fw-bold mb-3 py-3">주문 정보</h4>
        <OrderItem v-for="item in items" :key="item.id" :item="item"/>

        <!-- 결제 정보 -->
        <PaymentInfo :totalPrice="totalPrice"/>

        <!-- 버튼 -->
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