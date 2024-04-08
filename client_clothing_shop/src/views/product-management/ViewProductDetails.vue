

<template>
<h1>Detalles</h1>
</template>
<script>
import ProductManagementService from "@/services/product-management/ProductManagementService";
export default{
  props:{
    idProduct: {
      type: String,
      required: true
    }
  },
  methods:{
    async getProduct(){
      const response = await ProductManagementService.getProduct({idProduct:this.idProduct})
      console.log(response)
    },
    method(){

    }
  },
  mounted() {
    this.getProduct()
  }
}
</script>
<style>

</style>