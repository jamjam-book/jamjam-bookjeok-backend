<script setup>

import MyPageNav from "@/components/common/MyPageNav.vue";
</script>

<template>
<div class="mypage-container">
    <div class="mypage-layout-left">
        <MyPageNav/>
    </div>
    <div class="mypage-layout-right">
        <RouterView/>
    </div>
</div>
</template>

<style scoped>
.mypage-container {
    display: flex;
    width: 1200px;
    margin-top: 42px;
}
.mypage-layout-left {
    width: 134px;
    margin-right: 100px;
}

.mypage-layout-right {
    width: calc(100% - 134px - 100px);
}
</style>