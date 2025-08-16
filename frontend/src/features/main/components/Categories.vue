<template>
    <div class="category-button-group">
        <button
                v-for="category in categories"
                :key="category.categoryId"
                class="category-button"
                @click="goToCategory(category.categoryId)"
        >
            {{ category.categoryName }}
        </button>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import {getMainCategories} from "@/features/main/api.js";

const router = useRouter();
const categories = ref([]);

const goToCategory = (id) => {
    router.push({ path: '/books', query: { categoryId: id } });
};

const fetchCategories = async () => {

    try {
        const res = await getMainCategories();

        if(res.data && res.data.success) {
            categories.value = res.data.data;
        } else {
            console.error('카테고리 목록 조회 실패')
        }
    } catch(e) {
        console.error('카테고리 목록 조회 실패', e);
    }
}
onMounted(()=> {
    fetchCategories();
});
</script>

<style scoped>
.category-button-group {
    display: flex;
    justify-content: center;
    gap: 1rem;
    margin-top: 2rem;
    flex-wrap: wrap;
}
.category-button {
    background-color: #f5f5f5;
    color: #4b2e2e;
    border: none;
    border-radius: 1.5rem;
    padding: 0.6rem 1.2rem;
    font-size: 0.95rem;
    cursor: pointer;
    transition: background-color 0.2s ease;
}
.category-button:hover {
    background-color: #e0e0e0;
}
</style>