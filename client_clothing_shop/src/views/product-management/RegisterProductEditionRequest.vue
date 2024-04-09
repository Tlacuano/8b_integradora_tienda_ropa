<template>
<section class="interface">
  <b-row class="mb-4">
    <b-col class="text-center mt-4">
      <h1>Registrar Solicitud de Producto</h1>
    </b-col>
  </b-row>
  <b-row>
    <b-col class="" lg="6">
      <b-form>
        <b-form-group label="Imagen Principal:" label-for="principal-image">
          <b-form-file id="principal-image" v-model="file" @input="handleImageUpload" accept="image/*"></b-form-file>
          <b-col class="text-center mt-4">
            <img :src="imageUrl" width="250px" height="300px"/>
          </b-col>
        </b-form-group>
      </b-form>
    </b-col>
    <b-col  lg="6">
      <b-row>
        <b-col>
          <b-form-group label="Nombre" label-for="name">
            <b-form-input
                id="name"
            ></b-form-input>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-form-group label="Categoria" label-for="category-select">
            <b-form-select id="category-select"></b-form-select>
          </b-form-group>
        </b-col>
        <b-col>
          <b-form-group label="Subcategoria" label-for="subcategory-select">
            <b-form-select id="subcategory-select"></b-form-select>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-form-group label="Descripción: " label-for="description">
            <b-form-textarea id="description"></b-form-textarea>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row>
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
      <b-row>
        <b-col>
          <b-form-group label="Estado del producto" label-for="status">
            <b-form-select id="status"></b-form-select>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-right"><b-button variant="dark" class="btn-success mr-2">Solicitar</b-button><b-button variant="outline-dark" class="btn-cancel">Cancelar</b-button></b-col>
      </b-row>
    </b-col>
  </b-row>
  <b-row></b-row>
</section>
</template>
<script>
export default {
  props:{
    idProduct:{
      type:String,
      required:true
    }
  },
  data(){
    return{
      imageUrl:null
    }
  },
  methods:{
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
    }
  },
  mounted() {
    this.method()
  }
}
</script>
<style>
.btn-success{
  width: 150px;
}
.btn-cancel{
  width: 150px;
}
</style>