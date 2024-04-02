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
            <p>Fecha estimada de entrega</p>
            <p>????</p>
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
                <p>??????</p>
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
                    <p style="margin: 1px;">{{ product.product.productName }}</p>
                    <p style="margin: 1px;">{{ product.product.category }}</p>
                    <p style="margin: 1px;">{{ product.product.subcategory }}</p>
                    <p style="margin: 1px;">Cantidad: {{ product.amount }}</p>
                    <p style="margin: 1px;">MXN ${{ product.product.price }}</p>
                  </b-card-text>
                </b-card-body>
              </b-card>
            </div>
            <div class="mt-auto"> <!-- Agrega un contenedor con mt-auto -->
              <hr/>
              <b-row class="justify-content-center m-2">
                <b-button style="background-color: red; border-color: red;" block>Cancelar compra</b-button>
              </b-row>
            </div>
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
    idOrder: {
      type: String,
      required: true
    }
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
        idOrder: this.idOrder,
      }
      this.orderDetails = await OrderService.getOrderDetailsByIdOrderService(payload);
      this.products = await OrderService.getOrderHasProductsService(payload);
      for (let i = 0; i < this.products.length; i++) {
        this.subtotal += this.products[i].product.price;
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