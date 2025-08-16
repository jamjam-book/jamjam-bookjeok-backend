export const adminRoutes = [
    {
        path: '/admin',
        name: 'AdminPage',
        component: () => import('@/features/admin/views/AdminView.vue'),
        children: [
            { path: 'members', component: () => import('@/features/admin/views/AdminMemberView.vue')},
            { path: 'members/:memberId', component: () => import('@/features/admin/views/AdminMemberDetailView.vue')},
            { path: 'book-stock', component: () => import('@/features/admin/views/AdminBookStocksView.vue')},
            { path: 'book-store', component: () => import('@/features/admin/views/AdminBookStoreView.vue')},
            { path: 'book/:bookId', component: () => import('@/features/admin/views/AdminBookDetailView.vue')},
            { path: 'book/edit/:bookId', component: () => import('@/features/admin/views/AdminBookDetailView.vue')},
        ]
    },
];