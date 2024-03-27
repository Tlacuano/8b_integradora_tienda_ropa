<template>
  <section class="interface">
    <b-row>
      <b-col class="text-center">
        <h1>Solicitudes de vendedor</h1>
      </b-col>
    </b-row>

    <b-row class="mt-3 container-requests">
      <b-col>
        <b-row>
          <b-col lg="4" v-for="request in requests" :key="request.id">
            <b-card no-body class="highlight-on-hover mb-2">
              <b-row class="m-2" no-gutters>
                <b-col cols="auto" class="d-done d-md-block px-2 my-auto">
                  <b-avatar
                      v-if="request.status"
                      size="2.5rem"
                      variant="success"
                      class="text-uppercase"
                      :src="request.picture"
                  />
                  <b-avatar
                      v-else
                      size="2.5rem"
                      variant="secondary"
                      class="text-uppercase"
                  >{{ request.personName.charAt(0) }}</b-avatar>
                </b-col>

                <b-col cols="8" class="ml-2">
                  <b-row>
                    <b-col>
                      <div class="text-truncate font-weight-bold small"> {{ request.email }}</div>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <div class="text-ellipsis text-secondary small"> {{ request.personName + ' ' + request.personLastName }}</div>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <b-badge :variant="request.status.status ? 'success' : 'warning'" class="text-ellipsis text-secondary small">{{ request.status.status }}</b-badge>
                    </b-col>
                  </b-row>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
  </section>
</template>

<script>
import Vue from 'vue';
import RequestsBecomeSellerService from "@/services/requests-become-seller/RequestsBecomeSellerService";

export default Vue.extend({
  name: "RequestsBecomeSellerManagement",
  data() {
    return {
      objectPagination: {
        page: 1,
        size: 24,
        elements: 0
      },
      requests: []
    }
  },
  methods: {
    async getPageRequests() {
      this.showOverlay();
      const response = await RequestsBecomeSellerService.getPageRequestsService(this.objectPagination);
      this.showOverlay();

      console.log(response.content);

      this.objectPagination.elements = response.totalElements;
      this.requests = response.content;
    },

    showOverlay() {
      this.$store.dispatch('changeStatusOverlay')
    }
  },
  mounted() {
    this.getPageRequests();
  }
})
</script>

<style scoped>
.container-subcategories {
  height: 65vh !important;
  overflow-x: hidden;
}
</style>