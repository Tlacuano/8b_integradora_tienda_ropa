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
            <b-form-input @input="getPageProductSalesRequests" v-model="search" id="search" type="text" placeholder="Buscar..." class="pr-5"></b-form-input>
            <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
          </div>
        </b-form-group>
      </b-col>
    </b-row>
    <b-row class="mt-3 container-products" >
      <b-col>
        <b-row>
          <b-col lg="3" md="4" sm="6" v-for="product in products" :key="product.id" >
            <b-card no-body class="highlight-on-hover mb-2 selectable" @click="openModal(product)">
              <b-row class="m-2" no-gutters>
                <b-col cols="auto" class="d-none d-md-block my-auto">
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
                      <div class="text-truncate  small"><b>Usuario:</b> {{ product.email }}</div>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <b-badge :variant="getVariant(product.status.status)" class="text-ellipsis text-white small">{{ product.status.status }}</b-badge>
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
    <ProductSalesRequestsDetails @request-success="handleRequestSuccess" @request-error="handleRequestError" :product-id="selectProductId"/>
  </section>
</template>
<script>
import ProductSalesRequestsService from '@/services/request-seller-product/RequestSellerProduct';
import {showSuccessToast, showWarningToast} from "@/components/alerts/alerts";
export default {
  name:"RequestSellerProduct",
  components: {
    ProductSalesRequestsDetails: () => import('@/views/request-seller-product/RequestSellerProductDetails.vue')
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
      selectProductId:null,
      search: null
    };
  },
  methods: {
    async getPageProductSalesRequests() {
      if(this.search ===null || this.search === ""){
        this.showOverlay()
        const response = await ProductSalesRequestsService.getPageProductSalesRequests(this.objectPagination)
        this.showOverlay()
        this.objectPagination.elements = response.totalElements
        this.products = response.content;
      }else{
        const payload = {
          email: this.search
        }
        const response = await ProductSalesRequestsService.getPageProductSalesRequestsByUserEmail(this.objectPagination, payload)
        this.objectPagination.elements = response.totalElements
        this.products = response.content;
      }
    },
    showOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    },
    openModal(productId) {
      this.selectProductId = productId.idRequestSellProduct;
      this.$nextTick(() => {
        this.$bvModal.show("productDetail");
      });
    },

    handleRequestSuccess() {
      showSuccessToast("Solicitud actualizada")
      this.getPageProductSalesRequests();
    },
    handleRequestError() {
      showWarningToast("Error al actualizar la solicitud")
    },
    getVariant(status) {
      switch (status) {
        case "Pendiente":
          return "warning";
        case "Aprobado":
          return "success";
        case "Rechazado":
          return "danger";
        default:
          return "secondary";
      }
    },
  },
  mounted() {
    this.getPageProductSalesRequests();
  },

}
</script>
<style>
.container-products {
  height: calc(100vh - 270px);
  overflow-x: hidden;
}

.status {
  color: #28a745;
}
</style>