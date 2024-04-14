<template>
  <b-modal id="fiscal-information-change-modal" hide-footer hide-header centered @shown="fetchSellerInformation" @hidden="resetForm">
    <b-overlay :show="loading">
      <b-container>
        <b-row>
          <b-col class="text-right">
            <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('fiscal-information-change-modal')" />
          </b-col>
        </b-row>
        <b-row class="mt-3">
          <b-col class="text-center">
            <h1 style="margin-bottom: 20px">Solicitud de Cambio de Información Fiscal</h1>
          </b-col>
        </b-row>
        <b-row>
          <b-col class="text-center">
            <img v-if="!file" :src="formData.imageIdentification" alt="Imagen Fiscal Actual" class="img-fluid rounded img-preview">
            <img v-if="file" :src="imagePreview" alt="Nueva Imagen Fiscal" class="img-fluid rounded img-preview">
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <b-form-file
                v-model="file"
                accept="image/png, image/jpeg"
                placeholder="Seleccione una imagen nueva"
                drop-placeholder="Arrastre una imagen aquí"
                @change="handleFileChange"
                class="mt-3"
            ></b-form-file>
          </b-col>
        </b-row>
        <b-form @submit.prevent="submitChangeRequest">
          <b-row class="mt-3">
            <b-col>
              <b-form-group label="RFC (Número de Identificación Fiscal)" label-for="input-tax-id">
                <b-form-input
                    id="input-tax-id"
                    v-model="formData.taxIdentificationNumber"
                    placeholder="Ingrese nuevo RFC si desea cambiarlo"
                    v-validate="'required|rfc'"
                />
                <span class="text-danger">{{ errors.first('input-tax-id') }}</span>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row>
            <b-col>
              <b-form-group label="Teléfono Secundario" label-for="input-secondary-phone">
                <b-form-input
                    id="input-secondary-phone"
                    v-model="formData.secondaryPhoneNumber"
                    placeholder="Ingrese nuevo teléfono secundario si desea cambiarlo"
                    v-validate="'required|phone'"
                />
                <span class="text-danger">{{ errors.first('input-secondary-phone') }}</span>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row>
            <b-col>
              <b-form-group label="CURP" label-for="input-curp">
                <b-form-input
                    id="input-curp"
                    v-model="formData.curp"
                    placeholder="Ingrese nueva CURP si desea cambiarla"
                    v-validate="'required|curp'"
                />
                <span class="text-danger">{{ errors.first('input-curp') }}</span>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row class="my-4">
            <b-col>
              <b-button type="button" class="main-button px-5" @click="confirmSubmission" :disabled="!canSubmit">Solicitar cambio de datos</b-button>
            </b-col>
          </b-row>
        </b-form>
      </b-container>
    </b-overlay>
  </b-modal>
</template>

<script>
import { mapGetters } from 'vuex';
import RequestsDataChangeService from "../../services/requests-data-change/RequestsDataChangeService";
import SellerService from "@/services/seller/sellerService";
import { showSuccessToast, showInfoAlert } from '@/components/alerts/alerts';
import CloudinaryService from "@/services/cloudinary/CloudinaryService";

export default {
  name: 'FiscalInformationChangeModal',
  data() {
    return {
      formData: {
        taxIdentificationNumber: '',
        secondaryPhoneNumber: '',
        curp: '',
        imageIdentification: ''
      },
      originalData: {},
      loading: false,
      file: null,
      image: null,
      error: {
        show: false,
        message: ''
      },
      imagePreview: ''
    }
  },
  methods: {
    async fetchSellerInformation() {
      this.loading = true;
      try {
        const email = this.$store.getters.getEmail;
        const response = await SellerService.getSellerInformationByEmailService(email);
        this.originalData = response;
        this.formData = {
          taxIdentificationNumber: response.taxIdentificationNumber || '',
          secondaryPhoneNumber: response.secondaryPhoneNumber || '',
          curp: response.curp || '',
          imageIdentification: response.imageIdentification || ''
        };
      } catch (error) {
        console.error("Error while fetching data:", error);
        showInfoAlert("Error", "No se pudo cargar la información del vendedor", "Aceptar");
      } finally {
        this.loading = false;
      }
    },
    handleFileChange(event) {
      const fileInput = event.target;
      if (fileInput.files.length > 0) {
        const file = fileInput.files[0];


        if (!file.type.match('image.*')) {
          this.error = { show: true, message: 'Formato no válido, selecciona una imagen.' };
          return;
        }


        if (file.size > 2097152) { // 2MB
          this.error = { show: true, message: 'El archivo es demasiado grande. Máximo permitido es 2MB.' };
          return;
        }

        const reader = new FileReader();
        reader.onload = (e) => {
          this.imagePreview = e.target.result;
          this.file = file;
          this.error = { show: false, message: '' };
        };
        reader.readAsDataURL(file);
      }
    },
    confirmSubmission() {
      if (this.canSubmit) {
        showInfoAlert(
            "Confirmar Cambios",
            "¿Estás seguro de que quieres hacer estos cambios?",
            "Sí, solicitar cambio de datos",
            () => this.submitChangeRequest()
        );
      }
    },
    async submitChangeRequest() {
      this.loading = true;
      try {
        if (this.file) {
          const uploadResponse = await CloudinaryService.uploadImage(this.file);
          if (uploadResponse.status === 200 && uploadResponse.data.data) {
            this.formData.imageIdentification = uploadResponse.data.data;
          } else {
            showInfoAlert("Error", "No se pudo cargar la imagen.", "Aceptar");
            this.loading = false;
            return;
          }
        }
        const changes = this.calculateChanges();
        if (Object.keys(changes).length === 0) {
          showInfoAlert("Información", "No has realizado ningún cambio.", "Aceptar");
          this.loading = false;
          return;
        }
        const email = this.$store.getters.getEmail;
        await RequestsDataChangeService.postRequestDataChangeService({
          email,
          newInformation: changes
        });
        showSuccessToast("Solicitud enviada", "Tu solicitud de cambio ha sido enviada exitosamente.");
        this.$bvModal.hide('fiscal-information-change-modal');
      } catch (error) {
        showInfoAlert("Error", "No se pudo enviar la solicitud de cambio", "Aceptar");
      } finally {
        this.loading = false;
      }
    },
    calculateChanges() {
      const changes = {};
      Object.keys(this.formData).forEach(key => {
        if (this.formData[key] !== this.originalData[key] && this.formData[key]) {
          changes[key] = this.formData[key];
        }
      });
      return changes;
    },
    resetForm() {
      this.formData = { taxIdentificationNumber: '', secondaryPhoneNumber: '', curp: '', imageIdentification: '' };
      this.file = null;
      this.imagePreview = null;
      this.fetchSellerInformation();
    },
  },
  computed: {
    ...mapGetters(['showOverlay']),
    canSubmit() {
      const hasChanges = this.formData.taxIdentificationNumber !== this.originalData.taxIdentificationNumber ||
          this.formData.secondaryPhoneNumber !== this.originalData.secondaryPhoneNumber ||
          this.formData.curp !== this.originalData.curp ||
          this.file;
      return hasChanges && !this.error.show;
    }
  },
}
</script>


<style scoped>
.main-button {
  background-color: var(--primary);
  color: white;
}
.img-preview {
  width: 300px;
  height: 300px;
  object-fit: contain;
  border-radius: 50%;
  border: 1px solid #ccc;
}

</style>
