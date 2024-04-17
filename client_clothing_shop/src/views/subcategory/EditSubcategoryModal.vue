<template>
  <section>
    <b-modal id="editSubcategoryModal" hide-header hide-footer centered size="lg" @show="chargeData">
      <b-card class="custom-card">
        <b-row >
          <b-col class="text-center">
            <h3>Editar subcategoría</h3>
          </b-col>
        </b-row>
        <b-row class="mt-3">
          <b-col>
            <b-form>
              <b-form-group
                  label="Nombre de la subcategoría"
                  label-for="subcategory"
              >
                <b-form-input
                    id="subcategory"
                    :placeholder="subcategory.subcategory"
                    v-model="form.subcategory"
                    v-validate="'required|alpha_spaces|min:5|name_max'"
                    name="subcategory"
                />
                <span v-show="errors.has('subcategory')" class="text-danger">{{ errors.first('subcategory') }}</span>
              </b-form-group>

              <b-form-group
                  label="Imagen de la subcategoría"
                  label-for="image"
              >
                <b-form-file
                    id="image"
                    v-model="newImage"
                    accept="image/jpeg, image/png, image/jpg"
                    :state="Boolean(newImage)"
                    placeholder="Seleccione una imagen"
                    @input="handleFileUpload"
                    name="image"
                    v-validate="'image|mimes:jpeg,jpg,png|image_size'"
                />
                <span v-show="errors.has('image')" class="text-danger">{{ errors.first('image') }}</span>
              </b-form-group>

              <b-form-group
                  label="Categoría"
                  label-for="category"
              >
                <b-form-select
                    id="category"
                    v-model="form.category"
                    :options="categories"
                    value-field="idCategory"
                    text-field="category"
                    name="category"
                    v-validate="'required'"
                />
                <span v-show="errors.has('category')" class="text-danger">{{ errors.first('category') }}</span>
              </b-form-group>
            </b-form>
          </b-col>
          <b-col class="text-center align-self-center">
            <div class="image-container">
              <b-img
                  v-if="imgPreview || subcategory.image"
                  :src="imgPreview ? imgPreview : subcategory.image"
                  thumbnail
                  class="image-preview"
              />
              <div v-else class="no-image-message">No se ha seleccionado ninguna imagen</div>
            </div>
          </b-col>
        </b-row>
        <b-row class="mt-3 justify-content-center">
          <b-button variant="dark" @click="editSubcategory" class="w-25 mx-5" style="border-radius: 0.5rem">Guardar</b-button>
          <b-button variant="dark" @click="closeModal" class="w-25 mx-5" style="border-radius: 0.5rem; background-color: red; border-color: red;">Cancelar</b-button>
        </b-row>
      </b-card>
    </b-modal>
  </section>
</template>

<script>
import Vue from "vue";
import CategoriesService from "../../services/category/CategoryService";
import CloudinaryService from "../../services/cloudinary/CloudinaryService";
import SubcategoriesService from "../../services/subcategory/SubcategoryService";
import {showInfoAlert, showWarningToast} from "@/components/alerts/alerts";

export default Vue.extend({
  name: "EditSubcategoryModal",
  props: {
    subcategory: Object
  },
  data() {
    return {
      form: {
        idSubcategory: null,
        subcategory: null,
        image: null,
        category: null,
        status: true
      },
      categories: [],
      imgPreview: null,
      newImage: null
    };
  },
  methods: {
    async editSubcategory() {
      await this.$validator.validateAll().then(async (result) => {
        if (result) {
          await showInfoAlert(
              "¿Estás seguro de editar la subcategoría?",
              "Se guardaran los cambios",
              "Sí, guardar",
              async () => {
                if (this.newImage) {
                  const payload = this.newImage;
                  const imageUrl = await CloudinaryService.uploadImage(payload);

                  if (imageUrl.status === 200) {
                    this.form.image = imageUrl.data.data;
                  } else {
                    showWarningToast("Error al editar la subcategoría", "No se pudo editar la subcategoría");
                  }
                } else {
                  this.form.image = this.subcategory.image;
                }

                const payload = {
                  idSubcategory: this.subcategory.idSubcategory,
                  subcategory: this.form.subcategory,
                  image: this.form.image,
                  idCategory: this.form.category,
                  status: this.form.status
                };

                const response = await SubcategoriesService.putSubcategoryService(payload);

                if (response && response.status === 201) {
                  this.clean();
                  this.$emit("subcategory-edited");
                  this.$bvModal.hide("editSubcategoryModal");
                } else {
                  showWarningToast("Error al editar la subcategoría", "No se pudo editar la subcategoría");
                }
              }
          );
        }
      });
    },

    async getCategories() {
      const pagination = {
        page: 1,
        size: 100
      }
      const response = await CategoriesService.getPageCategoriesService(pagination);
      this.categories = response.data.content;
    },

    handleFileUpload() {
      this.imgPreview = URL.createObjectURL(this.newImage);
    },

    closeModal() {
      this.clean();
      this.$bvModal.hide("editSubcategoryModal");
    },

    clean() {
      this.newImage = null;
      this.imgPreview = null;
    },

    chargeData() {
      this.form.subcategory = this.subcategory.subcategory;
      for (let i = 0; i < this.categories.length; i++) {
        if (this.categories[i].category === this.subcategory.category) {
          this.form.category = this.categories[i].idCategory;
          break;
        }
      }
    }
  },
  mounted() {
    this.getCategories();
  }
})
</script>

<style scoped>
.custom-card {
  background-color: #f8f9fa;
  padding: 10px;
  border-radius: 10px;
}
.image-preview {
  border: 2px solid #ced4da;
  border-radius: 5px;
  max-height: 20rem;
}
.image-container {
  position: relative;
}
.no-image-message {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #6c757d;
  font-style: italic;
}
</style>