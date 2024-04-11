<template>
  <b-modal id="returnRequestModal" hide-header hide-footer centered @hidden="resetForm">
    <b-container>
      <form @submit.prevent="submitReturnRequest">
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
            <textarea rows="4" cols="40" placeholder="Escribe tu razón aquí" v-model="returnReason" v-validate="'required|max:100'" name="returnReason" class="form-control"></textarea>
            <span class="text-danger" v-show="errors.has('returnReason')">{{ errors.first('returnReason') }}</span>
          </b-col>
        </b-row>
        <b-row class="mt-3">
          <b-col class="text-center">
            <b-form-file  v-model="selectedFile"
                          @change="previewImage($event.target.files[0])"
                          placeholder="Subir foto"
                          drop-placeholder="Arrastra una foto aquí"
                          accept="image/jpeg"
                          v-validate="'required|image|image_size:2000000'"
                          name="image"></b-form-file>
            <b-img v-if="imagePreview" :src="imagePreview" fluid class="mt-3"></b-img>
            <span class="text-danger" v-show="errors.has('image')">{{ errors.first('image') }}</span>
          </b-col>
        </b-row>
        <b-row>
          <b-col class="text-right mt-3">
            <b-button type="submit" variant="danger" :disabled="!validated">Enviar solicitud</b-button>
          </b-col>
        </b-row>
      </form>
    </b-container>
  </b-modal>
</template>

<script>
import RequestsReturnProductService from "../../services/request-return-product/RequestsReturnProductService";
import CloudinaryService from "@/services/cloudinary/CloudinaryService";
import { showSuccessToast, showWarningToast } from "@/components/alerts/alerts";

export default {
  name: 'ReturnRequestModal',
  props: {
    orderNumber: String
  },
  data() {
    return {
      returnReason: '',
      selectedFile: null,
      imagePreview: null
    }
  },
  methods: {
    resetForm() {
      this.returnReason = '';
      this.selectedFile = null;
      this.imagePreview = null;
    },
    openModal() {
      this.$bvModal.show('returnRequestModal');
    },
    closeModal() {
      this.$bvModal.hide('returnRequestModal');
    },
    async submitReturnRequest() {
      if (!this.selectedFile) {
        showWarningToast("Error", "Por favor, selecciona una imagen.");
        return;
      }

      try {
        const uploadResponse = await CloudinaryService.uploadImage(this.selectedFile);
        if (uploadResponse.data.error) {
          showWarningToast("Error", "Error al cargar la imagen.");
          return;
        }
        const imageUrl = uploadResponse.data.data;

        const requestData = {
          orderNumber: this.orderNumber,
          returnReason: this.returnReason,
          image: imageUrl
        };
        const response = await RequestsReturnProductService.postRequestReturnProductService(requestData);
        showSuccessToast("Éxito", "Solicitud de devolución creada.");
        this.closeModal();
      } catch (error) {
        console.log(error.response.data);
        if (error.response && error.response.status === 400) {
          showWarningToast("Advertencia", "Ya existe una solicitud de devolución pendiente para este producto.");
        } else {
          showWarningToast("Error", "Error al enviar la solicitud de devolución.");
        }
      }
    },


    previewImage(event) {
      const file = event;
      if (!file) {
        this.imagePreview = null;
        return;
      }

      if (!file.type.startsWith('image/jpeg')) {
        showWarningToast("Error", "Por favor, selecciona una imagen válida.");
        this.selectedFile = null;
        this.imagePreview = null;
        return;
      }

      if (file.size > 2 * 1024 * 1024) {
        showWarningToast("Error", "La imagen no debe superar los 2 MB.");
        this.selectedFile = null;
        this.imagePreview = null;
        return;
      }

      this.imagePreview = URL.createObjectURL(file);
    }
  },
  computed: {
    validated() {
      return this.returnReason && this.selectedFile;
    }
  },
}
</script>

<style scoped>
.text-area textarea {
  width: 100%;
  margin-top: 15px;
}
</style>