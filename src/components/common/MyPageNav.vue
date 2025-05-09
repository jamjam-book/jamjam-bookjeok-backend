<script setup>
import { useRoute } from "vue-router";
import {ref} from "vue";

const route = useRoute();
// 로그인 정보 저장 후 값 변경
const memberId = ref('user01');

// 현재 경로와 비교해서 메뉴의 활성 여부 판단
const isActive = (path) => route.path === path;
</script>

<template>
    <aside>
        <nav>
            <ul>
                <li>
                    <RouterLink
                            :to="`/members/${memberId}`"
                            :class="{ active: isActive(`/members/${memberId}`) }"
                    >
                        회원 정보
                    </RouterLink>
                </li>
                <li>
                    <RouterLink
                            :to="`/members/${memberId}/profile`"
                            class="small-side"
                            :class="{ active: isActive(`/members/${memberId}/profile`) }"
                    >
                        프로필 변경
                    </RouterLink>
                </li>
                <li>
                    <RouterLink
                            :to="`/members/${memberId}/password/modify`"
                            class="small-side"
                            :class="{ active: isActive(`/members/${memberId}/password/modify`) }"
                    >
                        비밀번호 변경
                    </RouterLink>
                </li>
                <li>
                    <RouterLink
                            :to="`/members/${memberId}/followings`"
                            class="small-side"
                            :class="{ active: isActive(`/members/${memberId}/followings`) }"
                    >
                        팔로잉 목록
                    </RouterLink>
                </li>
            </ul>
            <ul>
                <li>
                    <RouterLink
                            :to="`/members/${memberId}/orders`"
                            :class="{ active: isActive(`/members/${memberId}/orders`) }"
                    >
                        주문 내역
                    </RouterLink>
                </li>
                <li>
                    <RouterLink
                            :to="`/members/${memberId}/interest/authors`"
                            :class="{ active: isActive(`/members/${memberId}/interest/authors`) }"
                    >
                        관심 목록
                    </RouterLink>
                </li>
                <li>
                    <RouterLink
                            :to="`/members/${memberId}/questions`"
                            :class="{ active: isActive(`/members/${memberId}/questions`) }"
                    >
                        문의 사항
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
aside nav ul li .active {
    font-weight: bold;
    color: #000;
    border-bottom: 2px solid #666;
}
main {
    flex: 1;
    padding: 20px;
}
</style>
