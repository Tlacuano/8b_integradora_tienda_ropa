<template>
  <section class="interface">
    <b-row>
      <b-col class="text-center">
        <h1>Solicitudes de cambio de datos</h1>
      </b-col>
    </b-row>
    <b-row class="mt-4" align-h="between">
      <b-col cols="12" lg="4">
        <b-form-group>
          <div class="position-relative">
            <b-form-input
                id="search"
                type="text"
                placeholder="Buscar..."
                class="pr-5"
                v-model="searchQuery"
                @input="getPageRequestsDataChange(searchQuery)"
            ></b-form-input>
            <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
          </div>
        </b-form-group>
      </b-col>
    </b-row>
    <b-row class="mt-3 container-products" >
      <b-col>
        <b-row>
          <b-col lg="3" md="4" sm="6" v-for="request in requests" :key="request.id" >
            <b-card no-body class="highlight-on-hover mb-2" @click="openModal(request)">
              <b-row class="m-2" no-gutters>
                <b-col cols="auto" class="d-none d-md-block my-auto">
                  <b-avatar
                      size="2.5rem"
                      class="text-uppercase"
                      :src="request.picture ? request.picture : 'https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png'"
                  />
                </b-col>
                <b-col cols="8" class="ml-2">
                  <b-row>
                    <b-col>
                      <div class="text-truncate font-weight-bold small"> {{ request.personName + ' ' + request.personLastName }}</div>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <b-badge :variant="getVariant(request.status.status)" class="text-ellipsis text-white small">{{ request.status.status }}</b-badge>
                    </b-col>
                  </b-row>
                </b-col>
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
    <RequestsDataChangeManagementModal @request-succes="handleRequestSuccess" @request-error="handleRequestError" :request-id="selectRequestId"/>
  </section>
</template>
<script>
import RequestsDataChangeService from "../../services/requests-data-change/RequestsDataChangeService";
import swal from 'sweetalert2';


export default{
  name:"RequestsDataChangeManagement",
  components: {
    RequestsDataChangeManagementModal: () => import('../../views/requests-data-change/RequestsDataChangeManagementModal.vue')
  },
  data(){
    return{
      requestDataChangeModal: false,
      objectPagination: {
        page: 1,
        size: 24,
        elements: 0,
      },
      requests: [],
      selectRequestId: null,
      searchQuery: ''
    }
  },
  methods: {
    async getPageRequestsDataChange(searchQuery) {
      const response = await RequestsDataChangeService.getPageRequestsDataChangeService(this.objectPagination, searchQuery);
      this.objectPagination.elements = response.totalElements;
      this.requests = response.content;
    },
    openModal(request) {
      this.selectRequestId = request.requestId;
      this.$nextTick(() => {
        this.$bvModal.show("requestDataChangeModal");
      });
    },

    handleRequestSuccess(response) {
      swal.fire({
        title: 'Petición exitosa',
        text: 'La petición se completó con éxito',
        icon: 'success',
        button: 'Aceptar'
      });
      this.getPageRequestsDataChange();
    },
    handleRequestError(error) {
      swal.fire({
        title: 'Error',
        text: 'Ocurrió un error al procesar la petición',
        icon: 'error',
        button: 'Aceptar'
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
    }
  },
  mounted() {
    this.getPageRequestsDataChange();
  },

}
</script>

<style>
.container-products {
  height: calc(100vh - 270px);
  overflow-x: hidden;
}

.status {
  color: #28a745;
}
</style>