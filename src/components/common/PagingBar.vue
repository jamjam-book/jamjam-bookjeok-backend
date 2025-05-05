<template>
    <div class="pagination">
        <button
                class="pagination-arrow"
                :disabled="currentPage === 1"
                @click="changePage(currentPage - 1)"
        >
            &lt;
        </button>

        <button
                v-for="page in visiblePages"
                :key="page"
                class="pagination-page"
                :class="{ active: page === currentPage }"
                @click="changePage(page)"
        >
            {{ page }}
        </button>

        <button
                class="pagination-arrow"
                :disabled="currentPage === totalPage"
                @click="changePage(currentPage + 1)"
        >
            &gt;
        </button>

    </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
    currentPage: { type: Number, required: true },
    totalPage: { type: Number, required: true },
    totalItems: { type: Number, required: true }
})

const emit = defineEmits(['page-changed'])

const changePage = (page) => {
    if (page >= 1 && page <= props.totalPage && page !== props.currentPage) {
        emit('page-changed', page)
    }
}

const visiblePages = computed(() => {
    const range = 2
    let start = Math.max(1, props.currentPage - range)
    let end = Math.min(props.totalPage, props.currentPage + range)

    if (end - start < range * 2) {
        if (props.currentPage < props.totalPage / 2) {
            end = Math.min(props.totalPage, end + (range * 2 - (end - start)))
        } else {
            start = Math.max(1, start - (range * 2 - (end - start)))
        }
    }

    const pages = []
    for (let i = start; i <= end; i++) {
        pages.push(i)
    }
    return pages
})
</script>

<style scoped>
.pagination {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 2px;
    flex-wrap: wrap;
    margin-top: 20px;
}

.pagination-arrow,
.pagination-page {
    padding: 0.25rem 0.5rem;
    font-size: 1rem;
    border: none;
    cursor: pointer;
    background-color: transparent;
    color: #333;
    border-radius: 6px;
    transition: background-color 0.2s;
}

.pagination-arrow:disabled,
.pagination-page:disabled {
    background-color: transparent;
    cursor: not-allowed;
}

.pagination-page.active {
    background-color: #f9f0df;
    color: white;
    font-weight: bold;
}

.pagination-info {
    margin-left: 1rem;
    font-size: 0.875rem;
    color: #757575;
}
</style>
