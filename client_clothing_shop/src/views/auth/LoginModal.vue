<template>
  <b-modal id="login-modal" title="Iniciar sesión" hide-footer centered>

  </b-modal>
</template>

<script>
export default {
  name: 'LoginModal',
  data() {
    return {
      form: {
        email: '',
        password: ''
      },
    }
  },
  methods: {

  },
}
</script>

<style scoped>

</style>