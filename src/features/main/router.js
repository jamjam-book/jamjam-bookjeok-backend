import LoginPage from "@/features/member/views/LoginPage.vue";

export const mainRoutes = [
    {
        path : '/',
        name : 'main',
        // component : MainView
    },
    {
        path: '/login',
        name: 'login',
        component: LoginPage
    }
];