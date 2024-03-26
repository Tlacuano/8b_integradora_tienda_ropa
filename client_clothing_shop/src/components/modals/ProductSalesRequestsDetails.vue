<template>
  <b-modal id="productDetail" @show="getByIdRequestSellProduct">
    <h1>Detalles del producto</h1>
    <h1>Product ID {{productId}}</h1>
  </b-modal>
</template>
<script>
import ProductSalesRequestsService from '@/services/admin/adminService';
export default {
  props: {
    productId: String,
  },
  name: "ProductSalesRequestsDetails",
  data() {
    return {
      product: {},
    };
  },
  methods: {
    async getByIdRequestSellProduct(){
      const response = await ProductSalesRequestsService.getByIdProductSalesRequest(this.productId)
      console.log(response)
      this.product = response;
    }
  },


}
</script>
<style scoped>

</style>