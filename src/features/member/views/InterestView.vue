<script setup>
import InterestTab from '../components/InterestTab.vue' // 탭 버튼 컴포넌트
</script>

<template>
    <div class="interest-container">
        <InterestTab />
        <RouterView />
    </div>
</template>

<style>
.interest-container {
    padding: 0 0 40px 0;
}
</style>