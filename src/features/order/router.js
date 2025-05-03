export const orderRoutes = [
    {
        path: '/order',
        name: 'order',
        component: () => import('@/features/order/views/OrderView.vue')
    },
    {
        path: '/order/completion',
        name: 'orderCompletion',
        component: () => import('@/features/order/views/OrderCompletionView.vue')
    }
]