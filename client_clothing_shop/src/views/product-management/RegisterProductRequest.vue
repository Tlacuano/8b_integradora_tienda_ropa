<template>
  <section class="interface">
    <b-row class="mb-4">
      <b-col class="text-center">
        <h1>Registrar solicitud de producto</h1>
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
                    v-validate="'required|alpha_spaces|product_name_max|product_name_min'"
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
                <b-form-select  name="category" v-model="category" v-validate="'required|alpha'" id="category-select" @change="updateSubcategories">
                  <option v-for="(category, index) in categories" :key="index" :value="category.category">
                    {{ category.category }}
                  </option>
                </b-form-select>
                <span style="color: red;">{{ errors.first('category') }}</span>
              </b-form-group>
            </b-col>
            <b-col>
              <b-form-group label="Subcategoria" label-for="subcategory-select">
                <b-form-select :disabled="!category" name="subcategory" v-model="formData.subcategory" v-validate="'required'" id="subcategory-select">
                  <option v-for="(subcategory, index) in filteredSubcategories" :key="index" :value="subcategory.idSubcategory">
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
                <b-form-textarea name="description" v-model="formData.description" v-validate="'required|alpha_spaces|description_min|description_max'"
                                 id="description"></b-form-textarea>
              </b-form-group>
              <span style="color: red;">{{ errors.first('description') }}</span>
            </b-col>
          </b-row>
          <b-row>
            <b-col>
              <b-form-group label="Precio $MXN: " label-for="price" >
                <b-form-input name="price" id="price" v-model.number="formData.price" v-validate="'required|negative_numbers|not_zero'"
                              type="number" step="any"></b-form-input>
              </b-form-group>
              <span style="color: red;">{{ errors.first('price') }}</span>
            </b-col>
            <b-col>
              <b-form-group label="Stock:" label-for="stock">
                <b-form-input name="stock" id="stock" v-model.number="formData.amount" v-validate="'required|negative_numbers|not_zero|only_enters'"
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
                    v-validate="'image'"
                    accept="image/*"></b-form-file>
                <span style="color: red;">{{ errors.first('images') }}</span>
              </b-form-group>

              <b-col class="preview-container">
                <div v-if="imagePreviews.length === 0" class="placeholder d-flex align-items-center text-center">
                  <div class="placeholder-content">Prev...</div>
                </div>
                <div v-else v-for="(imagePreview, index) in imagePreviews" :key="index" class="image-preview">
                  <img :src="imagePreview" alt="Preview"/>
                  <b-button class="delete-button" @click="removeImage(index)">❌</b-button>
                </div>
              </b-col>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="12" class="mt-5">
          <b-row class="text-right">
            <b-col>
              <b-button variant="dark" class="btn-success mr-2" type="submit">Solicitar Registro</b-button>
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

  data() {
    return {
      imagePreviews: [],
      formData: {
        productName: '',
        email:this.$store.getters.getEmail,
        amount: 0,
        subcategory:'',
        description: '',
        price: 0,
        productGallery: []
      },
      filteredSubcategories: [],
      categories: null,
      subcategories: [],
      category:null
    }
  },
  methods: {
    onSubmit() {
      this.$validator.validate().then(async valid => {
        if (this.formData.productGallery.length === 0 || this.formData.productGallery.length < 2) {
          showWarningToast("Debes cargar al menos dos imagenes");
          return;
        }
        if(this.formData.productGallery.length > 5){
          showWarningToast("No puedes cargar más de 5 imágenes");
          return;
        }
        if (!valid) {
          showWarningToast("Completar los requisitos")
        } else {
          this.showOverlay()
          for (let i= 0; i < this.formData.productGallery.length; i++){
            const uploadImg = await CloudinaryService.uploadImage(this.formData.productGallery[i])
            if(uploadImg){
              this.formData.productGallery[i] = null
              this.formData.productGallery[i] = uploadImg.data.data
            }else{
              showSuccessToast("Ocurrio un error al subir las imagenes, intentalo de nuevo")
              return
            }
          }
          const response = await ProductManagementService.postProduct(this.formData)
          if(response){
            showSuccessToast("Solcitud enviada correctamente, en breve se revisará tu solicitud")
            setTimeout(() => {
              window.location.reload()
            }, 2000)
          }else{
            showWarningToast("Ocurrio un error inesperado", "La solicitud no se pudo enviar")
            setTimeout(() => {
              window.location.reload()
            }, 2000)
          }
          this.showOverlay()
        }
      })
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
      if(files[0].size > 2000000){
        showWarningToast("La imagen no puede pesar más de 2MB");
        return;
      }

      const reader = new FileReader();
      reader.onload = async (e) => {
        this.imagePreviews.push(e.target.result);
        this.formData.productGallery.push(files[0])
      };
      reader.readAsDataURL(files[0]);
    },
    removeImage(index) {
      this.imagePreviews.splice(index, 1);
      this.formData.productGallery.splice(index, 1);
      if (this.formData.productGallery.length > 2) {
        this.formData.productGallery.splice(index, 1);
      }
      if (this.imagePreviews.length === 0) {
        showWarningToast("Debes cargar al menos una imagen");
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
      this.filteredSubcategories = this.subcategories
          .filter(subcategory => subcategory.category === this.category)
          .map(subcategory => ({ idSubcategory: subcategory.idSubcategory, subcategory: subcategory.subcategory }));
    }
  },
  computed: {
    required() {
      return required
    },
  },
  mounted() {
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