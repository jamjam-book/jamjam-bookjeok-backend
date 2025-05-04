<script setup>
import {ref, onMounted, reactive} from 'vue'
import FollowingList from '@/features/member/components/FollowingList.vue'

const followings = ref([])

// 팔로잉 정보 정보 가져오기 : memberId를 매개변수에 넘겨줘야 함
const fetchFollowing = async () => {
    try {
        // 팔로잉 정보 api로 가져오는 것으로 수정할 부분
        followings.value = [
            { memberId: "user02", nickname: "문학소년" },
            { memberId: "user03", nickname: "책벌레춘식이" },
            { memberId: "user04", nickname: "밤샘독서왕" },
            { memberId: "user05", nickname: "서재여우" }
        ]

    } catch (e) {
        console.log('팔로잉 정보를 불러오지 못했습니다.', e);
    }
}

// 삭제하는 부분
const handleDelete = async () => {
    try {
        await deleteFollowing(memberId.value);
        console.log('언팔로우 성공')
    } catch (e) {
        console.log('언팔로우 실패', e);
    }
}

onMounted(() => fetchFollowing())
</script>

<template>
    <h4>팔로잉 목록</h4>
    <FollowingList
            :followings="followings"
            @confirm-delete="handleDelete"
    />
</template>

<style scoped>
h4 {
    font-weight : 700;
    margin-top: 21px;
    color: #391902;
}
</style>