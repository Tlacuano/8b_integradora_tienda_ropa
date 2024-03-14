<template>
  <div>
    <b-navbar class="px-4" type="light" variant="dark" style="background-color: var(--background-navbar) !important;">
      <b-navbar-nav >
        <b-navbar-brand class="selectable">
          <font-awesome-icon icon="fa-solid fa-bars"/>
        </b-navbar-brand>
      </b-navbar-nav>


      <b-navbar-brand class="mx-auto pl-md-5">
        <img src="../../assets/image/logo.png" alt="logo k&I" class="d-inline-block align-top logo selectable">
      </b-navbar-brand>

      <b-navbar-nav class="d-none d-md-block mr-3">
        <b-navbar-brand class="selectable">
          <font-awesome-icon icon="fa-solid fa-right-left"/>
        </b-navbar-brand>
      </b-navbar-nav>

      <b-navbar-nav class="d-none d-md-block ">
        <b-navbar-brand class="selectable">
          <font-awesome-icon icon="fa-solid fa-user"/>
        </b-navbar-brand>
      </b-navbar-nav>
    </b-navbar>
  </div>
</template>

<script>
export default {
  name: 'NavbarSeller',
}
</script>

<style scoped>
.logo {
  width: 200px;
}

</style>
