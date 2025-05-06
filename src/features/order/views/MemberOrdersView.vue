<script setup>
import {ref, computed, onMounted, watch} from 'vue'
import {useRoute} from 'vue-router'
import axios from 'axios'

import MyPageNav from "@/components/common/MyPageNav.vue"
import PagingBar from "@/components/common/PagingBar.vue"
import OrderCard from "@/features/order/components/OrderCard.vue"

const route = useRoute()
const memberId = route.params.memberId

const currentPage = ref(1)
const pageSize = 5
const orders = ref([])
const totalItems = ref(0)

const totalPages = computed(() =>
        Math.ceil(totalItems.value / pageSize)
)

const fetchOrders = async () => {
    try {
        const response = await axios.get(
                `${import.meta.env.VITE_API_BASE_URL}/members/${memberId}/orders`,
                {
                    params: {
                        page: currentPage.value - 1,
                        size: pageSize,
                        sort: 'orderedAt,desc'
                    }
                }
        )

        const rawItems = response.data.data.orders
        totalItems.value = response.data.data.totalElements

        orders.value = rawItems.map(order => ({
            ...order,
            totalAmount: order.items.reduce((sum, i) => sum + i.totalPrice, 0)
        }))
    } catch (e) {
        console.error('주문 내역 조회 실패', e)
    }
}

onMounted(fetchOrders)
watch(currentPage, fetchOrders)

const onPageChanged = (page) => {
    currentPage.value = page
}
</script>

<template>
    <div class="mypage-container">
        <div class="mypage-layout-left">
            <MyPageNav/>
        </div>
        <div class="mypage-layout-right">
            <h3 class="fw-bold mb-4">주문 내역</h3>

            <OrderCard
                    v-for="order in orders"
                    :key="order.orderId"
                    :order="order"
            />

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
</style>