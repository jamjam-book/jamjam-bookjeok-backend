<script setup>
import {ref, computed, onMounted} from 'vue'
import {useAuthStore} from '@/stores/auth'
import {storeToRefs} from 'pinia'
import {useRouter} from 'vue-router'
import SearchBar from "@/components/common/SearchBar.vue"

const authStore = useAuthStore()
const {isAuthenticated, userRole} = storeToRefs(authStore)
const router = useRouter()

const cartCount = ref(0)
const isOpen = ref(false)

const toggleDropdown = () => {
    isOpen.value = !isOpen.value
}

const logoutHandler = () => {
    authStore.clearAuth()
    router.push('/')
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
                        <button v-if="!isOpen" @click="toggleDropdown" class="header-dropdown-toggle">▶</button>
                        <button v-else @click="toggleDropdown" class="header-dropdown-toggle">▼</button>
                        <div v-if="isOpen" class="header-dropdown-menu">
                            <RouterLink to="/books" @click="toggleDropdown">도서</RouterLink>
                            <RouterLink to="/posts" @click="toggleDropdown">게시글</RouterLink>
                        </div>
                    </div>
                </div>

                <SearchBar/>

                <nav class="header-right">
                    <div class="icon-group">
                        <!-- 로그인 전 -->
                        <template v-if="!isAuthenticated">
                            <div class="auth-links">
                                <RouterLink to="/login" class="auth-link">로그인</RouterLink>
                                <RouterLink to="/signup" class="auth-link">회원가입</RouterLink>
                            </div>
                            <div class="header-icons">
                                <RouterLink to="/carts">
                                    <img class="icon" src="../../assets/icons/cart.png" alt="장바구니"/>
                                </RouterLink>
                                <RouterLink to="/profile">
                                    <img class="icon" src="../../assets/icons/mypage.png" alt="마이페이지"/>
                                </RouterLink>
                            </div>
                        </template>

                        <!-- 관리자 로그인 -->
                        <template v-else-if="userRole === 'ADMIN'">
                            <div class="auth-links">
                                <RouterLink to="/admin" class="auth-link">관리자</RouterLink>
                                <button @click="logoutHandler" class="auth-link logout"
                                        style="all: unset; cursor: pointer;">
                                    로그아웃
                                </button>
                            </div>
                            <div class="header-icons">
                                <img class="icon" src="../../assets/icons/cart.png" alt="장바구니"/>
                                <RouterLink to="/profile">
                                    <img class="icon" src="../../assets/icons/mypage.png" alt="마이페이지"/>
                                </RouterLink>
                            </div>
                        </template>

                        <!-- 회원 로그인 -->
                        <template v-else>
                            <div class="auth-links">
                                <button @click="logoutHandler" class="auth-link logout"
                                        style="all: unset; cursor: pointer;">
                                    로그아웃
                                </button>
                            </div>
                            <div class="header-icons">
                                <img class="icon favorite" src="../../assets/icons/favorite.png" alt="관심 목록"/>
                                <div class="cart-wrapper">
                                    <RouterLink to="/carts">
                                        <img class="icon cart" src="../../assets/icons/cart.png" alt="장바구니"/>
                                        <span class="cart-count">{{ cartCount }}</span>
                                    </RouterLink>
                                </div>
                                <RouterLink to="/profile">
                                    <img class="icon" src="../../assets/icons/mypage.png" alt="마이페이지"/>
                                </RouterLink>
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
