<script setup>
import { ref, onMounted, reactive } from 'vue'
import PostList from '@/features/post/components/PostList.vue'
import PagingBar from '@/components/common/PagingBar.vue'

const memberPosts = ref([])
const showDelete = ref(false)

const itemsPerPage = 10

const allPosts = [
    {
        postId: 13,
        title: '책 읽고 글쓰기 시작해봤어요',
        content: `최근에 책을 읽고 나서 느낀 점을 짧게 글로 정리해보는 습관을 들였어요.
              처음엔 귀찮았지만, 지금은 생각을 정리하는 데 정말 큰 도움이 되네요.
              글을 쓰면서 내 생각을 더 잘 이해하게 되는 것 같아요.`,
        createdAt: '2025-03-09',
        imageUrl: '/src/assets/images/post13.jpg'
    },
    {
        postId: 14,
        title: 'AI 관련 도서 추천',
        content: `요즘 AI에 관심이 많아서 관련 서적들을 읽고 있어요.
              특히 『AI 슈퍼파워』라는 책이 기억에 많이 남네요.
              중국과 미국의 AI 경쟁 구도도 흥미롭고, 앞으로의 미래에 대해 고민하게 만들어요.`,
        createdAt: '2025-03-10',
        imageUrl: '/src/assets/images/post14.jpg'
    },
    {
        postId: 15,
        title: '독서 후 산책의 즐거움',
        content: `책을 읽은 후에는 가볍게 산책을 하면서 내용을 곱씹어 보는 걸 좋아해요.
              조용한 공원길을 걷다 보면, 책의 문장들이 더 깊게 마음에 새겨지는 것 같아요.
              몸과 마음이 동시에 정리되는 기분이랄까요?`,
        createdAt: '2025-03-11',
        imageUrl: '/src/assets/images/post15.jpg'
    }

]

const pagination = reactive({
    currentPage: 1,
    totalPages: 1,
    totalItems: 0
})

const fetchMemberPosts= (page = 1) => {
    const start = (page - 1) * itemsPerPage
    const end = start + itemsPerPage

    memberPosts.value = allPosts.slice(start, end)
    pagination.currentPage = page
    pagination.totalItems = allPosts.length
    pagination.totalPages = Math.ceil(allPosts.length / itemsPerPage)

    window.scrollTo(0,0)
}

onMounted(() => fetchMemberPosts(1))
</script>

<template>
    <div class="members-post-container">
        <RouterLink to="/members/:memberId/followings"  class="back">
            <div>팔로잉 목록</div>
        </RouterLink>
        <h4>nick01</h4>
        <PostList
                :posts="memberPosts"
                :showDelete="showDelete"
                id="member-post"
        />

        <PagingBar
                v-bind="pagination"
                @page-changed="fetchMemberPosts"
        />
    </div>
</template>

<style scoped>
.members-post-container {
    margin-bottom: 40px;
}

.back {
    font-size: 14px;
    text-decoration: none;
    color: #391902;
}

h4 {
    font-weight : 700;
    margin: 21px;
    color: #391902;
}

#member-post {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 0 auto;
    max-width: 966px;
}

</style>
