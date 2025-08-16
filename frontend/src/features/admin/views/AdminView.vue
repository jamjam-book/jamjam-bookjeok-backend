<script setup>

import AdminNav from "@/components/layout/AdminNav.vue";
</script>

<template>
    <div class="admin-container">
        <div class="admin-layout-left">
            <AdminNav/>
        </div>
        <div class="admin-layout-right">
            <RouterView :key="$route.fullPath"/>
        </div>
    </div>
</template>

<style scoped>
.admin-container {
    display: flex;
    height: 100vh;
}

.admin-layout-left {
    width: 280px;
}

.admin-layout-right {
    width: calc(100% - 280px);
    padding: 32px 16px;
}

</style>