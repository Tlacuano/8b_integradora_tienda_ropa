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

      <!-- Cambios solicitados a la información personal -->
      <b-row>
        <b-col>
          <h4 class="changes-title">Cambios solicitados a la información personal:</h4>
          <b-table striped bordered :items="formattedRequestData" class="changes-table"></b-table>
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
      />    </b-container>
  </b-modal>
</template>

<script>
import RejectionReason from "../../views/requests-data-change/RejectionReason.vue";
import RequestsDataChangeService from "../../services/requests-data-change/RequestsDataChangeService";

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
    };
  },
  methods: {
    formatLabel(key) {
      const formatted = key.replace(/([A-Z])/g, " $1");
      return formatted.charAt(0).toUpperCase() + formatted.slice(1);
    },

    async getByIdRequestDataChange() {
      try {
        const response = await RequestsDataChangeService.getRequestDataChangeByIdService(this.requestId);
        if (response && response.requestId) {
          this.request = response;
        } else {
          console.error("La respuesta no contiene datos válidos o la propiedad esperada:", response);
        }
      } catch (error) {
        console.error("Error al obtener la solicitud de cambio de datos:", error);
      }
    },

    async acceptRequestDataChange() {
      try {
        const requestDataChangePutDTO = {
          idRequestDataChange: this.request.requestId,
          status: "Aprobado",
          rejectionReason: "",
          name: this.request.newUserInformation.name || null,
          lastName: this.request.newUserInformation.lastName || null,
          secondLastName: this.request.newUserInformation.secondLastName || null,
          birthday: this.request.newUserInformation.birthday || null,
          phoneNumber: this.request.newUserInformation.phoneNumber || null,
          gender: this.request.newUserInformation.gender || null,
        };

        const response = await RequestsDataChangeService.putRequestDataChangeService(requestDataChangePutDTO);

        this.$bvModal.hide('requestDataChangeModal');
      } catch (error) {
        console.error("Error al aceptar la solicitud de cambio de datos:", error);
      }
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
  },
  computed: {
    formattedRequestData() {
      if (!this.request || !this.request.newUserInformation) {
        return [];
      }

      const fieldsToDisplay = ['name', 'lastName', 'secondLastName', 'gender', 'birthday', 'phoneNumber'];
      const birthdayArray = this.request.newUserInformation.birthday;
      const formattedBirthdayNew = birthdayArray
          ? `${birthdayArray[0]}-${String(birthdayArray[1]).padStart(2, '0')}-${String(birthdayArray[2]).padStart(2, '0')}`
          : 'No especificado';

      const oldBirthdayArray = this.request.birthday; // Asegúrate de que esta es la forma en que se reciben los datos
      const formattedBirthdayOld = oldBirthdayArray
          ? `${oldBirthdayArray[0]}-${String(oldBirthdayArray[1]).padStart(2, '0')}-${String(oldBirthdayArray[2]).padStart(2, '0')}`
          : 'No especificado';
      const fieldMappings = {
        name: 'personName',
        lastName: 'personLastName',
        secondLastName: 'personSecondLastName',
        gender: 'gender',
        birthday: 'birthday',
        phoneNumber: 'phoneNumber'
      };

      const data = fieldsToDisplay.map((field) => {
        const oldKey = fieldMappings[field];
        let oldValue = this.request[oldKey] !== undefined ? this.request[oldKey] : 'No especificado';
        let newValue = this.request.newUserInformation[field] || 'No especificado';

        if (field === 'birthday') {
          oldValue = formattedBirthdayOld;
          newValue = formattedBirthdayNew;
        }

        return {
          Campo: this.formatLabel(field),
          Anterior: oldValue,
          Nuevo: newValue,
        };
      });

      return data;
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
</style>
