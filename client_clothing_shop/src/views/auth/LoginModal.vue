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

    onReset(evt) {
      evt.preventDefault()
      this.form.email = ''
      this.form.password = ''
    }
  },
}
</script>

<style scoped>

</style>