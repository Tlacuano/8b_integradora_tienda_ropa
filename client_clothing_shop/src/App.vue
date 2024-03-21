<template>
  <div id="app">
    <b-overlay :show="showOverlay" class="app-container">
      <NavbarBuyer v-if="(getRole === 'BUYER' && isLoggedIn) || !isLoggedIn"/>
      <NavbarAdmin v-if="getRole === 'ADMIN' && isLoggedIn"/>
      <NavbarSeller v-if="getRole === 'SELLER' && isLoggedIn" />

      <b-container fluid class="interface-container pt-2">
        <router-view />
      </b-container>
    </b-overlay>
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
    ...mapGetters(['isLoggedIn', 'getRole', "getToken","showOverlay"])
  }
}
</script>

<style >
  .app-container{
    overflow-y: hidden;
    position: absolute;
    height: 100%;
  }

  .interface-container{
    min-height: 81.5%;
    max-height: 90%;
    overflow-y: auto;
  }
</style>
