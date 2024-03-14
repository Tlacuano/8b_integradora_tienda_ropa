<template>
  <div id="app">
    <NavbarBuyer v-if="(getRole === 'comprador' && isLoggedIn) || !isLoggedIn"/>
    <NavbarAdmin v-if="getRole === 'administrador' && isLoggedIn"/>
    <NavbarSeller v-if="getRole === 'vendedor' && isLoggedIn" />

    <b-container fluid>
      <router-view />
      <!-- Formulario Temporal para Login y Logout -->
      {{isLoggedIn}}
      {{getRole}}
      {{getToken}}
      <div v-if="!isLoggedIn">
        <input v-model="tempToken" placeholder="Token">
        <input v-model="tempRole" placeholder="Role (administrador, vendedor, comprador)">
        <input v-model="tempEmail" placeholder="Email">
        <button @click="login">Login</button>
      </div>
      <div v-else>
        <button @click="logout">Logout</button>
      </div>
    </b-container>
  </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: 'App',
  components: {
    NavbarAdmin: () => import('./components/navs/NavbarAdmin.vue'),
    NavbarSeller: () => import('./components/navs/NavbarSeller.vue'),
    NavbarBuyer: () => import('./components/navs/NavbarBuyer.vue')
  },
  data() {
    return {
      tempToken: '',
      tempRole: '',
      tempEmail: '',
    };
  },
  computed: {
    ...mapGetters(['isLoggedIn', 'getRole', "getToken"])
  },
  methods: {
    login() {
      this.$store.dispatch('login', {
        token: this.tempToken,
        email: this.tempEmail,
        role: this.tempRole,
        hasMultipleRoles: false,
      });
    },
    logout() {
      this.$store.dispatch('logout');
    }
  }
}
</script>

<style >
</style>
