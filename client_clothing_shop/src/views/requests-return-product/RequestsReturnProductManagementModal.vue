<template>
<b-modal
id="requestReturnProductModal"
@show="getByIdRequestReturnProduct"
@hide="modalClosed"
centered
size="lg"
hide-footer
hide-header
ok-only
>
<b-container fluid>
  <b-row class="mb-2">
    <b-col class="text-right">
      <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('requestReturnProductModal')"/>
    </b-col>
  </b-row>

  <!-- Título del Modal -->
  <b-row class="mb-4">
    <b-col class="text-center">
      <h3 class="modal-title">Solicitud de devolucion</h3>
    </b-col>
  </b-row>

  <!-- Usuario y Número de Orden -->
  <b-row class="mb-3">
    <b-col>
      <p class="user-label"><strong>Usuario: </strong>{{ request.email }}</p>
    </b-col>
    <b-col>
      <p class="order-number-label"><strong>Número de Orden: </strong>{{ request.orderNumber }}</p>
    </b-col>
  </b-row>

  <!-- Razón de la Devolución -->
  <b-row class="mb-3">
    <b-col>
      <p class="return-reason-label"><strong>Razón de la Devolución: </strong>{{ request.returnReason }}</p>
    </b-col>
  </b-row>
  <!-- Detalles del Producto -->
  <b-row class="mb-3">
    <b-col cols="3">
      <b-img :src="request.image" fluid alt="Imagen del Producto"></b-img>
    </b-col>
    <b-col>
      <p class="product-name-label"><strong>Producto: </strong>{{ request.productName }}</p>
      <p class="product-amount-label"><strong>Cantidad: </strong>{{ request.amount }}</p>
      <p class="product-price-label"><strong>Precio: $</strong>{{ request.price}}</p>
    </b-col>
  </b-row>



  <!-- Botones de Acción -->

  <b-row class="mt-4">
    <b-col class="text-right action-buttons">
      <b-button class="btn-accept mr-2" @click="acceptRequestReturnProduct" v-if="!isStatusFinal">Aprobar</b-button>
      <b-button variant="danger" @click="openRejectionModal(request.requestId)" v-if="!isStatusFinal">Rechazar</b-button>
    </b-col>
  </b-row>

  <RejectionReason
      @request-success="handleRequestSuccess"
      @request-error="handleRequestError"
      :selected-request-id="selectedRequestId"
      :request-id="request.idRequestReturnProduct"
      :user-email="request.email"
      @rejection-submitted="handleRejection"
      @close-main-modal="closeMainModal"
      @rejection-completed="closeAllModals"
  />
</b-container>
</b-modal>
</template>

<script>
import RejectionReason from "../../views/requests-return-product/RejectionReason.vue";
import {showInfoAlert, showSuccessToast} from "../../components/alerts/alerts";
import RequestsReturnProductService from "../../services/request-return-product/RequestsReturnProductService";
import swal from "sweetalert2";

export default{
  name: "RequestsReturnProductManagementModal",
  components: {RejectionReason},
  props: {
    requestId: String,
    requestStatus: String,
  },
  data() {
    return {
      request: {},
      images: [],
      selectedRequestId: null,

    }

  },
  methods: {
    async getByIdRequestReturnProduct() {
      try {
        const response = await RequestsReturnProductService.getRequestReturnProductByIdService(this.requestId);
        if (response && response.data && response.data.idRequestReturnProduct) {
          this.request = response.data;
        } else {
        }
      } catch (error) {
      }
    },
    async modalClosed() {
      this.request = {};
    },
    handleRequestSuccess() {
      swal.fire({
        title: 'Petición exitosa',
        text: 'La petición se completó con éxito',
        icon: 'success',
        button: 'Aceptar'
      });
    },
    handleRequestError() {
      swal.fire({
        button: 'Aceptar',
        icon: 'error',
        text: 'Ocurrió un error al procesar la petición',
        title: 'Error'
      });
    },
    handleRejection(response) {
      if (response) {
        showSuccessToast('La solicitud de devolución ha sido rechazada');
        this.request = {};
      }
    },
closeMainModal() {
      this.$bvModal.hide('requestReturnProductModal');
    },

    async acceptRequestReturnProduct() {
      showInfoAlert(
          'Confirmación',
          '¿Estás seguro de que quieres aprobar esta solicitud?',
          'Aceptar',
          async () => {
            try {
              const requestData = {
                requestId: this.request.idRequestReturnProduct,
                status: 'Aprobado',
                rejectionReason: '',
                email: this.request.email
              };
              const response = await RequestsReturnProductService.putRequestReturnProductStatusService(requestData);
              if (response && response.data && response.data.status === 'Aprobado') {
                showSuccessToast('La solicitud de devolución ha sido aprobada');
                this.request.status = 'Aprobado';
                this.$emit('approval-completed');
                this.$bvModal.hide('requestReturnProductModal');
              } else {
              }
            } catch (error) {
              swal.fire({
                button: 'Aceptar',
                icon: 'error',
                text: 'Ocurrió un error al procesar la petición',
                title: 'Error'
              });
            }
          }
      );
    },

    openRejectionModal(requestId) {
      this.selectedRequestId = requestId;
      this.$bvModal.show('rejectionReasonModal');
    },
    closeAllModals() {
      this.$bvModal.hide('rejectionReasonModal');
      this.$bvModal.hide('requestReturnProductModal');
      window.location.reload();
    },
    },
  computed: {
    isStatusFinal() {
      return this.requestStatus === 'Aprobado' || this.requestStatus === 'Rechazado';
    }
  }
}
</script>

<style scoped>
.modal-title {
  font-weight: bold;
}
.user-label, .order-number-label, .return-reason-label, .product-name-label, .product-amount-label, .product-price-label {
  font-size: 1.1rem;
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