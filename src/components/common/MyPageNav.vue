<script setup>
import {onMounted, ref} from "vue";

const activeMenu = ref('주문');
const handleMenuClick = (menu) => {
  activeMenu.value = menu;
};
</script>

<template>
  <div class="container">
    <aside>
      <nav>
        <ul>
          <li>
            <a href="#" :class="{ active: activeMenu === '회원 정보' }" @click.prevent="handleMenuClick('회원 정보')">
              회원 정보
            </a>
          </li>
          <li><a href="#" class="small-side" :class="{ active: activeMenu === '프로필 변경' }" @click.prevent="handleMenuClick('프로필 변경')">프로필 변경</a></li>
          <li><a href="#" class="small-side" :class="{ active: activeMenu === '비밀번호 변경' }" @click.prevent="handleMenuClick('비밀번호 변경')">비밀번호 변경</a></li>
          <li><a href="#" class="small-side" :class="{ active: activeMenu === '팔로우 목록' }" @click.prevent="handleMenuClick('팔로우 목록')">팔로우 목록</a></li>
        </ul>
        <ul>
          <li><a href="#" :class="{ active: activeMenu === '주문' }" @click.prevent="handleMenuClick('주문')">주문</a></li>
          <li><a href="#" :class="{ active: activeMenu === '관심 목록' }" @click.prevent="handleMenuClick('관심 목록')">관심 목록</a></li>
          <li><a href="#" :class="{ active: activeMenu === '문의 사항' }" @click.prevent="handleMenuClick('문의 사항')">문의 사항</a></li>
        </ul>
      </nav>
    </aside>

    <main>
      <div>
        <h1>여기서 부터 구현</h1>
      </div>
    </main>
  </div>
</template>


<style scoped>
.container {
  display: flex;
  max-width: 1200px;
  margin: 0 auto;
  font-family: sans-serif;
}

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
