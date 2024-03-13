<template>
  <div id="app">
    <NavbarAdmin />
    <b-container fluid>
      <router-view />
    </b-container>
  </div>
</template>

<script>
export default {
  name: 'App',
  components: {
    NavbarAdmin: () => import('./components/NavbarAdmin.vue')
  }
}
</script>

<style >
  
</style>

