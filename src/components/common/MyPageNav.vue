<script setup>
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();
const activeMenu = ref('주문 내역');
const handleMenuClick = (menu) => {
    activeMenu.value = menu;
};
</script>

<template>
    <aside>
        <nav>
            <ul>
                <li>
                    <RouterLink to="/members/:memberId" :class="{ active: activeMenu === '회원 정보' }"
                                @click.prevent="handleMenuClick('회원 정보')">회원 정보
                    </RouterLink>
                </li>
                <li><a href="#" class="small-side" :class="{ active: activeMenu === '프로필 변경' }"
                       @click.prevent="handleMenuClick('프로필 변경')">프로필 변경</a></li>
                <!-- :to="`/members/${memberId}/password/modify`" 로 변경 예정-->
                <li>
                    <RouterLink to="/members/:memberId/password/modify" class="small-side"
                                :class="{ active: activeMenu === '비밀번호 변경' }"
                                @click.prevent="handleMenuClick('비밀번호 변경')">비밀번호 변경
                    </RouterLink>
                </li>
                <li>
                    <RouterLink to="/members/:memberId/followings" class="small-side"
                                :class="{ active: activeMenu === '팔로잉 목록' }"
                                @click.prevent="handleMenuClick('팔로잉 목록')">팔로잉 목록
                    </RouterLink>
                </li>
            </ul>
            <ul>
                <li>
                    <RouterLink to="members/:memberId/orders" :class="{ active: activeMenu === '주문 내역' }"
                                @click.prevent="handleMenuClick('주문')">주문 내역
                    </RouterLink>
                </li>
                <li>
                    <RouterLink to="/members/:memberId/interest-authors" :class="{ active: activeMenu === '관심 목록' }"
                                @click.prevent="handleMenuClick('관심 목록')">관심 목록
                    </RouterLink>
                </li>
                <li>
                    <RouterLink to="/members/questions" :class="{ active: activeMenu === '문의 사항' }"
                                @click.prevent="handleMenuClick('문의 사항')">문의 사항
                    </RouterLink>
                </li>
            </ul>
        </nav>
    </aside>
</template>

<style scoped>
aside {
    width: 200px;
    padding: 14px;
    margin-right: 20px;
}

.small-side {
    font-size: 12px;
}

li {
    list-style: none;
    margin: 10px 0;
}

aside a {
    text-decoration: none;
    font-size: 19px;
    color: #000;
    display: inline-block;
    padding-bottom: 2px;
    transition: all 0.2s;
    cursor: pointer;
}

aside a.active {
    font-weight: bold;
    color: #000;
    border-bottom: 2px solid #666;
}

main {
    flex: 1;
    padding: 20px;
}
</style>