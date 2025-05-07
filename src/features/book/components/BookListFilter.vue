<template>
    <div class="category-filter">
        <h3 class="category-label">카테고리</h3>

        <!-- 선택된 카테고리 태그 버튼 -->
        <div class="selected-tags" v-if="selectedCategoryIds.length">
            <button
                    v-for="id in selectedCategoryIds"
                    :key="id"
                    class="tag-button"
                    @click="toggleCategory(id)"
            >
                {{ getCategoryName(id) }} ✕
            </button>
        </div>

        <!-- 체크박스 카테고리 목록 -->
        <ul class="checkbox-list">
            <li v-for="category in categories" :key="category.categoryId" class="checkbox-item">
                <label>
                    <input
                            type="checkbox"
                            :value="category.categoryId"
                            :checked="selectedCategoryIds.includes(category.categoryId)"
                            @change="toggleCategory(category.categoryId)"
                    />
                    {{ category.categoryName }}
                </label>
            </li>
        </ul>

        <h3 class="price-label">가격</h3>
        <div class="slider-wrapper">
            <Slider
                    v-model="priceRange"
                    :min="minPrice"
                    :max="maxPrice"
                    :tooltip="'always'"
                    :format="val => `${val.toLocaleString()}원`"
            />
        </div>
    </div>
</template>

<script setup>
import '@vueform/slider/themes/default.css';
import { ref, watch, watchEffect } from 'vue';
import Slider from '@vueform/slider';
import { debounce } from 'lodash';

const props = defineProps({
    categories: Array,
    selectedCategoryIds: Array,
    minPrice: Number,
    maxPrice: Number,
});
const emit = defineEmits(['update:categoryIds', 'update:priceRange']);

const priceRange = ref([props.minPrice, props.maxPrice]);

const selectedCategoryIds = ref([...props.selectedCategoryIds]);

const toggleCategory = (id) => {
    const index = selectedCategoryIds.value.indexOf(id);
    if (index === -1) {
        selectedCategoryIds.value.push(id);
    } else {
        selectedCategoryIds.value.splice(index, 1);
    }
    emit('update:categoryIds', selectedCategoryIds.value);
};

const getCategoryName = (id) => {
    const category = props.categories.find((cat) => cat.categoryId === id);
    return category ? category.categoryName : '';
};

watch(() => [props.minPrice, props.maxPrice], ([min, max]) => {
    priceRange.value = [min, max];
});

watch(
        priceRange,
        debounce((val) => {
            emit('update:priceRange', val);
        }, 300)
);

</script>

<style>
.category-filter {
    width: 240px;
    padding: 1rem;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #fff;
}

.category-label,
.price-label {
    margin-bottom: 0.5rem;
    font-weight: bold;
    font-size: 1rem;
    color: #333;
}

.price-label {
    margin-top: 2rem;
    margin-bottom: 2.5rem ! important;
}

.selected-tags {
    display: flex;
    flex-wrap: wrap;
    gap: 6px;
    margin-bottom: 0.75rem;
}

.tag-button {
    background-color: #854d14;
    color: white;
    border: none;
    border-radius: 16px;
    padding: 0.3rem 0.7rem;
    font-size: 13px;
    cursor: pointer;
    transition: background-color 0.2s ease;
}

.tag-button:hover {
    background-color: #a05a1d;
}

.checkbox-list {
    list-style: none;
    padding: 0;
    margin-bottom: 1.5rem;
}

.checkbox-item {
    font-size: 14px;
    margin-bottom: 8px;
    display: flex;
    align-items: center;
}

.checkbox-item input[type="checkbox"] {
    accent-color: #f0e4d4;
    width: 16px;
    height: 16px;
    cursor: pointer;
    margin-right: 6px;
}

.slider-wrapper {
    margin-top: 1rem;
    margin-bottom: 1.5rem;
}

/* slider 바 색상 */
.vue-slider .vue-slider-rail {
    background-color: #f0e0cc; /* 슬라이더 트랙 색상 */
}

.slider-connect {
    background-color: #854d14; /* 진행된 영역 색상 */
}

/* 핸들 색상 */
.vue-slider .vue-slider-dot-handle {
    background-color: #854d14;
    border: 2px solid #854d14;
}

/* 툴팁 색상 */
.slider-tooltip {
    background-color: #854d14;
    color: #fff;
    border: none;
}

.vue-slider .vue-slider-tooltip::after {
    border-top-color: #854d14; /* 툴팁 화살표 색 */
}
</style>
