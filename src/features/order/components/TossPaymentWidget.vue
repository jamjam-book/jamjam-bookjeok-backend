<script setup>
import axios from 'axios'
import { onMounted, ref } from 'vue'

const props = defineProps({
    items: Array
})

const totalPrice = props.items.reduce((sum, item) => sum + item.price * item.quantity, 0)
const paymentWidget = ref(null)
const clientKey = 'test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm'
const customerKey = generateRandomString()

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

onMounted(async () => {
    const { loadPaymentWidget } = await import('@tosspayments/payment-widget-sdk')

    paymentWidget.value = await loadPaymentWidget(clientKey, customerKey)

    await paymentWidget.value.renderPaymentMethods(
            '#payment-method',
            { value: totalPrice },
            { variantKey: 'DEFAULT' }
    )

    await paymentWidget.value.renderAgreement('#agreement')
})

async function requestPayment() {
    if (!paymentWidget.value) return

    try {
        const pendingOrderRequest = {
            memberUid: 1, // TODO: 실제 로그인 사용자 ID로 대체
            orderBookItems: props.items.map(item => ({
                bookId: item.id,
                bookName: item.bookName,
                quantity: item.quantity,
                totalPrice: item.price * item.quantity
            }))
        }

        console.log('[DEBUG] props.items:', props.items)


        const responsePendingOrder = await axios.post(`${API_BASE_URL}/pending-order`, pendingOrderRequest)
        const { orderId, totalAmount } = responsePendingOrder.data.data

        const orderName = props.items.length === 1
                ? props.items[0].bookName
                : `${props.items[0].bookName} 외 ${props.items.length - 1}건`

        await paymentWidget.value.requestPayment({
            orderId,
            orderName,
            amount: totalAmount,
            successUrl: 'http://localhost:5173/order/completion',
            failUrl: 'http://localhost:5173/order/fail',
            customerName: '김북적',
            customerEmail: 'example@email.com'
        })
    } catch (err) {
        console.error('[ERROR] 결제 요청 실패:', err)
        alert('결제 처리 중 오류가 발생했습니다.')
    }
}

function generateRandomString() {
    return window.btoa(Math.random().toString()).slice(0, 20)
}

defineExpose({ requestPayment })
</script>

<template>
    <div>
        <h4 class="fw-bold mt-5 mb-3 py-3" id="payment-title">결제 방법</h4>
        <div id="payment-method" class="mb-3"></div>
        <div id="agreement" class="payment-widget-cache-1boe9vx mb-3"></div>
    </div>
</template>

<style scoped>
#payment-method {
    border: 1px solid #e1e1e1;
    padding: 1rem;
    border-radius: 8px;
}

#payment-title {
    border-bottom: 2px solid black;
}

.payment-widget-cache-1boe9vx {
    margin: -15px -30px;
}
</style>