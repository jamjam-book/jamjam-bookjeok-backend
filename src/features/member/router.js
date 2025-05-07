import ProfileView from "@/features/member/views/ProfileView.vue";

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
            { path: '/profile', component: () => import('@/features/member/views/ProfileView.vue')},
            { path: '/:memberId', component: () => import('@/features/member/views/PasswordResetRequestView.vue')},
            { path: ':memberId/password/modify', component: () => import('@/features/member/views/PasswordModifyView.vue')},
            { path: ':memberId/followings', component: () => import('@/features/member/views/FollowingView.vue')},
            //{ path: '/:memberId/orders', component: () => import ('@/features/order/views/OrderListView.vue')},
            {
                path: ':memberId/interest',
                name: 'MemberInterest',
                component: () => import('@/features/member/views/InterestView.vue'),
                children: [
                    { path: 'authors', component: () => import('@/features/member/views/InterestAuthorView.vue') },
                    { path: 'books', component: () => import('@/features/member/views/InterestBookView.vue') },
                    { path: 'posts', component: () => import('@/features/member/views/InterestPostView.vue') },
                ]
            },
            { path: 'questions', component: () => import('@/features/question/views/QuestionListView.vue')},
            { path: 'questions/register', component: () => import('@/features/question/views/QuestionRegisterView.vue')},
            { path: 'questions/:questionId/edit', component: () => import('@/features/question/views/QuestionEditView.vue')},
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/features/member/views/LoginPage.vue')
    },
    {
        path: '/signup',
        name: 'SignUp',
        component: () => import('@/features/member/views/SignUp.vue')
    },
    {
        path: '/profile',
        name: 'profile',
        component: ProfileView }
];