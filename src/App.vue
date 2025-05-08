<script setup>

import Footer from "@/components/layout/Footer.vue";
import Header from "@/components/layout/Header.vue";
import { useRoute } from "vue-router";
import {computed} from "vue";

const route = useRoute();
// 관리자 경로인지 확인하는 부분
const isAdminRoute = computed(() => {
    const isAdmin = route.path.startsWith('/admin');
    return isAdmin;
});
</script>

<template>
    <!-- 관리자가 아니면 헤더 표시 -->
    <Header v-if="!isAdminRoute" />

    <!-- 관리자가 아니면 container 클래스 적용 -->
    <div :class="{ container: !isAdminRoute }">
        <RouterView :key="$route.fullPath"/>
    </div>

    <!-- 관리자가 아니면 푸터 표시 -->
    <Footer v-if="!isAdminRoute" />
</template>

<style scoped>
.container {
    align-items: center;
    justify-content: center;
    width: 1200px;
    margin-top: 42px;
    padding: 0;
}
</style>