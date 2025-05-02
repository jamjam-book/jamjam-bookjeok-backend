import { defineStore } from 'pinia';

export const useCartStore = defineStore('cart', {
    state: () => ({
        selectedItems: [],
        totalPrice: 0,
    }),
    actions: {
        setOrderData(items, totalPrice) {
            this.selectedItems = items;
            this.totalPrice = totalPrice;
        },
    },
});