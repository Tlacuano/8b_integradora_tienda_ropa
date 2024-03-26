import Vue from 'vue'
import store from "@/store/store";
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  base: import.meta.env.BASE_URL,
  routes: [
    {
      path:"/user-management",
      name:"ADMINUserManagement",
      component: () => import("../views/user/UserManagement.vue"),
      meta: { requiresAuth: true, roles: ["ADMIN"] },
    },
    {
      path: "/user-details/:email",
      name: "UserDetails",
      component: () => import("../views/user/UserDetails.vue"),
      meta: { requiresAuth: true, roles: ["ADMIN"] },
      props: true,
    },
    {
      path: "/privacy-policy",
      name: "PrivacyPolicy",
      component: () => import("../views/privacy-policy/PrivacyPolicy.vue"),
      meta: { requiresAuth: false },
    },
    {
      path: "/profile",
      name: "Profile",
      component: () => import("../views/user/Profile.vue"),
        meta: { requiresAuth: true, roles: ["ADMIN", "BUYER", "SELLER", "SUPERADMIN"] },
    }
    
  ]
});


router.beforeEach((to, from, next) => {
  const { requiresAuth, roles } = to.meta;
  const isAuthenticated = store.getters.isLoggedIn || localStorage.getItem("token") ? true : false;
  const role = store.getters.getRole || localStorage.getItem("role");

  if (!requiresAuth){
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




