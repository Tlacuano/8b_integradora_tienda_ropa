<template>
  <section class="interface">
    <b-modal id="editCardModal" title="Editar Tarjeta" hide-footer centered @show="chargeData">
      <form>
        <b-form-group label="Número de Tarjeta">
          <b-form-input v-model="form.cardNumber"></b-form-input>
        </b-form-group>
        <b-form-group label="Nombre del Titular">
          <b-form-input v-model="form.cardholderName"></b-form-input>
        </b-form-group>
        <b-form-group label="Fecha de Vencimiento">
          <b-form-input v-model="form.expirationDate"></b-form-input>
        </b-form-group>
        <b-row class="justify-content-center">
          <b-button type="submit" variant="dark" class="w-25 mx-5" style="border-radius: 0.5rem">Guardar</b-button>
          <b-button variant="secondary" @click="" class="w-25 mx-5" style="border-radius: 0.5rem; background-color: red; border-color: red;">Cancelar</b-button>
        </b-row>
      </form>
    </b-modal>
  </section>
</template>

<script>
import Vue from "vue";

export default Vue.extend({
  name: "EditCardModal",
  props: {
    paymentCard: Object,
    required: true
  },
  data() {
    return {
      form: {
        cardNumber: null,
        cardHolderName: null,
        expirationDate: null
      }
    }
  },
  methods: {
    chargeData() {
      this.form.cardNumber = this.paymentCard.cardNumber;
      this.form.cardholderName = this.paymentCard.cardholderName;
      this.form.expirationDate = this.paymentCard.expirationDate;
    }
  }
})

</script>

<style scoped>

</style>