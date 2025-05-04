<template>
    <div class="book-detail-page">
        <!-- 상단 도서 정보 -->
        <BookInfoHeader :book="book" :reviews="book.reviews" />

        <!-- 작가의 다른 도서 목록 -->
        <AuthorOtherBooks :books="authorBooks" />

        <!-- 탭 메뉴 -->
        <BookTabs :book="book"/>

    </div>
</template>

<script setup>
import BookInfoHeader from '@/features/book/components/BookInfoHeader.vue'
import AuthorOtherBooks from '@/features/book/components/AuthorOtherBooks.vue'
import BookTabs from "@/features/book/components/BookTabs.vue";

// 더미 book 데이터
const book = {
    id: 1,
    title: '아무튼, 디지몬',
    author: '천선란',
    publisher: '위고',
    category: '시/에세이',
    isbn: '9791193044162',
    description :
            '길고도 매우 짧았던 유년 시절에 건네는 작별, 천선란 세계의 시작\n' +
            '\n' +
            '“찾아라 비밀의 열쇠, 미로같이 얽힌 모험들!” 세기말의 혼란이 막 잠잠해지려던 2000년, 신나는 가사의 오프닝 송과 함께 〈디지몬 어드벤처〉가 한국에 처음 방영되었다. 일곱 명의 평범한 초등학생들이 ‘선택받은 아이들’이 되어 디지털 세상에서 펼치는 모험 이야기에 당시의 어린이들은 열광했다. 열한 살의 천선란 역시 마찬가지였다. 모니터에 말을 걸며, 내 디지몬이 컴퓨터 안에서 아직 깨어나지 못한 건 아닐까 노심초사하는, 언젠가 디지털 세상으로 가게 될 것이라고 굳게 믿는 그에게 〈디지몬 어드벤처〉는 인생 최초의 SF이자, 그를 창작의 세계로 이끈 첫 작품이었다.\n' +
            '\n' +
            '『아무튼, 디지몬』은 악에 맞서 싸우는 아이들의 모험에 설레고, “상처받고 외롭고 두렵지만, 용기와 위로 한마디로 언제든 다시 진화할 수 있는 인물”들에게서 위로를 받았던 유년 시절의 두근거림과 슬픔에 관한 이야기이자 그런 슬픔을 통해 글쓰기로 나아가는, 지금의 천선란 세계의 시작에 관한 이야기이다.\n' +
            '\n' +
            '“노을이 가득 들어찬 집, 현관 앞 TV에 바싹 붙어 앉아, 이제 막 〈디지몬 어드벤처〉 첫 화를 본 열한 살의 나는 리키의 내레이션을 들으며 내게도 길고도 매우 짧은 여름방학이 시작되었다는 것을 깨달았다.”\n' +
            '\n',
    price: 15000,
    publishDate: '2024-05-31',
    stock: 100,
    imageUrl: '/images/main_482791348279137620.20240609071045.jpg',
    reviews: [
        { id: 1, content: '좋았어요', date: '2025-04-01', rating: 5 },
        { id: 2, content: '최고!', date: '2025-04-02', rating: 5 },
        { id: 3, content: '디지몬 짱', date: '2025-04-02', rating: 5 },
        { id: 4, content: '감동적이에요', date: '2025-04-02', rating: 5 },
        { id: 5, content: '그냥그래요', date: '2025-04-03', rating: 3 },
        { id: 6, content: '별로', date: '2025-04-03', rating: 1 },
        { id: 7, content: '추천할만한책은아님', date: '2025-04-04', rating: 2 },
        { id: 8, content: '존잼', date: '2025-04-04', rating: 5 },
        { id: 9, content: '또 볼래요', date: '2025-04-04', rating: 5 },
        { id: 10, content: '최고!', date: '2025-04-04', rating: 5 },
    ],
}

const authorBooks = [
    { id: 2, title: '나인', imageUrl: '/images/main_324803232480322263.20230725121109.jpg' },
    { id: 3, title: '노랜드', imageUrl: '/images/main_329433932943397636.20230725121902.jpg' },
    { id: 4, title: '랑과 나의 사막', imageUrl: '/images/main_354920835492080618.20250102071614.jpg' },
    { id: 5, title: '이끼숲', imageUrl: '/images/main_396281739628177623.20230704084715.jpg' },
    { id: 6, title: '노을 건너기', imageUrl: '/images/main_420729542072954632.20230905100926.jpg' },
    { id: 7, title: '어떤 물질의 사랑', imageUrl: '/images/어떤 물질.jpg' },
    { id: 8, title: '천 개의 파랑', imageUrl: '/images/천 개의 파랑.jpg' },
]
</script>

<style scoped>
.book-detail-page {
    max-width: 1000px;
    margin: 0 auto;
    padding: 40px 20px;
}
</style>
