<template>
  <section class="interface">
    <b-modal id="addCardModal" title="Añadir Tarjeta" hide-footer centered>
      <form>
        <b-form-group label="Número de Tarjeta">
          <b-form-input v-model="form.cardNumber" type="number" name="cardNumber" v-validate="'required|card_number'"/>
          <span v-show="errors.has('cardNumber')" class="text-danger">{{ errors.first('cardNumber') }}</span>
        </b-form-group>
        <b-form-group label="Nombre del Titular">
          <b-form-input v-model="form.cardHolderName" name="cardHolderName" v-validate="'required|alpha_spaces'"/>
          <span v-show="errors.has('cardHolderName')" class="text-danger">{{ errors.first('cardHolderName') }}</span>
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
          </b-row>
          <span v-if="errors.has('expirationMonth') || errors.has('expirationYear')" class="text-danger">
              {{ errors.first('expirationMonth') || errors.first('expirationYear') }}
          </span>
        </b-form-group>
        <b-form-group label="CVV">
          <b-form-input v-model="form.cvv" type="number" name="cvv" v-validate="'required|cvv'"/>
          <span v-show="errors.has('cvv')" class="text-danger">{{ errors.first('cvv') }}</span>
        </b-form-group>
        <b-row class="justify-content-center">
          <b-button variant="dark" @click="addCard" class="w-25 mx-5" style="border-radius: 0.5rem">Añadir</b-button>
          <b-button variant="secondary" @click="closeModal" class="w-25 mx-5" style="border-radius: 0.5rem; background-color: red; border-color: red;">Cancelar</b-button>
        </b-row>
      </form>
    </b-modal>
  </section>
</template>

<script>
import Vue from "vue";
import PaymentCardService from "@/services/payment-card/PaymentCardService";
import {showInfoAlert, showSuccessToast, showWarningToast} from "@/components/alerts/alerts";

export default Vue.extend({
  name: "AddCardModal",
  data() {
    return {
      form: {
        cardNumber: null,
        cardHolderName: null,
        expirationMonth: null,
        expirationYear: null,
        cvv: null,
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
    async addCard() {
      this.$validator.validateAll().then((result) => {
        if (result) {
          showInfoAlert(
              "Añadir tarjeta",
              "¿Estás seguro de que deseas añadir esta tarjeta?",
              "Sí, añadir",
              async () => {
                const payload = {
                  cardNumber: this.form.cardNumber,
                  cardholderName: this.form.cardHolderName,
                  expirationDate: `${this.form.expirationMonth}/${String(this.form.expirationYear).slice(-2)}`,
                  cvv: this.form.cvv,
                  email: this.$store.getters.getEmail,
                }
                const response = await PaymentCardService.postPaymentCard(payload);
                if (response === 200) {
                  showSuccessToast("Tarjeta añadida correctamente")
                  this.$emit("cardAdded");
                  this.closeModal();
                } else {
                  showWarningToast("Error al añadir la tarjeta")
                }
              }
          );
        }
      });
    },
    closeModal() {
      this.form.cardNumber = null;
      this.form.cardHolderName = null;
      this.form.expirationMonth = null;
      this.form.expirationYear = null;
      this.$bvModal.hide("addCardModal");
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
.custom-select {
  border-radius: 0.5rem;
  border: 1px solid #ced4da;
  height: 2.25rem;
  background-color: #ffffff;
  box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.1);
  appearance: none;
  padding: 0.375rem 1.75rem 0.375rem 0.75rem;
  font-size: 1rem;
  color: #495057;
  cursor: pointer;
  margin-right: 0;
}
.custom-select::-ms-expand {
  display: none;
}
.custom-select:focus {
  border-color: #80bdff;
  outline: 0;
  box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}
@media (min-width: 576px) {
  .b-col-md-6 .custom-select {
    max-width: 100%;
  }
}
input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
input[type="number"] {
  -moz-appearance: textfield;
}
</style>