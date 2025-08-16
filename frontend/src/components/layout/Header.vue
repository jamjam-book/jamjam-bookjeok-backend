<script setup>
import { onMounted, ref, computed, watch } from 'vue'
import axios from 'axios'
import SearchBar from "@/components/common/SearchBar.vue"
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'
import {useRoute, useRouter} from 'vue-router'
import Modal from '@/components/common/Modal2.vue'
import { loginUser } from "@/lib/user.js"

const authStore = useAuthStore()
const { isAuthenticated, userRole } = storeToRefs(authStore)
const router = useRouter()
const route = useRoute()

const isLoggedIn = computed(() => isAuthenticated.value)
const memberStatus = computed(() => userRole.value || 'MEMBER')

const cartCount = ref(0)
const isOpen = ref(false)
const modalVisible = ref(false)
const modalMessage = ref('')

// const memberId = ref('') 로그인 정보 저장 후 변경
const memberId = ref('user01');
const password = ref('')

const toggleDropdown = () => {
    isOpen.value = !isOpen.value
}

const getCartCount = async () => {
    try {
        const id = authStore.memberId || memberId.value || 'user01'
        if (!isLoggedIn.value) {
            cartCount.value = 0
            return
        }
        const response = await axios.get(`${import.meta.env.VITE_API_BASE_URL}/members/${id}/carts/count`);
        if (response.data && response.data.data) {
            cartCount.value = response.data.data.cartCount;
        }
    } catch (error) {
        cartCount.value = 0
        console.error('장바구니 수량을 가져오는 데 실패했습니다:', error);
    }
}

const logoutHandler = () => {
    authStore.clearAuth()
    cartCount.value = 0
    router.push('/')
}

const handleLogin = async () => {
    try {
        const resp = await loginUser({
            memberId: memberId.value,
            password: password.value
        })
        const at = resp.data.data.accessToken;
        authStore.setAuth(at);
        await getCartCount();
        await router.push('/')
    } catch (e) {
        modalMessage.value = '아이디 또는 비밀번호를 확인해주세요.';
        modalVisible.value = true;
    }
}

onMounted(() => {
    if (isLoggedIn.value) {
        getCartCount();
    }
})

watch(isLoggedIn, (val) => {
    if (val) {
        getCartCount()
    } else {
        cartCount.value = 0
    }
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
                        <div class="auth-links">
                            <!-- 첫 칸: 로그인/관리자/빈칸(회원) -->
                            <template v-if="!isLoggedIn">
                                <RouterLink to="/login" class="auth-link">로그인</RouterLink>
                            </template>
                            <template v-else-if="memberStatus === 'ADMIN'">
                                <RouterLink to="/admin" class="auth-link">관리자</RouterLink>
                            </template>
                            <template v-else>
                                <span class="auth-link" style="visibility:hidden;">로그인</span>
                            </template>
                            <!-- 두 번째 칸: 회원가입/로그아웃 -->
                            <template v-if="!isLoggedIn">
                                <RouterLink to="/signup" class="auth-link">회원가입</RouterLink>
                            </template>
                            <template v-else>
                                <button
                                        @click="logoutHandler"
                                        class="auth-link logout"
                                        type="button"
                                >로그아웃</button>
                            </template>
                        </div>
                        <div class="header-icons">
                            <!-- 관심목록: 항상 첫 칸 확보(비로그인/관리자 상태는 투명) -->
                            <template v-if="isLoggedIn && memberStatus === 'MEMBER'">
                                <RouterLink to="/favorites">
                                    <img class="icon favorite" src="../../assets/icons/favorite.png" alt="관심 목록"/>
                                </RouterLink>
                            </template>
                            <template v-else>
                                <span class="icon favorite" style="visibility:hidden;width:28px;height:28px;"></span>
                            </template>
                            <!-- 장바구니 -->
                            <RouterLink to="/carts">
                                <div class="cart-wrapper">
                                    <img class="icon cart" src="../../assets/icons/cart.png" alt="장바구니"/>
                                    <span v-if="cartCount > 0" class="cart-count">{{ cartCount }}</span>
                                </div>
                            </RouterLink>
                            <!-- 마이페이지 -->
                            <RouterLink :to="`/members/${memberId}/profile`">
                                <img class="icon" src="../../assets/icons/mypage.png" alt="마이페이지"/>
                            </RouterLink>
                        </div>
                    </div>
                </nav>
            </div>
        </div>
        <Modal
                :visible="modalVisible"
                :message="modalMessage"
                @close="modalVisible = false"
        />
    </div>
</template>

<style scoped>
@import "@/assets/css/header.css";

/* 텍스트 크기/위치 완전 일치 (RouterLink/버튼 모두) */
.auth-link,
.auth-link.logout {
    all: unset;
    display: inline;
    font-size: 15px;
    font-weight: 500;
    color: #391902;
    cursor: pointer;
    background: none;
    border: none;
    padding: 0;
    margin: 0;
    line-height: 1;
    vertical-align: middle;
    text-decoration: none;
}

/* 아이콘 정렬/간격/크기 통일 */
.header-icons {
    display: flex;
    align-items: center;
    gap: 16px;
    height: 38px;
}

.icon, .icon.cart, .icon.favorite {
    width: 28px;
    height: 28px;
    object-fit: contain;
    display: block;
    margin: 0 auto;
}

/* favorite만 미세하게 왼쪽/아래로 조정 */
.icon.favorite {
    margin-left: 2px;
    margin-top: 1px;
}

.cart-wrapper {
    position: relative;
    display: flex;
    align-items: center;
    min-width: 28px;
    min-height: 28px;
}

.cart-count {
    position: absolute;
    right: -8px;
    top: -6px;
    font-size: 12px;
    font-weight: 700;
    border-radius: 50%;
    min-width: 18px;
    height: 18px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0 4px;
    box-sizing: border-box;
    z-index: 1;
}
</style>