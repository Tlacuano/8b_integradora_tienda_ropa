<template>
  <section class="interface">
    <b-row class="mb-4">
      <b-col class="text-center">
        <h1>Registrar solicitud de edición de producto</h1>
      </b-col>
    </b-row>
    <b-form @submit.prevent="onSubmit()">
      <b-row>
        <b-col lg="6">
          <b-row>
            <b-col>
              <b-form-group label="Nombre" label-for="name">
                <b-form-input
                    name="name"
                    v-validate="'required|alpha_spaces|product_name_max|min'"
                    v-model="formData.productName"
                    id="name"
                ></b-form-input>
                <span style="color: red;">{{ errors.first('name') }}</span>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row>
            <b-col>
              <b-form-group label="Categoria" label-for="category-select" class="font-weight-bold">
                <b-form-select name="category" v-model="category" v-validate="'required'" id="category-select"
                               @change="updateSubcategories">
                  <option v-for="(category, index) in categories" :key="index" :value="category.category">
                    {{ category.category }}
                  </option>
                </b-form-select>
                <span style="color: red;">{{ errors.first('category') }}</span>
              </b-form-group>
            </b-col>
            <b-col>
              <b-form-group label="Subcategoria" label-for="subcategory-select">
                <b-form-select name="subcategory" v-model="formData.subcategory" v-validate="'required'"
                               id="subcategory-select">
                  <option v-for="(subcategory, index) in filteredSubcategories" :key="index"
                          :value="subcategory.idSubcategory">
                    {{ subcategory.subcategory }}
                  </option>
                </b-form-select>
                <span style="color: red;">{{ errors.first('subcategory') }}</span>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row>
            <b-col>
              <b-form-group label="Descripción: " label-for="description">
                <b-form-textarea name="description" v-model="formData.description"
                                 v-validate="'required|alpha_spaces|description_min|description_max'"
                                 id="description"></b-form-textarea>
              </b-form-group>
              <span style="color: red;">{{ errors.first('description') }}</span>
            </b-col>
          </b-row>
          <b-row>
            <b-col>
              <b-form-group label="Precio: " label-for="price">
                <b-form-input name="price" id="price" v-model.number="formData.price" v-validate="'required'"
                              type="number"></b-form-input>
              </b-form-group>
              <span style="color: red;">{{ errors.first('price') }}</span>
            </b-col>
            <b-col>
              <b-form-group label="Stock:" label-for="stock">
                <b-form-input name="stock" id="stock" v-model.number="formData.amount" v-validate="'required'"
                              type="number"></b-form-input>
                <span style="color: red;">{{ errors.first('stock') }}</span>
              </b-form-group>
            </b-col>
          </b-row>


        </b-col>
        <b-col class="" lg="6">
          <b-row>
            <b-col>
              <b-form-group label="Selecciona hasta 5 imágenes">
                <b-form-file
                    v-on:change="handleImageUpload2"
                    name="images"
                    placeholder=""
                    browse-text="Búscar"
                    accept="image/*"></b-form-file>
                <span style="color: red;">{{ errors.first('images') }}</span>
              </b-form-group>

              <b-col class="preview-container">
                <div v-if="imagePreviews.length === 0" class="placeholder d-flex align-items-center text-center">
                  <div class="placeholder-content">Preview</div>
                </div>
                <div v-else v-for="(imagePreview, index) in imagePreviews" :key="index" class="image-preview">
                  <img :src="imagePreview" alt="Preview" @click="openImage(index)"/>
                  <template v-if="index !== 0">
                    <b-button class="delete-button" @click="removeImage(index)">❌</b-button>
                  </template>
                  <template v-else>
                    <span class="principal-indicator">Principal</span>
                  </template>
                </div>
              </b-col>
            </b-col>
            <b-modal v-model="showImageModal" no-close-on-esc hide-footer hide-header>
              <b-row>
                <b-col class="text-center">
                  <h3>Vista previa de la imagen</h3>
                </b-col>
              </b-row>
              <b-row>
                <b-col class="text-center">
                  <img :src="selectedImage" alt="Selected Image" style="max-width: 100%; max-height: 100%;">
                </b-col>
              </b-row>

              <b-row>
                <b-col cols="3" class="mt-3">
                  <div v-if="loading">
                    <b-spinner variant="primary" label="Spinning"></b-spinner>
                  </div>
                  <div v-else>
                    <h5>
                      <b-badge variant="light">
                        {{ formData.productGallery[this.index].status === 'Principal' ? 'Principal' : (formData.productGallery[this.index].status === 'Habilitada' ? 'Habilitada' : 'Deshabilitada') }}
                      </b-badge>
                    </h5>
                  </div>
                </b-col>
                <b-col cols="9" class="text-right mt-3">
                  <template v-if="formData.productGallery[this.index].status !== 'Principal'">
                    <b-button class="mr-4" variant="dark" @click="changeStatus()">Cambiar Estado</b-button>
                    <b-button :disabled="loading" variant="outline-dark" @click="closeImageModal">Cerrar</b-button>
                  </template>
                  <template v-else>
                    <span class="principal-indicator-text">La imagen principal no se puede deshabilitar</span>
                    <b-button class="mt-4" :disabled="loading" variant="outline-dark" @click="closeImageModal">Cerrar</b-button>
                  </template>

                </b-col>
              </b-row>
            </b-modal>
          </b-row>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="12" class="mt-5">
          <b-row class="text-right">
            <b-col>
              <b-button variant="dark" class="btn-success mr-2" type="submit">Solicitar Edición</b-button>
              <b-button variant="outline-dark" class="btn-cancel" @click="$router.push({name: 'product-management'})">Cancelar</b-button>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
    </b-form>
  </section>
