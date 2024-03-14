<template>
  <div>
    <b-navbar class="px-4" type="light" variant="dark" style="background-color: var(--background-navbar) !important;">
      <b-navbar-nav class=" d-md-none">
        <b-navbar-brand class="selectable">
          <font-awesome-icon icon="fa-solid fa-bars"/>
        </b-navbar-brand>
      </b-navbar-nav>

      <b-navbar-nav class="d-none d-md-block ml-5 mr-5 categoria" >
        <b-nav-item-dropdown text="Categorías" right class="mr-5">
          <template v-slot:button-content>
            <span>{{ selectedCategory.name || 'Categorías' }}</span>
          </template>
          <b-dropdown-item v-for="category in categories" :key="category.id" @click="selectCategory(category)">
            {{ category.name }}
          </b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>

      <b-navbar-brand class="mx-auto">
        <img src="../../assets/image/logo.png" alt="logo k&I" class="d-inline-block align-top logo selectable">
      </b-navbar-brand>

      <b-navbar-nav class="d-none d-md-block pl-md-5">
        <b-navbar-brand class="selectable pr-3" @click="navigateTo('/wish-list')">
          <font-awesome-icon icon="fa-solid fa-heart"/>
        </b-navbar-brand>

        <b-navbar-brand class="selectable pr-3" @click="navigateTo('/shopping-cart')">
          <font-awesome-icon icon="fa-solid fa-cart-shopping"/>
        </b-navbar-brand>

        <b-navbar-brand class="selectable pr-3">
          <font-awesome-icon icon="fa-solid fa-right-left"/>
        </b-navbar-brand>

        <b-navbar-brand class="selectable">
        <font-awesome-icon icon="fa-solid fa-user"/>
        </b-navbar-brand>
      </b-navbar-nav>


    </b-navbar>
    <nav-buyer />
    <LoginModal />
  </div>
</template>

<script>
import { isLoggedInS} from "@/utils/security/sessionFunctions";
import { mapGetters } from "vuex";


export default {
  name: 'NavbarBuyer',
  components: {
    NavBuyer: () => import("@/components/navs/NavBuyer.vue"),
    LoginModal: () => import("@/views/auth/LoginModal.vue")
  },
  data() {
    return {
      categories:[
        {name: 'Mujeres', id: 1},
        {name: 'Hombres', id: 2},
        {name: 'Niños', id: 3},
      ],
      selectedCategory:{}
    }
  },
  methods: {
    selectCategory(category){
      this.selectedCategory = category;
    },
    navigateTo(route){
      if(this.isLoggedIn){
        this.$router.push(route);
      }else{
        this.$root.$emit('bv::show::modal', 'login-modal')
      }
    },
  },
  computed: {
    ...mapGetters(['isLoggedIn'])
  },
}
</script>

<style scoped>
.logo {
  width: 200px;
}
.categoria{
  width: 140px;
}

</style>
