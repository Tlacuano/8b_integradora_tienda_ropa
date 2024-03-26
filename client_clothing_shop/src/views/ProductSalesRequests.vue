<template>
  <section class="interface">
    <b-row>
      <b-col class="text-center">
        <h1>Solicitudes de venta productos</h1>
      </b-col>
    </b-row>
    <b-row class="mt-4" align-h="between">
      <b-col cols="12" lg="4">
        <b-form-group>
          <div class="position-relative">
            <b-form-input id="search" type="text" placeholder="Buscar..." class="pr-5"></b-form-input>
            <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
          </div>
        </b-form-group>
      </b-col>
    </b-row>
    <b-row class="mt-3 container-products">
      <b-col lg="4" v-for="product in products" :key="product.id" @click="openModal(product)">
        <b-card no-body class="highlight-on-hover mb-2">
          <b-row class="m-2" no-gutters>
            <b-col cols="auto" class="d-none d-md-block px-2 my-auto">
              <b-avatar
                  v-if="product.status"
                  size="2.5rem"
                  variant="success"
                  class="text-uppercase"
                  :src="product.image"
              />
              <b-avatar
                  v-else
                  size="2.5rem"
                  variant="secondary"
                  class="text-uppercase"
              >{{ product.productName.charAt(0) }}
              </b-avatar>
            </b-col>
            <b-col cols="8" class="ml-2">
              <b-row>
                <b-col>
                  <div class="text-truncate font-weight-bold small">{{ product.productName }}</div>
                </b-col>
              </b-row>
              <b-row>
                <b-col>
                  <div class="status text-truncate font-weight-bold small">{{ product.status.status }}</div>
                </b-col>
              </b-row>
            </b-col>
          </b-row>
        </b-card>
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
    <ProductSalesRequestsDetails :product-id="selectProductId"/>
  </section>
</template>
<script>
import ProductSalesRequestsService from '@/services/admin/adminService';
export default {
  components: {
    ProductSalesRequestsDetails: () => import('@/components/modals/ProductSalesRequestsDetails.vue')
  },
  data() {
    return {
      productDetail: false,
      objectPagination: {
        page: 1,
        size: 24,
        elements: 0,
      },
      products:[],
      selectProductId:null
    };
  },
  methods: {
    async getPageProductSalesRequests() {
      const response = await ProductSalesRequestsService.getPageProductSalesRequests(this.objectPagination)
      this.objectPagination.elements = response.totalElements
      this.products = response.content;
    },
    openModal(productId) {
      this.selectProductId = productId.idRequestSellProduct;
      this.$nextTick(() => {
        this.$bvModal.show("productDetail");
      });
    },
  },
  mounted() {
    this.getPageProductSalesRequests();
  },

}
</script>
<style>
.container-products {
  height: 60vh !important;
  overflow-x: hidden;
}

.status {
  color: #28a745;
}
</style>