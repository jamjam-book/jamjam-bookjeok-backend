import LoginPage from "@/features/member/views/LoginPage.vue";
import SignUp from "@/features/member/views/SignUp.vue";
import ProfileView from "@/features/member/views/ProfileView.vue";

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
    },
    {
        path: '/signup',
        name: 'signup',
        component: SignUp
    },
    {
        path: '/profile',
        name: 'profile',
        component: ProfileView
    }
];