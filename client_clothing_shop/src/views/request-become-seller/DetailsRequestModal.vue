<template>
  <section>
    <b-modal id="detailsRequestModal" hide-header hide-footer centered size="lg" @show="getRequest">
      <b-container>
        <b-row>
          <b-col class="text-right">
            <font-awesome-icon icon="times" class="text-secondary selectable" @click="$bvModal.hide('detailsRequestModal')"/>
          </b-col>
        </b-row>
        <b-row class="text-center">
          <b-col>
            <h1>Información del solicitante</h1>
          </b-col>
        </b-row>
        <b-row class="my-4">
          <b-col md="6" sm="12" class="modal-info">
            <ul>
              <li><strong>Nombre:</strong> {{ request.personName }}</li>
              <li><strong>Apellidos:</strong> {{ request.personLastName + " "  + request.personSecondLastName }}</li>
              <li><strong>Correo:</strong> {{ request.userEmail }}</li>
              <li><strong>Teléfono:</strong> {{ request.phoneNumber }}</li>
              <li><strong>CURP:</strong> {{ request.userSellerInformation?.curp }}</li>
              <li><strong>RCF:</strong> {{ request.userSellerInformation?.taxIdentificationNumber }}</li>
              <li><strong>Dirección:</strong> {{ request.address }}</li>
              <li v-if="request.status === 'Rechazado'"><strong>Razón del rechazo:</strong> {{ request.rejectionReason }}</li>
            </ul>
          </b-col>
          <b-col md="6" sm="12" class="d-flex justify-content-center align-items-center">
            <b-img v-show="request.userSellerInformation?.imageIdentification" class="image-size" :src="request.userSellerInformation?.imageIdentification"/>
          </b-col>
        </b-row>
        <b-row v-if="request.status === 'Pendiente' && showRejectionReason === false" class="justify-content-center mt-2 mb-2">
          <b-col>
            <b-button variant="dark" @click="putStatusRequest('Aprobado')" class="w-100 my-2">Aceptar</b-button>
          </b-col>
          <b-col>
            <b-button @click="openRejectionField" class="w-100 my-2" style="border-radius: 0.5rem; background-color: red; border-color: red;">Rechazar</b-button>
          </b-col>
        </b-row>
        <b-collapse v-model="showRejectionReason">
          <b-row>
            <b-col class="ml-4">
              <b-form-textarea
                  id="textarea-rejection-reason"
                  v-model="rejectionReason"
                  placeholder="Escribe aquí la razón de rechazo"
                  rows="3"
                  max-rows="6"
                  v-validate="'required|rejection_reason_length'"
                  name="rejectionReason"
              >
              </b-form-textarea>
              <span v-show="errors.has('rejectionReason')" class="text-danger">{{ errors.first('rejectionReason') }}</span>
            </b-col>
          </b-row>
          <b-row class="justify-content-center mt-2 mb-2">
            <b-button @click="putStatusRequest('Rechazado')" variant="dark" class="w-25 mx-5" style="border-radius: 0.5rem">Aceptar</b-button>
            <b-button @click="closeRejectionField" class="w-25 mx-5" style="border-radius: 0.5rem; background-color: red; border-color: red;">Cancelar</b-button>
          </b-row>
        </b-collapse>
      </b-container>
    </b-modal>
  </section>
</template>

<script>
import Vue from 'vue';
import RequestsBecomeSellerService from "@/services/requests-become-seller/RequestsBecomeSellerService";
import {showInfoAlert, showSuccessToast, showWarningToast} from "@/components/alerts/alerts";

export default Vue.extend({
  name: "DetailsRequestModal",
  props: {
    idRequest: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      request: {},
      rejectionReason: null,
      showRejectionReason: false
    }
  },
  methods: {
    async getRequest() {
      this.request = await RequestsBecomeSellerService.getRequestByIdService(this.idRequest);
    },

    async putStatusRequest(status) {
      if (status === "Rechazado") {
        const result = await this.$validator.validateAll();
        if (!result) {
          return;
        }
      }
      await showInfoAlert(
          "¿Estás seguro de cambiar el estado de la solicitud?",
          "Esta acción no se puede deshacer",
          "Cambiar",
          async () => {
            const payload = {
              idRequestBecomeSeller: this.idRequest,
              rejectionReason: this.rejectionReason,
              status: status
            }
            const response = await RequestsBecomeSellerService.putStatusRequestService(payload);

            if (response === 200) {
              if (status === "Rechazado") {
                this.rejectionReason = null;
              }
              this.$emit('request-updated');
              this.$bvModal.hide('detailsRequestModal');
              showSuccessToast("Estado de la solicitud actualizado correctamente")
            } else if (!response) {
              showWarningToast("Error al actualizar el estado de la solicitud")
            }
          }
      )
    },
    openRejectionField() {
      this.showRejectionReason = true;
    },
    closeRejectionField() {
      this.showRejectionReason = false;
      this.rejectionReason = null;
    },
  }
})
</script>

<style scoped>
.modal-info {
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 5px;
  padding: 10px;
  margin-bottom: 10px;
}

.modal-info li {
  margin-bottom: 8px;
}

.modal-info strong {
  font-weight: bold;
}
.image-size {
  max-width: 100%;
  height: auto;
  display: block;
  min-width: 150px;
}
</style>