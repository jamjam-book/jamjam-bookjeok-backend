<script setup>
import PostItem from '@/features/post/components/PostItem.vue'

const { posts, showDelete } = defineProps({
    posts: {
        type: Array,
        required: true
    },
    showDelete: {
        type: Boolean,
        default: false
    }
})

const emit = defineEmits(["confirm-delete"]);

const confirmDelete = () => {
    emit("confirm-delete", post.postId);
};

</script>

<template>
    <div class="posts-table-wrapper">
        <table class="post-table">
            <tbody>
            <template v-for="(post, index) in posts" :key="post.postId">
                <tr v-if="index === 0">
                    <td colspan="6"><hr class="post-divider" /></td>
                </tr>
                <PostItem
                        :post="post"
                        :show-delete-button="showDelete"
                        @confirm-delete="confirmDelete"
                />
                <tr><td colspan="6"><hr class="post-divider"/></td></tr>
            </template>
            </tbody>
        </table>
    </div>
</template>


<style scoped>
.posts-table-wrapper {
    min-height: 1000px;
    overflow-y: auto;
}

.post-table {
    width: 966px;
    border-collapse: collapse;
    border: none;
}

.post-divider {
    border: none;
    border-top: 1px solid #ccc;
    margin: 12px 0;
}
</style>
