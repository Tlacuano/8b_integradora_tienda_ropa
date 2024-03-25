import Vue from 'vue'
import store from "@/store/store";
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const router = new VueRouter({
    mode: 'history',
    base: import.meta.env.BASE_URL,
    routes: [
        {
            path: "/user-management",
            name: "ADMINUserManagement",
            component: () => import("../views/user/UserManagement.vue"),
            meta: {requiresAuth: true, roles: ["ADMIN"]},
        },
        {
            path: "/user-details/:email",
            name: "UserDetails",
            component: () => import("../views/user/UserDetails.vue"),
            meta: {requiresAuth: true, roles: ["ADMIN"]},
            props: true,
        },
        {
            path: "/product-sale-request",
            name: "ProductSaleRequest",
            component: () => import("../views/ProductSalesRequests.vue"),
            meta: {requiresAuth: true, roles: ["ADMIN"]},
        }
    ]
});


router.beforeEach((to, from, next) => {
    const {requiresAuth, roles} = to.meta;
    const isAuthenticated = store.getters.isLoggedIn || localStorage.getItem("token") ? true : false;
    const role = store.getters.getRole || localStorage.getItem("role");

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




