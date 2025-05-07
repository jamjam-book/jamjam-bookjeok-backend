<script setup>
import FollowingItem from '@/features/member/components/FollowingItem.vue'

const { followings } = defineProps({
    followings: {
        type: Array,
        required: true
    }
})
</script>

<template>
    <div class="following-table-wrapper">
        <table class="following-table">
            <tbody>
            <template v-for="(following, index) in followings" :key="following.memberId">
                <tr v-if="index === 0">
                    <td colspan="4"><hr class="following-divider" /></td>
                </tr>

                <FollowingItem :following="following"/>

                <tr>
                    <td colspan="4"><hr class="following-divider" /></td>
                </tr>
            </template>
            </tbody>
        </table>
    </div>
</template>


<style scoped>
.following-table-wrapper {
    min-height: 600px;
    overflow-y: auto;
    margin: 50px 100px;
}

.following-table {
    border-collapse: collapse;
    border: none;
}

.following-divider {
    border: none;
    border-top: 1px solid #ccc;
    margin: 12px 0;
}
</style>
