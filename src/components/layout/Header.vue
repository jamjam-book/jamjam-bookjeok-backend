<script setup>
import {onBeforeUnmount, onMounted, ref} from 'vue'
import SearchBar from "@/components/common/SearchBar.vue";

const isLoggedIn = ref(false);
const memberStatus = ref('MEMBER');
const cartCount = ref(0);
const isOpen = ref(false)

const toggleDropdown = () => {
    isOpen.value = !isOpen.value
}

</script>

<template>
    <div class="header-content">
        <div class="header">
            <div class="header-content">
                <div class="overlap-group">
                    <RouterLink to="/">
                        <img class="logo" src="../../assets/images/logo.png" alt="로고"/>
                    </RouterLink>
                    <div class="header-dropdown">
                        <!-- icon으로 바꾸는게 더 좋을듯!-->
                        <button v-if="!isOpen" @click="toggleDropdown" class="header-dropdown-toggle">▶</button>
                        <button v-else @click="toggleDropdown" class="header-dropdown-toggle">▼</button>
                        <div v-if="isOpen" class="header-dropdown-menu">
                            <RouterLink to="/books">도서</RouterLink>
                            <RouterLink to="/posts">게시글</RouterLink>
                        </div>
                    </div>
                </div>

                <SearchBar/>

                <nav class="header-right">
                    <div class="icon-group">
                        <!-- 로그인 전 -->
                        <template v-if="!isLoggedIn">
                            <div class="auth-links">
                                <RouterLink to="/books" class="auth-link">로그인</RouterLink>
                                <RouterLink to="/signup" class="auth-link">회원가입</RouterLink>
                            </div>
                            <div class="header-icons">
                                <RouterLink to="/carts">
                                    <img class="icon" src="../../assets/icons/cart.png" alt="장바구니"/>
                                </RouterLink>
                                <img class="icon" src="../../assets/icons/mypage.png" alt="마이페이지"/>
                            </div>
                        </template>

                        <!-- 관리자가 로그인 한 경우 -->
                        <template v-else-if="memberStatus === 'ADMIN'">
                            <div class="auth-links">
                                <RouterLink to="/admin" class="auth-link">관리자</RouterLink>
                                <!-- 로그아웃 된 다음에 메인 페이지로 이동할 예정 -->
                                <a href="/logout" class="auth-link logout">로그아웃</a>
                            </div>
                            <div class="header-icons">
                                <img class="icon" src="../../assets/icons/cart.png" alt="장바구니"/>
                                <img class="icon" src="../../assets/icons/mypage.png" alt="마이페이지"/>
                            </div>
                        </template>

                        <!-- 회원이 로그인 한 경우 -->
                        <template v-else-if="memberStatus === 'MEMBER'">
                            <div class="auth-links">
                                <!-- 로그아웃 된 다음에 메인 페이지로 이동할 예정 -->
                                <a href="/logout" class="auth-link logout">로그아웃</a>
                            </div>
                            <div class="header-icons">
                                <img class="icon favorite" src="../../assets/icons/favorite.png" alt="관심 목록"/>
                                <div class="cart-wrapper">
                                    <img class="icon cart" src="../../assets/icons/cart.png" alt="장바구니"/>
                                    <span class="cart-count">{{ cartCount }}</span>
                                </div>
                                <img class="icon" src="../../assets/icons/mypage.png" alt="마이페이지"/>
                            </div>
                        </template>
                    </div>
                </nav>

            </div>
        </div>
    </div>
</template>

<style scoped>
@import "@/assets/css/header.css";
</style>