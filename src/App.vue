<script setup>

import Footer from "@/components/layout/Footer.vue"
import MyPageNav from "@/components/common/MyPageNav.vue";
import AdminNav from "@/components/layout/AdminNav.vue";
import Header from "@/components/layout/Header.vue";
</script>

<template>
    <Header/>
    <div class="container">
          <RouterView/>
    <!--    <MyPageNav/>-->
    <!--    <AdminNav/>-->
    </div>
    <Footer/>
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
