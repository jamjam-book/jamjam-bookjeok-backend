<script setup>
import { ref, watch } from 'vue';
import { useRoute } from 'vue-router';

const interestTab = [
    { id: 'authors', name: '관심작가' },
    { id: 'books', name: '관심도서' },
    { id: 'posts', name: '관심게시글' }
];

const selectedTab = ref(interestTab[0].id);

const route = useRoute();

watch(
        () => route.path,
        (newPath) => {
            const matchedTab = interestTab.find(tab => newPath.includes(tab.id));
            if (matchedTab) {
                selectedTab.value = matchedTab.id;
            }
        },
        { immediate: true }
);
</script>

<template>
    <div class="interest-tab-container">
        <RouterLink
                v-for="tab in interestTab"
                :key="tab.id"
                :to="`/members/:memberId/interest/${tab.id}`"
                class="interest-category-button"
                :class="{ active: selectedTab === tab.id }"
        >
            {{ tab.name }}
        </RouterLink>
    </div>
</template>

<style scoped>
.interest-tab-container {
    display: flex;
    justify-content: flex-start;
    gap: 10px;
    padding: 10px;
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
    text-decoration: none;
    display: inline-block;
}

.interest-category-button.active {
    background-color: #854d14;
    color: white;
}
</style>
