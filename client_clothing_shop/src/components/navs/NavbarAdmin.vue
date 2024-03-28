<template>
  <header>
    <b-navbar class="px-4" type="light" variant="dark" style="background-color: var(--background-navbar) !important;">
      <b-navbar-nav >
        <b-navbar-brand class="selectable" v-b-toggle:sidebar-admin>
          <font-awesome-icon icon="fa-solid fa-bars"/>
        </b-navbar-brand>
      </b-navbar-nav>


      <b-navbar-brand class="mx-auto pl-md-4">
        <img src="../../assets/image/logo.png" alt="logo k&I" class="d-inline-block align-top logo selectable">
      </b-navbar-brand>

      <b-navbar-nav class="d-none d-md-block">

        <b-navbar-brand class="p-0">
          <b-nav-item-dropdown right class="selectable" no-caret >
            <template v-slot:button-content>
              <font-awesome-icon icon="fa-solid fa-user"/>
            </template>
            <b-dropdown-item  @click="prepateForNavigate('/profile')">{{getEmail || "Perfil"}}</b-dropdown-item>
            <b-dropdown-item  @click="logout()">Cerrar sesión</b-dropdown-item>

          </b-nav-item-dropdown>
        </b-navbar-brand>
      </b-navbar-nav>
    </b-navbar>

    <SidebarAdmin/>
  </header>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: 'NavbarAdmin',
  components: {
    SidebarAdmin: () => import("@/components/sidebars/SidebarAdmin.vue"),
  },
  methods: {
    logout() {
      this.$store.dispatch('logout');
    },
    prepateForNavigate(route){
      if(this.isLoggedIn){
        this.$router.push(route);
      }else{
        this.$root.$emit('bv::show::modal', 'login-modal')
      }
    },
  },
  computed: {
    ...mapGetters(['isLoggedIn', "getEmail"])
  },
}
</script>

<style scoped>
  .logo {
    width: 200px;
  }

</style>