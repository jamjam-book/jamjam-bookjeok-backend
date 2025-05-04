<script setup>

const { following } = defineProps({
    following: {
        type: Object,
        required: true,
    },
});

const emit = defineEmits(["confirm-delete"]);

// 상위로 전파하기
const confirmDelete = () => {
    emit("confirm-delete", following.memberId);
};
</script>

<template>
    <!-- 팔로우 하는 사람의 게시글 목록 페이지로 이동하기 위한 router-link -->
    <!--    <router-link-->
    <!--            :to="`/${following.memberId}/posts`">-->
    <tr>
        <td class="following-nickname" :title="following.nickname">
            {{ following.nickname }}
        </td>
        <td class="delete-cell">
            <button @click="confirmDelete" class="delete-button">언팔로우</button>
        </td>
    </tr>
</template>

<style scoped>
td {
    vertical-align: middle;
    padding: 0 12px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    border: none;
    width: 100%;
}

.following-nickname {
    font-weight: bold;
    color: #391902;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.delete-cell {
    text-align: right;
    max-width: 100px;
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
