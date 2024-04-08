<template>
  <section>
    <b-modal id="orderDetailsModal" hide-footer hide-header centered size="xl" @show="getOrderHasProducts">
      <b-container>
        <b-row>
          <b-col style="border: 1px solid black;" class="m-2" cols="auto">
            <p>Numero de orden:</p>
            <p>#{{ orderDetails.orderNumber }}</p>
            <p>Dirección de envio:</p>
            <p>{{ orderDetails.address + ", " + orderDetails.street  + ", " + orderDetails.neighborhood + ", " + orderDetails.state + ", " + orderDetails.postalCode}}</p>
            <p>Metodo de pago:</p>
            <p>Tarjeta {{ orderDetails.cardNumber }}</p>
            <hr>
            <b-row>
              <b-col>
                <p>Subtotal:</p>
              </b-col>
              <b-col>
                <p>MXN ${{ subtotal }}</p>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <p>Gastos de envio:</p>
              </b-col>
              <b-col>
                <p>Gratis</p>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <p>Total:</p>
              </b-col>
              <b-col>
                <p>MXN ${{ total }}</p>
              </b-col>
            </b-row>
          </b-col>
          <b-col style="border: 1px solid black;" class="m-2" >
            <p>Detalles</p>
            <div style="max-height: 400px; overflow-y: auto;">
              <b-card img-left no-body v-for="(product, index) in products" :key="index" class="mb-2">
                <b-carousel :interval="4000" controls indicators background="#ababab" style="text-shadow: 1px 1px 2px #333;"
                            @sliding-start="onSlideStar" @sliding-end="onSlideEnd" class="carousel-image mt-2 ml-2 mb-2">
                  <b-carousel-slide v-for="(productGallery, index) in product.product.productGallery" :key="index"
                                    :img-src="productGallery.image"/>
                </b-carousel>
                <b-card-body>
                  <b-card-text>
                    <p style="margin-bottom: 0.5rem;">{{ product.product.productName }}</p>
                    <p style="margin-bottom: 0.5rem;">Categoría: {{ product.product.category }}</p>
                    <p style="margin-bottom: 0.5rem;">Subcategoría: {{ product.product.subcategory }}</p>
                    <p style="margin-bottom: 0.5rem;">Cantidad: {{ product.amount }}</p>
                    <p>MXN ${{ product.product.price }}</p>
                  </b-card-text>
                </b-card-body>
              </b-card>
            </div>
            <hr/>
            <b-row class="m-2" v-if="order.status !== 'Entregado' & order.status !== 'Reembolsado'">
              <b-button style="background-color: red; border-color: red;" block>Cancelar compra</b-button>
            </b-row>
          </b-col>
        </b-row>
      </b-container>
    </b-modal>
  </section>
</template>

<script>
import Vue from "vue";
import OrderService from "@/services/order/OrderService";

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

    onSlideStar(slide) {
      this.sliding = true;
    },

    onSlideEnd(slide) {
      this.sliding = false;
    },
  },
});
</script>

<style scoped>
.carousel-image {
  max-width: 12rem;
  max-height: 12rem;
}
</style>