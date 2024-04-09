<template>
  <section class="interface">
    <b-modal id="product-detail" size="xl" @hide="modalClosed" @show="getProduct" hide-header hide-footer scrollable centered>
      <b-row>
        <b-col class="text-right">
          <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('product-detail')"/>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-center">
          <h3>Detalles del producto</h3>
        </b-col>
      </b-row>
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
            <b-col class="text-right" sm="12" md="12" lg="12">
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
            <b-col class="mt-4" lg="12">
              <b-row>
                <b-col class="text-left" lg="6">
                  <div><b>Precio: </b>{{ data.price }}</div>
                </b-col>
                <b-col class="text-right" lg="6">
                  <div><b>Stock: </b>{{ data.amount }}</div>
                </b-col>
              </b-row>
            </b-col>
            <b-col class="mt-3" lg="12">
              <b-row>
                <b-col class="btn-disable text-right" lg="8"><b-button @click="putStatusProduct(data.idProduct)" variant="outline-dark">
                  {{ data.status ? 'Deshabilitar' : 'Habilitar' }}</b-button></b-col>
                <b-col class="btn-edit text-right" lg="4"><b-button @click="registerProductEditionRequest(data.idProduct)"  variant="dark">Editar</b-button></b-col>
              </b-row>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <hr class="pb-2"/>
          <b-row>
            <b-col class="text-center">
              <h3>Reseñas</h3>
            </b-col>
          </b-row>
          <b-row class="mt-4 ">
            <b-col cols="12" lg="5" class="mb-5">
              <b-row style="font-size: 1.5rem" class="text-center">
                <b-col cols="12">
                  <font-awesome-icon  class="mr-2" v-for="star in 5" :key="`star_${star}`" :icon="'fa-solid fa-star'" />
                  {{reviewStats.countByRating["5"]}}
                </b-col>
                <b-col cols="12">
                  <font-awesome-icon  class="mr-2" v-for="star in 5" :key="`star_${star}`" :icon="star <= 4 ? 'fa-solid fa-star' : 'fa-regular fa-star' " />
                  {{reviewStats.countByRating["4"]}}
                </b-col>
                <b-col cols="12">
                  <font-awesome-icon  class="mr-2" v-for="star in 5" :key="`star_${star}`" :icon="star <= 3 ? 'fa-solid fa-star' : 'fa-regular fa-star' " />
                  {{reviewStats.countByRating["3"]}}
                </b-col>
                <b-col cols="12">
                  <font-awesome-icon  class="mr-2" v-for="star in 5" :key="`star_${star}`" :icon="star <= 2 ? 'fa-solid fa-star' : 'fa-regular fa-star' " />
                  {{reviewStats.countByRating["2"]}}
                </b-col>
                <b-col cols="12">
                  <font-awesome-icon  class="mr-2" v-for="star in 5" :key="`star_${star}`" :icon="star <= 1 ? 'fa-solid fa-star' : 'fa-regular fa-star' " />
                  {{reviewStats.countByRating["1"]}}
                </b-col>
                <b-col cols="12">
                  <hr/>
                </b-col>
                <b-col cols="12">
                  <font-awesome-icon  class="mr-2" v-for="star in 5" :key="`star_${star}`" :icon="star <= reviewStats.averageRating ? 'fa-solid fa-star' : 'fa-regular fa-star' " />
                </b-col>

              </b-row>
            </b-col>

            <b-col cols="12" lg="7" >
              <section v-if="reviews.length > 0" class="reviews-container">
                <b-row v-for="review in reviews" :key="review.idReview" class="highlight-on-hover">
                  <b-col cols="1" class="pr-0 mt-1 mr-0 d-none d-lg-block">
                    <b-avatar
                        :src="review.picture || null"
                        variant="dark"
                        size="2.5rem"
                    />
                  </b-col>
                  <b-col cols="11">
                    <b-row align-h="between">
                      <b-col cols="5" lg="9">
                        <b-row>
                          <b-col>
                            <b class="small">
                              {{ review.fullName }}
                            </b>
                          </b-col>
                        </b-row>
                        <b-row>
                          <b-col>
                            <small>
                              <font-awesome-icon v-for="index in 5" :key="`star_${index}_${review.idReview}`" :icon="index <= review.assessment ? 'fa-solid fa-star' : 'fa-regular fa-star'" />
                            </small>
                          </b-col>
                        </b-row>
                      </b-col>
                      <b-col cols="5" lg="2" class="text-right mt-2">
                        <b-row>
                          <b-col>
                            <small>{{ review.reviewDate }}</small>
                          </b-col>
                        </b-row>
                      </b-col>
                    </b-row>
                    <b-row>
                      <b-col class="mt-3">
                        <small>{{ review.comment }}</small>
                      </b-col>
                    </b-row>
                  </b-col>
                </b-row>
              </section>
              <section v-else>
                <b-row>
                  <b-col class="text-center">
                    <p>No hay reseñas todavía</p>
                  </b-col>
                </b-row>
              </section>

            </b-col>
          </b-row>
        </b-col>
      </b-row>
    </b-modal>
  </section>
</template>
<script>
import ProductManagementService from "@/services/product-management/ProductManagementService";
import {showSuccessToast, showWarningToast} from "@/components/alerts/alerts";


export default {
  props: {
    idProduct: String
  },
  data() {
    return {
      productGallery: [],
      data: Object,
      slide:0,
      reviews:[],
      reviewStats: {
        countByRating: { 1: 0, 2: 0, 3: 0, 4: 0, 5: 0 },
        averageRating: 0,
      },
    }

  },
  methods: {
    async getProduct() {
      const response = await ProductManagementService.getProduct({idProduct: this.idProduct})
      console.log(response)
      this.productGallery = response.data.productGallery
      console.log(this.productGallery)
      this.data = response.data
      if(this.data !==null){
        const getReviews = await ProductManagementService.getReviews({idProduct:this.idProduct})
        console.log(getReviews)
        this.reviews = getReviews.data
      }
    },
    async putStatusProduct(idProduct){
      const response = await ProductManagementService.putStatusProduct(idProduct)
      if(response){
        showSuccessToast("Estado del producto actualizado")
        this.getProduct()
            .then(product => {
              console.log("Éxito");
            })
            .catch(error => {
            });
      }else{
        showWarningToast("Ocurrio un error inesperado", "La actualización del estado fallo")
      }
    },
    registerProductEditionRequest(idProduct){
      this.$router.push({name:'RegisterProductEditionRequest',params:{idProduct:idProduct}})
    },
    modalClosed() {
      this.$emit('modalClosed')
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
  width: 50px;
  height: auto;
  margin: 0 5px;
  cursor: pointer;
}
.btn-disable button{
  width: 150px;
}
.btn-edit button{
  width: 150px;
}
</style>