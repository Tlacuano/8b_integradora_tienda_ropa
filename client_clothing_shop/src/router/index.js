import Vue from 'vue'
import VueRouter from 'vue-router'
import {getRoleS, isLoggedInS} from "@/utils/security/sessionFunctions";

Vue.use(VueRouter)

const router = new VueRouter({
    mode: 'history',
    base: import.meta.env.BASE_URL,
    routes: [
        {
            path: "/user-management",
            name: "ADMINUserManagement",
            component: () => import("../views/user/UserManagement.vue"),
        },
        {
            path: "/",
            name: "UserProducts",
            component: () => import("../views/products/GuestProducts.vue"),
        },
        {
            path: "/:category",
            name: "UserProductsCategory",
            component: () => import("../views/products/GuestProducts.vue"),
            props: true
        },
        {
            path: "/:category/:subcategory",
            name: "UserProductsSubcategory",
            component: () => import("../views/products/GuestProducts.vue"),
            props: true
        }
    ]
});


router.beforeEach((to, from, next) => {
    const {requiresAuth, roles} = to.meta;
    const isAuthenticated = isLoggedInS();
    const role = getRoleS();

    if (!requiresAuth) {
        next();
    } else if (requiresAuth && !isAuthenticated) {
        next("/");
    } else if (roles && !roles.includes(role)) {
        next("/");
    } else {
        next();
    }
});

export default router




