<template>
  <section class="interface">
    <b-row>
      <b-col class="text-center">
        <h1>Solicitudes de vendedor</h1>
      </b-col>
    </b-row>

    <b-row class="mt-4" align-h="between">
      <b-col cols="12" lg="4">
        <b-form-group>
          <div class="position-relative">
            <b-form-input @input="getPageRequests" v-model="search" id="search" type="text" placeholder="Buscar..."  class="pr-5"/>
            <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
          </div>
        </b-form-group>
      </b-col>
    </b-row>

    <b-row v-if="!requests" class="mt-3 container-requests">
      <b-col class="text-center">
        <h3>No hay solicitudes de vendedor</h3>
      </b-col>
    </b-row>

    <b-row v-else class="mt-3 container-requests">
      <b-col>
        <b-row>
          <b-col lg="4" v-for="(request, index) in requests" :key="index">
            <b-card no-body class="highlight-on-hover mb-2 selectable" style="border-radius: 0.7rem;" @click="openDetailsRequestModal(request.idRequestBecomeSeller)">
              <b-row class="m-2" no-gutters>
                <b-col cols="auto" class="d-done d-md-block px-2 my-auto">
                  <b-avatar
                      size="2.5rem"
                      class="text-uppercase"
                      :src="request.picture ? request.picture : 'https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png'"
                  />
                </b-col>

                <b-col cols="auto" class="ml-2">
                  <b-row>
                    <b-col>
                      <div class="text-truncate font-weight-bold small"> {{ request.userEmail }}</div>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <div class="text-ellipsis text-secondary small"> {{ request.personName + ' ' + request.personLastName + ' ' + request.personSecondLastName }}</div>
                    </b-col>
                  </b-row>
                </b-col>
                <b-col class="text-right align-content-center">
                  <b-badge :variant="getVariant(request.status.status)" class="text-ellipsis text-white small" style="margin-right: 5%;">{{ request.status.status }}</b-badge>
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
            @change="getPageRequests"
        ></b-pagination>
      </b-col>
    </b-row>

    <DetailsRequestModal :idRequest="selectedRequest" @request-updated="refreshRequests" />
  </section>
</template>

<script>
import Vue from 'vue';
import RequestsBecomeSellerService from "@/services/requests-become-seller/RequestsBecomeSellerService";

export default Vue.extend({
  name: "RequestsBecomeSellerManagement",
  components: {
    DetailsRequestModal: () => import("@/views/request-become-seller/DetailsRequestModal.vue")
  },
  data() {
    return {
      objectPagination: {
        page: 1,
        size: 10,
        elements: 0
      },
      requests: [],
      selectedRequest: "",
      search: null
    }
  },
  methods: {
    async getPageRequests(page) {
      this.objectPagination.page = page;
      if (this.search === null || this.search === "")   {
        this.showOverlay();
        const response = await RequestsBecomeSellerService.getPageRequestsService(this.objectPagination);
        this.showOverlay();
        this.objectPagination.elements = response.totalElements;
        this.requests = response.content;
      } else {
        const response = await RequestsBecomeSellerService.getPageRequestsByUserEmailService(this.search, this.objectPagination);
        this.objectPagination.elements = response.totalElements;
        this.requests = response.content;
      }
    },

    getVariant(status) {
      switch (status) {
        case "Pendiente":
          return "warning";
        case "Aprobado":
          return "success";
        case "Rechazado":
          return "danger";
        default:
          return "secondary";
      }
    },

    openDetailsRequestModal(idRequest) {
      this.selectedRequest = idRequest;
      this.$nextTick(() => {
        this.$bvModal.show("detailsRequestModal");
      })
    },

    showOverlay() {
      this.$store.dispatch('changeStatusOverlay')
    },

    refreshRequests() {
      this.getPageRequests();
    }
  },
  mounted() {
    this.getPageRequests();
  }
})
</script>

<style scoped>
.container-requests {
  height: calc(100vh - 270px);
  overflow-x: hidden;
}
</style>