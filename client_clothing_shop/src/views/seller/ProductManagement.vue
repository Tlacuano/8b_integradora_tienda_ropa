

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
        <b-pagination
            v-model="objetPagination.page"
            :total-rows="objetPagination.elements"
            :per-page="objetPagination.size"
            aria-controls="my-table"
        ></b-pagination>
      </b-col>
    </b-row>
  </section>
</template>
<script setup>
  export default {
    name: "ProductManagement",
    data() {
      return {
        objetPagination:{
          page: 1,
          size: 24,
          elements: 0,
        },

      };
    },
    methods:{
    },
    mounted() {
    }
  }
</script>

<style scoped>

</style>