<script setup>
import {reactive, computed} from 'vue';
import CartItem from "@/features/cart/components/CartItem.vue";
import CartSummary from "@/features/cart/components/CartSummary.vue";

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
    <div class="container">
        <div class="d-flex">
            <div class="flex-grow-1">
                <h3 class="fw-bold mb-4">장바구니</h3>

                <div id="select-all-box" class="form-check border-bottom py-3">
                    <input class="form-check-input" id="cart-all-checkbox" type="checkbox" v-model="allSelected"/>
                    <label class="form-check-label">전체 선택 {{ selectedCount }} / {{ items.length }}</label>
                </div>

                <CartItem
                        v-for="item in items"
                        :key="item.id"
                        :item="item"
                        @increase="increaseQuantity"
                        @decrease="decreaseQuantity"
                        @remove="removeItem"
                />
            </div>

            <CartSummary :items="items" :totalPrice="totalPrice"/>
        </div>
    </div>
</template>

<style scoped>
.container {
    max-width: 1200px;
    margin: 42px auto;
}

#cart-all-checkbox:checked {
    background-color: #E2D1C5;
    border: 1px solid #ccc;
}
</style>