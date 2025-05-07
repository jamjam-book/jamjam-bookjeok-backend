<script setup>
const allPosts = [
    {
        postId: 1,
        title: '한강 작가님 최고...!',
        imageUrl: '/src/assets/images/book1.jpg',

    },
    {
        postId: 2,
        title: 'Vue 관련 도서 추천합니다~!',
        imageUrl: '/src/assets/images/post01.jpg',

    },
    {
        postId: 3,
        title: '요즘 읽은 최고의 자기계발서'
    },
    {
        postId: 4,
        title: 'JS 코드 리팩토링 팁!'
    },
    {
        postId: 5,
        title: '이 책은 나에게 위로였다'
    },
    {
        postId: 6,
        title: '개발자 책상 셋업 공유'
    },
    {
        postId: 7,
        title: '책 읽는 습관 들이기'
    },
    {
        postId: 8,
        title: 'React와 Vue 비교 후기'
    },
    {
        postId: 9,
        title: '좋은 문장 모음 공유'
    },
    {
        postId: 10,
        title: '개발 슬럼프 극복기'
    },
]

</script>

<template>
    <section class="top-post-wrapper">
        <h2 class="top-post-title"> 인기 게시글 TOP 10</h2>
        <!-- 왼쪽 (1, 2등) -->
        <div class="post-ranking">
            <div class="post-left">
                <div
                        v-for="(post, index) in allPosts.slice(0, 2)"
                        :key="post.postId"
                        :class="['post-item', index === 0 ? 'top-border' : '']"
                >
                    <img :src="post.imageUrl" :alt="post.title" class="post-image" v-if="post.imageUrl" />
                    <span class="post-title ">{{ index + 1 }}. {{ post.title }}</span>
                </div>
            </div>

            <!-- 가운데 (3~6등) -->
            <div class="post-center">
                <div
                        v-for="(post, index) in allPosts.slice(2, 6)"
                        :key="post.postId"
                        :class="['post-item post-height', index === 0 ? 'top-border' : '']"
                >
                    <div class="post-title">{{ index + 3 }}. {{ post.title }}</div>
                </div>
            </div>

            <!-- 오른쪽 (7~10등) -->
            <div class="post-right">
                <div
                        v-for="(post, index) in allPosts.slice(6, 10)"
                        :key="post.postId"
                        :class="['post-item post-height', index === 0 ? 'top-border' : '']"
                >
                    <div class="post-title">{{ index + 7 }}. {{ post.title }}</div>
                </div>
            </div>
        </div>
        <div class="more-link">
            <RouterLink to="/posts" class="no-line"> 더보기 +</RouterLink>
        </div>

    </section>

</template>

<style scoped>
.top-post-wrapper {
    margin: 40px 0;
    padding: 0 40px;
}

.post-ranking {
    display: flex;
    justify-content: space-between;
}

.top-post-title {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 12px;
    color: #5a3921;
    padding-left: 8px;
}

.post-left, .post-right, .post-center {
    flex-basis: 20%;
    display: flex;
    flex-direction: column;
}

.post-right, .post-center {
    text-align: center;
    height: 284px;
}

.post-item {
    width: 360px;
    padding: 15px;
    border-bottom: 1px solid #ddd;
}

.post-height {
    display: flex;
    align-items: center;
    height: 71px;
}

.top-border {
    border-top: 1px solid #ddd;
}

.post-image {
    height: 110px;
    width: auto;
    margin : 0 15px 0 0;
    border-radius: 8px;
    text-align: left;
}

.post-title {
    font-weight: bold;
    color: #391902;
    font-size: 16px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    flex-grow: 1;
}

.more-link {
    width: 100%;
    text-align: right;
    cursor: pointer;
    margin-top: 12px;
}

.no-line {
    color: #5a3921;
    text-decoration: none;
    font-weight: bold;
}

</style>