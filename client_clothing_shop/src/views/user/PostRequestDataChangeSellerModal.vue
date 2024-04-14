<template>
  <b-modal id="fiscal-information-change-modal" hide-footer hide-header centered @shown="fetchSellerInformation">
    <b-overlay :show="loading">
      <b-container>
        <b-row>
          <b-col class="text-right">
            <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('fiscal-information-change-modal')" />
          </b-col>
        </b-row>
        <b-row class="mt-3">
          <b-col class="text-center">
            <h1>Solicitud de Cambio de Información Fiscal</h1>
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
                />
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
                />
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
                />
              </b-form-group>
            </b-col>
          </b-row>
          <b-row>
            <b-col>
              <b-form-group label="Identificación de Imagen" label-for="input-image-id">
                <b-form-input
                    id="input-image-id"
                    v-model="formData.imageIdentification"
                    placeholder="Ingrese URL de nueva imagen si desea cambiarla"
                />
              </b-form-group>
            </b-col>
          </b-row>
          <b-row class="my-4">
            <b-col>
              <b-button type="submit" class="main-button px-5">Solicitar cambio de datos</b-button>
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
      loading: false
    }
  },
  methods: {
    async fetchSellerInformation() {
      this.loading = true;
      try {
        const email = this.$store.getters.getEmail;
        const response = await SellerService.getSellerInformationByEmailService({ email });
        this.originalData = response.data;
        this.formData = { ...this.originalData };
      } catch (error) {
        showInfoAlert("Error", "No se pudo cargar la información del vendedor");
      } finally {
        this.loading = false;
      }
    },
    async submitChangeRequest() {
      this.loading = true;
      try {
        const changes = {};
        Object.keys(this.formData).forEach(key => {
          if (this.formData[key] !== this.originalData[key]) {
            changes[key] = this.formData[key];
          }
        });
        const email = this.$store.getters.getEmail;
        await RequestsDataChangeService.postRequestDataChangeService({
          email,
          newInformation: changes
        });
        showSuccessToast("Solicitud enviada", "Tu solicitud de cambio ha sido enviada exitosamente.");
        this.$bvModal.hide('fiscal-information-change-modal');
      } catch (error) {
        showInfoAlert("Error", "No se pudo enviar la solicitud de cambio");
      } finally {
        this.loading = false;
      }
    }
  },
  computed: {
    ...mapGetters(['showOverlay'])
  }
}
</script>


<style scoped>
.main-button {
  background-color: var(--primary);
  color: white;
}
</style>
