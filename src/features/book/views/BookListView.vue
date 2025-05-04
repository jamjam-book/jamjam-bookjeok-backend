<template>
    <div class="book-category-page">
        <!-- 왼쪽: 필터 영역 -->
        <aside class="filter-section">
            <BookListFilter
                    :categories="categories"
                    :selected-category-ids="selectedCategoryIds"
                    :min-price="minPrice"
                    :max-price="maxPrice"
                    @update:categoryIds="updateCategories"
                    @update:priceRange="updatePriceRange"
            />
        </aside>

        <!-- 오른쪽: 정렬 + 목록 -->
        <main class="content-section">
            <div class="sort-bar">
                <button
                        v-for="option in sortOptions"
                        :key="option.value"
                        :class="['sort-button', { active: selectedSort === option.value }]"
                        @click="selectedSort = option.value"
                >
                    {{ option.label }}
                </button>
            </div>

            <div class="book-grid">
                <BookCard
                        v-for="book in filteredBooks"
                        :key="book.id"
                        :book="book"
                />
            </div>
        </main>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import BookCard from '@/features/book/components/BookCard.vue';
import BookListFilter from "@/features/book/components/BookListFilter.vue";

const categories = [
    { id: 1, name: '소설' },
    { id: 2, name: '시/에세이' },
    { id: 3, name: '기술/공학' },
];

const books = ref([
    { id: 1, title: '아무튼, 디지몬', price: 15000, categoryId: 2, publishDate: '2024-05-31', imageUrl:'/images/main_482791348279137620.20240609071045.jpg'},
    { id: 2, title: '거꾸로 읽는 세계사', price: 22000, categoryId: 3, publishDate: '1988-07-30', imageUrl:'/images/main_324438732443877025.20230509165419.jpg' },
    { id: 3, title: '행성어 서점', price: 18000, categoryId: 1, publishDate: '2021-11-05', imageUrl:'/images/main_324721232472125024.20240302074840.jpg' },
    { id: 4, title: '희랍어 시간', price: 17000, categoryId: 1, publishDate: '2011-11-10', imageUrl:'/images/main_324760932476098329.20230829085010.jpg' },
    { id: 5, title: '나인', price: 21000, categoryId: 1, publishDate: '2021-10-05', imageUrl:'/images/main_324803232480322263.20230725121109.jpg' },
    { id: 6, title: '소년이 온다', price: 17500, categoryId: 1, publishDate: '2014-05-19', imageUrl:'/images/main_324914032491401626.20231004072435.jpg' },
    { id: 7, title: '책과 우연들', price: 16200, categoryId: 2, publishDate: '2022-09-23', imageUrl:'/images/main_347741134774110623.20230919124626.jpg' },
    { id: 8, title: '아무튼, SF게임', price: 15000, categoryId: 2, publishDate: '2024-07-10', imageUrl:'/images/main_487410648741065629.20240629092241.jpg' },
]);

const selectedCategoryIds = ref([]);
const selectedSort = ref('latest');
const minPrice = 0;
const maxPrice = 50000;
const priceRange = ref([minPrice, maxPrice]);

const updateCategories = (ids) => {
    selectedCategoryIds.value = ids;
};

const updatePriceRange = (range) => {
    priceRange.value = range;
};

const sortOptions = [
    { value: 'latest', label: '최신순' },
    { value: 'low', label: '가격 낮은 순' },
    { value: 'high', label: '가격 높은 순' },
    { value: 'popular', label: '인기순' },
];

const filteredBooks = computed(() => {
    let result = books.value
            .filter(book =>
            selectedCategoryIds.value.length === 0 || selectedCategoryIds.value.includes(book.categoryId)
    )
            .filter(book =>
            book.price >= priceRange.value[0] && book.price <= priceRange.value[1]
    );

    const sortedResult = [...result];

    switch (selectedSort.value) {
        case 'low':
            sortedResult.sort((a, b) => a.price - b.price);
            break;
        case 'high':
            sortedResult.sort((a, b) => b.price - a.price);
            break;
        case 'latest':
            sortedResult.sort((a, b) => new Date(b.publishDate) - new Date(a.publishDate));
            break;
        case 'popular':
            sortedResult.sort((a, b) => b.id - a.id);
            break;
    }

    return sortedResult;
});
</script>

<style scoped>
.book-category-page {
    display: flex;
    max-width: 1200px;
    margin: 0 auto;
    padding: 24px;
    gap: 24px;
}

.filter-section {
    width: 240px;
}

.content-section {
    flex: 1;
    display: flex;
    flex-direction: column;
}

.sort-bar {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-bottom: 16px;
}

.sort-button {
    padding: 8px 12px;
    border: none;
    background: none;
    color: #854d14;
    font-size: 14px;
    cursor: pointer;
    border-radius: 6px;
}

.sort-button.active {
    background-color: #854d14;
    color: white;
}

.book-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
    gap: 20px;
}
</style>
