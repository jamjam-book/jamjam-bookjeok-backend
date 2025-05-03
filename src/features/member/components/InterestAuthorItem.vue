<script setup>
import { ref } from "vue";
import Modal from "@/components/common/Modal.vue";

const { author } = defineProps({
    author: {
        type: Object,
        required: true,
    },
});

const emit = defineEmits(["confirm-delete"]);

const showModal = ref(false);
const openModal = () => (showModal.value = true);
const closeModal = () => (showModal.value = false);
const confirmDelete = () => {
    emit("confirm-delete", author.authorId);
    closeModal();
};
</script>

<template>
<!-- 작가 상세 페이지로 이동하기 위한 router-link -->
<!--    <router-link-->
<!--            :to="`/authors/${author.authorId}`">-->
    <tr>
        <td class="author-name" :title="author.authorName">
            {{ author.authorName }}
        </td>
        <td class="divider-cell">
            <div class="vertical-divider"></div>
        </td>
        <td class="book-list">
      <span v-for="(book, i) in author.bookList" :key="i">
        {{ book }}<span v-if="i < author.bookList.length - 1">, </span>
      </span>
        </td>
        <td class="delete-cell">
            <button @click="openModal" class="delete-button">삭제</button>
        </td>
    </tr>

    <Modal
            :visible="showModal"
            :message="`${author.authorName} 작가를 관심 목록에서 삭제하시겠습니까?`"
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

.author-name {
    font-weight: bold;
    color: #391902;
    width: 15%;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
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
