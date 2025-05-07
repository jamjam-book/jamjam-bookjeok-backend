<template>
    <div class="search-bar-wrapper">
        <form class="search-bar" @submit.prevent="onSearch">
            <!-- 토글 버튼 -->
            <button type="button" class="toggle-btn" @click="toggleFilter">
                <span class="bar"></span>
                <span class="bar"></span>
                <span class="bar"></span>
            </button>

            <!-- 선택된 조건 표시 -->
            <div class="selected-filter">
                {{ selectedLabel || '전체' }}
            </div>

            <!-- 검색 입력창 -->
            <input
                    v-model="searchQuery"
                    type="text"
                    class="search-input"
                    :placeholder="placeholder"
            />

            <!-- 검색 버튼 -->
            <button type="submit" class="search-button">
                <img class="search-icon" src="../../assets/icons/search.png" alt="검색" />
            </button>
        </form>

        <!-- 라디오 필터 박스 -->
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

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const searchQuery = ref('')
const selectedFilter = ref('all') // 기본값을 'all'로 지정
const selectedLabel = ref('전체')
const showFilters = ref(false)
const placeholder = '도서를 검색해보세요'

const filterOptions = [
    { label: '전체', value: 'all' },
    { label: '제목', value: 'title' },
    { label: '저자', value: 'author' },
    { label: '출판사', value: 'publisher' }
]

const toggleFilter = () => {
    showFilters.value = !showFilters.value
}

const selectFilter = (option) => {
    selectedFilter.value = option.value
    selectedLabel.value = option.label
    showFilters.value = false
}

const onSearch = async () => {
    const keyword = searchQuery.value.trim()
    const keywordType = selectedFilter.value || 'all'

    if (!keyword) return

    // ✅ 조건별 라우팅 분기
    if (keywordType === 'all' || keywordType === 'title') {
        await router.push({
            path: '/books',
            query: { keyword, keywordType }
        })
    } else if (keywordType === 'author') {
        await router.push({
            path: '/books/authorbooks',
            query: { keyword }
        })
    } else if (keywordType === 'publisher') {
        await router.push({
            path: '/books/publisherbooks',
            query: { keyword }
        })
    }
}
</script>

<style scoped>
.search-bar-wrapper {
    position: relative;
    max-width: 720px;
    width: 100%;
}

.search-bar {
    display: flex;
    align-items: center;
    background-color: #f5f5f5;
    border-radius: 28px;
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
    padding: 0 10px;
    cursor: pointer;
}

.bar {
    width: 18px;
    height: 2px;
    background-color: #555;
    border-radius: 1px;
}

.selected-filter {
    font-size: 14px;
    color: #333;
    margin-right: 10px;
    white-space: nowrap;
    min-width: 60px;
}

.search-input {
    flex: 1;
    border: none;
    background: transparent;
    font-size: 16px;
    padding: 8px;
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
