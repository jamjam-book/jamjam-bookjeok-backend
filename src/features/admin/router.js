export const adminRoutes = [
    {
        path: '/admin',
        name: 'AdminPage',
        component: () => import('@/features/admin/views/AdminView.vue'),
        children: [
            { path: 'members', component: () => import('@/features/admin/views/AdminMemberView.vue')}
        ]
    },
];