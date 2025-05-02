export const orderRoutes = [
    {
        path: '/order',
        name: 'order',
        component: () => import('@/features/order/views/OrderView.vue')
    }
]