<script setup>
import MemberAddressInfo from "@/features/order/components/MemberAddressInfo.vue";
import {onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import OrderDetailCard from "@/features/order/components/OrderDetailCard.vue";
import PaymentInfo from "@/features/order/components/PaymentInfo.vue";

const router = useRouter();
const route = useRoute();
const orders = ref([
    {
        id: 1,
        date: "2025.04.18.",
        orderId: "order-1746259565167",
        items: [
            {
                id: 101,
                image: "/src/assets/images/ddd.png",
                title: "도메인 주도 개발 시작하기",
                quantity: 1,
                price: 25000,
            },
            {
                id: 102,
                image: "/src/assets/images/cleancode.png",
                title: "Clean Code(클린 코드)",
                quantity: 2,
                price: 30000,
            },
        ],
    },
]);

const totalPrice = orders.value.reduce(
        (sum, order) => sum + order.items.reduce((sum, item) => sum + item.price * item.quantity, 0), 0);
const filteredOrder = ref(null);

onMounted(() => {
    const orderId = route.params.orderId;
    filteredOrder.value = orders.value.find((o) => o.orderId === orderId);
});

const goToOrders = () => router.push('/members/orders')
</script>

<template>
    <div class="container">
        <h3 class="fw-bold mb-4">주문 상세 내역</h3>
        <MemberAddressInfo/>

        <OrderDetailCard v-if="filteredOrder" :order="filteredOrder"/>
        <div v-else>해당 주문을 찾을 수 없습니다.</div>

        <PaymentInfo :totalPrice="totalPrice"/>

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
</style>