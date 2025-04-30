<script setup>
import { ref } from 'vue'
import SearchBar from "@/components/common/SearchBar.vue";

const isLoggedIn = ref(false);
const memberStatus = ref('MEMBER');
const cartCount = ref(0);
</script>

<template>
  <div class="header-content">
    <div class="header">
      <div class="header-content">
        <div class="overlap-group">
          <img class="logo" src="../../assets/images/logo.png" alt="로고" />
        </div>

        <SearchBar/>

        <div class="header-right">
          <div class="icon-group">
            <!-- 로그인 전 -->
            <template v-if="!isLoggedIn">
              <div class="auth-links">
                <a href="/login" class="auth-link">로그인</a>
                <a href="/signup" class="auth-link">회원가입</a>
              </div>
              <div class="header-icons">
                <img class="icon" src="../../assets/icons/cart.png" alt="장바구니" />
                <img class="icon" src="../../assets/icons/mypage.png" alt="마이페이지" />
              </div>
            </template>

            <!-- 관리자가 로그인 한 경우 -->
            <template v-else-if="memberStatus === 'ADMIN'">
              <div class="auth-links">
                <a href="/admin" class="auth-link">관리자</a>
                <a href="/logout" class="auth-link logout">로그아웃</a>
              </div>
              <div class="header-icons">
                <img class="icon" src="../../assets/icons/cart.png" alt="장바구니" />
                <img class="icon" src="../../assets/icons/mypage.png" alt="마이페이지" />
              </div>
            </template>

            <!-- 회원이 로그인 한 경우 -->
            <template v-else-if="memberStatus === 'MEMBER'">
              <div class="auth-links">
                <a href="/logout" class="auth-link logout">로그아웃</a>
              </div>
              <div class="header-icons">
                <img class="icon favorite" src="../../assets/icons/favorite.png" alt="관심 목록" />
                <div class="cart-wrapper">
                  <img class="icon cart" src="../../assets/icons/cart.png" alt="장바구니" />
                  <span class="cart-count">{{ cartCount }}</span>
                </div>
                <img class="icon" src="../../assets/icons/mypage.png" alt="마이페이지" />
              </div>
            </template>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "@/assets/css/header.css";
</style>