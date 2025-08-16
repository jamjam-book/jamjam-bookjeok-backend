<template>
    <div class="stock-page-wrapper">
        <!-- 필터 영역 -->
        <div class="filter-box">
            <!-- 1행: 카테고리 & 판매상태 -->
            <div class="filter-grid">
                <div class="filter-pair">
                    <label class="filter-label">카테고리</label>
                    <select v-model="filters.category" class="naked-select">
                        <option value="">카테고리를 선택해주세요.</option>
                        <option v-for="c in categoryList" :key="c.categoryId" :value="c.categoryId">{{ c.categoryName }}</option>
                    </select>
                </div>

                <div class="filter-pair">
                    <label class="filter-label">판매상태</label>
                    <div class="status-group">
                        <label><input type="radio" value="ALL" v-model="filters.statusMode" /> 전체선택</label>
                        <label><input type="radio" value="SALE" v-model="filters.statusMode" /> 판매중</label>
                        <label><input type="radio" value="STOP" v-model="filters.statusMode" /> 판매중지</label>
                    </div>
                </div>
            </div>

            <!-- 2행: 키워드 & 버튼 -->
            <div class="filter-grid">
                <div class="filter-pair">
                    <label class="filter-label">키워드</label>
                    <select v-model="filters.keywordType" class="naked-select">
                        <option value="author">작가명</option>
                        <option value="isbn">ISBN</option>
                        <option value="title">도서제목</option>
                    </select>
                    <input v-model="filters.keyword" :placeholder="keywordPlaceholder" />
                </div>
                <div class="filter-pair">
                    <div class="filter-buttons">
                        <button @click="resetFilters">초기화</button>
                        <button @click="handleSearch">조회하기</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 정렬 드롭다운 -->
        <div class="sort-select-wrapper">
            <label class="sort-label">정렬 기준</label>
            <select v-model="filters.sort" class="sort-select">
                <option value="">최신순</option>
                <option value="old">과거순</option>
                <option value="ordersAsc">판매 오름차순</option>
                <option value="ordersDesc">판매 내림차순</option>
                <option value="interest">관심순</option>
            </select>
        </div>

        <!-- 테이블 -->
        <table class="stock-table">
            <thead>
            <tr>
                <th>아이디</th>
                <th>저자명</th>
                <th>ISBN</th>
                <th>도서명</th>
                <th>카테고리</th>
                <th>출판사</th>
                <th>가격</th>
                <th>재고 수량</th>
                <th>출판일</th>
                <th>판매상태</th>
            </tr>
            </thead>
            <tbody>
                <tr v-if="books.length === 0">
                    <td colspan="8" style="text-align: center; padding: 20px;">
                        조회된 결과가 없습니다.
                    </td>
                </tr>
                <tr v-for="book in books" :key="book.bookId" @click="bookDetail(book.bookId)">
                    <td>{{ book.bookId }}</td>
                    <td>{{ book.authorNames }}</td>
                    <td>{{ book.isbn }}</td>
                    <td>{{ book.bookName }}</td>
                    <td>{{ book.bookCategory.categoryName }}</td>
                    <td>{{ book.publisherName }}</td>
                    <td>{{ formatPrice(book.price) }}</td>
                    <td>{{ book.stockQuantity }}</td>
                    <td>{{ formatDate(book.publishedAt) }}</td>
                    <td>{{ formatStatus(book.isDeleted) }}</td>
                </tr>
            </tbody>
        </table>

        <!-- 페이지네이션 -->
        <PagingBar
            :currentPage="page"
            :totalPages="totalPages"
            :totalItems="totalItems"
            @page-changed="handlePageChange"
        />
    </div>
</template>

