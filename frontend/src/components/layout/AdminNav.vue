<template>
  <aside class="sidebar">
    <img class="logo" src="../../assets/images/logo.png" alt="Logo" />
    <div class="admin-name">관리자 99</div>

    <nav class="nav-section">
      <router-link to="/admin/members" class="nav-item">회원 정보</router-link>

      <!-- 도서 재고 관리 메뉴 -->
      <div class="nav-item toggle-wrapper" @click="toggleBooksMenu">
        <span>도서 재고 관리</span>
        <span class="toggle-icon" :class="{ open: isBooksMenuOpen }">▶</span>
      </div>
      <div v-show="isBooksMenuOpen" class="submenu">
        <router-link to="/admin/book-stock" class="submenu-item">도서 재고 조회</router-link>
        <router-link to="/admin/book-store" class="submenu-item">도서 입고 관리</router-link>
      </div>

      <router-link to="/admin/questions" class="nav-item">문의 사항</router-link>
    </nav>

    <div class="logout" @click="logout">로그아웃</div>
  </aside>
</template>

<script setup>
import { ref } from 'vue';
// import image3 from '@/assets/image-3.png';
const isBooksMenuOpen = ref(false);
const toggleBooksMenu = () => {
  isBooksMenuOpen.value = !isBooksMenuOpen.value;
};

const logout = () => {
  alert('로그아웃 되었습니다!');
};

</script>

<style scoped>
.sidebar {
  width: 280px;
  height: 100vh;
  background-color: #fff;
  border-right: 1px solid #ddd;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem 1rem;
}

.logo {
  width: 180px;
  margin-bottom: 1rem;
}

.admin-name {
  font-weight: bold;
  font-size: 20px;
  margin-bottom: 2rem;
  color: #522404;
}

.nav-section {
  display: flex;
  flex-direction: column;
  width: 100%;
  gap: 1rem;
}

.nav-item {
  cursor: pointer;
  text-decoration: none;
  padding: 0.75rem 1rem;
  border-radius: 8px;
  background-color: #f9f0df;
  color: #522404;
  text-align: center;
  font-weight: 600;
  transition: background-color 0.2s ease;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.toggle-icon {
  display: inline-block;
  transform: rotate(0deg);
  transition: transform 0.2s ease;
}

.toggle-icon.open {
  transform: rotate(90deg);
}

.nav-item:hover {
  background-color: #e3d3b2;
}

.submenu {
  margin-left: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.submenu-item {
  font-size: 14px;
  color: #333;
  text-decoration: none;
  padding: 0.4rem 0.8rem;
  border-left: 3px solid #ccc;
}

.submenu-item:hover {
  background-color: #f3f3f3;
}

.logout {
  margin-top: auto;
  cursor: pointer;
  color: #666;
  font-size: 16px;
  padding-top: 2rem;
}
</style>