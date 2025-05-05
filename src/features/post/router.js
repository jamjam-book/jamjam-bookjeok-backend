export const postRoutes = [
    {
        path: '/:writerId/posts',
        name: 'MemberPostView',
        component: () => import('@/features/post/views/MemberPostView.vue')
    }
]