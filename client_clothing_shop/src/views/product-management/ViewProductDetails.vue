

<template>
<h1>Detalles</h1>
</template>
<script>
export default{
  props:{
    idProduct: {
      type: String,
      required: true
    }
  },
  methods:{
    method(){
      console.log(this.idProduct)
    }
  },
  mounted() {
    this.method()
  }
}
</script>
<style>

</style>