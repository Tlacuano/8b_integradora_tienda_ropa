<template>
  <section class="interface">
    <b-row>
      <b-col class="text-center">
        <h1>Gestión de Pedidos</h1>
      </b-col>
    </b-row>

    <b-row class="mt-4" align-h="between">
      <b-col cols="12" lg="4">
        <b-form-group>
          <div class="position-relative">
            <b-form-input @input="getPageOrders" v-model="search" id="search" type="text" placeholder="Buscar..."  class="pr-5"/>
            <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
          </div>
        </b-form-group>
      </b-col>
    </b-row>

    <b-row class="mt-3 container-orders">
      <b-col>
        <b-row>
          <b-col lg="4" v-for="(order, index) in orders" :key="index">
            <b-card no-body class="highlight-on-hover mb-2" style="border-radius: 0.7rem;" @click="openOrderDetailsModal(order)">
              <b-row class="m-2" no-gutters>
                <b-col cols="auto" class="d-done d-md-block px-2 my-auto">
                  <b-avatar
                      size="2.5rem"
                      class="text-uppercase"
                      :src="order.picture ? order.picture : 'https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png'"
                  />
                </b-col>

                <b-col cols="auto" class="ml-2">
                  <b-row>
                    <b-col>
                      <div class="text-truncate font-weight-bold small">Orden #{{ order.orderNumber }}</div>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <div class="text-ellipsis text-secondary small">{{ order.personName + ' ' + order.personLastName + ' ' + order.personSecondLastName }}</div>
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
            @change="getPageOrders"
        ></b-pagination>
      </b-col>
    </b-row>

    <OrderDetailsModal :order="selectedOrder" @updateOrder="getPageOrders"/>
  </section>
</template>

<script>
import Vue from "vue";
import OrderService from "@/services/order/OrderService";

export default Vue.extend({
  name: "OrderManagement",
  components: {
    OrderDetailsModal: () => import("@/views/order/OrderDetailsModal.vue")
  },
  data() {
    return {
      objectPagination: {
        page: 1,
        size: 10,
        elements: 0,
      },
      orders: [],
      selectedOrder: {},
      search: null,
    }
  },
  methods: {
    async getPageOrders(page) {
      this.objectPagination.page = page;
      if (this.search === null || this.search === "") {
        this.showOverlay();
        const response = await OrderService.getPageOrdersService(this.objectPagination);
        this.showOverlay();
        this.objectPagination.elements = response.totalElements;
        this.orders = response.content;
      } else {
        const response = await OrderService.getPageOrderByOrderNumberService(this.objectPagination, this.search);
        this.objectPagination.elements = response.totalElements;
        this.orders = response.content;
      }
    },

    openOrderDetailsModal(order) {
      this.selectedOrder = {
        idOrder: order.idOrder,
        status: order.status
      };
      this.$nextTick(() => {
        this.$bvModal.show('orderDetailsModal');
      });
    },

    showOverlay() {
      this.$store.dispatch('changeStatusOverlay')
    },
  },
  mounted() {
    this.getPageOrders()
  }
})
</script>

<style scoped>
.container-orders {
  height: calc(100vh - 270px);
  overflow-x: hidden;
}
</style>