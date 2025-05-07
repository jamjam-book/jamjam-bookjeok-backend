<script setup>
import {onMounted, ref} from 'vue'
import axios from 'axios'
import SearchBar from "@/components/common/SearchBar.vue";

const isLoggedIn = ref(false);
const memberStatus = ref('MEMBER');
const cartCount = ref(0);  // 장바구니 수량을 저장하는 상태 변수
const isOpen = ref(false)

const toggleDropdown = () => {
    isOpen.value = !isOpen.value
}

// 장바구니 수량을 가져오는 API 호출
const getCartCount = async () => {
    try {
        const memberId = 'user01';
        const response = await axios.get(`${import.meta.env.VITE_API_BASE_URL}/members/${memberId}/carts/count`);
        console.log(response.data); // 확인용 로그

        if (response.data && response.data.data) {
            cartCount.value = response.data.data.cartCount;
            console.log(cartCount.value)
        }
    } catch (error) {
        console.error('장바구니 수량을 가져오는 데 실패했습니다:', error);
    }
}

// 컴포넌트가 마운트될 때 장바구니 수량을 가져옴
onMounted(() => {
    getCartCount();
})
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
                        <template v-if="!isLoggedIn">
                            <div class="auth-links">
                                <RouterLink to="/login" class="auth-link">로그인</RouterLink>
                                <RouterLink to="/signup" class="auth-link">회원가입</RouterLink>
                            </div>
                            <div class="header-icons">
                                <RouterLink to="/carts">
                                    <div class="cart-wrapper">
                                        <img class="icon" src="../../assets/icons/cart.png" alt="장바구니"/>
                                        <span v-if="cartCount > 0" class="cart-count">{{ cartCount }}</span>
                                    </div>
                                </RouterLink>
                                <img class="icon" src="../../assets/icons/mypage.png" alt="마이페이지"/>
                            </div>
                        </template>

                        <template v-else-if="memberStatus === 'ADMIN'">
                            <div class="auth-links">
                                <RouterLink to="/admin" class="auth-link">관리자</RouterLink>
                                <a href="/logout" class="auth-link logout">로그아웃</a>
                            </div>
                            <div class="header-icons">
                                <img class="icon" src="../../assets/icons/cart.png" alt="장바구니"/>
                                <img class="icon" src="../../assets/icons/mypage.png" alt="마이페이지"/>
                            </div>
                        </template>

                        <template v-else-if="memberStatus === 'MEMBER'">
                            <div class="auth-links">
                                <a href="/logout" class="auth-link logout">로그아웃</a>
                            </div>
                            <div class="header-icons">
                                <img class="icon favorite" src="../../assets/icons/favorite.png" alt="관심 목록"/>
                                <div class="cart-wrapper">
                                    <img class="icon cart" src="../../assets/icons/cart.png" alt="장바구니"/>
                                    <span v-if="cartCount > 0" class="cart-count">{{ cartCount }}</span>
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