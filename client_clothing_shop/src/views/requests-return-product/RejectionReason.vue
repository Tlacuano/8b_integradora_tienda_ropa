<template>
  <b-modal id="rejectionReasonModal" ok-only hide-header hide-footer centered>
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
          <textarea rows="4" cols="40" placeholder="Escribe tu razón aquí" v-model="rejectionReason" v-validate="'required|max:100'" name="rejectionReason" class="form-control"></textarea>
          <span class="text-danger" v-show="errors.has('rejectionReason')">{{ errors.first('rejectionReason') }}</span>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-right mt-3">
          <b-button variant="danger" @click="submitRejection" :disabled="!validated">Rechazar</b-button>
        </b-col>
      </b-row>
    </b-container>
  </b-modal>
</template>

<script>
import RequestsReturnProductService from "../../services/request-return-product/RequestsReturnProductService";
import { showSuccessToast, showInfoAlert } from "../../components/alerts/alerts";

export default {
  props: {
    requestId: String,
    userEmail: String
  },
  data() {
    return {
      rejectionReason: ''
    };
  },
  methods: {
    async submitRejection() {
      if (this.rejectionReason.trim()) {
        showInfoAlert(
            'Confirmación',
            '¿Estás seguro de que quieres rechazar esta solicitud?',
            'Aceptar',
            async () => {
              try {
                const requestData = {
                  requestId: this.requestId,
                  status: 'Rechazado',
                  rejectionReason: this.rejectionReason,
                  email: this.userEmail
                };
                await RequestsReturnProductService.putRequestReturnProductStatusService(requestData);
                showSuccessToast('La solicitud de devolución ha sido rechazada');
                this.$emit('rejection-submitted', true);
                this.closeModal();
              } catch (error) {
                console.error("Error al rechazar la solicitud de devolución:", error);
              }
            }
        );
      }
    },
    closeModal() {
      this.$bvModal.hide('rejectionModal');
    },
  },
  computed: {
    validated() {
      return !this.errors.any();
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
