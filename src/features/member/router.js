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
    }
];