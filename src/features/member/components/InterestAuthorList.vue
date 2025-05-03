<script setup>
import InterestAuthorItem from '@/features/member/components/InterestAuthorItem.vue'

const { interestAuthors } = defineProps({
    interestAuthors: {
        type: Array,
        required: true
    }
})
</script>

<template>
    <div class="author-table-wrapper">
        <table class="author-table">
            <tbody>
            <template v-for="(author, index) in interestAuthors" :key="author.authorId">
                <!-- 가장 위에 들어가는 선 -->
                <tr v-if="index === 0">
                    <td colspan="4"><hr class="author-divider" /></td>
                </tr>
                <!-- 관심 작가 가져오기 -->
                <InterestAuthorItem :author="author" />

                <!-- 작가 아래 구분선 -->
                <tr>
                    <td colspan="4"><hr class="author-divider" /></td>
                </tr>
            </template>
            </tbody>
        </table>
    </div>
</template>


<style scoped>
.author-table-wrapper {
    height: 600px;
    overflow-y: auto;
}

.author-table {
    border-collapse: collapse;
    border: none;
}

.author-divider {
    border: none;
    border-top: 1px solid #ccc;
    margin: 12px 0;
}
</style>
