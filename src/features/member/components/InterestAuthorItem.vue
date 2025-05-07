<script setup>
import { ref } from "vue";
import Modal from "@/components/common/Modal.vue";
import { deleteInterestAuthors} from "@/features/member/interestApi.js";
import { useRoute } from "vue-router";

const route = useRoute();
const memberId = route.params.memberId;

const { author } = defineProps({
    author: {
        type: Object,
        required: true,
    },
});
const authorId = author.authorId;

const showModal = ref(false);
const openModal = () => (showModal.value = true);
const closeModal = () => (showModal.value = false);

const handleDelete = async () => {
    try {
        await deleteInterestAuthors(memberId, authorId);
        console.log("관심 작가가 삭제되었습니다.");

        // 삭제 한 이후에 새로 고침하기
        window.location.reload();

        closeModal();
    } catch (e) {
        console.error("관심 작가를 삭제되지 않았습니다.", e);
    }
};
</script>


<template>
    <tr>
        <td class="author-name" :title="author.authorName">
            <RouterLink :to="`/books/authors/${author.authorId}`" id="no-line">
                {{ author.authorName }}
            </RouterLink>
        </td>
        <td class="divider-cell">
            <div class="vertical-divider"></div>
        </td>
        <td class="book-list">
      <span v-for="(book, i) in author.bookList" :key="i">
        {{ book.bookName }}<span v-if="i < author.bookList.length - 1">, </span>
      </span>
        </td>
        <td class="delete-cell">
            <button @click="openModal" class="delete-button">삭제</button>
        </td>
    </tr>

    <Modal
            :visible="showModal"
            :message="`${author.authorName} 작가를 관심 목록에서 삭제하시겠습니까?`"
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

.author-name {
    font-weight: bold;
    width: 15%;
    text-align: center;
}

#no-line {
    text-decoration: none;
    color: #391902;
}

.divider-cell {
    text-align: center;
}

.vertical-divider {
    width: 1px;
    height: 30px;
    background-color: #ccc;
    margin: 0 auto;
}

.book-list {
    color: #333;
    width: 75%;
}

.delete-cell {
    text-align: right;
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
