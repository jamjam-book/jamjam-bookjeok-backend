<script setup>
import { ref, computed } from 'vue';
import MemberAddressInfo from '../components/MemberAddressInfo.vue';
import OrderItem from '../components/OrderItem.vue';
import OrderSummary from '../components/OrderSummary.vue';
import TossPaymentWidget from "@/features/order/components/TossPaymentWidget.vue";

const items = ref([]);
const totalPrice = ref(0);
const paymentRef = ref();

const storedItems = sessionStorage.getItem('orderItems');
const storedPrice = sessionStorage.getItem('orderTotalPrice');

if (storedItems && storedPrice) {
    try {
        items.value = JSON.parse(storedItems);
        totalPrice.value = parseInt(storedPrice, 10);
    } catch (e) {
        console.error('[ERROR] 주문 정보 파싱 실패', e);
    }
}
</script>

<template>
    <div class="container d-flex justify-content-between">
        <!-- 좌측 영역 -->
        <div class="left-area flex-grow-1 me-4">
            <h3 class="fw-bold mb-4">주문/결제</h3>
            <MemberAddressInfo/>

            <h4 id="order-info-title" class="fw-bold mb-3 py-3">주문 정보</h4>
            <OrderItem
                    v-for="item in items"
                    :key="item.id"
                    :item="item"
            />

            <!-- 토스페이먼츠 위젯 호출 -->
            <TossPaymentWidget
                    ref="paymentRef"
                    :items="items"
            />
        </div>

        <!-- 결제하기 버튼s -->
        <OrderSummary
                :totalPrice="totalPrice"
                @pay="paymentRef?.requestPayment()"
        />
    </div>
</template>

<style scoped>
.container {
    max-width: 1200px;
    margin: 42px auto;
}

#order-info-title {
    margin-top: 42px;
    border-bottom: 2px solid black;
}
</style>