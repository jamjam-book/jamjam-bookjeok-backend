<script setup>
import {reactive, computed, onMounted} from 'vue';
import axios from 'axios';
import CartItem from "@/features/cart/components/CartItem.vue";
import CartSummary from "@/features/cart/components/CartSummary.vue";
import {useRouter} from "vue-router";

const items = reactive([]); // 초기화만 해놓고 onMounted에서 채움
const router = useRouter();

const totalPrice = computed(() =>
        items.filter(i => i.selected).reduce((sum, i) => sum + i.price * i.quantity, 0)
);

const selectedCount = computed(() => items.filter(i => i.selected).length);

const allSelected = computed({
    get: () => selectedCount.value === items.length,
    set: (value) => items.forEach(i => (i.selected = value))
});

async function updateQuantity(item) {
    const memberId = 'user01';
    try {
        const baseUrl = import.meta.env.VITE_API_BASE_URL;
        await axios.put(`${baseUrl}/members/${memberId}/carts`, {
            bookId: item.id,
            bookName: item.bookName,
            quantity: item.quantity
        });
    } catch (error) {
        console.error('[ERROR] 수량 수정 실패', error);
        alert('수량 변경에 실패했습니다.');
    }
}

function increaseQuantity(id) {
    const item = items.find(i => i.id === id);
    if (item && item.quantity < 999) {
        item.quantity++;
        updateQuantity(item); // 수량 수정 API 호출
    }
}

function decreaseQuantity(id) {
    const item = items.find(i => i.id === id);
    if (item && item.quantity > 1) {
        item.quantity--;
        updateQuantity(item); // 수량 수정 API 호출
    }
}

async function removeItem(id) {
    const item = items.find(i => i.id === id);
    if (!item) return;

    try {
        const baseUrl = import.meta.env.VITE_API_BASE_URL;
        const memberId = 'user01';

        await axios.delete(`${baseUrl}/members/${memberId}/carts`, {
            data: {
                bookId: item.id,
                bookName: item.bookName,
                quantity: item.quantity
            }
        });

        const index = items.findIndex(i => i.id === id);
        if (index !== -1) {
            items.splice(index, 1);
        }
    } catch (error) {
        console.error('[ERROR] 장바구니 삭제 실패', error);
        alert('장바구니 항목 삭제에 실패했습니다.');
    }
}

function validateAllQuantities() {
    items.forEach(item => {
        if (!item.quantity || isNaN(item.quantity) || item.quantity < 1) {
            item.quantity = 1;
        }
    });
}

async function handleOrderNow() {
    validateAllQuantities();
    const selectedItems = items.filter(i => i.selected);

    if (selectedItems.length === 0) {
        alert('선택된 상품이 없습니다.');
        return;
    }

    const total = selectedItems.reduce((sum, i) => sum + i.price * i.quantity, 0);

    sessionStorage.setItem('orderItems', JSON.stringify(selectedItems));
    sessionStorage.setItem('orderTotalPrice', total.toString());

    await router.push('/order');
}

onMounted(async () => {
    try {
        const memberId = 'user01'; // 실제 로그인 유저 ID로 대체 필요
        const baseUrl = import.meta.env.VITE_API_BASE_URL;
        const resp = await axios.get(`${baseUrl}/members/${memberId}/carts`);

        if (resp?.data?.data?.bookList) {
            const bookList = resp.data.data.bookList;

            bookList.forEach(book => {
                items.push({
                    id: book.bookId,
                    bookName: book.bookName,
                    price: Math.floor(book.totalPrice / book.quantity),
                    quantity: book.quantity,
                    selected: true,
                    image: book.imageUrl
                });
            });
        } else {
            console.error('[ERROR] 응답 구조가 예상과 다릅니다:', resp.data);
        }

    } catch (error) {
        console.error('[ERROR] 장바구니 조회 실패', error);
    }
});

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
                        @update="updateQuantity"
                />
            </div>

            <CartSummary :items="items" :totalPrice="totalPrice" @order-now="handleOrderNow"/>
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