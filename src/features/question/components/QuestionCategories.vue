<!--문의사항 카테고리 버튼-->
<template>
    <div class="frame">
        <!-- 전체 버튼 -->
        <button v-if="list"
                class="category-button"
                :class="{ active: modelValue === null }"
                @click="selectCategory(null)"
        >
            전체
        </button>

        <!-- 카테고리 버튼 -->
        <button
                v-for="category in categories"
                :key="category.id"
                class="category-button"
                :class="{ active: category.id === modelValue }"
                @click="selectCategory(category.id)"
        >
            {{ category.name }}
        </button>
    </div>
</template>

<script setup>

const { categories, list, modelValue } = defineProps({
  categories: {
    type: Array,
    required: true },
  list: {
    type: Boolean,
    default: false },
  modelValue: Number,
});

const emit = defineEmits(['update:modelValue']);

const selectCategory = (id) => {
  emit('update:modelValue', id);
};
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
    padding: 6px 16px;
    border-radius: 25px;
    border: 1px solid #854d14;
    font-weight: 400;
    font-size: 14px;
    background-color: white;
    color: #854d14;
    cursor: pointer;
    min-width: 50px;
    text-align: center;
    transition: background-color 0.2s ease, color 0.2s ease, transform 0.2s ease;
}

.category-button:hover {
  background-color: #f0e0cf;
  transform: translateY(-1px);
}

.category-button.active {
    background-color: #854d14;
    color: white;
}
</style>