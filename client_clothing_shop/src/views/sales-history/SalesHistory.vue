<template>
  <section class="interface">
    <b-row>
      <b-col cols="12">
        <h1 class="text-center">Historial de ventas</h1>
      </b-col>
    </b-row>

    <b-row class="mt-4" align-h="between">
      <b-col cols="12" lg="4">
        <b-form-group>
          <div class="position-relative">
            <b-form-input  id="search" type="text" placeholder="Buscar..." class="pr-5"></b-form-input>
            <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
          </div>
        </b-form-group>
      </b-col>
      <b-col cols="2" class="text-right">
        <b-form-group>
          <b-select v-model="statusSelected" @change="getOrders">
            <option v-for="status in orderStatus" :key="status.id" :value="status.status">{{status.status}}</option>
          </b-select>
        </b-form-group>
      </b-col>
    </b-row>

    <b-row class="mt-3 container-sales">
      <b-col>
        <b-row>
          <b-col cols="12" md="6" lg="3" v-for="order in orders" :key="order.idOrderProduct" >
            <b-card class="highlight-on-hover">
              <b-row>
                <b-col cols="8" class="text-truncate">
                  <b-row>
                    <b-col>
                      <b>
                        {{order.productName}}
                      </b>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                  <span class="small">
                    <small>
                      No. orden:
                      <b>
                        {{order.orderNumber}}
                      </b>
                    </small>
                  </span>
                    </b-col>
                  </b-row>
                </b-col>
                <b-col cols="2" class="my-auto">
                  <span class="text-right text-secondary">
                    ({{order.amount}})
                  </span>
                </b-col>
                <b-col cols="2" class="text-right">
                  <b-dropdown
                      variant="link-dark"
                      toggle-class="text-decoration-none"
                      no-caret
                  >
                    <template v-slot:button-content>
                      <font-awesome-icon icon="ellipsis-v"/>
                    </template>
                    <b-dropdown-item @click="seeDetails(order)" >
                      <span class="ml-2">Ver detalles</span>
                    </b-dropdown-item>
                  </b-dropdown>
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
            v-model="objetPagination.page"
            :total-rows="objetPagination.elements"
            :per-page="objetPagination.size"
            aria-controls="my-table"
        ></b-pagination>
      </b-col>
    </b-row>

    <SaleDetailsModal :order="orderSelcted"/>
    <CancelSellBySellerModal :order="orderSelcted"/>
    <markAsSentBySellerModal :order="orderSelcted"/>
  </section>
</template>

<script>
import OrderService from "@/services/order/OrderService";

export default {
  name: 'SalesHistory',
  components: {
    SaleDetailsModal: () => import('@/views/sales-history/SaleDetailsModal.vue'),
    CancelSellBySellerModal : () => import('@/views/sales-history/CancelSellBySellerModal.vue'),
    markAsSentBySellerModal: () => import('@/views/sales-history/MarkAsSentBySellerModal.vue')
  },
  data() {
    return {
      orderStatus:[],
      orderSelcted: {},

      statusSelected: 'Preparación',
      orders: [],
      objetPagination:{
        page: 1,
        size: 24,
        elements: 0,
      },
    }
  },
  methods: {
    seeDetails(order){
      this.orderSelcted = order;
      this.$bvModal.show('sale-details-modal');

    },

    async getOrders(){
      this.showOverlay();

      const payload ={
        email: this.$store.getters.getEmail,
        status: this.statusSelected
      }
      const pagination = {
        page: this.objetPagination.page,
        size: this.objetPagination.size
      }

      const response = await OrderService.getOrdersBySellerAndStatusService(payload, pagination);

      this.orders = response.data.content;
      this.objetPagination.elements = response.data.totalElements;

      this.showOverlay();
    },

    async getOrderStatus(){
      const response =await OrderService.getOrderStatusService();
      this.orderStatus = response.data;

      await this.getOrders();
    },
    showOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    }
  },
  mounted() {
    this.getOrderStatus();
  },
  watch: {
    objetPagination: {
      handler: function () {
        this.getOrders();
      },
      deep: true
    }
  }
}
</script>

<style scoped>
.container-sales{
  height: calc(100vh - 270px);
  overflow-x: hidden;
}
</style>