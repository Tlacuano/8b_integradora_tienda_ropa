<template>
  <section>
    <b-modal id="orderDetailsModal" hide-footer hide-header centered size="xl" @show="getOrderHasProducts">
      <b-container>
        <b-row>
          <b-col style="border: 1px solid black;" class="m-2" cols="auto">
            <b-row class="mt-4">
              <b-col>
                <p>Numero de orden:</p>
              </b-col>
              <b-col>
                <p>#{{ orderDetails.orderNumber }}</p>
              </b-col>
            </b-row>
            <b-row class="mt-4">
              <b-col>
                <p>Dirección de envio:</p>
              </b-col>
              <b-col>
                <p>{{ orderDetails.address + ", " + orderDetails.street  + ", " + orderDetails.neighborhood + ", " + orderDetails.state + ", " + orderDetails.postalCode}}</p>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <p>Metodo de pago:</p>
              </b-col>
              <b-col>
                <p>Tarjeta {{ orderDetails.cardNumber }}</p>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <p>Fecha estimada de entrega</p>
              </b-col>
              <b-col>
                <p>????</p>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <p>Subtotal:</p>
              </b-col>
              <b-col>
                <p>MXN $699</p>
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
                <p>MXN $699</p>
              </b-col>
            </b-row>
          </b-col>
          <b-col style="border: 1px solid black;" class="m-2">
            <p>Detalles</p>
            <b-card img-left no-body>
              <b-img :src="image" fluid alt="Fluid image" style="max-width: 10rem;" class="mt-4 mb-4 ml-2"/>
              <b-card-body>
                <b-card-title>Card Title</b-card-title>
                <b-card-text>
                  <p>Categoria</p>
                  <p>Subcategoria</p>
                  <p>x1</p>
                  <p>MXN $699</p>
                </b-card-text>
              </b-card-body>
            </b-card>
            <hr/>
            <b-row class="justify-content-center m-2">
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
    idOrder: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      orderDetails: {},
      products: [],
      total: 0,
    }
  },
  methods: {
    async getOrderHasProducts() {
      const payload = {
        idOrder: this.idOrder,
      }
      this.orderDetails = await OrderService.getOrderDetailsByIdOrderService(payload);
      this.products = await OrderService.getOrderHasProductsService(payload);
    }
  },
});
</script>

<style scoped>
</style>