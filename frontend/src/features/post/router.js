export const postRoutes = [
    {
        path: '/:writerId/posts',
        name: 'MemberPostView',
        component: () => import('@/features/post/views/MemberPostView.vue')
    },
    {
        path: '/posts',
        name: 'PostView',
        component: () => import('@/features/post/views/PostView.vue')
    }
]