<script setup>
import { ref } from 'vue';

const emit = defineEmits(['search']);
const searchQuery = ref('');
const selectedFilter = ref('memberId');
const selectedLabel = ref('아이디');
const showFilters = ref(false);
const placeholder = '회원을 검색하세요.';

const filterOptions = [
    { label: '아이디', value: 'memberId' },
    { label: '닉네임', value: 'nickname' },
];

const toggleFilter = () => {
    showFilters.value = !showFilters.value;
};

const selectFilter = (option) => {
    selectedFilter.value = option.value;
    selectedLabel.value = option.label;
    showFilters.value = false;
    searchQuery.value = ''; // 필터 변경 시 입력값 초기화 하기
};

const emitSearch = (e) => {
    if (e) e.preventDefault();
    emit('search', {
        memberId: selectedFilter.value === 'memberId' ? searchQuery.value : null,
        nickname:  selectedFilter.value === 'nickname' ? searchQuery.value : null
    });
};
</script>

<template>
    <div class="search-bar-wrapper">
        <form class="search-bar" @submit="emitSearch">
            <!-- 현재 선택된 필터 -->
            <div class="selected-filter">
                {{ selectedLabel }}
                <div v-if="!showFilters"  class="toggle-btn" @click="toggleFilter">▶</div>
                <div v-else class="toggle-btn" @click="toggleFilter">▼</div>
            </div>

            <input
                    v-model="searchQuery"
                    type="text"
                    class="search-input"
                    :placeholder="placeholder"
                    @keyup.enter="emitSearch"
            />
            <button
                    type="submit"
                    class="search-button"
            >
                <img class="search-icon" src="../../../assets/icons/search.png" alt="검색" />
            </button>
        </form>

        <div v-if="showFilters" class="filter-box">
            <div
                    v-for="option in filterOptions"
                    :key="option.value"
                    class="filter-option"
                    @click="selectFilter(option)"
            >
                {{ option.label }}
            </div>
        </div>
    </div>
</template>

<style scoped>
.search-bar-wrapper {
    position: relative;
    max-width: 900px;
    width: 100%;
}

.search-bar {
    display: flex;
    align-items: center;
    border-radius: 8px;
    padding: 0 12px;
    height: 52px;
    box-shadow: inset 0 0 0 1px #ccc;
}

.toggle-btn {
    display: flex;
    flex-direction: column;
    justify-content: center;
    gap: 3px;
    background: none;
    border: none;
    padding: 0 0 0 10px;
    cursor: pointer;
    color: #522404;
}

.selected-filter {
    display: flex;
    font-size: 14px;
    color: #333;
    white-space: nowrap;
    background-color: #f9f0df;
    padding: 5px 10px;
    border-radius: 8px;
}

.search-input {
    flex: 1;
    border: none;
    background: transparent;
    font-size: 16px;
    padding: 10px;
    outline: none;
}

.search-button {
    background: none;
    border: none;
    cursor: pointer;
    padding-left: 10px;
}

.search-icon {
    width: 20px;
    height: 20px;
}

.filter-box {
    position: absolute;
    top: 60px;
    left: 10px;
    background: white;
    border: 1px solid #ddd;
    padding: 10px 12px;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    z-index: 10;
    display: flex;
    flex-direction: column;
    gap: 6px;
}

.filter-option {
    font-size: 14px;
    color: #333;
    padding: 6px 10px;
    border-radius: 4px;
    cursor: pointer;
    transition: background 0.2s;
}

.filter-option:hover {
    background-color: #f0f0f0;
}
</style>
