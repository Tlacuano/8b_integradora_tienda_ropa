<template>
  <b-modal id="returnRequestModal" hide-header hide-footer centered>
    <b-container>
      <b-row>
        <b-col class="text-right">
          <font-awesome-icon icon="times" class="selectable text-secondary" @click="closeModal"/>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-center">
          <h3>Solicitud de devolución</h3>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-area text-center">
          <textarea rows="4" cols="40" placeholder="Escribe tu razón aquí" v-model="returnReason" class="form-control"></textarea>
        </b-col>
      </b-row>
      <b-row class="mt-3">
        <b-col class="text-center">
          <b-form-file v-model="selectedFile" @input="previewImage" placeholder="Subir foto" drop-placeholder="Arrastra una foto aquí"></b-form-file>
          <b-img v-if="imagePreview" :src="imagePreview" fluid class="mt-3"></b-img>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-right mt-3">
          <b-button variant="danger" @click="submitReturnRequest">Enviar solicitud</b-button>
        </b-col>
      </b-row>
    </b-container>
  </b-modal>
</template>

<script>
export default{
  name : 'ReturnRequestModal',
  props:{
    orderNumber: String
  },
  data(){
    return{
      returnReason: '',
      selectedFile: null,
      imagePreview: null
    }
  },
  methods:{
    openModal() {
      this.$bvModal.show('returnRequestModal');
    },
    submitReturnRequest(){
      this.closeModal();
    },
    previewImage(event) {
      const file = event.target.files[0];
      if (file) {
        this.imagePreview = URL.createObjectURL(file);
      } else {
        this.imagePreview = null;
      }
    }
  }
}
</script>
<style scoped>
.text-area textarea {
  width: 100%;
  margin-top: 15px;
}
</style>