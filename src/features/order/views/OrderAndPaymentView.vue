<script setup>
const {item} = defineProps(['item'])
</script>

<template>
    <div id="order-item-container">
        <div id="order-left" class="d-flex align-items-center flex-grow-1">
            <img :src="item.image"
                 id="order-image"
                 alt="도서 이미지"/>
            <div id="order-book-info">
                <span id="order-book-title" class="fw-bold mb-2">{{ item.title }}</span>
                <span>{{ item.price.toLocaleString() }}원</span>
            </div>
        </div>
    </div>
</template>

<style scoped>
#order-item-container {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding-top: 1rem;
    padding-bottom: 1rem;
    border-bottom: 1px solid #dee2e6;
}

#order-checkbox {
    top: 0.5rem;
    left: 0.5rem;
    z-index: 1;
    position: absolute;
}

#order-checkbox:checked {
    background-color: #E2D1C5;
    border: 1px solid #ccc;
}

#order-left {
    padding-left: 1.5rem;
    padding-bottom: 10px;
}

#order-image {
    width: 110px;
    height: 136px;
    object-fit: cover;
    margin-left: 20px;
    margin-right: 1rem;
}

#order-book-info {
    margin-left: 30px;
}

#order-book-title {
    margin-bottom: 10px;
}

#order-divider {
    width: 1px;
    height: 140px;
    background-color: #ccc;
    margin: 0 1.5rem;
}

#order-right {
    min-width: 170px;
    margin-right: 30px;
}

#order-total-price {
    padding-bottom: 10px;
}

#order-quantity-wrapper {
    border-radius: 0.375rem;
}

#order-quantity-input {
    width: 48px;
    font-size: 13px;
}

/* 스핀 버튼 제거 */
#order-quantity-input::-webkit-outer-spin-button,
#order-quantity-input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

#order-quantity-input {
    -moz-appearance: textfield;
}

#order-remove-button {
    top: 8px;
    right: 8px;
    font-size: 18px;
    position: absolute;
}
</style>