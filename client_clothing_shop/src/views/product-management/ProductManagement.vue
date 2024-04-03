

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
            <b-form-input id="search" type="text" placeholder="Buscar..." class="pr-5"></b-form-input>
            <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
          </div>
        </b-form-group>
      </b-col>
      <b-col cols="auto" class="text-right">
        <b-button variant="dark">Registrar</b-button>
      </b-col>
    </b-row>
    <b-row>
      <b-col class="container-card mb-4" md="6" lg="4" sm="12" v-for="item in items" :key="item">
        <b-card no-body class="card highlight-on-hover">
          <b-row no-gutters class="body-card">
            <b-col cols="12" sm="4" md="4" lg="4" class="image-container">
              <b-img :src="item.img"  class="img-fluid"></b-img>
            </b-col>
            <b-col cols="11" sm="6" md="6" lg="6" class="body-text">
              <b-row>
                <b-col>
                  <h4>{{item.title}}</h4>
                </b-col>
              </b-row>
              <b-row class="mt-3">
                <b-col >
                  <strong>{{item.category}}: <p style="display: inline">{{item.category}}</p></strong>
                </b-col>
              </b-row>
              <b-row>
                <b-col>
                  <strong>{{item.subcategory}}: <p style="display: inline">{{item.subcategory}}</p></strong>
                </b-col>
              </b-row>
              <b-row class="mt-5">
                <b-col cols="9" sm="10" md="10" lg="10" >
                  <p>{{item.price}}: <strong style="display: inline">${{item.price}}</strong></p>
                </b-col>
                <b-col cols="3" sm="2" md="2" lg="2">
                  <b-badge variant="success">{{item.status}}</b-badge>
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
                <b-dropdown-item>Ver detalles</b-dropdown-item>
                <b-dropdown-item>Editar</b-dropdown-item>
                <b-dropdown-item>Desabilitar</b-dropdown-item>
              </b-dropdown>
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
  </section>
</template>
<script>

  import ProductManagementService from "@/services/product-management/ProductManagementService";

  export default {
    name: "ProductManagement",
    data() {
      return {
        objectPagination:{
          pageNumber: 1,
          pageSize: 24,
          elements: 0,
        },
        items:[
          {id:1, title:"productname", category:"categoria", subcategory:"subcategoria",price:"precio",status:"estado",img:"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRnFBG_0oT9hDXEXoGwi5JAlMn_cREKdYAb9sJ0bGgfYQ&s"},
          {id:2, title:"productname", category:"categoria", subcategory:"subcategoria",price:"precio",status:"estado",img:"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRnFBG_0oT9hDXEXoGwi5JAlMn_cREKdYAb9sJ0bGgfYQ&s"},
          {id:3, title:"productname", category:"categoria", subcategory:"subcategoria",price:"precio",status:"estado",img:"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRnFBG_0oT9hDXEXoGwi5JAlMn_cREKdYAb9sJ0bGgfYQ&s"},
        ]
      };
    },
    methods:{
      async getProductByUser(){
        const email = this.$store.getters.getEmail
        const response = await ProductManagementService.getProductByUser(this.objectPagination,email)
        this.objectPagination.elements = response.elements
        this.items = response.content
        console.log(response)
      }
    },
    mounted() {
      this.getProductByUser()
    }
  }
</script>

<style>
.card{
  height: 195px;
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
  .card{
    height: 450px;
  }
  .dropdown{
    margin-left:-10px
  }
}

</style>