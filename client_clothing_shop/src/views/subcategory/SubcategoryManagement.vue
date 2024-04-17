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
            <b-form-input @input="getPageSubcategories" v-model="search" id="search" type="text" placeholder="Buscar..."  class="pr-5"/>
            <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
          </div>
        </b-form-group>
      </b-col>
      <b-col cols="auto" class="text-right">
        <b-button variant="dark" @click="openAddSubcategoryModal">Registrar</b-button>
      </b-col>
    </b-row>

    <b-row v-if="!subcategories" class="container-subcategories align-content-center justify-content-center">
      <b-col class="text-center">
        <h3>No hay subcategorías registradas</h3>
      </b-col>
    </b-row>

    <b-row v-else class="container-subcategories align-content-center justify-content-center">
        <b-row class="">
          <b-col cols="auto" v-for="(subcategory, index) in subcategories" :key="index">
            <b-card
                class="highlight-on-hover mb-2"
            >
              <b-card-text class="d-flex justify-content-between align-items-center" style="max-height: 0.1rem;">
                <div class="d-flex align-items-center">
                  <h5 class="mr-3 text-truncate">{{ subcategory.subcategory }}</h5>
                </div>
                <div class="d-flex align-items-center">
                  <font-awesome-icon
                      icon="fa-solid fa-circle"
                      :class="subcategory.status ? 'text-success' : 'text-danger'"
                      class="mr-2"
                  />
                  <b-dropdown
                      variant="link-dark"
                      toggle-class="text-decoration-none"
                      no-caret
                      style="margin-bottom: 0.5rem;"
                  >
                    <template v-slot:button-content>
                      <font-awesome-icon icon="ellipsis-v" />
                    </template>
                    <b-dropdown-item @click="openEditSubcategoryModal(subcategory)">Editar</b-dropdown-item>
                    <b-dropdown-item @click="changeStatusSubcategory(subcategory)">
                      <div v-if="!subcategory.status">
                        Habilitar
                      </div>
                      <div v-else>
                        Deshabilitar
                      </div>
                    </b-dropdown-item>
                  </b-dropdown>
                </div>
              </b-card-text>
              <b-card-img
                  :src="subcategory.image"
                  alt="Image"
                  class="mb-2 image-card"
              />
            </b-card>
          </b-col>
        </b-row>
    </b-row>

    <b-row>
      <b-col>
        <b-pagination
          v-model="objectPagination.page"
          :total-rows="objectPagination.elements"
          :per-page="objectPagination.size"
          aria-controls="my-table"
          @change="getPageSubcategories"
        ></b-pagination>
      </b-col>
    </b-row>
    <AddSubcategoryModal @subcategory-added="refreshSubcategories"/>
    <EditSubcategoryModal @subcategory-edited="refreshSubcategories" :subcategory="selectedSubcategory"/>
  </section>
</template>

<script>
import Vue from "vue";
import SubcategoriesService from "../../services/subcategory/SubcategoryService"
import {showInfoAlert, showSuccessToast, showWarningToast} from "@/components/alerts/Alerts";

export default Vue.extend({
  name: "SubcategoryManagement",
  components: {
    AddSubcategoryModal: () => import("@/views/subcategory/AddSubcategoryModal.vue"),
    EditSubcategoryModal: () => import("@/views/subcategory/EditSubcategoryModal.vue")
  },
  data() {
    return {
      objectPagination: {
        page: 1,
        size: 4,
        elements: 0
      },
      subcategories: [],
      selectedSubcategory: {},
      search: null,
    }
  },
  methods: {
    async getPageSubcategories(page) {
      this.objectPagination.page = page;
      if (this.search === null || this.search === "") {
        this.showOverlay()
        const response = await SubcategoriesService.getPageSubcategoriesService(this.objectPagination);
        this.showOverlay()
        this.objectPagination.elements = response.data.totalElements;
        this.subcategories = response.data.content;
      } else {
        const payload = {
          subcategory: this.search
        }
        const response = await SubcategoriesService.getPageSubcategoriesBySubcategoryService(payload, this.objectPagination);
        this.objectPagination.elements = response.data.totalElements;
        this.subcategories = response.data.content;
      }
    },

    async changeStatusSubcategory(subcategory) {
      await showInfoAlert(
          "¿Estás seguro?",
          "¿Desea cambiar el estado de la subcategoría?",
          "Sí, cambiar",
          async () => {
            const response = await SubcategoriesService.putStatusSubcategoryService(subcategory.idSubcategory);
            if (response) {
              showSuccessToast("Estado de la subcategoría actualizado correctamente");
              await this.getPageSubcategories();
            } else {
              showWarningToast("Error al actualizar el estado de la subcategoría");
            }
          }
      )
    },

    openAddSubcategoryModal() {
      this.$nextTick(() => {
        this.$bvModal.show("addSubcategoryModal");
      })
    },

    openEditSubcategoryModal(subcategory) {
      this.selectedSubcategory = subcategory;

      this.$nextTick(() => {
        this.$bvModal.show("editSubcategoryModal");
      })
    },

    showOverlay() {
      this.$store.dispatch('changeStatusOverlay')
    },

    refreshSubcategories() {
      this.getPageSubcategories();
    },
  },
  mounted() {
    this.getPageSubcategories();
  }
})
</script>

<style>
.container-subcategories {
  margin-top: 1rem;
  margin-bottom: 1rem;
  overflow-x: hidden;
  overflow-y: auto;
}

.image-card {
  max-height: 350px;
  max-width: 380px;
  object-fit: cover;
}

.highlight-on-hover {
  transition: box-shadow 0.3s ease-in-out;
}

.highlight-on-hover:hover {
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);
}

@media (min-width: 992px) {
  .container-subcategories .b-col {
    max-width: 20%;
  }
}

@media (max-width: 991px) and (min-width: 768px) {
  .container-subcategories .b-col {
    max-width: 33.33%;
  }
}

@media (max-width: 767px) {
  .container-subcategories .b-col {
    max-width: 50%;
  }
}

@media (max-width: 576px) {
  .container-subcategories .b-col {
    max-width: 100%;
  }
}

.b-card-text h5 {
  font-size: 1rem;
  color: #333;
}

.search-icon {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
}
</style>