<!--문의사항 카테고리 버튼-->
<template>
    <div class="frame">
        <!-- 전체 버튼 -->
        <button v-if="list"
                class="category-button"
                :class="{ active: selectedCategoryId === null }"
                @click="selectCategory(null)"
        >
            전체
        </button>

        <!-- 동적으로 생성된 카테고리 버튼 -->
        <button
                v-for="category in categories"
                :key="category.id"
                class="category-button"
                :class="{ active: selectedCategoryId === category.id }"
                @click="selectCategory(category.id)"
        >
            {{ category.name }}
        </button>
    </div>
</template>

<script setup>

import {ref} from "vue";

const selectedCategoryId = ref(null);

const {categories, list} = defineProps({
    categories: {
        type: Array,
        required: true,
    },
    list : true
});

const emit = defineEmits(['update:selectedCategoryId']);

const selectCategory = (id) => {
    selectedCategoryId.value = id;
    emit('update:selectedCategoryId', selectedCategoryId.value)
}

</script>

<style scoped>
.frame {
    display: flex;
    justify-content: flex-start;
    gap: 10px;
    padding: 10px;
    flex-wrap: wrap;
    width: 100%;
    max-width: 600px;

}

.category-button {
    padding: 5px 10px;
    border-radius: 25px;
    border: #854d14;
    font-weight: 400;
    font-size: 14px;
    background-color: white;
    color: #854d14;
    cursor: pointer;
    min-width: 30px;
    text-align: center;
    transition: all 0.2s ease;
}

.category-button.active {
    background-color: #854d14;
    color: white;
}
</style>