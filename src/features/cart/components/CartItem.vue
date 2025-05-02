<script setup>
const {item} = defineProps(['item']);
const emit = defineEmits(['increase', 'decrease', 'remove']);

function increase() {
    if (item.quantity < 999) {
        emit('increase', item.id);
    }
}

function decrease() {
    if (item.quantity > 1) {
        emit('decrease', item.id);
    }
}

function remove() {
    emit('remove', item.id);
}

function handleInput(item) {
    if (item.quantity > 999) {
        item.quantity = 999;
    } else if (item.quantity < 1 || isNaN(item.quantity)) {
        item.quantity = 1;
    }
}
</script>

<template>
    <div id="cart-item-container"
         class="position-relative d-flex align-items-center justify-content-between border-bottom py-3">

        <!-- 체크박스 -->
        <input type="checkbox"
               v-model="item.selected"
               id="cart-checkbox"
               class="form-check-input position-absolute"/>

        <!-- 도서명, 가격 -->
        <div id="cart-left" class="d-flex align-items-center flex-grow-1">
            <img :src="item.image" id="cart-image" alt="도서 이미지"/>
            <div id="cart-book-info">
                <div id="cart-book-title" class="fw-bold mb-2">{{ item.title }}</div>
                <div>{{ item.price.toLocaleString() }}원</div>
            </div>
        </div>

        <!-- 중앙 회색 선 -->
        <div id="cart-divider"></div>

        <!-- 수량 -->
        <div id="cart-right" class="d-flex flex-column align-items-center">
            <div id="cart-total-price" class="fw-bold mb-2 text-nowrap">
                {{ (item.price * item.quantity).toLocaleString() }}원
            </div>
            <div id="cart-quantity-wrapper" class="d-flex align-items-center border rounded">
                <button class="btn btn-sm btn-outline-secondary border-0" @click="decrease">-</button>
                <input
                        type="number"
                        id="cart-quantity-input"
                        v-model.number="item.quantity"
                        @input="handleInput(item)"
                        class="form-control text-center border-0"
                        min="1"
                        max="999"
                />
                <button class="btn btn-sm btn-outline-secondary border-0" @click="increase">+</button>
            </div>
        </div>

        <!-- 삭제 버튼 -->
        <button @click="remove" id="cart-remove-button" class="btn btn-sm position-absolute">×</button>
    </div>
</template>

<style scoped>
#cart-item-container {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding-top: 1rem;
    padding-bottom: 1rem;
    border-bottom: 1px solid #dee2e6;
}

#cart-checkbox {
    top: 0.5rem;
    left: 0.5rem;
    z-index: 1;
    position: absolute;
}

#cart-checkbox:checked {
    background-color: #E2D1C5;
    border: 1px solid #ccc;
}

#cart-left {
    padding-left: 1.5rem;
    padding-bottom: 10px;
}

#cart-image {
    width: 110px;
    height: 136px;
    object-fit: cover;
    margin-left: 20px;
    margin-right: 1rem;
}

#cart-book-info {
    margin-left: 30px;
}

#cart-book-title {
    margin-bottom: 10px;
}

#cart-divider {
    width: 1px;
    height: 140px;
    background-color: #ccc;
    margin: 0 1.5rem;
}

#cart-right {
    min-width: 170px;
    margin-right: 30px;
}

#cart-total-price {
    padding-bottom: 10px;
}

#cart-quantity-wrapper {
    border-radius: 0.375rem;
}

#cart-quantity-input {
    width: 48px;
    font-size: 13px;
    -moz-appearance: textfield;
}

/* 스핀 버튼 제거 */
#cart-quantity-input::-webkit-outer-spin-button,
#cart-quantity-input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

#cart-remove-button {
    top: 8px;
    right: 8px;
    font-size: 18px;
    position: absolute;
}
</style>