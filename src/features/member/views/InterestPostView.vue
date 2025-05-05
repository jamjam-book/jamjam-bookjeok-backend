<script setup>
import { ref, onMounted, reactive } from 'vue'
import PostList from '@/features/post/components/PostList.vue'
import PagingBar from '@/components/common/PagingBar.vue'
import InterestTab from '@/features/member/components/InterestTab.vue'

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
        『소년이 온다』 역시 읽고 나서 한참을 멍하니 있었네요. 이 두 작품은 누구에게나 꼭 한 번 추천하고 싶은 책이에요.
        시간을 내서 천천히 곱씹으며 읽으면 정말 많은 걸 느낄 수 있습니다. 강추드립니다!`,
        createdAt: '2024-11-12',
        imageUrl: '/src/assets/images/book1.jpg'
    },
    {
        postId: 2,
        title: 'Vue 관련 도서 추천합니다~!',
        content: `Vue.js를 공부하면서 정말 도움 많이 받은 책이 있어 추천하려고 합니다. 『Vue.js 프로젝트 시작하기』라는 책인데,
        단순한 API 설명에서 끝나는 게 아니라 실제로 실무에서 어떻게 Vue를 활용하는지, 컴포넌트 분리는 어떻게 하고
        Vuex는 어떤 구조로 써야 유지보수가 좋은지 등등 실용적인 팁이 가득해요.
        처음 Vue를 접했을 때 막막했는데 이 책 보면서 자신감이 많이 붙었습니다. 입문자나 중급자 분들께 모두 추천해요!`,
        createdAt: '2025-01-01',
        imageUrl: '/src/assets/images/post01.jpg'
    },
    {
        postId: 3,
        title: '요즘 읽은 최고의 자기계발서',
        content: `『습관의 힘』이라는 책을 최근에 다 읽었는데요, 단순히 "좋은 습관을 만들어라"가 아니라
        왜 우리가 나쁜 습관에 빠지고, 좋은 습관은 왜 유지하기 힘든지를 과학적으로 설명해 줍니다.
        특히 루틴과 보상 시스템에 대한 챕터는 개발자로서 업무 효율을 올리는 데도 많은 인사이트를 줬어요.
        자기계발서 좋아하시는 분들, 혹은 목표를 꾸준히 달성하지 못해 고민하시는 분들께 정말 강력히 추천합니다.`,
        createdAt: '2025-02-14',
        imageUrl: '/src/assets/images/post03.jpg'
    },
    {
        postId: 4,
        title: 'JS 코드 리팩토링 팁!',
        content: `최근에 기존 JS 코드베이스를 리팩토링하면서, 함수형 프로그래밍 기법을 도입해 봤습니다.
        불변성을 유지하고, map, filter, reduce 등을 적극 활용했더니 훨씬 가독성도 좋아지고 테스트도 쉬워졌어요.
        특히 Lodash나 Ramda 같은 라이브러리를 잘 활용하면 중복 로직을 깔끔하게 정리할 수 있더라고요.
        리팩토링에 관심 있는 개발자 분들은 꼭 함수형 접근을 한 번 시도해보세요. 진짜 코드가 살아나는 느낌입니다!`,
        createdAt: '2025-02-20',
        imageUrl: '/src/assets/images/post04.jpg'
    },
    {
        postId: 5,
        title: '이 책은 나에게 위로였다',
        content: `『아몬드』는 정말 특별한 책이에요. 감정을 느끼지 못하는 소년이 세상을 마주하는 이야기를 통해,
        우리는 오히려 우리의 감정에 대해 더 많이 생각하게 됩니다.
        사랑, 분노, 슬픔, 기쁨 등 인간의 본질적인 감정들이 얼마나 소중한지 새삼 느낄 수 있었고,
        책을 덮고 나서도 한동안 마음이 잔잔하게 울리더라고요. 힘든 시기를 지나고 있다면 이 책이 큰 위로가 될 수 있을 거예요.`,
        createdAt: '2025-03-01',
        imageUrl: '/src/assets/images/post05.jpg'
    },
    {
        postId: 6,
        title: '개발자 책상 셋업 공유',
        content: `요즘 개발에 몰입하기 좋은 환경을 만드는 데 꽂혀서, 책상 셋업을 완전히 리뉴얼했어요!
        울트라 와이드 모니터, 기계식 키보드, 그리고 높이 조절이 가능한 데스크까지.
        무엇보다 조명이 정말 중요하더라고요. 눈 피로도 줄이고 분위기도 확 달라집니다.
        사진도 곧 공유할게요~ 개발자분들, 셋업 고민하고 계신다면 작은 변화가 큰 집중력 향상을 가져다줄 수 있어요!`,
        createdAt: '2025-03-02',
        imageUrl: '/src/assets/images/post06.jpg'
    },
    {
        postId: 7,
        title: '책 읽는 습관 들이기',
        content: `바쁜 일상 속에서도 매일 10분씩 책 읽는 습관을 들이기 시작했는데요, 생각보다 효과가 엄청납니다.
        처음엔 '10분으로 뭘 해?' 싶었지만, 매일 꾸준히 하다 보니 어느새 한 권 두 권 읽어내고 있는 저를 보게 됐어요.
        자기 전, 또는 출근길에 가볍게 읽기 좋은 에세이부터 시작하면 부담이 없습니다.
        책 읽는 시간이 하루 중 가장 소중한 시간이 되었어요.`,
        createdAt: '2025-03-03',
        imageUrl: '/src/assets/images/post07.jpg'
    },
    {
        postId: 8,
        title: 'React와 Vue 비교 후기',
        content: `두 프레임워크 모두 써봤는데, 개인적으로 Vue가 더 직관적이고 빠르게 익힐 수 있었어요.
        React는 자유도가 높고 생태계가 크다는 장점이 있지만, 처음 접하는 사람에게는 진입 장벽이 꽤 느껴지더라고요.
        반면 Vue는 문서도 잘 돼 있고 컴포넌트 구조가 확실해서, 빠르게 UI 개발을 시작할 수 있었어요.
        물론 상황에 따라 선택은 달라지겠지만, 처음 프론트엔드를 배우는 분들께는 Vue를 추천하고 싶네요!`,
        createdAt: '2025-03-04',
        imageUrl: '/src/assets/images/post08.jpg'
    },
    {
        postId: 9,
        title: '좋은 문장 모음 공유',
        content: `요즘 책을 읽으면서 마음에 남는 문장을 하나씩 정리하고 있어요.
        책 속에는 우리가 놓치고 살던 감정이나 시선들이 꽤 많더라고요.
        예를 들면 “너무 무리하지 마. 남들보다 늦어도 괜찮아.” 같은 문장은 가끔 마음을 다잡게 해줍니다.
        여러분도 한 권 읽을 때마다 기억에 남는 문장을 따로 메모해보세요. 나중에 다시 꺼내보면 큰 위안이 되기도 해요.`,
        createdAt: '2025-03-05',
        imageUrl: '/src/assets/images/post09.jpg'
    },
    {
        postId: 10,
        title: '개발 슬럼프 극복기',
        content: `최근에 개발 슬럼프가 크게 왔었어요. 아무리 코드를 봐도 손에 안 잡히고,
        무의미하게 반복되는 하루가 계속되더라고요. 그러다 문득 예전에 감명 깊게 읽었던 책을 다시 펼쳐봤는데,
        그 문장 하나하나가 다시 나를 깨워주는 느낌이었습니다. 그날 이후 조금씩 루틴을 되찾고, 다시 개발이 재미있어졌어요.
        때때로 멈추는 것도 앞으로 나아가기 위한 과정이란 걸 느꼈습니다.`,
        createdAt: '2025-03-06',
        imageUrl: '/src/assets/images/post10.jpg'
    },
    {
        postId: 11,
        title: '이력서에 쓸 책 추천',
        content: `개발자로서 이력서에 작성할 때 '읽은 도서' 항목을 추가하면, 그 사람의 성장 방향을 엿볼 수 있죠.
        『Clean Code』, 『읽기 좋은 코드가 좋은 코드다』, 『도메인 주도 설계』 등은 실제로도 면접에서 자주 언급되는 책이에요.
        단순히 읽었다는 표시보다는, 어떤 내용을 얻었고 실제 업무에 어떻게 적용해봤는지 짧게 정리해두면 강한 인상을 남길 수 있어요.`,
        createdAt: '2025-03-07',
        imageUrl: '/src/assets/images/post11.jpg'
    },
    {
        postId: 12,
        title: '웹 성능 최적화 도서',
        content: `프론트엔드 실력을 한 단계 더 끌어올리고 싶다면 『웹 성능 최적화 가이드』를 꼭 읽어보세요.
        리소스 최소화, 렌더링 최적화, lazy loading, CDN 활용 등 실무에 바로 적용할 수 있는 내용이 가득합니다.
        특히 Lighthouse로 측정한 퍼포먼스 점수를 개선하는 실전 팁들이 너무 유용했어요.
        팀 내에서도 코드 리뷰 때 참고할 수 있는 기준들이 생겨서 더욱 좋았습니다.`,
        createdAt: '2025-03-08',
        imageUrl: '/src/assets/images/post12.jpg'
    }

]

const pagination = reactive({
    currentPage: 1,
    totalPages: 1,
    totalItems: 0
})

// 게시글 페이징 처리
const fetchInterestPosts = (page = 1) => {
    const start = (page - 1) * itemsPerPage
    const end = start + itemsPerPage

    interestPosts.value = allPosts.slice(start, end)
    pagination.currentPage = page
    pagination.totalItems = allPosts.length
    pagination.totalPages = Math.ceil(allPosts.length / itemsPerPage)

    // 버튼 클릭시 최상단으로 올라갈 수 있게 조정
    window.scrollTo(0,0)
}

onMounted(() => fetchInterestPosts(1))
</script>

<template>
    <PostList
            :posts="interestPosts"
            :showDelete="showDelete"
    />

    <PagingBar
            v-bind="pagination"
            @page-changed="fetchInterestPosts"
    />
</template>
