<script setup>
import { ref } from 'vue';

const interestTab = [
    { id: 'interestAuthors', name: '관심작가' },
    { id: 'interestBooks', name: '관심도서' },
    { id: 'interestPosts', name: '관심게시글' }
];

const selectedTab = ref(interestTab[0].id);

const selectTab = (id) => {
    selectedTab.value = id;
};
</script>

<!-- 다 만든 뒤에 여기에서 탭을 클릭하는 경우 경로를 이동할 수 있게 router 설정을 할 예정임!-->
<template>
    <div class="interest-container">
        <button
                v-for="tab in interestTab"
                :key="tab.id"
                class="interest-category-button"
                :class="{ active: selectedTab === tab.id }"
                @click="selectTab(tab.id)"
        >
            {{ tab.name }}
        </button>
    </div>
</template>

<style scoped>
.interest-container {
    display: flex;
    justify-content: flex-start;
    gap: 10px;
    padding: 20px 0;
    flex-wrap: wrap;
}

.interest-category-button {
    padding: 6px 14px;
    border-radius: 20px;
    border: 1px solid #854d14;
    font-size: 14px;
    font-weight: 500;
    background-color: white;
    color: #854d14;
    cursor: pointer;
    transition: 0.2s;
}

.interest-category-button.active {
    background-color: #854d14;
    color: white;
}
</style>
