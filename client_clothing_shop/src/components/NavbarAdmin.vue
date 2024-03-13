<template>
  <div>
    <b-navbar type="light" variant="dark" style="background-color: var(--background-navbar) !important;">
      <b-navbar-brand class="mx-auto">
        <img src="../assets/image/logo.png" alt="logo k&I"  class="d-inline-block align-top logo selectable">
      </b-navbar-brand>
    </b-navbar>
  </div>
</template>

<script>
export default {
  name: 'NavbarAdmin'
}
</script>

<style scoped>
  .logo {
    width: 200px;
  }

</style>