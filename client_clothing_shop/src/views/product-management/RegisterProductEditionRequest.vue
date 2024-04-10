<template>
  <section class="interface">
    <b-row class="mb-4">
      <b-col class="text-center mt-4">
        <h1>Registrar solicitud de edición de producto</h1>
      </b-col>
    </b-row>
    <b-form @submit="handleSubmit">
      <b-row>
        <b-col class="" lg="6">
          <b-col>
            <b-form-group label="Imagen Principal:" label-for="principal-image">
              <b-form-file id="principal-image" v-model="file" @input="handleImageUpload"
                           accept="image/*"></b-form-file>
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
            <b-form-group label="Selecciona hasta 5 imágenes">
              <b-form-file
                  v-on:change="handleImageUpload2"
                  :state="Boolean(selectedImages.length)"
                  accept="image/*"
                  multiple
              ></b-form-file>
            </b-form-group>

            <b-col class="preview-container">
              <div v-if="imagePreviews.length === 0" class="placeholder d-flex align-items-center text-center">
                <div class="placeholder-content">
                  Preview
                </div>
              </div>
              <div
                  v-else
                  v-for="(imagePreview, index) in imagePreviews"
                  :key="index"
                  class="image-preview"
              >
                <img :src="imagePreview" alt="Preview"/>
              </div>
            </b-col>
          </b-col>
        </b-col>
        <b-col lg="6">
          <b-row>
            <b-col>
              <b-form-group label="Nombre" label-for="name">
                <b-form-input
                    id="name"
                ></b-form-input>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col>
              <b-form-group label="Categoria" label-for="category-select" class="font-weight-bold">
                <b-form-select id="category-select"></b-form-select>
              </b-form-group>
            </b-col>
            <b-col>
              <b-form-group label="Subcategoria" label-for="subcategory-select">
                <b-form-select id="subcategory-select"></b-form-select>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col>
              <b-form-group label="Descripción: " label-for="description">
                <b-form-textarea id="description"></b-form-textarea>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col>
              <b-form-group label="Precio: " label-for="price">
                <b-form-input id="price" type="number"></b-form-input>
              </b-form-group>
            </b-col>
            <b-col>
              <b-form-group label="Stock:" label-for="stock">
                <b-form-input id="stock" type="number"></b-form-input>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col>
              <b-form-group label="Estado del producto" label-for="status">
                <b-form-select id="status"></b-form-select>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col class="text-right">
              <b-button variant="dark" class="btn-success mr-2">Solicitar Edición</b-button>
              <b-button variant="outline-dark" class="btn-cancel">Cancelar</b-button>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
    </b-form>
  </section>
</template>
<script>
import {showWarningToast} from "@/components/alerts/alerts";

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
      imagePreviews: []
    }
  },
  methods: {
    handleImageUpload() {
      const file = this.file;
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.imageUrl = e.target.result;
        };
        reader.readAsDataURL(file);
      } else {
        this.imageUrl = null;
      }
    },
    handleImageUpload2(event) {
      const files = event.target.files;
      const imagePreviews = [];
      if (files.length < 2) {
        showWarningToast("Selecciona minimo 2 imagenes")
        return;
      } else if (files.length > 4) {
        showWarningToast("Contenido máximo 4 imagenes")
        return;
      }
      for (let i = 0; i < files.length; i++) {
        const reader = new FileReader();
        reader.onload = (e) => {
          imagePreviews.push(e.target.result);
          if (imagePreviews.length === files.length) {
            this.imagePreviews = imagePreviews;
          }
        };
        reader.readAsDataURL(files[i]);
      }
    }

  },
  mounted() {
    this.method()
  }
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
</style>