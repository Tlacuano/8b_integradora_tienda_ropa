<template>
  <header>
    <b-navbar class="px-4" type="light" variant="dark" style="background-color: var(--background-navbar) !important;">
      <b-navbar-nav class=" d-md-none">
        <b-navbar-brand class="selectable" v-b-toggle:sidebar-buyer>
          <font-awesome-icon icon="fa-solid fa-bars"/>
        </b-navbar-brand>
      </b-navbar-nav>

      <b-navbar-nav class="d-none d-md-block ml-5 mr-5 categoria">
        <b-nav-item-dropdown text="Categorías" right class="mr-5">
          <template v-slot:button-content>
            <span>{{ selectedCategory.name || 'Categorías' }}</span>
          </template>
          <b-dropdown-item v-for="category in categories" :key="category.id" @click="selectCategory(category)">
            {{ category.name }}
          </b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>

      <b-navbar-brand class="mx-auto" :to="'/'">
        <img src="../../assets/image/logo.png" alt="logo k&I" class="d-inline-block align-top logo selectable">
      </b-navbar-brand>

      <b-navbar-nav class="d-none d-md-block pl-md-5 ">
        <b-navbar-brand class="selectable pr-3" @click="prepareToNavigate('/wish-list')">
          <font-awesome-icon icon="fa-solid fa-heart"/>
        </b-navbar-brand>

        <b-navbar-brand class="selectable pr-3" @click="prepareToNavigate('/shopping-cart')">
          <font-awesome-icon icon="fa-solid fa-cart-shopping"/>
        </b-navbar-brand>

        <b-navbar-brand class="selectable pr-3"
                        v-if="hasMultipleRoles"
                        v-b-modal:switch-user-role-modal
        >
          <font-awesome-icon icon="fa-solid fa-right-left"/>
        </b-navbar-brand>

        <b-navbar-brand class="p-0">
          <b-nav-item-dropdown right class="selectable" no-caret>
            <template v-slot:button-content>
              <font-awesome-icon icon="fa-solid fa-user"/>
            </template>
            <b-dropdown-item v-if="isLoggedIn" @click="prepareToNavigate('/profile')">{{ getEmail || "Perfil" }}
            </b-dropdown-item>
            <b-dropdown-item v-if="isLoggedIn" @click="prepareToNavigate('/my-orders')">Mis compras</b-dropdown-item>
            <b-dropdown-item v-if="isLoggedIn" @click="logout()">Cerrar sesión</b-dropdown-item>

            <b-dropdown-item v-if="!isLoggedIn" v-b-modal:login-modal>Iniciar sesión</b-dropdown-item>
            <b-dropdown-item v-if="!isLoggedIn" v-b-modal:post-user-modal>Registrate</b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-brand>
      </b-navbar-nav>
    </b-navbar>


    <NavBuyer/>
    <LoginModal/>
    <SidebarBuyer/>
    <SwitchUserRoleModal/>
    <PostUser/>
    <RecoverPassword/>
  </header>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: 'NavbarBuyer',
  components: {
    NavBuyer: () => import("@/components/navs/NavBuyer.vue"),
    LoginModal: () => import("@/views/auth/LoginModal.vue"),
    SidebarBuyer: () => import("@/components/sidebars/SidebarBuyer.vue"),
    SwitchUserRoleModal: () => import("@/views/auth/SwitchUserRoleModal.vue"),
    PostUser: () => import("@/views/auth/PostUser.vue"),
    RecoverPassword: () => import("@/views/auth/RecoverPassword.vue"),
  },
  data() {
    return {
      categories: [
        {name: 'Mujeres', id: 1},
        {name: 'Hombres', id: 2},
        {name: 'Niños', id: 3},
      ],
      selectedCategory: {},

    }
  },
  methods: {
    selectCategory(category) {
      this.selectedCategory = category;
      this.$router.push({name: 'UserProductsCategory', params: {category: category.name}});
    },
    prepareToNavigate(route){
      if(this.isLoggedIn){
        if(this.$route.path !== route){
          this.$router.push(route);
        }
      }else{
        this.$root.$emit('bv::show::modal', 'login-modal')
      }
    },
    logout() {
      this.$store.dispatch('logout');
    }
  },
  computed: {
    ...mapGetters(['isLoggedIn', "hasMultipleRoles", "getEmail"])
  },
}
</script>


<style scoped>
.logo {
  width: 200px;
}

.categoria {
  width: 120px;
}


</style>
