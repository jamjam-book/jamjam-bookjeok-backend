<script setup>
import { ref } from "vue";
import Modal from "@/components/common/Modal.vue";

const { book } = defineProps({
    book: {
        type: Object,
        required: true,
    },
});

const emit = defineEmits(["confirm-delete"]);

const showModal = ref(false);
const openModal = () => (showModal.value = true);
const closeModal = () => (showModal.value = false);
const confirmDelete = () => {
    emit("confirm-delete", book.bookId);
    closeModal();
};
</script>

<template>
    <!-- 도서 상세 페이지로 이동하기 위한 router-link -->
    <!--    <router-link-->
    <!--            :to="`/books/${author.bookId}`">-->
    <tr>
        <td class="book-image">
            <img :src="book.imageUrl" alt="book.bookName">
        </td>

        <td id="divider-cell">
            <div class="vertical-divider"></div>
        </td>

        <td class="book-infos" :title="book.bookName">
            <div class="book-name">{{ book.bookName }}</div>
            <div class="book-authors">
                <span>저자 | </span>
                <span v-for="(authorName, i) in book.authorNames" :key="i">
                    {{ authorName }}
                    <span v-if="i < book.authorNames.length - 1">, </span>
                </span>
            </div>
            <div class="book-info"><span>작품 소개 | </span>{{ book.bookInfo}}</div>
        </td>


        <td class="delete-cell">
            <button @click="openModal" class="delete-button">삭제</button>
        </td>
    </tr>

    <Modal
            :visible="showModal"
            :message="`${book.bookName}을(를) 관심 도서에서 삭제하시겠습니까?`"
            @confirm="confirmDelete"
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
    width: 90px;
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
    gap: 10px;
    margin: 20px 0;
}

.book-name {
    font-size: 18px;
    font-weight: bold;
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

</style>
