<template>
  <section class="interface">
    <b-row>
      <b-col class="text-center">
        <h1>Solicitudes de devolución de productos</h1>
      </b-col>
    </b-row>
    <b-row class="mt-4" align-h="between">
      <b-col cols="12" lg="4">
        <b-form-group>
          <div class="position-relative">
            <b-form-input
                id="search"
                type="text"
                placeholder="Buscar por número de orden..."
                class="pr-5"
                v-model="searchQuery"
                @input="getPageRequestReturnProduct(searchQuery)"
            ></b-form-input>
            <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
          </div>
        </b-form-group>
      </b-col>
    </b-row>
    <b-row class="mt-3 container-requests" >
      <b-col>
        <b-row>
          <b-col lg="3" md="4" sm="6" v-for="request in requests" :key="request.idRequestReturnProduct">
            <b-card no-body class="request-card highlight-on-hover mb-2" @click="openModal(request.idRequestReturnProduct, request.status)">
              <b-row no-gutters class="align-items-center">

                <b-col cols="6">
                  <div class="request-order-number">{{ request.orderNumber }}</div>
                </b-col>
                <b-col>
                  <b-badge :variant="getVariant(request.status)" class="status-badge">{{ request.status }}</b-badge>

                </b-col>
                  <b-col class="request-date">{{ formatDate(request.orderDate) }}</b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>
      </b-col>
    </b-row>

    <b-row>
      <b-col>
        <b-pagination
            v-model="objectPagination.page"
            :total-rows="objectPagination.elements"
            :per-page="objectPagination.size"
            aria-controls="my-table"
        ></b-pagination>
      </b-col>
    </b-row>
    <RequestReturnProductModal @request-success="handleRequestSuccess" @request-error="handleRequestError" :request-id="selectedRequestId"   :request-status="selectedRequestStatus"   @approval-completed="handleApprovalCompleted"

    />
  </section>
</template>

<script>
import RequestReturnProductService from "../../services/request-return-product/RequestsReturnProductService";
import swal from 'sweetalert2';

export default {
  name: "RequestsReturnProductManagement",
  components: {
    RequestReturnProductModal: () => import('../../views/requests-return-product/RequestsReturnProductManagementModal.vue')
  },
  data() {
    return {
      requestDataChangeModal: false,
      objectPagination: {
        page: 1,
        size: 24,
        elements: 0,
      },
      requests: [],
      selectedRequestId: null,
      searchQuery: '',
      selectedRequestStatus: '',
    };
  },
  methods: {
    async getPageRequestReturnProduct(searchQuery) {
      const response = await RequestReturnProductService.getPageRequestsReturnProductService(this.objectPagination, searchQuery);
      this.objectPagination.elements = response.totalElements;
      this.requests = response.content;
    },
    openModal(requestId, status) {
      this.selectedRequestId = requestId;
      this.selectedRequestStatus = status;
      this.$nextTick(() => {
        this.$bvModal.show("requestReturnProductModal");
      });
    },
    handleRequestSuccess() {
      swal.fire({
        title: 'Petición exitosa',
        text: 'La petición se completó con éxito',
        icon: 'success',
        button: 'Aceptar'
      });
      this.getPageRequestReturnProduct(this.searchQuery);
    },
    handleRequestError() {
      swal.fire({
        button: 'Aceptar',
        icon: 'error',
        text: 'Ocurrió un error al procesar la petición',
        title: 'Error'
      });
    },
    getVariant(status) {
      switch (status) {
        case 'Pendiente':
          return 'warning';
        case 'Aprobado':
          return 'success';
        case 'Rechazado':
          return 'danger';
        default:
          return 'secondary';
      }
    },
    formatDate(date) {
      const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
      return new Date(date).toLocaleDateString('es-ES', options);
    },
    handleApprovalCompleted() {
      this.getPageRequestReturnProduct(this.searchQuery);
    }
  },
  mounted() {
    this.getPageRequestReturnProduct(this.searchQuery);
  }
}
</script>

<style scoped>
.container-requests {
  height: calc(100vh - 270px);
  overflow-x: hidden;
}
.status {
  color: #28a745;
}
.request-card {
  display: flex;
  justify-content: space-between;
  padding: 10px 20px;

}

.request-date {
  font-size: 0.9em;
  color: #666;
}

.request-order-number {
  font-size: 1.2em;
  font-weight: bold;
  margin-right: 10px;
}

.status-badge {
  font-size: 0.8em;
  font-weight: normal;
  padding: 5px 10px;
  border-radius: 8px;
  margin-right: 10px;

}
</style>
