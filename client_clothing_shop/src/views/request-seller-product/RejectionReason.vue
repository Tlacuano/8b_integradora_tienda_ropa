<template>
  <b-modal id="rejectionReason" ok-only hide-header hide-footer  >
    <b-container>
      <b-row>
        <b-col class="text-right">
          <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('rejectionReason')"/>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-center">
          <h3>Razón de rechazo</h3>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-area text-center">
          <textarea rows="4" cols="40" placeholder="Escribe tu razon aqui" v-model="reason" @input="validateInput">

          </textarea>
          <div v-if="notNull" style="color: red;">Razón obligatoria para rechazo</div>
          <div v-if="error" style="color: red;">¡Los caracteres especiales no son permitidos!</div>
        </b-col>
      </b-row>
      <b-row>
        <b-col v-if="isLoading" class="ml-4 mt-4">
          <p style="display: inline; margin-right:10px">Cargando...</p>
          <b-spinner small variant="dark"></b-spinner>
        </b-col>
        <b-col class="text-right mt-4">
          <b-button variant="danger"  @click="rejectionRequestSellProduct" :disabled="error || notNull">Rechazar</b-button>
        </b-col>
      </b-row>
    </b-container>
  </b-modal>
</template>
<script>
import ProductSalesRequestsService from "@/services/request-seller-product/RequestSellerProduct";
import {regexRejectionReason} from "@/utils/validation/regex";

export default{
  name: "RejectionReason",
  props: {
    productId: String,
  },
  data(){
    return{
      isLoading: false,
      error:false,
      notNull: true
    }
  },
  methods: {
    async rejectionRequestSellProduct(){
      this.isLoading = true;
      try {
        const response = await ProductSalesRequestsService.putProductSalesRequestStatus(this.productId, "Rechazado", this.reason);
        this.$emit('request-success',response);
      } catch (error) {
        this.$emit('request-error', error);
      } finally {
        this.isLoading = false; //
        this.$bvModal.hide('rejectionReason');
      }
    },
    validateInput(){
      const regex = regexRejectionReason;
      if (this.reason.trim().length === 0) {
        this.notNull = true
        this.error = false;
      } else if (!regex.test(this.reason)) {
        this.error = true;
        this.notNull = false;
      } else {
        this.error = false;
        this.notNull = false;
      }
    },
  },
  mounted() {
    this.isLoading = false;
  }
};

</script>
<style scoped>
.text-area{

}
</style>