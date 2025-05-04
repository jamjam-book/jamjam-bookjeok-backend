export const memberRoutes = [
    {
        path: '/password/reset-link',
        name: 'PasswordResetRequest',
        component: () => import('@/features/member/views/PasswordResetRequestView.vue')
    },
    {
        path: '/password/reset',
        name: 'PasswordReset',
        component: () => import('@/features/member/views/PasswordResetView.vue')
    },
    {
        path: '/members',
        name: 'MyPage',
        component: () => import('@/features/member/views/MyPageView.vue'),
        children: [
            { path: '', redirect: 'profile' },
            { path: '/:memberId', component: () => import('@/features/member/views/PasswordResetRequestView.vue')},
            { path: ':memberId/password/modify', component: () => import('@/features/member/views/PasswordModifyView.vue')},
            //{ path: '/:memberId/orders', component: () => import ('@/features/order/views/OrderListView.vue')},
            { path: ':memberId/interest-authors', component: () => import ('@/features/member/views/InterestAuthorView.vue')},
            { path: '/:memberId/questions', component: () => import('@/features/question/views/QuestionListView.vue')}
        ]
    },
];