<template>
  <div id="app">
    <NavbarAdmin />
    <NavbarSeller />
    <NavbarBuyer />
    <b-container fluid>
      <router-view />
    </b-container>
  </div>
</template>

<script>
export default {
  name: 'App',
  components: {
    NavbarAdmin: () => import('./components/NavbarAdmin.vue'),
    NavbarSeller: () => import('./components/NavbarSeller.vue'),
    NavbarBuyer: () => import('./components/NavbarBuyer.vue')
  }
}
</script>

<style >
  
</style>

