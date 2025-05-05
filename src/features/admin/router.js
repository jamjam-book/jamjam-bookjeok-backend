export const adminRoutes = [
    {
        path: '/admin',
        name: 'AdminPage',
        component: () => import('@/features/admin/views/AdminView.vue'),
        children: [
            { path: 'members', component: () => import('@/features/admin/views/AdminMemberView.vue')},
            { path: 'book-store', component: () => import('@/features/admin/views/AdminBookStoreView.vue')},
            { path: 'book/:bookId', component: () => import('@/features/admin/views/AdminBookDetailView.vue')},
            { path: 'book/edit/:bookId', component: () => import('@/features/admin/views/AdminBookDetailView.vue')},
        ]
    },
];