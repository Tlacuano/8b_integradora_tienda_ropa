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
          <b-row>
            <b-col cols="auto">
              <b-form-select
                  id="expiration-month"
                  v-model="form.expirationMonth"
                  :options="months"
                  class="custom-select"
                  name="expirationMonth"
                  v-validate="'required'"
              />
            </b-col>
            <b-col cols="auto">
              <b-form-select
                  id="expiration-year"
                  v-model="form.expirationYear"
                  :options="years"
                  class="custom-select"
                  name="expirationYear"
                  v-validate="'required'"
              />
            </b-col>
            <span v-show="errors.has('expirationMonth')" class="text-danger">{{ errors.first('expirationMonth') }}</span>
            <span v-show="errors.has('expirationYear')" class="text-danger">{{ errors.first('expirationYear') }}</span>
          </b-row>
        </b-form-group>
        <b-form-group label="CVV">
          <b-form-input v-model="form.cvv" type="number" name="cvv" v-validate="'required|cvv'"/>
          <span v-show="errors.has('cvv')" class="text-danger">{{ errors.first('cvv') }}</span>
        </b-form-group>
        <b-row class="justify-content-center">
          <b-button type="submit" variant="dark" class="w-25 mx-5" style="border-radius: 0.5rem">Guardar</b-button>
          <b-button variant="secondary" @click="closeModal" class="w-25 mx-5" style="border-radius: 0.5rem; background-color: red; border-color: red;">Cancelar</b-button>
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
        expirationMonth: null,
        expirationYear: null,
        cvv: null
      },
      months: [
        { value: '01', text: '01' },
        { value: '02', text: '02' },
        { value: '03', text: '03' },
        { value: '04', text: '04' },
        { value: '05', text: '05' },
        { value: '06', text: '06' },
        { value: '07', text: '07' },
        { value: '08', text: '08' },
        { value: '09', text: '09' },
        { value: '10', text: '10' },
        { value: '11', text: '11' },
        { value: '12', text: '12' },],
      years: [],
    }
  },
  methods: {
    chargeData() {
      this.form.cardNumber = this.paymentCard.cardNumber;
      this.form.cardholderName = this.paymentCard.cardholderName;
      let parts = this.paymentCard.expirationDate.split("/");
      this.form.expirationMonth = parts[0];
      this.form.expirationYear = 2000 + parseInt(parts[1]);
      this.form.cvv = this.paymentCard.cvv;
    },
    closeModal() {
      this.$bvModal.hide("editCardModal");
    },
    generateYears() {
      const currentYear = new Date().getFullYear();
      for (let i = 0; i < 20; i++) {
        this.years.push({ value: currentYear + i, text: currentYear + i });
      }
    },
  },
  created() {
    this.generateYears();
  }
})

</script>

<style scoped>

</style>