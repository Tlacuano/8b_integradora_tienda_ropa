<template>
  <div>
    <b-navbar class="px-4" type="light" variant="dark" style="background-color: var(--background-navbar) !important;">
      <b-navbar-nav >
        <b-navbar-brand class="selectable" v-for="category in categories">
          {{category.name}}
        </b-navbar-brand>
      </b-navbar-nav>


      <b-navbar-brand class="mx-auto">
        <img src="../assets/image/logo.png" alt="logo k&I"  class="d-inline-block align-top logo selectable">
      </b-navbar-brand>

      <b-navbar-nav class="d-none d-md-block pl-md-5">
        <b-navbar-brand class="selectable pr-3">
          <font-awesome-icon icon="fa-solid fa-right-left"/>
        </b-navbar-brand>

        <b-navbar-brand class="selectable pr-3">
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
  </div>
</template>

<script>
export default {
  name: 'NavbarBuyer',
  components: {
    NavBuyer: () => import("@/components/NavBuyer.vue"),
  },
  data() {
    return {
      categories:[
        {name: 'Mujeres', id: 1},
        {name: 'Hombres', id: 2},
        {name: 'Niños', id: 3},
      ]
    }
  }
}
</script>

<style scoped>
.logo {
  width: 200px;
}

</style>
