<script setup>
import { ref } from "vue";
import Modal from "@/components/common/Modal.vue";
import {BDropdownDivider} from "bootstrap-vue-3";

const emit = defineEmits(["confirm-delete"]);
const showModal = ref(false);

const { post, showDeleteButton } = defineProps({
    post: {
        type: Object,
        required: true,
    },
    showDeleteButton: {
        type: Boolean,
        default: true,
    },
});

const openModal = () => (showModal.value = true);
const closeModal = () => (showModal.value = false);
const confirmDelete = () => {
    emit("confirm-delete", post.postId);
    closeModal();
};


</script>

<template>
    <tr>
        <td class="post-image">
            <img :src="post.imageUrl || '../../../../public/images/placeholder.png'" alt="post.title" />
        </td>

        <td class="post-infos" :title="post.title">
            <div class="post-title">{{ post.title }}
                <span class="post-create">{{ post.createdAt }}</span>
            </div>

            <p class="post-content">
                {{ post.content }}
            </p>

            <div class="post-actions">
                <span class="comment">
                    💬 {{ post.comment }}
                </span>
                <span class="like">
                    <svg viewBox="0 0 24 24">
                        <path fill="currentColor" d="m18 1-6 4-6-4-6 5v7l12 10 12-10V6z"></path>
                    </svg>
                    {{ post.like }}
                </span>
            </div>
        </td>

        <td v-if="showDeleteButton" class="delete-cell">
            <button @click="openModal" class="delete-button">삭제</button>
        </td>
    </tr>

    <Modal
            :visible="showModal"
            :message="'관심 게시글을 삭제하시겠습니까?'"
            @confirm="confirmDelete"
            @cancel="closeModal"
    />
</template>

<style scoped>
td {
    vertical-align: middle;
    padding: 0 12px;
    border: none;
}

.post-image {
    text-align: center;
}

.post-image img {
    width: 90px;
}

.post-infos {
    color: #391902;
    text-align: left;
    flex-direction: column;
    display: flex;
    gap: 15px;
    margin: 20px 0;
}

.post-title {
    font-size: 18px;
    font-weight: bold;
}

.post-create {
    font-size: 16px;
    font-weight: normal;
    margin-left: 20px;
}

.post-content {
    font-size: 15px;
    color: #3b2e14;
    line-height: 1.5em;
    max-height: 3em;
    /* 밑에 3줄은 넘치는 내용을 ...으로 표현하기 위한 방법*/
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
}

.delete-button {
    background-color: #f9f0df;
    border: none;
    border-radius: 5px;
    padding: 6px 12px;
    cursor: pointer;
    font-size: 12px;
    color: #391902;
    width: 50px;
}

.delete-button:hover {
    background-color: #e3d3b2;
}

.comment, .like{
    color: #391902;
    border: none;
    border-radius: 6px;
    padding: 4px 12px 4px 0px;
    cursor: pointer;
    transition: background-color 0.2s;
    justify-content: flex-end;
}

.like svg {
    width: 16px;
    height: 16px;
    transition: fill 0.2s;
}

</style>