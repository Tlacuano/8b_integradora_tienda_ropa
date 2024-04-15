<template>
  <b-modal
      id="requestDataChangeModal"
      @show="getByIdRequestDataChange"
      @hide="modalClosed"
      centered
      size="lg"
      hide-footer
      hide-header
      ok-only
  >
    <b-container fluid>
      <!-- Encabezado del Modal con icono de cierre -->
      <b-row class="mb-2">
        <b-col class="text-right">
          <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('requestDataChangeModal')"/>
        </b-col>
      </b-row>

      <!-- Título del Modal -->
      <b-row class="mb-4">
        <b-col class="text-center">
          <h3 class="modal-title">Solicitud de cambio de datos</h3>
        </b-col>
      </b-row>

      <!-- Usuario -->
      <b-row class="mb-3">
        <b-col>
          <p class="user-label"><strong>Usuario: </strong>{{ request.userEmail }}</p>
        </b-col>
      </b-row>

      <!-- Cambios solicitados a la información del vendedor -->
      <b-row>
        <b-col>
          <h4 class="changes-title">Cambios solicitados a la información del vendedor:</h4>
          <b-table striped bordered :items="formattedRequestData" class="changes-table"></b-table>
        </b-col>
      </b-row>

      <b-row>
        <b-col>
          <h4 class="changes-title">Imágenes del Vendedor:</h4>
          <table class="table table-bordered">
            <thead>
            <tr>
              <th>Imagen Actual del Vendedor</th>
              <th>Nueva Imagen del Vendedor</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td class="image-cell"><img :src="currentImageUrl" alt="Imagen Actual del Vendedor" class="img-fluid vendor-image"></td>
              <td class="image-cell" v-if="isImageChanged"><img :src="newImageUrl" alt="Nueva Imagen del Vendedor" class="img-fluid vendor-image"></td>
              <td class="image-cell" v-else>No especificado</td>
            </tr>
            </tbody>
          </table>
        </b-col>
      </b-row>

      <!-- Botones de Acción -->
      <b-row class="mt-4">
        <b-col class="text-right action-buttons">
          <b-button class="btn-accept mr-2" @click="acceptRequestDataChange" v-if="request.status !== 'Aprobado' && request.status !== 'Rechazado'">Aprobar</b-button>
          <b-button variant="danger" @click="openRejectionModal(request.requestId)" v-if="request.status !== 'Aprobado' && request.status !== 'Rechazado'">Rechazar</b-button>

        </b-col>
      </b-row>

      <!-- RejectionReason Component -->
      <RejectionReason
          @request-success="handleRequestSuccess"
          @request-error="handleRequestError"
          :selected-request-id="selectedRequestId"
          @rejection-submitted="handleRejection"
          @close-main-modal="closeMainModal"
      />    </b-container>
  </b-modal>
</template>

<script>
import RejectionReason from "../../views/requests-data-change/RejectionReason.vue";
import RequestsDataChangeService from "../../services/requests-data-change/RequestsDataChangeService";
import {showInfoAlert, showSuccessToast} from "../../components/alerts/alerts";

export default {
  name: "RequestsDataChangeManagementModal",
  components: { RejectionReason },
  props: {
    requestId: String
  },
  data() {
    return {
      request: {},
      selectedRequestId: null,
      currentImage:'',
    };
  },
  methods: {
    formatLabel(key) {
      const translations = {
        taxIdentificationNumber: 'Número de Identificación Fiscal',
        secondaryPhoneNumber: 'Teléfono Secundario',
        imageIdentification: 'Identificación de Imagen',
        curp: 'CURP'
      };
      return translations[key] || key;
    },

    async getByIdRequestDataChange() {
      try {
        const response = await RequestsDataChangeService.getRequestDataChangeByIdService(this.requestId);
        if (response && response.requestId) {
          this.request = response;
          this.currentImage = response.imageIdentification || '';
        } else {
        }
      } catch (error) {
      }
    },

    async acceptRequestDataChange() {
      showInfoAlert(
          'Confirmación',
          '¿Estás seguro de que quieres aprobar esta solicitud?',
          'Aceptar',
          async () => {
            try {
              const requestDataChangePutDTO = {
                idRequestDataChange: this.request.requestId,
                status: "Aprobado",
                rejectionReason: "",
                taxIdentificationNumber: this.request.newUserInformation.taxIdentificationNumber,
                secondaryPhoneNumber: this.request.newUserInformation.secondaryPhoneNumber,
                imageIdentification: this.request.newUserInformation.imageIdentification,
                curp: this.request.newUserInformation.curp
              };

              const response = await RequestsDataChangeService.putRequestDataChangeService(requestDataChangePutDTO);
              this.$bvModal.hide('requestDataChangeModal');
              showSuccessToast('Éxito', 'La solicitud ha sido aprobada exitosamente');
            } catch (error) {
            }
          }
      );
    },

    openRejectionModal(requestId) {
      this.selectedRequestId = requestId;
      this.$nextTick(() => {
        this.$bvModal.show("rejectionModal");
      });
    },

    modalClosed() {
      this.$emit("modal-closed");
    },

    handleRequestSuccess(response) {
      this.$emit("request-success", response);
      this.$bvModal.hide("requestDataChangeModal");
    },

    handleRequestError(error) {
      this.$emit("request-error", error);
    },

    handleRejection(response) {
      this.$emit("request-success", response);
    },

    closeMainModal() {
      this.$bvModal.hide('requestDataChangeModal');
    }
  },
  computed: {
    formattedRequestData() {
      if (!this.request || !this.request.newUserInformation) {
        return [];
      }

      const fieldsToDisplay = ['taxIdentificationNumber', 'secondaryPhoneNumber', 'curp'];
      return fieldsToDisplay.map(field => ({
        Campo: this.formatLabel(field),
        Anterior: this.request[field] || 'No especificado',
        Nuevo: this.request.newUserInformation[field] || 'No especificado',
      }));
    },
    currentImageUrl() {
      return this.currentImage;
    },
    newImageUrl() {
      return (this.request.newUserInformation && this.request.newUserInformation.imageIdentification) ? this.request.newUserInformation.imageIdentification : 'No especificado';
    },
    isImageChanged() {
      return this.newImageUrl !== 'No especificado' && this.newImageUrl !== this.currentImageUrl;
    }
  },
};
</script>

<style scoped>
.modal-title {
  font-weight: bold;
}
.user-label {
  font-size: 1.2rem;
}
.changes-title {
  font-weight: bold;
  margin-bottom: 0.5rem;
}
.changes-table td {
  vertical-align: middle;
}
.action-buttons .btn-accept {
  background-color: var(--black-base) !important;
  color: white;
}
.action-buttons .btn-accept:hover {
  opacity: 0.9;
}
.action-buttons .btn-danger {
  color: white;
}
.action-buttons .btn-danger:hover {
  opacity: 0.9;
}
.vendor-image {
  max-width: 50%;
  height: auto;
  display: inline-block;
}

.changes-table, .vendor-image-table {
  width: 100%;
}

.vendor-image-table th, .vendor-image-table td {
  text-align: center;
}

.image-cell {
  text-align: center;
  vertical-align: middle;
  width: 200px;
}
</style>
