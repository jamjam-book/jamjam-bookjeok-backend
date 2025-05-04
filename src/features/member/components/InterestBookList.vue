<script setup>
import InterestBookItem from '@/features/member/components/InterestBookItem.vue'

const { interestBooks } = defineProps({
    interestBooks: {
        type: Array,
        required: true
    }
})
</script>

<template>
    <div class="interest-books-table-wrapper">
        <table class="interest-book-table">
            <tbody>
            <template v-for="(book, index) in interestBooks" :key="book.bookId">
                <tr v-if="index === 0">
                    <td colspan="6"><hr class="interest-book-divider" /></td>
                </tr>
                <InterestBookItem :book="book" />
                    <tr><td colspan="6"><hr class="interest-book-divider"/></td></tr>
            </template>
            </tbody>
        </table>
    </div>
</template>


<style scoped>
.interest-books-table-wrapper {
    min-height: 1000px;
    overflow-y: auto;
}

.interest-book-table {
    width: 966px;
    border-collapse: collapse;
    border: none;
}

.interest-book-divider {
    border: none;
    border-top: 1px solid #ccc;
    margin: 12px 0;
}
</style>
