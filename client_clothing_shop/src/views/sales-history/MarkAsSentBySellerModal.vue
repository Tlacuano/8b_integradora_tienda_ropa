<template>
  <b-modal id="mark-as-sent-by-seller-modal" hide-header hide-footer centered @hidden="resetModal">
    <b-row>
      <b-col class="text-right">
        <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('mark-as-sent-by-seller-modal')"/>
      </b-col>
    </b-row>

    <b-row class="mt-2">
      <b-col class="text-center">
        <h1>Marcar como enviado</h1>
      </b-col>
    </b-row>

    <section v-if="order != null && order.productGallery != null && order.price != null">
      <b-row class="mt-3">
        <b-col>
          <b-form-group label="Contraseña:" label-for="input-password">
            <b-form-input
                id="input-password"
                type="password"
                v-model="password"
                name="password"
                v-validate="'required'"
            ></b-form-input>
            <span v-show="errors.has('password')" class="text-danger">{{ errors.first('password') }}</span>
          </b-form-group>
        </b-col>
      </b-row>

      <b-row class="mt-3 mb-3">
        <b-col>
          <b-button class="main-button" @click="markAsSent()" >
            Marcar como enviado
          </b-button>
        </b-col>
      </b-row>
    </section>
  </b-modal>
</template>

<script>
import OrderService from "@/services/order/OrderService";
import {showInfoAlert, showSuccessToast} from "@/components/alerts/alerts";

export default {
  name: 'MarkAsSentBySellerModal',
  props: {
    order: {}
  },
  data() {
    return {
      password:'',
    }
  },
  methods: {
    async markAsSent(){
      this.$validator.validateAll().then(async result => {
        if (result) {
          const payload = {
            email: this.$store.getters.getEmail,
            idOrderProduct: this.order.idOrderProduct,
            password: this.password
          }
          await showInfoAlert(
              "¿Estás seguro?",
              "Ya no podrás cancelar la venta, ¿estás seguro de marcar como enviado?",
              "Sí, marcar como enviado",
              async () => {
                const response = await OrderService.markAsSentBySellerService(payload);

                if(response){
                  showSuccessToast("","Estado actualizado a 'Enviado'")

                  setTimeout(() => {
                    window.location.reload();
                  }, 1000);
                }
              }
          );
        }
      });
    },

    resetModal() {
      this.password = '';
    }
  }
}
</script>

<style scoped>

</style>