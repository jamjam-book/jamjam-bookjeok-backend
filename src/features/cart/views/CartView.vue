<script setup>
import {reactive, computed} from 'vue'
import CartItemView from "@/features/cart/views/CartItemView.vue";

const items = reactive([
    {id: 1, title: '도메인 주도 개발 시작하기', price: 25000, quantity: 1, selected: true, image: '/src/assets/images/ddd.png'},
    {id: 2, title: 'Clean Code(클린 코드)', price: 30000, quantity: 2, selected: true, image: '/src/assets/images/cleancode.png'},
    {id: 3, title: '객체지향의 사실과 오해', price: 18000, quantity: 10, selected: false, image: '/src/assets/images/oop.png'},
]);

const totalPrice = computed(() =>
        items.filter(i => i.selected).reduce((sum, i) => sum + i.price * i.quantity, 0)
);

const selectedCount = computed(() => items.filter(i => i.selected).length);

const allSelected = computed({
    get: () => selectedCount.value === items.length,
    set: (value) => items.forEach(i => (i.selected = value))
});

function increaseQuantity(id) {
    const item = items.find(i => i.id === id);
    if (item && item.quantity < 999) {
        item.quantity++;
    }
}

function decreaseQuantity(id) {
    const item = items.find(i => i.id === id);
    if (item && item.quantity > 1) {
        item.quantity--;
    }
}

function removeItem(id) {
    const index = items.findIndex(i => i.id === id);
    if (index !== -1) {
        items.splice(index, 1);
    }
}
</script>

<template>
    <div id="cart-container" class="d-flex">
        <div class="flex-grow-1">
            <h3 class="fw-bold mb-4">장바구니</h3>

            <div id="select-all-box" class="form-check border-bottom py-3">
                <input class="form-check-input" id="cart-all-checkbox" type="checkbox" v-model="allSelected"/>
                <label class="form-check-label">전체 선택 {{ selectedCount }} / {{ items.length }}</label>
            </div>

            <CartItemView
                    v-for="item in items"
                    :key="item.id"
                    :item="item"
                    @increase="increaseQuantity"
                    @decrease="decreaseQuantity"
                    @remove="removeItem"
            />
        </div>

        <div id="order-detail-info" class="border fw-bold rounded-4 border-black shadow-sm">
            <div class="mb-3 d-flex justify-content-between">
                <span>상품 금액</span>
                <strong>{{ totalPrice.toLocaleString() }}원</strong>
            </div>
            <div class="mb-3 d-flex justify-content-between">
                <span>배송비</span>
                <span>0원</span>
            </div>
            <div class="mb-3 d-flex justify-content-between">
                <span>상품 할인</span>
                <span>0원</span>
            </div>
            <hr/>
            <div class="mb-3 d-flex justify-content-between fw-bold">
                <span>결제 예정 금액</span>
                <span>{{ totalPrice.toLocaleString() }}원</span>
            </div>
            <div class="mb-3 d-flex justify-content-between">
                <span>적립 예정 포인트</span>
                <span>0P</span>
            </div>
            <button id="order-button" class="btn w-100 fw-bold">주문하기</button>
        </div>
    </div>
</template>

<style scoped>
#cart-container {
    margin-top: 3rem;
    margin-bottom: 3rem;
    display: flex;
}

#select-all-box {
    margin-left: 9px;
}

#order-detail-info {
    width: 274px;
    height: 325px;
    padding: 1.5rem;
    margin-left: 2rem;
}

#order-button {
    background-color: #f9f0df;
    box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.25);
    margin-top: 18px;
}

#cart-all-checkbox:checked {
    background-color: #E2D1C5;
    border: 1px solid #ccc;
}
</style>