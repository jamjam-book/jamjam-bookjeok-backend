<script setup>
import MyPageNav from "@/components/common/MyPageNav.vue";
import PagingBar from "@/components/common/PagingBar.vue";
import OrderCard from "@/features/order/components/OrderCard.vue";

import { ref, computed } from 'vue'

const currentPage = ref(1)
const pageSize = 5

const orders = ref([
    {
        id: 1,
        date: '2025.04.18.',
        orderId: 'order-1746259565167',
        items: [
            {
                id: 101,
                image: '/src/assets/images/ddd.png',
                title: '도메인 주도 개발 시작하기',
                quantity: 1,
                price: 25000
            },
            {
                id: 102,
                image: '/src/assets/images/cleancode.png',
                title: 'Clean Code(클린 코드)',
                quantity: 2,
                price: 60000
            }
        ]
    }
])

const totalItems = computed(() => orders.value.length)
const totalPages = computed(() =>
        Math.ceil(orders.value.length / pageSize)
)

const paginatedOrders = computed(() => {
    const start = (currentPage.value - 1) * pageSize
    const end = start + pageSize
    return orders.value.slice(start, end)
})

const onPageChanged = (page) => {
    currentPage.value = page
}
</script>

<template>
    <div class="mypage-container">
        <div class="mypage-layout-left">
            <MyPageNav />
        </div>
        <div class="mypage-layout-right">
            <h3 class="fw-bold mb-4">주문 내역</h3>

            <!-- 주문 카드 컴포넌트 사용 -->
            <OrderCard v-for="order in paginatedOrders" :key="order.id" :order="order" />

            <!-- 페이징 바 -->
            <PagingBar
                    :currentPage="currentPage"
                    :totalPages="totalPages"
                    :totalItems="totalItems"
                    @page-changed="onPageChanged"
            />
        </div>
    </div>
</template>

<style scoped>
.mypage-container {
    display: flex;
    width: 1200px;
    margin: 42px auto 0;
}

.mypage-layout-left {
    width: 200px;
    margin-right: 100px;
}

.mypage-layout-right {
    flex: 1;
}

.order-item img {
    width: 110px;
    height: 136px;
    object-fit: cover;
    margin-right: 16px;
    border: 1px solid #dee2e6;
    border-radius: 4px;
}
</style>