<template>
  <b-modal id="rejectionModal" ok-only hide-header hide-footer>
    <b-container>
      <b-row>
        <b-col class="text-right">
          <font-awesome-icon icon="times" class="selectable text-secondary" @click="closeModal"/>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-center">
          <h3>Razón de rechazo</h3>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-area text-center">
          <textarea rows="4" cols="40" placeholder="Escribe tu razón aquí" v-model="rejectionReason" @input="validateInput" class="form-control"></textarea>
          <div v-if="notNull" style="color: red;">Razón obligatoria para rechazo</div>
          <div v-if="error" style="color: red;">¡Los caracteres especiales no son permitidos!</div>
        </b-col>
      </b-row>
      <b-row>
        <b-col v-if="isLoading" class="ml-4 mt-4">
          <p style="display: inline; margin-right:10px">Cargando...</p>
          <b-spinner small variant="dark"></b-spinner>
        </b-col>
        <b-col class="text-right mt-3">
          <b-button variant="danger" @click="submitRejection" :disabled="error || !rejectionReason.trim()">Rechazar</b-button>
        </b-col>
      </b-row>
    </b-container>
  </b-modal>
</template>

<script>
import {regexRejectionReason} from "../../utils/validation/regex";
import RequestsDataChangeService from "../../services/requests-data-change/RequestsDataChangeService";
export default {
  props:{
    selectedRequestId: String

  },
  name: 'RejectionModal',
  data() {
    return {
      isLoading: false,
      error:false,
      notNull: true,
      rejectionReason: ''
    };
  },
  methods: {
    closeModal() {
      this.$bvModal.hide('rejectionModal');
    },
    async submitRejection() {
      if (this.rejectionReason.trim() && !this.error) {
        this.isLoading = true;
        try {
          const requestDataChangePutDTO = {
            idRequestDataChange: this.selectedRequestId,
            status: "Rechazado",
            rejectionReason: this.rejectionReason,
          };


          const response = await RequestsDataChangeService.putRequestDataChangeService(requestDataChangePutDTO);
          this.$emit('rejection-submitted', response);
          this.$bvModal.hide('rejectionModal');
        } catch (error) {
          console.error("Error al rechazar la solicitud de cambio de datos:", error);
        } finally {
          this.isLoading = false;
        }
      }
    },


    validateInput() {
      const regex = regexRejectionReason;
      if (this.rejectionReason.trim().length === 0) {
        this.notNull = true;
        this.error = false;
      } else if (!regex.test(this.rejectionReason)) {
        this.error = true;
        this.notNull = false;
      } else {
        this.error = false;
        this.notNull = false;
      }
    },

    mounted() {
      this.isLoading = false;
    }
  }
};
</script>

<style scoped>
.text-area textarea {
  width: 100%;
  margin-top: 15px;
}
</style>
