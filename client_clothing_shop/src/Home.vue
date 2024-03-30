<template >
  <div>
    <b-overlay :show="showOverlay" >
      <NavbarBuyer v-if="(getRole === 'BUYER' && isLoggedIn) || !isLoggedIn"/>
      <NavbarAdmin v-if="(getRole === 'ADMIN' || getRole === 'SUPERADMIN') && isLoggedIn"/>
      <NavbarSeller v-if="getRole === 'SELLER' && isLoggedIn" />

      <b-container fluid class="app-container pt-2" >
        <transition name="fade" mode="out-in">
          <router-view />
        </transition>
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
  height: calc(100vh - 50px);
  overflow-y: auto;
  .fade-enter-active, .fade-leave-active {
    transition: opacity 0.35s;
  }
  .fade-enter, .fade-leave-to {
    opacity: 0;
  }
}

.app-container::-webkit-scrollbar {
  width: 10px;
}

.app-container::-webkit-scrollbar-track {
  background: #f1f1f1;
}
.app-container::-webkit-scrollbar-thumb {
  background: #bab9b9;
  border-radius: 4px;
}

.app-container::-webkit-scrollbar-thumb:hover {
  background: #a3a3a3;
}

</style>
