<template>
  <section>
    <b-modal id="detailsRequestModal" hide-header hide-footer centered size="lg" @show="getRequest">
      <b-row>
        <b-col class="text-center">
          <h3>Información del solicitante</h3>
        </b-col>
      </b-row>
      <b-row class="mt-3">
        <b-col>
        </b-col>
        <b-col></b-col>
      </b-row>
    </b-modal>
  </section>
</template>

<script>
import Vue from 'vue';
import RequestsBecomeSellerService from "@/services/requests-become-seller/RequestsBecomeSellerService";

export default Vue.extend({
  name: "DetailsRequestModal",
  props: {
    request: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      requestsReceived: {}
    }
  },
  methods: {
    async getRequest() {
      await RequestsBecomeSellerService.getRequestByIdService(this.request)
    }
  }
})
</script>

<style scoped>

</style>