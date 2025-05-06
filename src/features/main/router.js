import LoginPage from "@/features/member/views/LoginPage.vue";

export const mainRoutes = [
    {
        path : '/',
        name : 'main',
        component: () => import('@/features/main/views/MainView.vue')
    },
    {
        path: '/login',
        name: 'login',
        component: LoginPage
    }
];