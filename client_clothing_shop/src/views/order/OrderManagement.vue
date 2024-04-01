<template>
  <section class="interface">
    <b-row>
      <b-col class="text-center">
        <h1>Gestión de Pedidos</h1>
      </b-col>
    </b-row>

    <b-row class="mt-3 container-orders">
      <b-col>
        <b-row>
          <b-col lg="4" v-for="(order, index) in orders" :key="index">
            <b-card no-body class="highlight-on-hover mb-2" style="border-radius: 0.7rem;">
              <b-row class="m-2" no-gutters>
                <b-col cols="auto" class="d-done d-md-block px-2 my-auto">
                  <b-avatar
                      size="2.5rem"
                      class="text-uppercase"
                      :src="order.picture ? order.picture : 'https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png'"
                  />
                </b-col>

                <b-col cols="8" class="ml-2">
                  <b-row>
                    <b-col>
                      <div class="text-truncate font-weight-bold small"> {{ order.personName + ' ' + order.personLastName + ' ' + order.personSecondLastName }}</div>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <div class="text-ellipsis text-secondary small"> {{ order.orderNumber }}</div>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <b-badge :variant="getVariant(order.status)" class="text-ellipsis text-white small">{{ order.status }}</b-badge>
                    </b-col>
                  </b-row>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>
      </b-col>
    </b-row>

    <b-row>
      <b-col>
        <b-pagination
            v-model="objectPagination.page"
            :total-rows="objectPagination.elements"
            :per-page="objectPagination.size"
            aria-controls="my-table"
        ></b-pagination>
      </b-col>
    </b-row>
  </section>
</template>

<script>
import Vue from "vue";
import OrderService from "@/services/order/OrderService";

export default Vue.extend({
  name: "OrderManagement",
  data() {
    return {
      objectPagination: {
        page: 1,
        size: 10,
        elements: 0,
      },
      orders: [],
    }
  },
  methods: {
    async getPageOrders() {
      const response = await OrderService.getPageOrdersService(this.objectPagination);

      this.objectPagination.elements = response.totalElements;
      this.orders = response.content;
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
  },
  mounted() {
    this.getPageOrders()
  }
})
</script>

<style scoped>
.container-orders {
  height: 65vh !important;
  overflow-x: hidden;
}
</style>