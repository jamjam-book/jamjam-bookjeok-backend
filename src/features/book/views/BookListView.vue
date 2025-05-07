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
                        @click="handleSort(option.value)"
                        :disabled="isLoading"
                >
                    {{ option.label }}
                </button>
            </div>

            <div v-if="isLoading && books.length === 0" class="spinner-wrapper">
                <span class="spinner" />
            </div>

            <div v-else class="book-grid">
                <BookCard
                        v-for="book in books"
                        :key="book.id"
                        :book="book"
                />
            </div>

            <!-- 무한 스크롤 감지용 div -->
            <div ref="scrollObserver"></div>
        </main>
    </div>
</template>

<script setup>
import {ref, watch, onMounted, onUnmounted, nextTick, computed} from 'vue';
import { debounce } from 'lodash';
import { useRoute } from 'vue-router';
import BookCard from '@/features/book/components/BookCard.vue';
import BookListFilter from '@/features/book/components/BookListFilter.vue';
import { getBookList, getCategoryList, getPriceRange } from '@/features/book/api.js';

const route = useRoute();

const books = ref([]);
const page = ref(0);
const size = 10;
const lastPage = ref(false);
const isLoading = ref(false);

const initialCategoryId = computed(() => Number(route.query.categoryId))
console.log(`categoryId : ${initialCategoryId}`)
const selectedCategoryIds = ref([initialCategoryId.value]);
const selectedSort = ref('latest');
const minPrice = ref(0);
const maxPrice = ref(50000);
const priceRange = ref([0, 50000]);

const categories = ref([]);
const scrollObserver = ref(null);
let observer = null;
let initialized = ref(false);

const sortOptions = [
    { value: 'latest', label: '최신순' },
    { value: 'low', label: '가격 낮은 순' },
    { value: 'high', label: '가격 높은 순' },
    { value: 'orderDesc', label: '판매 순' },
    { value: 'popular', label: '인기순' },
];

const fetchCategories = async () => {
    try {
        const res = await getCategoryList();
        if (res.data?.success) {
            categories.value = res.data.data;
        }
    } catch (e) {
        console.error('카테고리 API 호출 실패:', e);
    }
};

const fetchPriceRange = async () => {
    try {
        const params = {
            categoryIds: selectedCategoryIds.value.join(','),
            sort: selectedSort.value,
        };
        const res = await getPriceRange(params);
        if (res.data?.success) {
            const { min, max } = res.data.data;
            minPrice.value = min;
            maxPrice.value = max;
            priceRange.value = [min, max];
        }
    } catch (e) {
        console.error('가격 범위 조회 실패:', e);
    }
};

const fetchBooks = async () => {
    if (isLoading.value || lastPage.value) return;
    isLoading.value = true;

    const params = {
        page: page.value,
        size,
        categoryIds: selectedCategoryIds.value.join(','),
        sort: selectedSort.value,
        minPrice: priceRange.value[0],
        maxPrice: priceRange.value[1],
    };

    try {
        const res = await getBookList(params);
        const content = res.data.data.content;
        const isLast = res.data.data.last;

        if (page.value === 0) {
            books.value = content;
        } else {
            books.value = [...books.value, ...content];
        }

        lastPage.value = isLast;
        page.value += 1;
    } catch (e) {
        console.error('도서 목록 조회 실패:', e);
    } finally {
        isLoading.value = false;
    }
};

const resetAndFetch = async () => {
    page.value = 0;
    lastPage.value = false;
    books.value = [];
    await fetchBooks();
};

const updateCategories = async (ids) => {
    selectedCategoryIds.value = ids;
    await fetchPriceRange();
    await resetAndFetch();
};

const updatePriceRange = debounce(async (range) => {
    priceRange.value = range;
    await resetAndFetch();
}, 300);

const handleSort = async (sort) => {
    selectedSort.value = sort;
    await fetchPriceRange();
    await resetAndFetch();
};

const initObserver = () => {
    observer = new IntersectionObserver(entries => {
        if (entries[0].isIntersecting && !lastPage.value && !isLoading.value) {
            fetchBooks();
        }
    });
    if (scrollObserver.value) {
        observer.observe(scrollObserver.value);
    }
};

onMounted(async () => {
    await fetchCategories();
    await fetchPriceRange();
    await fetchBooks();
    initObserver();
    initialized.value = true;
});

onUnmounted(() => {
    if (observer && scrollObserver.value) {
        observer.unobserve(scrollObserver.value);
    }
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

.spinner-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 200px;
}

.spinner {
    width: 32px;
    height: 32px;
    border: 4px solid #ccc;
    border-top: 4px solid #854d14;
    border-radius: 50%;
    animation: spin 0.8s linear infinite;
}

@keyframes spin {
    to {
        transform: rotate(360deg);
    }
}
</style>
