<template>
  <section>
    <b-modal id="orderDetailsModal" hide-footer hide-header centered size="xl" @show="getOrderHasProducts">
      <b-container>
        <b-row>
          <b-col style="border: 1px solid black;" class="m-2" cols="auto">
            <p class="font-weight-bold">Numero de orden:</p>
            <p class="text-truncate">#{{ orderDetails.orderNumber }}</p>
            <p class="font-weight-bold">Dirección de envio:</p>
            <p class="text-truncate">{{ orderDetails.address + ", " + orderDetails.street  + ", " + orderDetails.neighborhood + ", " + orderDetails.state + ", " + orderDetails.postalCode}}</p>
            <p class="font-weight-bold">Metodo de pago:</p>
            <p class="text-truncate">Tarjeta de credito/debito {{ hideCardNumber(orderDetails.cardNumber) }}</p>
            <hr>
            <b-row>
              <b-col>
                <p class="font-weight-bold">Subtotal:</p>
              </b-col>
              <b-col>
                <p>MXN ${{ subtotal }}</p>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <p class="font-weight-bold">Gastos de envio:</p>
              </b-col>
              <b-col>
                <p style="text-decoration: underline;">Gratis</p>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <p class="font-weight-bold">Total:</p>
              </b-col>
              <b-col>
                <p>MXN ${{ total }}</p>
              </b-col>
            </b-row>
          </b-col>
          <b-col style="border: 1px solid black;" class="m-2">
            <p class="font-weight-bold">Detalles</p>
            <div style="max-height: 400px; overflow-y: auto;">
              <b-card img-left no-body v-for="(product, index) in products" :key="index" class="mb-2">
                <b-carousel :interval="4000" controls indicators background="#ababab" style="text-shadow: 1px 1px 2px #333;"
                            @sliding-start="onSlideStar" @sliding-end="onSlideEnd" class="carousel-image mt-2 ml-2 mb-2">
                  <b-carousel-slide v-for="(productGallery, index) in product.product.productGallery" :key="index"
                                    :img-src="productGallery.image"/>
                </b-carousel>
                <b-card-body>
                  <b-card-text>
                    <p style="margin-bottom: 0.1rem;">{{ product.product.productName }}</p>
                    <p style="margin-bottom: 0.1rem;">Categoría: {{ product.product.category }}</p>
                    <p style="margin-bottom: 0.1rem;">Subcategoría: {{ product.product.subcategory }}</p>
                    <p style="margin-bottom: 0.1rem;">Cantidad: {{ product.amount }}</p>
                    <p style="margin-bottom: 0.1rem;">MXN ${{ product.product.price }}</p>
                    <p>Estado: <b-badge :variant="getVariant(product.status)" class="text-ellipsis text-white small">{{ product.status }}</b-badge></p>
                    <hr/>
                    <b-button v-if="product.status !== 'Entregado' && product.status !== 'Cancelado' && product.status !== 'Reembolsado'"
                              style="background-color: red; border-color: red;" block @click="openReasonModal(product)">Cancelar compra</b-button>
                  </b-card-text>
                </b-card-body>
              </b-card>
            </div>
          </b-col>
        </b-row>
      </b-container>
    </b-modal>

    <b-modal id="rejectionReasonModal" hide-header hide-footer centered>
      <b-row>
        <b-col class="text-center">
          <h3>Indica la razón de la cancelación</h3>
        </b-col>
      </b-row>
      <b-row class="mt-3">
        <b-col class="ml-4" >
          <b-form-textarea
              id="textarea-rejection-reason"
              v-model="rejectionReason"
              placeholder="Escribe aquí la razón de rechazo"
              rows="3"
              max-rows="6"
              v-validate="'required|max:255'"
              name="rejectionReason"
          />
          <span v-show="errors.has('rejectionReason')" class="text-danger">{{ errors.first('rejectionReason') }}</span>
        </b-col>
      </b-row>
      <b-row class="justify-content-center mt-2 mb-2">
        <b-button :disabled="rejectionReason !== null" variant="dark" @click="cancelOrder" class="w-25 mx-5" style="border-radius: 0.5rem">Aceptar</b-button>
        <b-button @click="closeReasonModal" class="w-25 mx-5" style="border-radius: 0.5rem; background-color: red; border-color: red;">Cancelar</b-button>
      </b-row>
    </b-modal>
  </section>
</template>

<script>
import Vue from "vue";
import OrderService from "@/services/order/OrderService";
import {showInfoAlert, showSuccessToast, showWarningToast} from "@/components/alerts/alerts";

export default Vue.extend({
  props: {
    order: {
      type: Object,
      required: true
    },
  },
  data() {
    return {
      orderDetails: {},
      products: [],
      subtotal: 0,
      total: 0,
      slide: 0,
      sliding: null,
      rejectionReason: null,
      orderProduct: null,
    }
  },
  methods: {
    async getOrderHasProducts() {
      const payload = {
        idOrder: this.order.idOrder,
      }
      this.orderDetails = await OrderService.getOrderDetailsByIdOrderService(payload);
      this.products = await OrderService.getOrderHasProductsService(payload);
      this.subtotal = 0;
      this.total = 0;
      for (let i = 0; i < this.products.length; i++) {
        this.subtotal += (this.products[i].product.price * this.products[i].amount);
        this.subtotal = parseFloat(this.subtotal.toFixed(2));
        this.total = this.subtotal;
      }
    },

    async cancelOrder() {
      await this.$validator.validateAll().then(async result => {
        if (result) {
          await showInfoAlert(
              "¿Estás seguro de cancelar la compra?",
              "Esta acción no se puede deshacer",
              "Sí, cancelar compra",
              async () => {
                const payload = {
                  idOrderProduct: this.orderProduct.idOrderProduct,
                }
                const response = await OrderService.putStatusOrderHasProductService(payload);
                if (response === 200) {
                  showSuccessToast("Compra cancelada correctamente")
                  this.orderProduct.status = "Cancelado";
                  this.$bvModal.hide("rejectionReasonModal");
                } else {
                  showWarningToast("No se pudo cancelar la compra")
                }
              }
          )
        }
      });
    },

    onSlideStar(slide) {
      this.sliding = true;
    },

    onSlideEnd(slide) {
      this.sliding = false;
    },

    hideCardNumber(cardNumber) {
      if (cardNumber && cardNumber.length >= 16) {
        const cardHidden = cardNumber.slice(0, -4).replace(/\d/g, "•");
        const lastFourDigits = cardNumber.slice(-4);
        return cardHidden + lastFourDigits;
      } else {
        return cardNumber;
      }
    },

    getVariant(status) {
      switch (status) {
        case 'Pendiente':
          return 'warning';
        case 'Enviado':
          return 'primary';
        case 'Entregado':
          return 'success';
        case 'Cancelado':
          return 'danger';
        default:
          return 'secondary';
      }
    },

    openReasonModal(orderProduct) {
      this.orderProduct = orderProduct;
      this.$nextTick(() => {
        this.$bvModal.show("rejectionReasonModal");
      })
    },

    closeReasonModal() {
      this.$bvModal.hide("rejectionReasonModal");
    }
  },
});
</script>

<style scoped>
.font-weight-bold {
  font-weight: bold;
}
.carousel-image {
  max-width: 48%;
}
</style>