<script setup>
import { ref } from 'vue';

const emit = defineEmits(['search']);
const searchQuery = ref('');
const placeholder = '게시글을 검색하세요.';

const handleSubmit = (event) => {
    event.preventDefault();
    emit('search', searchQuery.value);
};
</script>

<template>
    <div class="search-bar-wrapper">
        <form class="search-bar" @submit="handleSubmit">
            <input
                    v-model="searchQuery"
                    type="text"
                    class="search-input"
                    :placeholder="placeholder"
            />
            <button type="submit" class="search-button">
                <img class="search-icon" src="@/assets/icons/search.png" alt="검색" />
            </button>
        </form>
    </div>
</template>

<style scoped>
.search-bar-wrapper {
    position: relative;
    max-width: 966px;
    width: 100%;
    margin: auto;
    display: flex;
    align-items: center;
    padding: 0 15px;
}

.search-bar {
    margin: 20px 0;
    display: flex;
    align-items: center;
    border-radius: 8px;
    padding: 0 12px;
    height: 52px;
    box-shadow: inset 0 0 0 1px #ccc;
    background-color: #ffffff;
    transition: box-shadow 0.2s ease;
    width: 966px;
}

.search-bar:focus-within {
    box-shadow: 0 0 0 2px #854d14;
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
    display: flex;
    align-items: center;
    justify-content: center;
    transition: transform 0.2s ease;
}

.search-button:hover {
    transform: scale(1.1);
}

.search-icon {
    width: 20px;
    height: 20px;
}
</style>
