<script setup>
import {computed, ref} from "vue";
import Modal from "@/components/common/Modal.vue";
import {deleteInterestBooks} from "@/features/member/interestApi.js";
import {useRoute} from "vue-router";

const route = useRoute();
const memberId = route.params.memberId
const showModal = ref(false);
const openModal = () => (showModal.value = true);
const closeModal = () => (showModal.value = false);

const { book } = defineProps({
    book: {
        type: Object,
        required: true,
    },
});

const bookId = book.bookId;

const IMAGE_BASE_URL = 'http://localhost:8080/images/';

const fullImageUrl = computed(() => {
    return book.imageUrl.startsWith('http')
            ? book.imageUrl
            : IMAGE_BASE_URL + book.imageUrl
})

const handleDelete = async () => {
    try {
        await deleteInterestBooks(memberId, bookId);
        console.log("관심 도서가 삭제되었습니다.");

        // 삭제 한 이후에 새로 고침하기
        window.location.reload();

        closeModal();
    } catch (e) {
        console.error("관심 도서가 삭제되지 않았습니다.", e);
    }
};
</script>

<template>
    <tr>
        <td class="book-image">
            <router-link :to="`/books/${book.bookId}`" class="router-style book-image">
<!--                <img :src="fullImageUrl" :alt="book.bookName" />-->
                <img :src="'../../../../public/images/placeholder.png'" :alt="book.bookName" />
            </router-link>
        </td>

        <td id="divider-cell">
            <div class="vertical-divider"></div>
        </td>

        <td class="book-infos" :title="book.bookName">
            <router-link :to="`/books/${book.bookId}`" class="router-style">
                <div class="book-name" >{{ book.bookName }}</div>
                <div class="book-authors">
                    <span>저자 | </span>
                    <span v-for="(authorName, i) in book.authorName" :key="i">
                        {{ authorName }}
                    </span>
                </div>
                <div class="book-info">
                    <span>작품 소개 | </span>{{ book.bookInfo }}
                </div>
            </router-link>
        </td>

        <td class="delete-cell">
            <button @click="openModal" class="delete-button">삭제</button>
        </td>
    </tr>

    <Modal
            :visible="showModal"
            :message="`${book.bookName}을(를) 관심 도서에서 삭제하시겠습니까?`"
            @confirm="handleDelete"
            @cancel="closeModal"
    />
</template>

<style scoped>
td {
    vertical-align: middle;
    padding: 0 12px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    border: none;
}

.book-image {
    text-align: center;
}

.book-image img{
    height: 90px;
}

#divider-cell {
    text-align: center;
    padding: 0 12px 0 0;
}

.vertical-divider {
    width: 1px;
    height: 136px;
    background-color: #ccc;
    margin: 0;
    padding: 0;
}

.book-infos {
    color: #391902;
    text-align: left;
    flex-direction: column;
    display: flex;
    margin: 20px 0;
}

.book-name {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
}

.book-authors .book-info {
    font-size: 14px;
    color: #5c4033;
}

.delete-button {
    background-color: #f9f0df;
    border: none;
    border-radius: 5px;
    padding: 6px 12px;
    cursor: pointer;
    font-size: 12px;
    color: #391902;
}

.delete-button:hover {
    background-color: #e3d3b2;
}

.router-style {
    text-decoration: none;
    color: #391902;
}

</style>
