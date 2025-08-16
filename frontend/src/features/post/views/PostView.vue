<script setup>
import { ref, onMounted, reactive } from 'vue'
import PostList from '@/features/post/components/PostList.vue'
import PagingBar from '@/components/common/PagingBar.vue'
import InterestTab from '@/features/member/components/InterestTab.vue'
import PostTab from "@/features/post/components/PostTab.vue";
import SearchBar from "@/components/common/SearchBar.vue";
import MemberSearchBar from "@/features/admin/components/MemberSearchBar.vue";
import PostSearchBar from "@/features/post/components/PostSearchBar.vue";

const interestPosts = ref([])
const showDelete = ref(true)

// 페이지 당 게시글 수 설정
const itemsPerPage = 10

const allPosts = [
    {
        postId: 1,
        title: '한강 작가님 최고...!',
        content: `요즘 다시 문학에 빠져들고 있습니다. 특히 한강 작가님의 작품들은 읽을 때마다 감정이 휘몰아치는 것 같아요.
        『채식주의자』는 단순히 문학 작품을 넘어 인간 존재에 대한 깊은 통찰을 던져주는 소설이었고,
        『소년이 온다』 역시 읽고 나서 한참을 멍하니 있었네요. 이 두 작품은 누구에게나 꼭 한 번 추천하고 싶은 책이에요.`,
        createdAt: '2024-11-12',
        imageUrl: '/src/assets/images/book1.jpg',
        comment: 8,
        like: 12
    },
    {
        postId: 2,
        title: 'Vue 관련 도서 추천합니다~!',
        content: `Vue.js를 공부하면서 정말 도움 많이 받은 책이 있어 추천하려고 합니다. 『Vue.js 프로젝트 시작하기』라는 책인데,
        단순한 API 설명에서 끝나는 게 아니라 실제로 실무에서 어떻게 Vue를 활용하는지, 컴포넌트 분리는 어떻게 하고
        Vuex는 어떤 구조로 써야 유지보수가 좋은지 등등 실용적인 팁이 가득해요.`,
        createdAt: '2025-01-01',
        imageUrl: '/src/assets/images/post01.jpg',
        comment: 5,
        like: 9
    },
    {
        postId: 3,
        title: '요즘 읽은 최고의 자기계발서',
        content: `『습관의 힘』이라는 책을 최근에 다 읽었는데요, 단순히 "좋은 습관을 만들어라"가 아니라
        왜 우리가 나쁜 습관에 빠지고, 좋은 습관은 왜 유지하기 힘든지를 과학적으로 설명해 줍니다.`,
        createdAt: '2025-02-14',
        comment: 2,
        like: 7
    },
    {
        postId: 4,
        title: 'JS 코드 리팩토링 팁!',
        content: `최근에 기존 JS 코드베이스를 리팩토링하면서, 함수형 프로그래밍 기법을 도입해 봤습니다.
        불변성을 유지하고, map, filter, reduce 등을 적극 활용했더니 훨씬 가독성도 좋아지고 테스트도 쉬워졌어요.`,
        createdAt: '2025-02-20',
        comment: 11,
        like: 15
    },
    {
        postId: 5,
        title: '이 책은 나에게 위로였다',
        content: `『아몬드』는 정말 특별한 책이에요. 감정을 느끼지 못하는 소년이 세상을 마주하는 이야기를 통해,
        우리는 오히려 우리의 감정에 대해 더 많이 생각하게 됩니다.`,
        createdAt: '2025-03-01',
        comment: 4,
        like: 10
    },
    {
        postId: 6,
        title: '개발자 책상 셋업 공유',
        content: `요즘 개발에 몰입하기 좋은 환경을 만드는 데 꽂혀서, 책상 셋업을 완전히 리뉴얼했어요!`,
        createdAt: '2025-03-02',
        comment: 6,
        like: 14
    },
    {
        postId: 7,
        title: '책 읽는 습관 들이기',
        content: `바쁜 일상 속에서도 매일 10분씩 책 읽는 습관을 들이기 시작했는데요, 생각보다 효과가 엄청납니다.`,
        createdAt: '2025-03-03',
        comment: 3,
        like: 8
    },
    {
        postId: 8,
        title: 'React와 Vue 비교 후기',
        content: `두 프레임워크 모두 써봤는데, 개인적으로 Vue가 더 직관적이고 빠르게 익힐 수 있었어요.`,
        createdAt: '2025-03-04',
        comment: 5,
        like: 12
    },
    {
        postId: 9,
        title: '좋은 문장 모음 공유',
        content: `요즘 책을 읽으면서 마음에 남는 문장을 하나씩 정리하고 있어요.
        책 속에는 우리가 놓치고 살던 감정이나 시선들이 꽤 많더라고요.`,
        createdAt: '2025-03-05',
        comment: 2,
        like: 5
    },
    {
        postId: 10,
        title: '개발 슬럼프 극복기',
        content: `최근에 개발 슬럼프가 크게 왔었어요. 아무리 코드를 봐도 손에 안 잡히고,
        무의미하게 반복되는 하루가 계속되더라고요.`,
        createdAt: '2025-03-06',
        comment: 10,
        like: 13
    },
    {
        postId: 11,
        title: '이력서에 쓸 책 추천',
        content: `개발자로서 이력서에 작성할 때 '읽은 도서' 항목을 추가하면, 그 사람의 성장 방향을 엿볼 수 있죠.
        『Clean Code』, 『읽기 좋은 코드가 좋은 코드다』, 『도메인 주도 설계』 등은 실제로도 면접에서 자주 언급되는 책이에요.
        단순히 읽었다는 표시보다는, 어떤 내용을 얻었고 실제 업무에 어떻게 적용해봤는지 짧게 정리해두면 강한 인상을 남길 수 있어요.`,
        createdAt: '2025-03-07',
        comment: 0,
        like: 3
    },
    {
        postId: 12,
        title: '웹 성능 최적화 도서',
        content: `프론트엔드 실력을 한 단계 더 끌어올리고 싶다면 『웹 성능 최적화 가이드』를 꼭 읽어보세요.
        리소스 최소화, 렌더링 최적화, lazy loading, CDN 활용 등 실무에 바로 적용할 수 있는 내용이 가득합니다.
        특히 Lighthouse로 측정한 퍼포먼스 점수를 개선하는 실전 팁들이 너무 유용했어요.
        팀 내에서도 코드 리뷰 때 참고할 수 있는 기준들이 생겨서 더욱 좋았습니다.`,
        createdAt: '2025-03-08',
        comment: 0,
        like: 3
    },
    {
        postId: 13,
        title: '책 읽고 글쓰기 시작해봤어요',
        content: `최근에 책을 읽고 나서 느낀 점을 짧게 글로 정리해보는 습관을 들였어요.
              처음엔 귀찮았지만, 지금은 생각을 정리하는 데 정말 큰 도움이 되네요.
              글을 쓰면서 내 생각을 더 잘 이해하게 되는 것 같아요.`,
        createdAt: '2025-03-09',
        comment: 1,
        like: 9
    },
    {
        postId: 14,
        title: 'AI 관련 도서 추천',
        content: `요즘 AI에 관심이 많아서 관련 서적들을 읽고 있어요.
              특히 『AI 슈퍼파워』라는 책이 기억에 많이 남네요.
              중국과 미국의 AI 경쟁 구도도 흥미롭고, 앞으로의 미래에 대해 고민하게 만들어요.`,
        createdAt: '2025-03-10',
        comment: 8,
        like: 5
    },
    {
        postId: 15,
        title: '독서 후 산책의 즐거움',
        content: `책을 읽은 후에는 가볍게 산책을 하면서 내용을 곱씹어 보는 걸 좋아해요.
              조용한 공원길을 걷다 보면, 책의 문장들이 더 깊게 마음에 새겨지는 것 같아요.
              몸과 마음이 동시에 정리되는 기분이랄까요?`,
        createdAt: '2025-03-11',
        comment: 1,
        like: 1
    }
]

const pagination = reactive({
    currentPage: 1,
    totalPage: 1,
    totalItems: 0
})

// 게시글 페이징 처리
const fetchInterestPosts = (page = 1) => {
    const start = (page - 1) * itemsPerPage
    const end = start + itemsPerPage

    interestPosts.value = allPosts.slice(start, end)
    pagination.currentPage = page
    pagination.totalItems = allPosts.length
    pagination.totalPage = Math.ceil(allPosts.length / itemsPerPage)

    // 버튼 클릭시 최상단으로 올라갈 수 있게 조정
    window.scrollTo(0,0)
}

onMounted(() => fetchInterestPosts(1))
</script>

<template>
    <PostSearchBar/>
    <PostTab/>
    <PostList
            :posts="interestPosts"
            id="posts"
    />
    <PagingBar
            v-bind="pagination"
            @page-changed="fetchInterestPosts"
    />
</template>

<style scoped>
#posts {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 0 auto;
    max-width: 966px;
}
</style>