<script setup>
    import { ref, onMounted, watch, computed } from 'vue'
    import PagingBar from "@/components/common/PagingBar.vue";
    import {getBookList, getCategoryList} from "@/features/admin/bookapi.js"
    import {useRouter} from "vue-router";

    const router = useRouter();

    const books = ref([])
    const page = ref(1)
    const totalPages = ref(1)
    const totalItems = ref(0)

    const filters = ref({
        category: '',
        keywordType: 'author',
        keyword: '',
        statusMode: 'ALL',
        sort: ''
    })

    const categoryList = ref(null)

    const keywordPlaceholder = computed(() => {
        switch (filters.value.keywordType) {
            case 'author': return '작가 이름을 입력해주세요.'
            case 'isbn': return 'ISBN를 입력해주세요.'
            case 'title': return '도서제목을 입력해주세요.'
            default: return ''
        }
    })

    const mapFiltersForRequest = () => {
        const raw = filters.value
        const params = {}

        for (const key in raw) {
            const value = raw[key]
            if (value !== null && value !== undefined && value !== '') {
                if (key === 'statusMode') {
                    if (value === 'SALE') params['isDeleted'] = false
                    else if (value === 'STOP') params['isDeleted'] = true
                    // 'ALL'은 보내지 않음
                } else {
                    params[key] = value
                }
            }
        }
        return params
    }

    const fetchBooks = async () => {
        const params = mapFiltersForRequest()
        params.page = page.value - 1 // 페이지는 0부터 시작

        try {
            const res = await getBookList(params)
            books.value = res.data.data.content
            totalPages.value = res.data.data.totalPages
            totalItems.value = res.data.data.totalElements
        } catch (e) {
            console.error('도서 목록 조회 실패:', e)
        }
    }

    const fetchCategories = async () => {
        try {
            const res= await getCategoryList()
            categoryList.value = res.data.data
        } catch (e) {
            console.log('카테고리 조회 실패 : ', e)
        }
    }

    const bookDetail = (bookId) => {
        router.push(`/admin/book/${bookId}`)
    }

    // 🔁 페이지 변경 핸들러
    const handlePageChange = (newPage) => {
        page.value = newPage
    }

    // 🔁 필터 초기화
    const resetFilters = () => {
        filters.value = {
            category: '',
            keywordType: 'author',
            keyword: '',
            statusMode: 'ALL',
            sort: ''
        }
        page.value = 1
    }

    // 🔍 필터 검색 버튼 눌렀을 때
    const handleSearch = () => {
        page.value = 1
        fetchBooks()
    }

    // 💰 포맷터들
    const formatPrice = (num) => {
        return Number(num).toLocaleString() + '원'
    }
        const formatStatus = (isDeleted) => {
        return isDeleted ? '판매 중지' : '판매 중'
    }

    const formatDate = (dateStr) => {
        const date = new Date(dateStr)
        return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
    }

    // 최초 로딩 및 페이지 변경 감지
    onMounted(() => {
        fetchBooks()
        fetchCategories()
    })
    watch(page, fetchBooks)

    watch(() => filters.value.sort, () => {
        page.value = 1 // 정렬 기준이 바뀌면 페이지는 1로 초기화
        fetchBooks()
    })
</script>


<style scoped>
.stock-page-wrapper {
    padding: 24px;
    margin: 24px 50px;
    font-size: 13px;
    text-align: center;
}

.filter-box {
    display: flex;
    flex-direction: column;
    gap: 12px;
    margin-bottom: 30px;
    padding: 16px;
    border: 1px solid #ddd;
    border-radius: 12px;
    font-size: 13px;
}

.filter-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 12px;
    align-items: center;
}

.filter-pair {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 8px 12px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #fff;
}

.filter-label {
    background: #fdf1de;
    padding: 6px 12px;
    border-radius: 6px;
    font-weight: bold;
    color: #5a3921;
    display: inline-block;
    white-space: nowrap;
    font-size: 13px;
}

.naked-select {
    border: none;
    font-size: 13px;
    background-color: transparent;
}

input {
    padding: 6px 8px;
    border: 1px solid #ccc;
    border-radius: 6px;
    width: 100%;
    font-size: 13px;
    text-align: left;
}

.status-group {
    display: flex;
    gap: 10px;
    align-items: center;
}

.filter-buttons {
    display: flex;
    gap: 8px;
    width: 100%;
}

.filter-buttons button {
    flex: 1;
    background-color: #bb936a;
    color: white;
    border: none;
    padding: 8px 16px;
    border-radius: 6px;
    font-weight: bold;
    cursor: pointer;
    font-size: 13px;
}


.stock-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 12px;
}
.stock-table th,
.stock-table td {
    padding: 10px;
    border: 1px solid #ddd;
    text-align: center;
}
.stock-table th {
    background-color: #fdf1de;
}

.sort-select-wrapper {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-bottom: 16px;
    font-size: 13px;
}

.sort-label {
    font-weight: bold;
    background: #fdf1de;
    padding: 6px 12px;
    border-radius: 6px;
    color: #5a3921;
}

.sort-select {
    padding: 6px 10px;
    border-radius: 6px;
    border: 1px solid #ccc;
    font-size: 13px;
}
</style>

