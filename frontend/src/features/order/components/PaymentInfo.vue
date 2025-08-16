<script setup>
const props = defineProps({
    totalPrice: {
        type: Number,
        required: true
    },
    paymentMethod: {
        type: String,
        required: true
    }
})

const point = props.totalPrice * 0.01;
</script>

<template>
    <div class="payment-info-wrapper">
        <h4 class="fw-bold mb-3 py-3" id="payment-info-title">결제 정보</h4>

        <div class="info-grid">
            <!-- 1행 -->
            <div class="info-box border-right">
                <div class="label">주문 금액</div>
                <div class="value">{{ totalPrice.toLocaleString() }}원</div>
            </div>
            <div class="info-box border-right">
                <div class="label">할인 금액</div>
                <div class="value">0원</div>
            </div>
            <div class="info-box">
                <div class="label">결제 금액</div>
                <div class="value">{{ totalPrice.toLocaleString() }}원</div>
            </div>

            <!-- 2행 -->
            <div class="info-box border-right">
                <div class="label">배송비</div>
                <div class="value">0원</div>
            </div>
            <div class="info-box border-right">
                <div class="label">적립 포인트</div>
                <div class="value">{{ point }}P</div>
            </div>
            <div class="info-box">
                <div class="label">결제 방법</div>
                <div class="value">{{ paymentMethod }}</div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.payment-info-wrapper {
    max-width: 980px;
    margin: 0 auto;
    margin-top: 42px;
}

#payment-info-title {
    border-bottom: 2px solid black;
}

.info-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    row-gap: 20px;
    border-collapse: collapse;
}

.info-box {
    padding: 0 20px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
}

.label {
    font-weight: 500;
    font-size: 14px;
    margin-bottom: 4px;
}

.value {
    font-weight: bold;
    font-size: 16px;
}

.border-right {
    border-right: 1px solid #ccc;
}
</style>