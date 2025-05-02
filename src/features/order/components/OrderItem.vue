<script setup>
const {item} = defineProps(['item'])
</script>

<template>
    <div id="order-item">
        <div id="left-section">
            <img :src="item.image" alt="도서 이미지" id="book-image"/>
            <div id="item-info">
                <div id="item-title">{{ item.title }}</div>
                <div id="item-quantity">{{ item.quantity }}개</div>
            </div>
        </div>
        <div id="item-price">
            {{ (item.price * item.quantity).toLocaleString() }}원
        </div>
    </div>
</template>

<style scoped>
#order-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #dee2e6;
    padding: 15px 0;
}

#left-section {
    display: flex;
}

#book-image {
    width: 110px;
    height: 136px;
    object-fit: cover;
    margin-right: 24px;
}

#item-info {
    display: flex;
    flex-direction: column;
    justify-content: center;
}

#item-title {
    font-weight: bold;
    font-size: 18px;
    margin-bottom: 8px;
}

#item-quantity {
    font-size: 16px;
}

#item-price {
    font-size: 18px;
    font-weight: bold;
    white-space: nowrap;
    display: flex;
    align-items: center;
}
</style>