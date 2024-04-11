<template>
  <section>
    <b-modal id="formBecomeSellerModal" centered hide-footer hide-header size="lg">
      <b-card class="custom-card">
        <b-row>
          <b-col>
            <h3 class="text-center">Solicitar ser vendedor</h3>
          </b-col>
        </b-row>
        <b-row class="mt-3">
          <b-col>
            <b-form @submit.prevent="postRequestBecomeSeller">
              <b-form-group label="CURP" label-for="curp">
                <b-form-input
                    id="curp"
                    v-model="form.curp"
                    v-validate="'required|curp_length|curp'"
                    name="curp"
                />
                <span v-show="errors.has('curp')" class="text-danger">{{ errors.first('curp') }}</span>
              </b-form-group>
              <b-form-group label="Tipo de persona">
                <b-form-radio-group v-model="personType">
                  <b-form-radio value="physical">Persona física</b-form-radio>
                  <b-form-radio value="moral">Persona moral</b-form-radio>
                </b-form-radio-group>
              </b-form-group>
              <b-form-group label="RFC" label-for="rfc">
                <b-form-input
                    v-if="personType === 'physical'"
                    id="rfc"
                    v-model="form.taxIdentificationNumber"
                    v-validate="'rfc_length|rfc'"
                    name="rfc"
                />
                <b-form-input
                    v-else
                    id="rfc"
                    v-model="form.taxIdentificationNumber"
                    v-validate="'rfc_length_moral|rfc_moral'"
                    name="rfc"
                />
                <span v-show="errors.has('rfc')" class="text-danger">{{ errors.first('rfc') }}</span>
              </b-form-group>
              <b-form-group label="Teléfono de contacto" label-for="phone">
                <b-form-input
                    id="phone"
                    v-model="form.secondaryPhoneNumber"
                    v-validate="'required|phone'"
                    name="phone"
                />
                <span v-show="errors.has('phone')" class="text-danger">{{ errors.first('phone') }}</span>
              </b-form-group>
              <b-form-group label="Imagen de identificación" label-for="image">
                <b-form-file
                    id="image"
                    v-model="form.imageIdentification"
                    accept="image/jpeg, image/png, image/jpg"
                    :state="Boolean(form.imageIdentification)"
                    placeholder="Seleccione una imagen"
                    @input="handleFileUpload"
                    name="image"
                    v-validate="'required|image|mimes:jpeg,jpg,png|image_size'"
                />
                <span v-show="errors.has('image')" class="text-danger">{{ errors.first('image') }}</span>
              </b-form-group>
            </b-form>
          </b-col>
          <b-col class="text-center">
            <div class="image-container">
              <b-img v-if="imgPreview" :src="imgPreview" thumbnail class="image-preview" />
              <div v-else class="no-image-message">No se ha seleccionado ninguna imagen</div>
            </div>
          </b-col>
        </b-row>
        <b-row class="mt-3 justify-content-center">
          <b-button variant="dark" @click="postRequestBecomeSeller" class="w-25 mx-5" style="border-radius: 0.5rem;">Enviar solicitud</b-button>
          <b-button variant="dark" @click="closeModal" class="w-25 mx-5" style="border-radius: 0.5rem; background-color: red; border-color: red;">Cancelar</b-button>
        </b-row>
      </b-card>
    </b-modal>
  </section>
</template>

<script>
import Vue from "vue";
import CloudinaryService from "@/services/cloudinary/CloudinaryService";
import RequestsBecomeSellerService from "@/services/requests-become-seller/RequestsBecomeSellerService";
import {showInfoAlert, showSuccessToast, showWarningToast} from "@/components/alerts/alerts";

export default Vue.extend({
  name: "FormBecomeSellerModal",
  data() {
    return {
      form: {
        curp: "",
        taxIdentificationNumber: "",
        secondaryPhoneNumber: "",
        imageIdentification: null,
        privacyPolicyAgreement: true,
      },
      imgPreview: null,
      personType: "physical",
    }
  },
  methods: {
    async postRequestBecomeSeller() {
      await this.$validator.validateAll().then(async (result) => {
        if (result) {
          await showInfoAlert(
              "¿Estás seguro de enviar la solicitud?",
              "Una vez enviada, no podrás modificarla",
              "Sí, enviar",
              async () => {
                const imageUrl = await CloudinaryService.uploadImage(this.form.imageIdentification);

                if (imageUrl.status === 200) {
                  const userSellerInformation = {
                    curp: this.form.curp,
                    taxIdentificationNumber: this.form.taxIdentificationNumber,
                    secondaryPhoneNumber: this.form.secondaryPhoneNumber,
                    imageIdentification: imageUrl.data.data,
                    privacyPolicyAgreement: this.form.privacyPolicyAgreement,
                  }

                  const payload = {
                    email: this.$store.getters.getEmail,
                    userSellerInformation: userSellerInformation,
                  }

                  const response = await RequestsBecomeSellerService.postRequestBecomeSellerService(payload);

                  if (response === 200) {
                    this.closeModal();
                    showSuccessToast("Solicitud enviada correctamente");
                  } else {
                    showWarningToast("Error al enviar la solicitud");
                  }
                } else {
                  showWarningToast("Error al subir la imagen");
                }
              }
          )
        }
      })
    },

    handleFileUpload() {
      const file = this.form.imageIdentification;
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.imgPreview = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    },

    closeModal() {
      this.clean();
      this.$bvModal.hide("formBecomeSellerModal");
    },

    clean() {
      this.form = {
        curp: "",
        taxIdentificationNumber: "",
        secondaryPhoneNumber: "",
        imageIdentification: null,
        privacyPolicyAgreement: true,
      };
      this.imgPreview = null;
    },
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
  max-height: 25rem;
  margin-top: 30%;
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
  margin-top: 50%;
}
</style>