

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
      <b-col>
        <b-row>
          <b-col class="container-card" lg="4" v-for="item in items" :key="item">
            <b-card no-body class="card highlight-on-hover" style="max-width: 540px;">
              <b-row no-gutters>
                <b-col lg="4">
                  <b-img :src="item.img"  class="img w-100"></b-img>
                </b-col>
                <b-col lg="7">
                  <b-row>
                    <b-col>
                      <h4>{{item.title}}</h4>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <strong>{{item.category}}: <p style="display: inline">{{item.category}}</p></strong>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <strong>{{item.subcategory}}: <p style="display: inline">{{item.subcategory}}</p></strong>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <p>{{item.price}}: <strong style="display: inline">${{item.price}}</strong></p>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <b-badge variant="success">{{item.status}}</b-badge>
                    </b-col>
                  </b-row>
                </b-col>
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
  </section>
</template>
<script>
  export default {
    name: "ProductManagement",
    data() {
      return {
        objectPagination:{
          page: 1,
          size: 24,
          elements: 0,
        },
        items:[
          {id:1, title:"productname", category:"categoria", subcategory:"subcategoria",price:"precio",status:"estado",img:"https://via.placeholder.com/250"},
          {id:2, title:"productname", category:"categoria", subcategory:"subcategoria",price:"precio",status:"estado",img:"https://via.placeholder.com/250"},
          {id:3, title:"productname", category:"categoria", subcategory:"subcategoria",price:"precio",status:"estado",img:"https://via.placeholder.com/250"},
        ]
      };
    },
    methods:{

    },
    mounted() {
    }
  }
</script>

<style>
.img{
  background-size: cover;
  height: 100%;
}
.container-card{
  height: 175px;
}
.card{
  height: 175px;
}

</style>