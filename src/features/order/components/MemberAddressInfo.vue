<script setup>
import { useRoute } from 'vue-router';
import { ref, watchEffect } from 'vue';

const route = useRoute();
const isOrderCompletePage = ref(false);

// 현재 경로를 기준으로 버튼 표시 여부 추적
watchEffect(() => {
    const path = route.path;

    isOrderCompletePage.value =
            path.includes('/order/completion') ||
            /^\/members\/orders\/[a-z0-9]+\/order-detail$/.test(path);
});
</script>

<template>
    <div>
        <h4 class="fw-bold mb-3 py-3" id="member-info-title">배송 정보</h4>

        <div class="row mb-2">
            <div class="col-3">회원명</div>
            <div class="col">김북적</div>
        </div>

        <div class="row mb-2">
            <div class="col-3">전화번호</div>
            <div class="col">010-1234-5678</div>
        </div>

        <div class="row mb-2 align-items-center">
            <div class="col-3">배송지</div>
            <div class="col">
                [07060] 서울시 동작구 보라매로 87 212 OOO동 OOOO호
                <button
                        v-if="!isOrderCompletePage"
                        class="btn btn-sm btn-outline-secondary ms-2"
                >
                    변경
                </button>
            </div>
        </div>

        <div class="row">
            <div class="col-3">배송 요청사항</div>
            <div class="col">문 앞에 놓아주세요</div>
        </div>
    </div>
</template>

<style scoped>
#member-info-title {
    margin-top: 40px;
    border-bottom: 2px solid black;
    padding-bottom: 8px;
}
</style>