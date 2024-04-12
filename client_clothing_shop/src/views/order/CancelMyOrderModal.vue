<template>
  <b-modal id="cancel-my-order-modal" hide-header hide-footer centered size="md" @hidden="resetModal">
    <b-row>
      <b-col class="text-right">
        <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('cancel-my-order-modal')"/>
      </b-col>
    </b-row>

    <b-row class="mt-2">
      <b-col class="text-center">
        <h1>Cancelar venta</h1>
      </b-col>
    </b-row>

    <section v-if="Product != null">
      <b-row class="mt-2">
        <b-col>
          <p class="text-justify text-secondary small">
            Cancelar la venta implica que el producto no se enviará y se le devolverá el dinero al comprador.
            Además, tu dinero será devuelto en un plazo de 3 a 5 días hábiles.
          </p>
        </b-col>
      </b-row>

      <b-row>
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
          <b-button class="main-button" @click="cancelBuy()" >
            Cancelar venta
          </b-button>
        </b-col>
      </b-row>

    </section>


  </b-modal>
</template>

<script>
import {showInfoAlert, showSuccessToast} from "@/components/alerts/alerts";
import OrderService from "@/services/order/OrderService";

export default {
    name: "CancelMyOrderModal",
    props: {
      Product: {
            type: Object,
            required: true,
        },
    },
    data() {
      return {
        password: '',
      };
    },
    methods: {
      async cancelBuy() {
        this.$validator.validateAll().then(async result => {
          if (result) {
            const payload = {
              email: this.$store.getters.getEmail,
              idOrderProduct: this.Product.idOrderProduct,
              password: this.password
            }

            console.log(payload);

            await showInfoAlert(
                "¿Estás seguro?",
                "Si cancelas la venta, el producto no se enviará y se le devolverá el dinero al comprador.",
                "Sí, cancelar",
                async () => {
                  const response = await OrderService.cancelBuyService(payload);

                  if(response){
                    showSuccessToast("Tu compra ha sido cancelada")

                    setTimeout(() => {
                      location.reload();
                    }, 1000);
                  }
                }
            );

            this.$bvModal.hide('cancel-sell-by-seller-modal');
          }
        });
      },

      resetModal(){
        this.password = '';
      }
    },
};
</script>

<style scoped>

</style>