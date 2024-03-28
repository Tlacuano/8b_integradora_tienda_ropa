import Vue from 'vue'
import store from "@/store/store";
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const router = new VueRouter({
    mode: 'history',
    base: import.meta.env.BASE_URL,
    routes: [
        {
            path: "/privacy-policy",
            name: "PrivacyPolicy",
            component: () => import("../views/privacy-policy/PrivacyPolicy.vue"),
            meta: { requiresAuth: false },
        },
        {
            path: "/",
            name: "Home",
            component: () => import("../Home.vue"),
            children: [
                {
                    path: "/",
                    name: "UserProducts",
                    component: () => import("../views/product/GuestProducts.vue"),
                },
                {
                    path: "user-management",
                    name: "ADMINUserManagement",
                    component: () => import("../views/user/UserManagement.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN"] },
                },
                {
                    path: "user-details/:email",
                    name: "UserDetails",
                    component: () => import("../views/user/UserDetails.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN"] },
                    props: true,
                },
                {
                    path: "profile",
                    name: "Profile",
                    component: () => import("../views/user/Profile.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN", "BUYER", "SELLER", "SUPERADMIN"] },
                },
                {
                    path: "/:category",
                    name: "UserProductsCategory",
                    component: () => import("../views/product/GuestProducts.vue"),
                    props: true
                },
                {
                    path: "/:category/:subcategory",
                    name: "UserProductsSubcategory",
                    component: () => import("../views/product/GuestProducts.vue"),
                    props: true
                },
                {
                    path:"user-management",
                    name:"ADMINUserManagement",
                    component: () => import("../views/user/UserManagement.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN"] },
                },
                {
                    path: "product-sale-request",
                    name: "ProductSaleRequest",
                    component: () => import("../views/request-seller-product/RequestSellerProduct.vue"),
                    meta: {requiresAuth: true, roles: ["ADMIN"]},
                },
                {
                    path: "subcategory-management",
                    name: "ADMINSubcategoryManagement",
                    component: () => import("../views/subcategory/SubcategoryManagement.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN", "SUPER_ADMIN"] }
                },
                {
                    path: "request-become-seller-management",
                    name: "ADMINRequestBecomeSellerManagement",
                    component: () => import("../views/requests-become-seller/RequestBecomeSellerManagement.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN", "SUPER_ADMIN"] },
                },
                {
                    path: "category-management",
                    name: "ADMINCategoryManagement",
                    component: () => import("../views/category/CategoryManagement.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN"] },
                },
            ]
        },

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




