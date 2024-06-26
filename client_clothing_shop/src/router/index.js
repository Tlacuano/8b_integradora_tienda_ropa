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
            path: "/finish-registration",
            name: "FinishRegistration",
            component: () => import("../views/user/FinishRegistration.vue"),
        },
        {
            path: "/",
            name: "Home",
            component: () => import("../Home.vue"),
            redirect: "/store",
            children: [
                {
                    path: "store",
                    name: "UserProducts",
                    component: () => import("../views/product/GuestProducts.vue"),
                    meta: { title: "Klein & Iversen | Tienda" },
                },
                {
                    path: "user-management",
                    name: "ADMINUserManagement",
                    component: () => import("../views/user/UserManagement.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN", "SUPERADMIN"] },
                },
                {
                    path: "user-details/:email",
                    name: "UserDetails",
                    component: () => import("../views/user/UserDetails.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN", "SUPERADMIN"] },
                    props: true,
                },
                {
                    path: "admin-management",
                    name: "AdminManagement",
                    component: () => import("../views/user/AdminManagement.vue"),
                    meta: { requiresAuth: true, roles: ["SUPERADMIN"] },
                },
                {
                    path: "profile",
                    name: "Profile",
                    component: () => import("../views/user/Profile.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN", "BUYER", "SELLER", "SUPERADMIN"] },
                },
                {
                    path: "my-orders",
                    name: "MyOrders",
                    component: () => import("../views/order/MyOrders.vue"),
                    meta: { requiresAuth: true, roles: ["BUYER"] },
                },
                {
                  path: "my-payment-cards",
                  name: "MyPaymentCards",
                  component: () => import("../views/payment-card/MyPaymentCards.vue"),
                    meta: { requiresAuth: true, roles: ["BUYER"] },
                },
                {
                    name:"ShoppingCart",
                    path:"shopping-cart",
                    component: () => import("../views/shopping-cart/ShoppingCart.vue"),
                    meta: { requiresAuth: true, roles: ["BUYER"] },
                },
                
                {
                    name: "wish-list",
                    path: "wish-list",
                    component: () => import("../views/wish-list/WishList.vue"),
                    meta: { requiresAuth: true, roles: ["BUYER"] },
                },
                {
                    path: "store/:category",
                    name: "UserProductsCategory",
                    component: () => import("../views/product/GuestProducts.vue"),
                    props: true,
                    meta: { title: "Klein & Iversen | Tienda" },
                },
                {
                    path: "store/:category/:subcategory",
                    name: "UserProductsSubcategory",
                    component: () => import("../views/product/GuestProducts.vue"),
                    props: true,
                    meta: { title: "Klein & Iversen | Tienda" },
                },
                {
                    path: "product-details/:id",
                    name: "UserProductDetails",
                    component: () => import("../views/product/ProductDetails.vue"),
                    props: true,
                    meta: { title: "Klein & Iversen | Detalles del producto" },
                },
                {
                    path: "product-sale-request",
                    name: "ProductSaleRequest",
                    component: () => import("../views/request-seller-product/RequestSellerProduct.vue"),
                    meta: {requiresAuth: true, roles: ["ADMIN", "SUPERADMIN"]},
                },
                {
                    path: "subcategory-management",
                    name: "ADMINSubcategoryManagement",
                    component: () => import("../views/subcategory/SubcategoryManagement.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN", "SUPERADMIN"] }
                },
                {
                    path: "request-become-seller-management",
                    name: "ADMINRequestBecomeSellerManagement",
                    component: () => import("../views/request-become-seller/RequestBecomeSellerManagement.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN", "SUPERADMIN"] },
                },
                {
                    path: "category-management",
                    name: "ADMINCategoryManagement",
                    component: () => import("../views/category/CategoryManagement.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN", "SUPERADMIN"] },
                },
                {
                    path: "product-management",
                    name: "ProductManagement",
                    component: () => import("../views/product-management/ProductManagement.vue"),
                    meta: { requiresAuth: true, roles: ["SELLER"] },
                },
                {
                    path:"register-product-edition",
                    name:"RegisterProductEditionRequest",
                    component: () => import("../views/product-management/RegisterProductEditionRequest.vue"),
                    meta: { requiresAuth: true, roles: ["SELLER"] },
                    props:true
                },
                {
                    path:"register-product-request",
                    name:"RegisterProductRequest",
                    component: () => import("../views/product-management/RegisterProductRequest.vue"),
                    meta: { requiresAuth: true, roles: ["SELLER"] },
                },
                {
                    path:"sales-history",
                    name:"SalesHistory",
                    component: () => import("../views/sales-history/SalesHistory.vue"),
                    meta: { requiresAuth: true, roles: ["SELLER"] },
                },
                {
                    path:"request-data-change-management",
                    name:"ADMINRequestDataChangeManagement",
                    component: () => import("../views/requests-data-change/RequestsDataChangeManagement.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN", "SUPERADMIN"] },
                },
                {
                    path:"buyer-address-management",
                    name:"BuyerAddressManagement",
                    component: () => import("../views/address-management/BuyerAddressManagement.vue"),
                    meta: { requiresAuth: true, roles: ["BUYER", "SELLER"] },
                },
                {
                    path:"request-return-product-management",
                    name:"ADMINRequestsReturnProductManagement",
                    component: () => import("../views/requests-return-product/RequestsReturnProductManagement.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN", "SUPERADMIN"] },
                },
                {
                    path: "order-management",
                    name: "ADMINOrderManagement",
                    component: () => import("../views/order/OrderManagement.vue"),
                    meta: { requiresAuth: true, roles: ["ADMIN", "SUPERADMIN"] },
                },
                {
                    path: "transaction",
                    name: "TransactionDetails",
                    component: () => import("../views/transaction/TransactionDetails.vue"),
                    meta: { title: "Klein & Iversen | Finaliza tu compra", requiresAuth: true, roles: ["BUYER"] },
                }
            ]
        },
        {
            path:"/no-response",
            name:"ServerError",
            component: () => import("../views/error/NoResponse.vue"),

        },
        {
            path: "/*",
            name: "NotFound",
            component: () => import("../views/error/Error404.vue"),
        }

    ]
});


router.beforeEach((to, from, next) => {
    const {requiresAuth, roles} = to.meta;
    const isAuthenticated = !!(store.getters.isLoggedIn || localStorage.getItem("token"));
    const role = store.getters.getRole || localStorage.getItem("role");

    const verifiedIncomplete = localStorage.getItem("verified");
    const path = to.path; // Assume 'to' is the target route object

    if (verifiedIncomplete && path !== "/finish-registration" && path !== "/privacy-policy") {
        next("/finish-registration");
    }else if (!verifiedIncomplete && path === "/finish-registration") {
        next("/");
    }


    if (requiresAuth && isAuthenticated && roles.includes(role)) {
        next();
    }else if(requiresAuth && isAuthenticated && !roles.includes(role)) {
        if(role === "SUPERADMIN" || role === "ADMIN") {
            next("/product-sale-request");
        }else if(role === "SELLER") {
            next("/product-management");
        }else if(role === "BUYER") {
            next("/store");
        }
    }else if(requiresAuth && !isAuthenticated) {
        next("/store");
    }else if(!requiresAuth && isAuthenticated && role !== "BUYER") {
        if(role === "SUPERADMIN" || role === "ADMIN") {
            next("/product-sale-request");
        }else if(role === "SELLER") {
            next("/product-management");
        }
    }else {
        next();
    }

});

export default router




