<template>
  <b-modal id="cancel-sell-by-seller-modal" hide-header hide-footer centered @hidden="resetModal">
    <b-row>
      <b-col class="text-right">
        <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('cancel-sell-by-seller-modal')"/>
      </b-col>
    </b-row>

    <b-row class="mt-2">
      <b-col class="text-center">
        <h1>Cancelar venta</h1>
      </b-col>
    </b-row>

    <b-row class="mt-2">
      <b-col>
        <p class="text-justify text-secondary small">
          Cancelar la venta implica que el producto no se enviará y se le devolverá el dinero al comprador.
          Además, tu producto será desactivado y no podrás volver a venderlo hasta que lo actives nuevamente
          en la sección de productos.
        </p>
      </b-col>
    </b-row>

    <section v-if="order != null">
      <b-row class="mt-2">
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
          <b-button class="main-button" @click="cancelSell()" >
            Cancelar venta
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
  name: 'CancelSellBySellerModal',
  props: {
    order: {
      required: true
    }
  },
  data() {
    return {
      password: ''
    }
  },
  methods: {
    async cancelSell() {
      this.$validator.validateAll().then(async result => {
        if (result) {
          const payload = {
            email: this.$store.getters.getEmail,
            idOrderProduct: this.order.idOrderProduct,
            password: this.password
          }

          await showInfoAlert(
              "¿Estás seguro?",
              "Si cancelas la venta, el producto no se enviará y se le devolverá el dinero al comprador.",
              "Sí, cancelar",
              async () => {
                const response = await OrderService.cancelSellBySellerService(payload);

                if(response){
                  showSuccessToast("", "La venta ha sido cancelada exitosamente")

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