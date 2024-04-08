<template>
  <section class="interface">
    <b-modal id="product-detail" size="xl" @show="getProduct" centered>
      <b-row>
        <b-col sm="12" md="6" lg="4">
          <b-row>
            <b-col class="mt-4" lg="12" md="12" sm="12">
              <b-carousel v-model="slide" :interval="5000">
                <b-carousel-slide v-for="(slide, index) in productGallery" :key="index" :img-src="slide.image"/>
              </b-carousel>
            </b-col>
            <b-col class="mt-4 text-center" lg="12" md="12" sm="12">
              <div class="carousel-thumbnails">
                <img v-for="(slide, index) in productGallery" :key="index" :src="slide.image" @click="goToSlide(index)" class="thumbnail"/>
              </div>
            </b-col>
          </b-row>
        </b-col>
        <b-col class="text-center" sm="12" md="6" lg="7">
          <b-row class="mt-4">
            <b-col class="text-center" sm="12" md="12" lg="12"><h3>{{ this.data.productName }}</h3></b-col>
            <b-col class="text-right" sm="11" md="11" lg="11">
              <b-badge :variant="data.status ? 'success' : 'danger'">
                {{ data.status ? 'Habilitado' : 'Deshabilitado' }}
              </b-badge>
            </b-col>
            <b-col class="mt-4" lg="12">
              <b-row>
                <b-col class="text-left" lg="6">
                  <div><b>Categoria: </b>{{ data.category }}</div>
                </b-col>
                <b-col class="text-right" lg="6">
                  <div><b>Subcategoria: </b>{{ data.subcategory }}</div>
                </b-col>
              </b-row>
            </b-col>
            <b-col class="text-left mt-5" lg="12">
              <div>
                <b>Descripción</b>
              </div>
              <div class="mt-3">
                {{ data.description }}
              </div>
            </b-col>
            <b-col class="mt-4">
              <b-row>
                <b-col class="text-left" lg="6">
                  <div><b>Precio: </b>{{ data.price }}</div>
                </b-col>
                <b-col class="text-right" lg="6">
                  <div><b>Stock: </b>{{ data.amount }}</div>
                </b-col>
              </b-row>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
    </b-modal>
  </section>
</template>
<script>
import ProductManagementService from "@/services/product-management/ProductManagementService";


export default {
  props: {
    idProduct: String
  },
  data() {
    return {
      productGallery: [],
      data: Object,
      slide:0
    }

  },
  methods: {
    async getProduct() {
      const response = await ProductManagementService.getProduct({idProduct: this.idProduct})
      console.log(response)
      this.productGallery = response.data.productGallery
      console.log(this.productGallery)
      this.data = response.data
    },
    goToSlide(index) {
      this.slide = index;
    }
  },
}
</script>
<style>
.carousel-thumbnails {
  justify-content: center;
}

.thumbnail {
  width: 50px; /* Ajusta según tus necesidades */
  height: auto; /* Ajusta según tus necesidades */
  margin: 0 5px; /* Ajusta según tus necesidades */
  cursor: pointer;
}
</style>