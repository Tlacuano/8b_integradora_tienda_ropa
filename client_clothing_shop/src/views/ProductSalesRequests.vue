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
      <b-col lg="4" v-for="product in products" :key="product.id">
        <b-card no-body class="highlight-on-hover mb-2" @click="productDetail">
          <b-row class="m-2" no-gutters>
            <b-col cols="auto" class="d-none d-md-block px-2 my-auto">
              <b-avatar
                  v-if="product.status"
                  size="2.5rem"
                  variant="success"
                  class="text-uppercase"
                  :src="product.picture"
              />
              <b-avatar
                  v-else
                  size="2.5rem"
                  variant="secondary"
                  class="text-uppercase"
              >{{ product.name.charAt(0) }}
              </b-avatar>
            </b-col>
            <b-col cols="8" class="ml-2">
              <b-row>
                <b-col>
                  <div class="text-truncate font-weight-bold small">{{ product.name }}</div>
                </b-col>
              </b-row>
              <b-row>
                <b-col>
                  <div class="status text-truncate font-weight-bold small">{{ product.status }}</div>
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
    <ProductSalesRequestsDetails/>
  </section>
</template>
<script>
import ProductSalesRequestsService from '@/services/admin/adminService';
export default {

  data() {
    return {
      productDetail: false,
      objectPagination: {
        page: 1,
        size: 24,
        elements: 0,
      },
      products:[
        {
          id: 1,
          name: 'Producto 1',
          status: 'En espera',
          picture: 'https://picsum.photos/200/300'
        },
        {
          id: 2,
          name: 'Producto 2',
          status: 'En espera',
          picture: 'https://picsum.photos/200/300'
        },
        {
          id: 3,
          name: 'Producto 3',
          status: 'En espera',
          picture: 'https://picsum.photos/200/300'
        },
        {
          id: 4,
          name: 'Producto 4',
          status: 'En espera',
          picture: 'https://picsum.photos/200/300'
        },
        {
          id: 5,
          name: 'Producto 5',
          status: 'En espera',
          picture: 'https://picsum.photos/200/300'
        },
        {
          id: 6,
          name: 'Producto 6',
          status: 'En espera',
          picture: 'https://picsum.photos/200/300'
        },
        {
          id: 7,
          name: 'Producto 7',
          status: 'En espera',
          picture: 'https://picsum.photos/200/300'
        },
        {
          id: 8,
          name: 'Producto 8',
          status: 'En espera',
          picture: 'https://picsum.photos/200/300'
        },
        {
          id: 9,
          name: 'Producto 9',
          status: 'En espera',
          picture: 'https://picsum.photos/200/300'
        },
        {
          id: 10,
          name: 'Producto 10',
          status: 'En espera',
          picture: 'https://picsum.photos/200/300'
        },
        {
          id: 11,
          name: 'Producto 11',
          status: 'En espera',
          picture: 'https://picsum.photos/200/300'
        },
        {
          id: 12,
          name: 'Producto 12',
          status: 'En espera',
          picture: 'https://picsum.photos/200/300'
        },
        {
          id: 13,
          name: 'Producto 13',
          status: 'En espera',
          picture: 'https://picsum.photos'
        }
      ]
    };
  },
  methods: {
    async getPageProductSalesRequests() {
      const response = await ProductSalesRequestsService.getPageProductSalesRequests(this.objectPagination)
      this.objectPagination.elements = response.totalElements
      //this.products = response.content;
    },
    productDetail() {
      this.$refs['productDetail'].show();
    }
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