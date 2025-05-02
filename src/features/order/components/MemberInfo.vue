<script setup>
// 추후 사용자 정보 store로부터 가져올 수 있음
</script>

<template>
    <div>
        <h4 class="fw-bold mb-3 border-bottom py-3" id="member-info-title">회원 정보</h4>
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
            <div class="col">[07060] 서울시 동작구 보라매로 87 212 OOO동 OOOO호
                <button class="btn btn-sm btn-outline-dark ms-2">변경</button>
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
}
</style>