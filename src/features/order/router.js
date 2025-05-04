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
    },
    {
        path: '/members/orders',
        name: 'memberOrders',
        component: () => import('@/features/order/views/MemberOrdersView.vue')
    },
    {
        path: '/members/orders/:orderId/order-detail',
        name: 'orderDetail',
        component: () => import('@/features/order/views/OrderDetailView.vue')
    }
]