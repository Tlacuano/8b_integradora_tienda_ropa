<template>
  <b-modal
      id="productDetail"
      @show="getByIdRequestSellProduct"
      @hide="modalClosed"
      centered
      size="lg"
      hide-footer
      hide-header
      ok-only
  >
    <b-container>
      <b-row>
        <b-col class="text-right">
          <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('productDetail')"/>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-center">
          <h3>Detalles de solicitud para venta de producto</h3>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="12" lg="6" class="mt-lg-3 order-md-2 order-lg-1 order-sm-2">
          <b-col class="mb-3">
            <strong>Usuario:</strong> <p style="display: inline;">{{product.userEmail}}</p>
          </b-col>
          <b-col class="mb-3">
            <strong style="display: inline;">Nombre:</strong> <p style="display: inline;">{{product.productName}}</p>
          </b-col>
          <b-col class="mb-3">
            <strong style="display: inline;">Categoria:</strong> <p style="display: inline;">{{product.category}}</p>
          </b-col>
          <b-col class="mb-3">
            <strong style="display: inline;">Descripción:</strong>
          </b-col>
        </b-col>
        <b-col cols="12"lg="6" class="mt-lg-3 order-md-1 order-lg-1 order-sm-1">
          <b-col class="mb-3">
            <strong style="display: inline;">Precio:</strong> <p style="display: inline;">${{product.price}}</p>
          </b-col>
          <b-col class="mb-3">
            <strong style="display: inline;">Stock:</strong> <p style="display: inline;">{{product.amount}}</p>
          </b-col>
          <b-col class="mb-3">
            <strong style="display: inline;">Subcategoria:</strong> <p style="display: inline;">{{product.subcategory}}</p>
          </b-col>
        </b-col>
        <b-col cols="12"lg="12" class="mt-lg-3 order-md-3 order-lg-1 order-sm-3" >
          <b-row>
            <b-col>
              <b-card class="description">
                <b-card-text>
                  {{product.description}}
                </b-card-text>
              </b-card>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row>
        <b-col md="4" lg="3" class="img-product mt-4" v-for="image in images" :key="image.idImage">
          <img :src="image.image"/>
        </b-col>
      </b-row>
      <br/>
      <b-row>
        <b-col class="text-right">
          <b-button class="btn-accept" @click="acceptRequestSellProduct">Aprobar</b-button>
          <span style="margin-left: 10px;"></span> <!-- Añade espacio entre los botones -->
          <b-button variant="danger" @click="openModal(product.idRequestSellProduct)" >Rechazar</b-button>
        </b-col>
      </b-row>
      <RejectionReason @request-success="handleRequestSuccess" @request-error="handleRequestError" :product-id="selectProductId"/>
    </b-container>
  </b-modal>
</template>
<script>
import ProductSalesRequestsService from '@/services/request-seller-product/RequestSellerProduct';
import RejectionReason from "@/views/request-seller-product/RejectionReason.vue";
import {showWarningToast} from "@/components/alerts/alerts";
export default {
  name: "ProductSalesRequestsDetails",
  components: {RejectionReason},
  props: {
    productId: String,
  },
  data() {
    return {
      product: {
        amount: 0,
        category: '',
        description: '',
        idRequestSellProduct: '',
        images: [],
        price: 0,
        productId: '',
        productName: '',
        subcategory: '',
        userEmail: ''
      },
      images:null,
      selectProductId: null
    };
  },
  methods: {
    async getByIdRequestSellProduct(){
      this.showOverlay();
      const response = await ProductSalesRequestsService.getByIdProductSalesRequest(this.productId)
      if(response){
        this.product = response.data;
        this.images = response.data.images;
        this.showOverlay()
      }else{
        this.showOverlay()
        showWarningToast("No se pudo obtener la información del producto")
      }
    },
    async acceptRequestSellProduct(){
      const response = await ProductSalesRequestsService.putProductSalesRequestStatus(this.productId, 'Aprobado','sin comentarios')
      this.$emit('request-success', response);
      this.$bvModal.hide('productDetail')
    },
    openModal(productId){
      this.selectProductId = productId;
      this.$nextTick(() => {
        this.$bvModal.show("rejectionReason");
      });
    },
    modalClosed() {
      this.$emit('modal-closed')
    },
    showOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    },
    handleRequestSuccess(response) {
      this.$emit('request-success', response);
      this.$bvModal.hide('productDetail');
    },
    handleRequestError(error) {
      this.$emit('request-error', error);
    },
  },


}
</script>
<style scoped>
.description{
  background-color: #D9D9D9;
  width: 95%;
  margin: auto;
  border: none;
}
.img-product img{
  width: 100%;
  height: auto;
  object-fit: cover;
}
.img-product{
  width: 100px;
}
.btn-accept{
  background-color: var(--black-base) !important;
  color: white;
}
</style>