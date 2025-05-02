import {createRouter, createWebHistory} from 'vue-router'
// import {adminRoutes} from "@/features/admin/router.js";
// import {bookRoutes} from "@/features/book/router.js";
import {mainRoutes} from "@/features/main/router.js";
import {memberRoutes} from "@/features/member/router.js";
import {orderRoutes} from "@/features/order/router.js";
import {cartRoutes} from "@/features/cart/router.js";
// import {postRoutes} from "@/features/post/router.js";
import {questionRoutes} from "@/features/question/router.js";

const router = createRouter({
    /*import.meta.env.BASE_URL*/
    history: createWebHistory(),
    routes: [
        // ...adminRoutes,
        // ...bookRoutes,
        ...mainRoutes,
        ...memberRoutes,
        ...orderRoutes,
        ...cartRoutes,
        // ...postRoutes,
        ...questionRoutes
    ],
})

export default router
