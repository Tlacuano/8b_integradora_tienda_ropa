<template>
  <section>
    <b-modal id="detailsRequestModal" hide-header hide-footer centered size="lg" @show="getRequest">
      <b-container>
        <b-row>
          <b-col>
            <h3 class="modal-title">Información del solicitante</h3>
          </b-col>
        </b-row>
        <b-row>
          <b-col class="modal-info">
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
          <b-col class="d-flex justify-content-center align-items-center">
            <b-avatar
                v-if="request.picture"
                size="18rem"
                class="text-uppercase"
                :src="request.picture"
            />
            <b-avatar
                v-else
                size="10rem"
                class="text-uppercase"
                icon="person-circle"
            />
          </b-col>
        </b-row>
        <b-row v-if="request.status === 'Pendiente'" class="justify-content-center mt-2 mb-2">
          <b-col>
            <b-button variant="dark" @click="putStatusRequest('Aprobado')" class="w-100 my-2">Aceptar</b-button>
          </b-col>
          <b-col>
            <b-button @click="openReasonModal" variant="danger" class="w-100 my-2">Rechazar</b-button>
          </b-col>
        </b-row>
      </b-container>
    </b-modal>


    <b-modal id="rejectionReasonModal" hide-header hide-footer centered>
      <b-row>
        <b-col class="text-center">
          <h3>Indica la razón de rechazo</h3>
        </b-col>
      </b-row>
      <b-row class="mt-3">
        <b-col class="ml-4" >
          <b-form-textarea
              id="textarea-rejection-reason"
              v-model="rejectionReason"
              placeholder="Escribe aquí la razón de rechazo"
              rows="3"
              max-rows="6"
          />
        </b-col>
      </b-row>
      <b-row class="justify-content-center mt-2 mb-2">
        <b-button variant="dark" @click="putStatusRequest('Rechazado')" class="w-25 mx-5" style="border-radius: 0.5rem">Aceptar</b-button>
        <b-button @click="closeReasonModal" class="w-25 mx-5" style="border-radius: 0.5rem; background-color: red; border-color: red;">Cancelar</b-button>
      </b-row>
    </b-modal>
  </section>
</template>

<script>
import Vue from 'vue';
import RequestsBecomeSellerService from "@/services/requests-become-seller/RequestsBecomeSellerService";
import {showInfoAlert} from "@/components/alerts/alerts";

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
      rejectionReason: null
    }
  },
  methods: {
    async getRequest() {
      this.request = await RequestsBecomeSellerService.getRequestByIdService(this.idRequest);
    },

    async putStatusRequest(status) {
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
            await RequestsBecomeSellerService.putStatusRequestService(payload);

            this.$emit('request-updated');
            this.$bvModal.hide('detailsRequestModal');
            if (status === "Rechazado") {
              this.rejectionReason = null;
              this.$bvModal.hide('rejectionReasonModal');
            }
          }
      )
    },

    openReasonModal() {
      this.$nextTick(() => {
        this.$bvModal.show("rejectionReasonModal");
      })
    },

    closeReasonModal() {
      this.$bvModal.hide("rejectionReasonModal");
    }
  }
})
</script>

<style scoped>
.modal-title {
  background-color: #f8f9fa;
  border-bottom: 1px solid #dee2e6;
  padding: 10px;
  margin-bottom: 20px;
  text-align: center;
}

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
</style>