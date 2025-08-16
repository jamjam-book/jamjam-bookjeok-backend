<script setup>
import {ref, onMounted, reactive} from 'vue'
import FollowingList from '@/features/member/components/FollowingList.vue'
import {getFollowings, getInterestAuthors} from "@/features/member/interestApi.js";
import {useRoute} from "vue-router";

const route = useRoute();
const memberId = 'user01';
// const memberId = route.params.memberId;
const followings = ref([])

// 팔로잉 정보 정보 가져오기 : memberId를 매개변수에 넘겨줘야 함
const fetchFollowings = async () => {
    console.log('회원의 아이디 :', memberId);
    try {
        const { data: wrapper } = await getFollowings(memberId);
        const respData = wrapper.data;
        followings.value = respData || [];
        console.log('팔로잉 목록을 불러왔습니다.', e)

    } catch (e) {
        console.log('팔로잉 목록을 불러오지 못했습니다.', e);
    }
}

onMounted(() => fetchFollowings())
</script>

<template>
    <h4>팔로잉 목록</h4>
    <FollowingList
            :followings="followings"
    />
</template>

<style scoped>
h4 {
    font-weight : 700;
    margin-top: 21px;
    color: #391902;
}
</style>