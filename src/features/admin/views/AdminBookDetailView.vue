<template>
    <div v-if="book" class="book-detail-wrapper">
        <div class="book-detail-top">
            <div class="book-image-box">
                <img :src="fullImageUrl" alt="도서 이미지" class="book-image" />
            </div>
            <div class="book-top-info-list">
                <AdminBookInfoRow label="제목" :value="book.bookName" />
                <AdminBookInfoRow label="저자" :value="book.authorNames" />
                <AdminBookInfoRow label="출판사" :value="book.publisherName" />
            </div>
        </div>

        <div class="book-info-list">
            <AdminBookInfoRow label="카테고리" :value="book.bookCategory.categoryName" />
            <AdminBookInfoRow label="출판일" :value="formatDate(book.publishedAt)" />
            <AdminBookInfoRow label="ISBN" :value="book.isbn" />
            <AdminBookInfoRow label="가격" :value="formatPrice(book.price)" />
            <AdminBookInfoRow label="재고 수량">
                <template v-if="isAdminEditMode">
                    <input type="number" v-model="book.stockQuantity" />
                </template>
                <template v-else>
                    {{ book.stockQuantity }} (EA)
                </template>
            </AdminBookInfoRow>
        </div>
    </div>

    <button class="submit-btn" @click="handleAction">
        {{ isAdminEditMode ? '저장하기' : '수정하기' }}
    </button>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import AdminBookInfoRow from "@/features/admin/components/AdminBookInfoRow.vue";
import {getBookDetail, updateQuantity} from "@/features/admin/bookapi.js";

const route = useRoute()
const router = useRouter()

const bookId = route.params.bookId;
const isAdminEditMode = route.path.includes('/admin/book/edit')

console.log(`isAdminEditMode : ${isAdminEditMode}`)

const book = ref(null);

onMounted(async () => {
    const res = await getBookDetail(bookId)
    book.value = res.data.data
})

const IMAGE_BASE_URL = 'http://localhost:8888/images/';

const fullImageUrl = computed(() => {
    return book.value.imageUrl.startsWith('http')
            ? book.value.imageUrl
            : IMAGE_BASE_URL + book.value.imageUrl
})

const formatDate = (dateStr) => {
    if (!dateStr) return ''
    const date = new Date(dateStr)
    return `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일`
}

const formatPrice = (num) => {
    if (!num) return ''
    return Number(num).toLocaleString() + '원'
}

const handleAction = async () => {
    if (isAdminEditMode) {
        try {
            await updateQuantity(bookId, book.value.stockQuantity);
            await router.push(`/admin/book/${bookId}`)
        } catch (err) {
            alert('수정 실패!')
        }
    } else {
        await router.push(`/admin/book/edit/${bookId}`)
    }
}
</script>

<style scoped>
.book-detail-wrapper {
    max-width: 720px;
    margin: 150px auto 50px;
    padding: 30px;
    background-color: #fff;
    border-radius: 16px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}

.book-detail-top {
    display: flex;
    gap: 24px;
    margin-bottom: 10px;
}

.book-image-box {
    flex-shrink: 0;
}

.book-image {
    height: 150px;
    width: auto;
    object-fit: contain;
    border-radius: 8px;
}

.book-top-info-list {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.book-info-list {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.submit-btn {
    margin: 30px auto 0;
    display: block;
    background-color: #fcf4e6;
    border: none;
    padding: 14px 24px;
    border-radius: 10px;
    font-weight: bold;
    font-size: 16px;
    cursor: pointer;
}

.submit-btn:hover {
    background-color: #f7e4c7;
}
</style>
