<template>
  <section class="interface">
    <b-row>
      <b-col class="text-center">
        <h1>Subcategorías</h1>
      </b-col>
    </b-row>

    <b-row class="mt-4" align-h="between">
      <b-col cols="12" lg="4">
        <b-form-group>
          <div class="position-relative">
            <b-form-input id="search" type="text" placeholder="Buscar..."  class="pr-5"/>
            <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
          </div>
        </b-form-group>
      </b-col>
      <b-col cols="auto" class="text-right">
        <b-button variant="dark" @click="openAddSubcategoryModal">Registrar</b-button>
      </b-col>
    </b-row>

    <b-row class="mt-4 container-subcategories">
      <b-col >
        <b-row></b-row>
        <b-row>
          <b-col cols="auto" v-for="subcategory in subcategories" :key="subcategory.id">
            <b-card
                class="highlight-on-hover mb-2"
                :img-src="subcategory.image"
                img-bottom
                style="max-width: 14rem;"
            >
              <b-card-text class="d-flex justify-content-between align-items-center" style="max-height: 0.1rem;">
                <h5>{{subcategory.subcategory}}</h5>
                <b-dropdown
                        variant="link-dark"
                        toggle-class="text-decoration-none"
                        no-caret
                        style="margin-bottom: 0.5rem;"
                >
                  <template v-slot:button-content>
                        <font-awesome-icon icon="ellipsis-v" />
                  </template>
                  <b-dropdown-item>Editar</b-dropdown-item>
                  <b-dropdown-item @click="changeStatusSubcategory(subcategory)">
                    <div v-if="!subcategory.status">
                      Habilitar
                    </div>
                    <div v-else>
                      Deshabilitar
                    </div>
                  </b-dropdown-item>
                </b-dropdown>
              </b-card-text>
            </b-card>
          </b-col>
        </b-row>
      </b-col>
      <b-col cols="auto" class="align-self-start" style="margin-top: 1rem">
          <font-awesome-icon icon="fa-solid fa-filter" />
      </b-col>
    </b-row>

    <b-row>
      <b-col>
        <b-pagination
          v-model="objectPagination.page"
          :total-rows="objectPagination.elements"
          :per-page="objectPagination.size"
          aria-controls="my-table"
        />
      </b-col>
    </b-row>
    <AddSubcategoryModal @subcategory-added="refreshSubcategories"/>
  </section>
</template>

<script>
import Vue from "vue";
import SubcategoriesService from "../../services/subcategory/SubcategoryService"
import {showInfoAlert} from "@/components/alerts/Alerts";

export default Vue.extend({
  name: "SubcategoryManagement",
  components: {
    AddSubcategoryModal: () => import("@/views/subcategory/AddSubcategoryModal.vue")
  },
  data() {
    return {
      objectPagination: {
        page: 1,
        size: 24,
        elements: 0
      },
      subcategories: []
    }
  },
  methods: {
    async getPageSubcategories() {
      this.showOverlay()
      const response = await SubcategoriesService.getPageSubcategoriesService(this.objectPagination);
      this.showOverlay()
      this.objectPagination.elements = response.data.totalElements;
      this.subcategories = response.data.content;
    },

    async changeStatusSubcategory(subcategory) {
      await showInfoAlert(
          "¿Estás seguro?",
          "¿Desea cambiar el estado de la subcategoría?",
          "Sí, cambiar",
          async () => {
            await SubcategoriesService.putStatusSubcategoryService(subcategory.idSubcategory);
          }
      )
    },

    openAddSubcategoryModal() {
      this.$nextTick(() => {
        this.$bvModal.show("addSubcategoryModal");
      })
    },

    showOverlay() {
      this.$store.dispatch('changeStatusOverlay')
    },

    refreshSubcategories() {
      this.getPageSubcategories();
    }
  },
  mounted() {
    this.getPageSubcategories();
  }
})
</script>

<style>
.container-subcategories {
  border: 1px solid black;
  margin-left: 0.1rem;
  margin-right: 0.1rem;
  border-radius: 0.5rem;
}
</style>