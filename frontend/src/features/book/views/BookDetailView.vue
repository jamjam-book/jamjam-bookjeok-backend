<template>
    <div class="book-detail-page">
        <!-- 상단 도서 정보 -->
        <BookInfoHeader v-if="book" :book="book" :reviews="reviews" />

        <!-- 작가의 다른 도서 목록 -->
        <h5 class="section-title">작가의 다른 추천 도서</h5>
        <AuthorOtherBooks v-if="authorBooks" :books="authorBooks" />

        <!-- 탭 메뉴 -->
        <BookTabs
                v-if="book"
                :book="book"
                :reviews="reviews"
                @submit-reviews="handleReviewSubmit"
        />

    </div>
</template>

<script setup>
import BookInfoHeader from '@/features/book/components/BookInfoHeader.vue'
import AuthorOtherBooks from '@/features/book/components/AuthorOtherBooks.vue'
import BookTabs from "@/features/book/components/BookTabs.vue";
import {useRoute} from "vue-router";
import {onMounted, ref} from "vue";
import {getAuthorOtherBooks, getBookDetail, getReviews, reviewRegister} from "@/features/book/api.js";

const route = useRoute();
const bookId = Number(route.params.bookId);
const book = ref(null);
const authorBooks = ref([]);
const reviews = ref(null);
const error = ref(null);

const fetchBook = async () => {
    error.value = null;

    try {
        const res = await getBookDetail(bookId);
        await fetchReviews();
        if(res.data && res.data.success) {
            book.value = res.data.data;

            const authorIds = (book.value.authors || []).map(author => author.authorId);

            if(authorIds.length > 0) {

                const params = {
                    authorIds : authorIds,
                    bookId : bookId
                }

                console.log(params);

                const authorBooksRes = await getAuthorOtherBooks(params);

                if(authorBooksRes.data && authorBooksRes.data.success) {
                    authorBooks.value = authorBooksRes.data.data;
                } else {
                    console.warn('저자 도서 목록을 불러오지 못했습니다.')
                }
            }
        } else {
            error.value = '도서 정보를 불러올 수 없습니다.'
        }
    } catch (e) {
        console.error(e);
        error.value = '도서 정보를 불러오는 중 오류가 발생했습니다.'
    }
}

const fetchReviews = async() => {
    error.value = null;

    try {
        const res = await getReviews(bookId);
        if(res.data && res.data.success) {
            reviews.value = res.data.data;
        } else {
            console.warn('리뷰를 불러오지 못했습니다.')
        }
    } catch (e) {
        console.error(e);
        error.value = '리뷰를 불러오는 중 오류가 발생했습니다.'
    }
}

const handleReviewSubmit = async ({ content, rating }) => {

    const params = {
        memberUid : 1,
        bookId : bookId,
        content : content,
        rating : rating
    }

    console.log(`memberUid : ${params.memberUid}`)
    console.log(`bookId : ${params.bookId}`)
    console.log(`content : ${params.content}`)
    console.log(`rating : ${params.rating}`)

    try {
        const res = await reviewRegister(bookId, params);

        if(res.data.success) {
            await fetchReviews();
        }

    } catch (e) {
        console.log('리뷰 등록 실패', e )
    }
}

onMounted(() => {
    fetchBook();
})
</script>

<style scoped>
.book-detail-page {
    max-width: 1000px;
    margin: 0 auto;
    padding: 40px 20px;
}

.section-title {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 16px;
    color: #333;
}
</style>
