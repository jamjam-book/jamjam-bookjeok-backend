<script setup>

import {deleteFollowings} from "@/features/member/interestApi.js";
import {useRoute} from "vue-router";

const route = useRoute();
const { following } = defineProps({
    following: {
        type: Object,
        required: true,
    },
});

const followerId = route.params.memberId;
const followingId = following.memberId;

const handleDelete = async () => {
    try {
        await deleteFollowings(followerId ,followingId);
        window.location.reload();
    } catch (e) {
        console.log("언팔로우 되지 않았습니다.", e);
    }
};
</script>

<template>
    <tr>
        <router-link class="no-line"
                     :to="`/${following.memberId}/posts`">
            <td class="following-nickname" :title="following.nickname">
                {{ following.nickname }}
            </td>
        </router-link>
        <td class="delete-cell">
            <button @click="handleDelete" class="delete-button">언팔로우</button>
        </td>
    </tr>
</template>

<style scoped>
td {
    vertical-align: middle;
    padding: 0 12px;
    border: none;
    width: 100%;
}

.no-line {
    text-decoration: none;
}

.following-nickname {
    font-weight: bold;
    color: #391902;
    white-space: nowrap;
    padding : 0 574px 0 0;
}

.delete-cell {
    text-align: right;
    width: auto;  /* 너비를 자동으로 조정 */
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