</template>
<script>
import {showSuccessToast, showWarningToast} from "@/components/alerts/alerts";
import ProductManagementService from "@/services/product-management/ProductManagementService";
import CategoryService from "@/services/category/CategoryService";
import SubcategoryService from "@/services/subcategory/SubcategoryService";
import {required} from "vee-validate/dist/rules.esm";
import CloudinaryService from "@/services/cloudinary/CloudinaryService";

export default {

  props: {
    idProduct: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      showImageModal: false,
      index: 0,
      category: null,
      selectedImage: '',
      imagePreviews: [],
      formData: {
        idProduct: '',
        productName: '',
        amount: 0,
        subcategory: '',
        description: '',
        price: 0,
        productGallery: [
          {
            idImage: '',
            image: '',
            status: ''
          }
        ]
      },
      filteredSubcategories: [],
      newImages: [
        {
          idImage: '',
          image: '',
          status: ''
        }
      ],
      categories: null,
      subcategories: [],
      loading: false
    }
  },
  methods: {
    onSubmit() {
      let count = 0;
      let principal = 0;
      for (let i = 0; i < this.formData.productGallery.length; i++) {
        if (this.formData.productGallery[i].status === 'Principal') {
          principal++;
        }
        if (this.formData.productGallery[i].status === 'Habilitada') {
          count++;
        }
      }
      if (principal != 1) {
        showWarningToast("Debe haber una imagen principal");
        return;
      }
      if (count < 1) {
        showWarningToast("Debe haber al menos dos imagenes habilitadas");
        return;
      }
      if (count > 4) {
        showWarningToast("No puedes cargar más de 5 imágenes");
        return;
      }
      if (this.formData.productGallery.length === 0 || this.formData.productGallery.length < 2) {
        showWarningToast("Debes cargar al menos dos imagenes");
        return;
      }
      if (this.formData.productGallery.length > 5) {
        showWarningToast("No puedes cargar más de 5 imágenes");
        return;
      }

      this.$validator.validate().then(async valid => {
        if (!valid) {
          showWarningToast("Completar los requisitos");
        } else {
          this.showOverlay();
          for (let i = 0; i < this.formData.productGallery.length; i++) {
            const image = this.formData.productGallery[i];
            if (image instanceof File && !image.$path) {
              const response = await CloudinaryService.uploadImage(this.formData.productGallery[i]);
              if (response) {
                this.formData.productGallery[i] = {
                  image: response.data.data,
                  status: 'Habilitada'
                }
              }else{
                this.showOverlay();
                showWarningToast("Ocurrio un error inesperado", "No se pudo cargar la imagen");
                return;
              }
            }
          }
          const response = await ProductManagementService.putProduct(this.formData)
          if (response) {
            this.showOverlay();
            showSuccessToast("Solicitud de edición enviada correctamente");
            setTimeout(() => {
              this.$router.push({name: 'product-management'});
            }, 2000);
          }else{
            this.showOverlay();
            showWarningToast("Ocurrio un error inesperado", "No se pudo enviar la solicitud de edición");
            setTimeout(() => {
              this.$router.push({name: 'product-management'});
            }, 2000);
          }
          this.showOverlay();
        }
      });
      this.showOverlay();
    },

    closeImageModal() {
      this.showImageModal = false;
    },
    changeStatus() {
      this.loading = true;
      const imagenPrincipalAnterior = this.formData.productGallery[0];
      if (this.formData.productGallery[this.index].status === 'Habilitada') {
        this.formData.productGallery[this.index].status = 'Principal';
      } else if (this.formData.productGallery[this.index].status === 'Principal') {
        this.formData.productGallery[this.index].status = 'Habilitada';
      } else if (this.formData.productGallery[this.index].status === 'Deshabilitada') {
        this.formData.productGallery[this.index].status = 'Habilitada';
        showWarningToast("Imagen Habilitada")
      }


      if (this.formData.productGallery[this.index].status === 'Principal') {
        this.$set(this.formData.productGallery, 0, this.formData.productGallery[this.index]);
        this.$set(this.formData.productGallery, this.index, imagenPrincipalAnterior);
        if (imagenPrincipalAnterior !== undefined) {
          imagenPrincipalAnterior.status = 'Habilitada';
        }
      }
      this.$nextTick(() => {
        this.$forceUpdate();
      });
      this.loading = false;
      this.imagePreviews = this.formData.productGallery.map(image => image.image);
      this.showImageModal = false;
    },
    openImage(index) {
      this.selectedImage = this.imagePreviews[index];
      this.index = index;
      this.showImageModal = true;
    },
    handleImageUpload2(event) {
      const files = event.target.files;
      if (files.length + this.imagePreviews.length > 5) {
        showWarningToast("No puedes cargar más de 5 imágenes");
        return;
      }
      if (files.length === 0 && this.imagePreviews.length === 0) {
        showWarningToast("Debes cargar al menos una imagen");
        return;
      }
      if (files[0].size > 2000000) {
        showWarningToast("La imagen no puede pesar más de 2MB");
        return;
      }

      const reader = new FileReader();
      reader.onload = async (e) => {
        this.imagePreviews.push(e.target.result);
        this.formData.productGallery.push(files[0]);
      };
      reader.readAsDataURL(files[0]);
    },
    removeImage(index) {
      this.formData.productGallery[index].status = 'Deshabilitada';
      showWarningToast("Imagen Deshabilitada");
    },

    async getDetailProduct() {
      this.showOverlay()
      const response = await ProductManagementService.getProduct({idProduct: this.idProduct})
      if(response){
        this.newImages = response.data.productGallery.slice()
        this.category = response.data.category
        this.formData = response.data
        this.formData.productGallery = [];
        for (let i = 0; i < this.newImages.length; i++) {
          if (this.newImages[i].status === 'Principal') {
            this.imagePreviews.unshift(this.newImages[i].image);
            this.formData.productGallery.unshift(this.newImages[i])
          } else {
            this.imagePreviews.push(this.newImages[i].image);
            this.formData.productGallery.push(this.newImages[i])
          }
        }
        delete this.formData.category
        this.showOverlay()
      }else{
        this.showOverlay()
        showWarningToast("Ocurrio un error inesperado", "No se pudo cargar la información del producto")
      }
    },
    async getCategories() {
      const response = await CategoryService.getCategories()
      this.categories = response.data.content
      await this.getSubcategories(5)
    },
    async getSubcategories(pagination) {
      const response = await SubcategoryService.getPageSubcategoriesService(pagination)
      this.subcategories = response.data.content
      this.updateSubcategories()
    },
    showOverlay() {
      this.$store.dispatch('changeStatusOverlay');
    },
    updateSubcategories() {
      this.filteredSubcategories = this.subcategories.filter(subcategory => subcategory.category === this.category);
    }
  },
  computed: {
    required() {
      return required
    },
  },
  mounted() {
    this.getDetailProduct()
    this.getCategories()
  },

}
</script>
<style>
.btn-success {
  width: 150px;
}

.btn-cancel {
  width: 150px;
}

.preview-container {
  display: flex;
  flex-wrap: wrap;
  margin-top: 20px;
  justify-content: center;
}

.image-preview {
  width: 100px;
  height: 100px;
  margin-right: 10px;
  margin-bottom: 10px;
  overflow: hidden;
}

.image-preview img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.principal-image img {
  object-fit: cover;
}

.placeholder {
  width: 100px;
  height: 100px;
  background-color: #fff;
  border: 1px solid #c0bebe;
  margin-right: 10px;
  margin-bottom: 10px;
  justify-content: center;
}

.placeholder-content {
  color: #c0bebe;
}

.delete-button {
  position: absolute;
  top: 0;
  right: 0;
  padding: 5px;
  background-color: rgba(255, 255, 255, 0.5);
  border: none;
  border-radius: 5px;
  color: red;
  cursor: pointer;
  transition: opacity 0.3s ease-in-out;
}

.image-preview {
  position: relative;
  display: inline-block;
  margin: 5px;
}

.image-preview:hover .delete-button {
  opacity: 1;
}
</style>