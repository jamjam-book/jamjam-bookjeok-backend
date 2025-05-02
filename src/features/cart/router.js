export const cartRoutes = [
    {
        path: '/carts',
        name: 'cart',
        component: () => import('@/features/cart/views/CartView.vue')
    }
]