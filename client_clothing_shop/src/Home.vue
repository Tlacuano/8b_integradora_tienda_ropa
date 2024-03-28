<template >
  <div>
    <b-overlay :show="showOverlay" >
      <NavbarBuyer v-if="(getRole === 'BUYER' && isLoggedIn) || !isLoggedIn"/>
      <NavbarAdmin v-if="(getRole === 'ADMIN' || getRole === 'SUPERADMIN') && isLoggedIn"/>
      <NavbarSeller v-if="getRole === 'SELLER' && isLoggedIn" />

      <b-container fluid class="app-container pt-2" >
        <router-view />
      </b-container>
    </b-overlay>
  </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: 'Home',
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
  max-height: calc(100vh - 100px);
  overflow-y: auto;
}

</style>
