<template>
  <section class="interface">
    <b-row class="mb-4">
      <b-col class="text-center mt-4">
        <h1>Registrar solicitud de edición de producto</h1>
      </b-col>
    </b-row>
    <b-form @submit.prevent="onSubmit()">
      <b-row>
        <b-col class="" lg="6">
          <b-col>
            <b-form-group label="Imagen Principal:" label-for="principal-image">
              <b-form-file id="principal-image" @change="handleImageUpload"  name="principal-image"
                           v-validate="'image:2000000|image|image_size'" accept="image/*"></b-form-file>
              <span style="color: red;">{{ errors.first('principal-image') }}</span>
            </b-form-group>
            <b-col class="preview-container">
              <div v-if="imageUrl===null" class="text-center d-flex align-items-center mt-4 principal-image">
                <div class="placeholder-content">
                  Preview
                </div>
              </div>
              <div v-else class="text-center mt-4 principal-image">
                <img :src="imageUrl" width="250px" height="300px"/>

              </div>
            </b-col>
          </b-col>
          <b-col class="mt-4">
            <b-form-group label="Selecciona hasta 4 imágenes">
              <b-form-file v-on:change="handleImageUpload2" v-validate="'image:2000000|image|image_size'" name="images"
                           accept="image/*"></b-form-file>
              <span style="color: red;">{{ errors.first('images') }}</span>
            </b-form-group>

            <b-col class="preview-container">
              <div v-if="imagePreviews.length === 0" class="placeholder d-flex align-items-center text-center">
                <div class="placeholder-content">Preview</div>
              </div>
              <div v-else v-for="(imagePreview, index) in imagePreviews" :key="index" class="image-preview">
                <img :src="imagePreview" alt="Preview"/>
                <b-button class="delete-button" @click="removeImage(index)">❌</b-button>
              </div>
            </b-col>
          </b-col>
        </b-col>
        <b-col lg="6">
          <b-row>
            <b-col>
              <b-form-group label="Nombre" label-for="name">
                <b-form-input
                    name="name"
                    v-validate="'required|alpha_spaces|product_name_max'"
                    v-model="formData.productName"
                    id="name"
                ></b-form-input>
                <span style="color: red;">{{ errors.first('name') }}</span>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col>
              <b-form-group label="Categoria" label-for="category-select" class="font-weight-bold">
                <b-form-select name="category" v-model="formData.category" v-validate="'required'" id="category-select" @change="updateSubcategories">
                  <option v-for="(category, index) in categories" :key="index" :value="category.category">
                    {{ category.category }}
                  </option>
                </b-form-select>
                <span style="color: red;">{{ errors.first('category') }}</span>
              </b-form-group>
            </b-col>
            <b-col>
              <b-form-group label="Subcategoria" label-for="subcategory-select">
                <b-form-select name="subcategory" v-model="formData.subcategory" v-validate="'required'" id="subcategory-select">
                  <option v-for="(subcategory, index) in filteredSubcategories" :key="index" :value="subcategory.subcategory">
                    {{ subcategory.subcategory }}
                  </option>
                </b-form-select>
                <span style="color: red;">{{ errors.first('subcategory') }}</span>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col>
              <b-form-group label="Descripción: " label-for="description">
                <b-form-textarea name="description" v-model="formData.description" v-validate="'required|alpha_spaces'"
                                 id="description"></b-form-textarea>
              </b-form-group>
              <span style="color: red;">{{ errors.first('description') }}</span>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col>
              <b-form-group label="Precio: " label-for="price">
                <b-form-input name="price" id="price" v-model="formData.price" v-validate="'required'"
                              type="number"></b-form-input>
              </b-form-group>
              <span style="color: red;">{{ errors.first('price') }}</span>
            </b-col>
            <b-col>
              <b-form-group label="Stock:" label-for="stock">
                <b-form-input name="stock" id="stock" v-model="formData.amount" v-validate="'required'"
                              type="number"></b-form-input>
                <span style="color: red;">{{ errors.first('stock') }}</span>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col>
              <b-form-group label="Estado del producto" label-for="status">
                <b-form-select name="status" id="status" v-model="formData.status" v-validate="'required'"
                               :options="optionsStatus"></b-form-select>
                <span style="color: red;">{{ errors.first('status') }}</span>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col class="text-right">
              <b-button variant="dark" class="btn-success mr-2" type="submit">Solicitar Edición</b-button>
              <b-button variant="outline-dark" class="btn-cancel">Cancelar</b-button>
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
import ProductService from "@/services/product/ProductService";

export default {

  props: {
    idProduct: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      imageUrl: null,
      selectedImages: [],
      imagePreviews: [],
      formData: {
        idProduct:'',
        productName: '',
        amount: 0,
        status: false,
        category: '',
        subcategory:'',
        description: '',
        price: 0,
        productGallery: []
      },
      i: 0,
      filteredSubcategories: [],
      newImages: null,
      newPrincipalImage: null,
      optionsStatus: [
        {value: true, text: "Habilitado"},
        {value: false, text: "Deshabilitado"}
      ],
      categories: null,
      subcategories: []
    }
  },
  methods: {
    onSubmit() {
      this.$validator.validate().then(async valid => {
        if (!valid) {
          showWarningToast("Completar los requisitos")
        } else {
          const response = await ProductManagementService.putProduct(this.formData)
          if(response){
            showSuccessToast("Producto editado")
          }
        }
      })
    },
    handleImageUpload(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = async (e) => {
          this.imageUrl = e.target.result
          this.newPrincipalImage = file
          const response = await CloudinaryService.uploadImage(this.newPrincipalImage)
          if (response) {
            this.formData.productGallery[0].image = response.data.data
          }
        };
        reader.readAsDataURL(file);
      }
    },
    handleImageUpload2(event) {
      const files = event.target.files;
      const totalImages = this.formData.productGallery.length
      if (files.length + this.imagePreviews.length > 4) {
        showWarningToast("No puedes cargar más de 4 imágenes");
        return;
      }
      if (files.length === 0 && this.imagePreviews.length === 0) {
        showWarningToast("Debes cargar al menos una imagen");
        return;
      }

      const reader = new FileReader();
      reader.onload = async (e) => {
        this.imagePreviews.push(e.target.result);
        const response = await CloudinaryService.uploadImage(files[0])
        if (response) {
          if(totalImages >= 2) {
            this.formData.productGallery.push({image: response.data.data});
          }
        }
      };
      reader.readAsDataURL(files[0]);
    },
    removeImage(index) {
      this.imagePreviews.splice(index, 1);
      if (this.formData.productGallery.length > 2) {
        this.formData.productGallery.splice(index, 1);
      }
      if (this.imagePreviews.length === 0) {
        showWarningToast("Debes cargar al menos una imagen");
      }
    },

    async getDetailProduct() {
      this.showOverlay()
      const response = await ProductManagementService.getProduct({idProduct: this.idProduct})
      this.productGallery = response.data.productGallery
      this.formData = response.data
      this.imageUrl = this.formData.productGallery[0].image
      for (let i = 1; i < this.formData.productGallery.length; i++) {
        this.imagePreviews.push(this.formData.productGallery[i].image);
      }
      this.showOverlay()
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
      this.filteredSubcategories = this.subcategories.filter(subcategory => subcategory.category === this.formData.category);
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

.principal-image {
  width: 250px;
  height: 300px;
  background-color: #fff;
  border: 1px solid #c0bebe;
  margin-right: 10px;
  margin-bottom: 10px;
  justify-content: center;
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