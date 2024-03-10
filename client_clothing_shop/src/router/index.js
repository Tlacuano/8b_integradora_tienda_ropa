import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  base: import.meta.env.BASE_URL,
  routes: [
    
  ]
});

//verifica si el usuario esta logeado
function isLoggedIn() {
  return !!localStorage.getItem("token");
}

//obtiene el rol del usuario
function getRole() {
  //implementar servicio para obtener el rol del usuario
  return "rol";
}

router.beforeEach((to, from, next) => {
  const { requiresAuth, roles } = to.meta;
  const isAuthenticated = isLoggedIn();
  const role = getRole();

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


