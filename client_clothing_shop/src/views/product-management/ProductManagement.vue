

<template>
  <section class="interface">
    <b-row>
      <b-col class="text-center">
        <h1>Gestión de productos</h1>
      </b-col>
    </b-row>

    <b-row class="mt-4" align-h="between">
      <b-col cols="12" lg="4">
        <b-form-group>
          <div class="position-relative">
            <b-form-input @input="getProductByUser" v-model="search" id="search" type="text" placeholder="Buscar producto por nombre..." class="pr-5"></b-form-input>
            <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
          </div>
        </b-form-group>
      </b-col>
      <b-col cols="auto" class="text-right">
        <b-button variant="dark" @click="registerProductRequest">Registrar</b-button>
      </b-col>
    </b-row>
    <b-row class="container-products">
      <b-col>
        <b-row class="mt-5" v-if="statusNotData">
          <b-col class="text-center m-auto" >
            <h3>No tienes productos registrados</h3>
          </b-col>
        </b-row>
        <b-row>
          <b-col class="mt-3" md="6" lg="4" sm="12" v-for="item in items" :key="item.idProduct">
            <b-card no-body class="card highlight-on-hover">
              <b-row no-gutters class="body-card">
                <b-col cols="12" sm="4" md="4" lg="4" class="image-container">
                  <b-img :src="item.productGallery"  class="img-fluid"></b-img>
                </b-col>
                <b-col cols="11" sm="6" md="6" lg="6" class="body-text">
                  <b-row>
                    <b-col>
                      <h4 class="mb-0 text-truncate">{{item.productName}}</h4>
                    </b-col>
                  </b-row>
                  <b-row class="mt-2">
                    <b-col >
                      <strong>Categoria: <p style="display: inline">{{item.category}}</p></strong>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <strong>Subcategoria: <p style="display: inline">{{item.subcategory}}</p></strong>
                    </b-col>
                  </b-row>
                  <b-row class="mt-3">
                    <b-col cols="9" sm="10" md="8" lg="9" >
                      <p>Precio <strong style="display: inline">${{item.price}}</strong></p>
                    </b-col>
                    <b-col cols="3" sm="2" md="4" lg="3">
                      <b-badge :variant="item.status ? 'success' : 'danger'">
                        {{ item.status ? 'Habilitado' : 'Deshabilitado' }}
                      </b-badge>
                    </b-col>
                  </b-row>
                </b-col>
                <b-col cols="1" sm="1" md="1" lg="1" class="mt-2 dropdown">
                  <b-dropdown
                      variant="link-dark"
                      toggle-class="text-decoration-none"
                      no-caret
                  >
                    <template v-slot:button-content>
                      <font-awesome-icon icon="ellipsis-v"/>
                    </template>
                    <b-dropdown-item @click="viewProductDetail(item.idProduct)">Ver detalles</b-dropdown-item>
                    <b-dropdown-item @click="registerProductEditionRequest(item.idProduct)">Editar</b-dropdown-item>
                    <b-dropdown-item @click="putStatusProduct(item.idProduct)">{{item.status ? 'Deshabilitar' : 'Habilitar'}}</b-dropdown-item>

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
            v-model="objectPagination.page"
            :total-rows="objectPagination.elements"
            :per-page="objectPagination.size"
            aria-controls="my-table"
        ></b-pagination>
      </b-col>
    </b-row>
    <ViewProductDetails @modalClosed="handleRequestSuccess" :id-product="selectProductId"/>
  </section>
</template>
<script>

  import ProductManagementService from "@/services/product-management/ProductManagementService";
  import {showSuccessToast, showWarningToast} from "@/components/alerts/alerts";
  import ViewProductDetails from "@/views/product-management/ViewProductDetails.vue";

  export default {
    name: "ProductManagement",
    components: {ViewProductDetails},
    data() {
      return {
        objectPagination:{
          page: 1,
          size: 6,
          elements: 0,
        },
        items:[],
        mainImage:[],
        selectProductId:null,
        search:null,
        statusNotData: false,
      };
    },
    methods:{
      async getProductByUser(){
        if(this.search === null || this.search === ""){
          this.showOverlay()
          const email = this.$store.getters.getEmail
          const response = await ProductManagementService.getProductByUser(this.objectPagination,email)
          if(response.data.content.length === 0) {
            this.statusNotData = true
            showWarningToast("No se encontraron productos", "No tienes productos registrados")
          }
          if(response){
            this.items = response.data.content
            this.mainImage = this.items
            for (let i = 0; i < this.mainImage.length; i++) {
              for (let j = 0; j < this.mainImage[i].productGallery.length; j++) {
                if (this.mainImage[i].productGallery[j].status === 'Principal') {
                  this.mainImage[i].productGallery = this.mainImage[i].productGallery[j].image;
                  break;
                }
              }
            }
            this.objectPagination.elements = response.totalElements

          }else{
            this.showOverlay()
            showWarningToast("Ocurrio un error inesperado", "No se pudieron obtener los productos")
          }
          this.showOverlay()
        }else{
          const payload ={
            productName: this.search,
            userEmail:this.$store.getters.getEmail
          }
          const response = await ProductManagementService.getProductByProductName(this.objectPagination,payload)
          this.items = response.data.content
          this.mainImage = this.items
          for (let i = 0; i < this.mainImage.length; i++) {
            for (let j = 0; j < this.mainImage[i].productGallery.length; j++) {
              if (this.mainImage[i].productGallery[j].status === 'Principal') {
                this.mainImage[i].productGallery = this.mainImage[i].productGallery[j].image;
                break;
              }
            }
          }
          this.objectPagination.elements = response.totalElements
        }

      },
      async putStatusProduct(idProduct){
        const response = await ProductManagementService.putStatusProduct(idProduct)
        if(response){
          showSuccessToast("Estado del producto actualizado")
          this.getProductByUser()
              .then(product => {
              })
              .catch(error => {
              });
        }else{
          showWarningToast("Ocurrio un error inesperado", "La actualización del estado fallo")
        }
      },
      viewProductDetail(idProduct){
        this.selectProductId = idProduct
        this.$nextTick(() => {
          this.$bvModal.show("product-detail");
        });
      },
      registerProductEditionRequest(idProduct){
        this.$router.push({name:'RegisterProductEditionRequest',params:{idProduct:idProduct}})
      },
      registerProductRequest(){
        this.$router.push({name:'RegisterProductRequest'})
      },
      handleRequestSuccess() {
        this.getProductByUser();
      },
      showOverlay(){
        this.$store.dispatch('changeStatusOverlay');
      },
    },
    mounted() {
      this.getProductByUser()
    },
    watch:{
      objectPagination(){
        this.getProductByUser()
      }
    }
  }
</script>

<style>
.container-products {
  height: calc(100vh - 270px);
  overflow-x: hidden !important;
}
.body-card{
  height: 100%;
}

.image-container {
  position: relative;
  width: 100%;
  height: auto;
  overflow: hidden;
}

.image-container img {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: auto;
  object-fit: cover; /* Esto asegura que la imagen cubra todo el contenedor */
}
.body-text{
  font-size: 14px;
  margin-left: 20px;
  margin-top: 10px;
}
@media (max-width: 576px) { /* Estilo para pantallas pequeñas */
  .image-container {
    height: 50%;
  }

  .image-container img {
    position: relative;
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  .body-text {
    margin-left: 10px;
    margin-top: 20px;
  }
  .dropdown{
    margin-left:-10px
  }
}

</style>