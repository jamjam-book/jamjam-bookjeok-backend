<script setup>
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const postTab = [
    { id: 'allPosts', name: '전체 목록' },
    { id: 'review', name: '후기' },
    { id: 'recommend', name: '추천' },
    { id: 'meet', name: '모임' },
];

const selectedTab = ref(postTab[0].id);

</script>

<template>
    <div class="post-tab-container">
        <div class="tab-buttons">
            <div
                    v-for="tab in postTab"
                    :key="tab.id"
                    class="post-category-button"
                    :class="{ active: selectedTab === tab.id }"
            >
                {{ tab.name }}
            </div>
        </div>

        <div class="action-buttons">
            <button class="action-button">게시글 작성하기</button>
            <button class="action-button">내 게시글</button>
        </div>
    </div>
</template>

<style scoped>
.post-tab-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 20px;
    padding: 10px;
    flex-wrap: wrap;
    width: 966px;
    margin: auto;
}

.tab-buttons {
    display: flex;
    gap: 10px;
    flex-wrap: wrap;
}

.post-category-button {
    padding: 6px 14px;
    border-radius: 20px;
    border: 1px solid #854d14;
    font-size: 14px;
    font-weight: 500;
    background-color: white;
    color: #854d14;
    cursor: pointer;
    transition: 0.2s;
    text-decoration: none;
    display: inline-block;
}

.post-category-button.active {
    background-color: #854d14;
    color: white;
}

.action-buttons {
    display: flex;
    gap: 10px;
}

.action-button {
    padding: 6px 14px;
    border : none;
    border-radius: 8px;
    font-size: 14px;
    font-weight: 500;
    background-color: #f9f0df;
    color: #391902;
    cursor: pointer;
    transition: 0.2s;
}

.action-button:hover {
    background-color: #e3d3b2;
}
</style>
