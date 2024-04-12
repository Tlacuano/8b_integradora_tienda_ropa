<template>
  <div>
  <b-modal id="my-order-details-modal" hide-header hide-footer centered size="md">
    <section v-if="Order && Product && Product.status">
      <b-row>
        <b-col class="text-right">
          <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('my-order-details-modal')"/>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-center">
          <h1>Pedido</h1>
        </b-col>
      </b-row>

      <b-row class="mx-1 mt-3">
        <b-col>
          <b >
            {{Order.orderDate}}
          </b>
        </b-col>
        <b-col>
          <div class="text-right">
            <b-badge>
              {{Product.status.status}}
            </b-badge>
          </div>
        </b-col>
      </b-row>

      <b-row class="mt-1">
        <b-col>
          <b-list-group>
            <b-list-group-item>
              <b-row>
                <b-col class="text-center">
                  <b>
                    Detalles de pago
                  </b>
                </b-col>
              </b-row>
            </b-list-group-item>
            <b-list-group-item>
              <b-row>
                <b-col>
                  <b>
                    Productos ({{Product.amount}})
                  </b>
                </b-col>
                <b-col class="text-right">
                  <span class="text-secondary">
                    ${{calculateProductPrice(Product)}}
                  </span>
                </b-col>
              </b-row>

              <b-row>
                <b-col>
                  <span>
                    Envío
                  </span>
                </b-col>
                <b-col class="text-right">
                  <span class="text-secondary underline small">
                    Gratis
                  </span>
                </b-col>
              </b-row>
              <hr>
              <b-row>
                <b-col>
                  <b>
                    Total
                  </b>
                </b-col>
                <b-col class="text-right">
                  <b>
                    ${{calculateProductPrice(Product)}}
                  </b>
                </b-col>
              </b-row>
            </b-list-group-item>
          </b-list-group>
        </b-col>
      </b-row>

      <b-row class="mt-3">
        <b-col>
          <b-list-group>
            <b-list-group-item>
              <b-row>
                <b-col class="text-center">
                  <b>
                    Detalles de envío
                  </b>
                </b-col>
              </b-row>
            </b-list-group-item>
            <b-list-group-item>
              <b-row>
                <b-col>
                  <p class="text-justify mb-0">
                    {{Order.address.address + ", " + Order.address.street  + ", " + Order.address.neighborhood + ", " + Order.address.state + ", CP: " + Order.address.postalCode}}
                  </p>
                </b-col>
              </b-row>
            </b-list-group-item>
          </b-list-group>
        </b-col>
      </b-row>

      <b-row class="mt-3">
        <b-col>
          <b-card no-body>
            <b-row>
              <b-col>
                <b-img :src="findMainImage(Product.product.productGallery)" class="img-fluid"/>
              </b-col>
              <b-col cols="8">
                <b-card-text class="mt-2">
                  <h5 class="mb-1">
                    {{Product.product.productName}}
                  </h5>
                  <span class="text-secondary">
                    Cantidad: {{Product.amount}}
                  </span>
                  <p class="mt-4">
                    ${{Product.product.price}}
                  </p>
                </b-card-text>

              </b-col>
            </b-row>
          </b-card>
        </b-col>
      </b-row>

      <b-row class="mt-5">
        <b-col>
          <b-list-group>
            <b-list-group-item :class="Product.status.status === 'Preparación' ? 'highlight-on-hover selectable' : ''">
              <b-row>
                <b-col>
                  Cancelar compra
                </b-col>
                <b-col class="text-right" v-if="Product.status.status !== 'Preparación'">
                  <font-awesome-icon icon="lock" />
                </b-col>
              </b-row>
            </b-list-group-item>

            <b-list-group-item :class="Product.status.status === 'Entregado' ? 'highlight-on-hover selectable' : ''" @click="openReturnRequestModal">
              <b-row>
                <b-col>
                  Solicitar devolución
                </b-col>
                <b-col class="text-right" v-if="Product.status.status !== 'Entregado'">
                  <font-awesome-icon icon="lock" />
                </b-col>
              </b-row>
            </b-list-group-item>

          </b-list-group>
        </b-col>
      </b-row>

      <b-row class="mt-4 pl-2">
        <b-col>
          <b-link @click.prevent="openPrivacyPolicy()" class="pr-3">
            <span class="font-weight-bold small underline">Leer términos, condiciones y políticas de privacidad</span>
          </b-link>
        </b-col>
      </b-row>
    </section>
  </b-modal>
    <return-request-modal :order-number="Order.orderNumber" ref="returnRequestModal"/>
  </div>
</template>

<script >
import Big from "big.js";
import ReturnRequestModal from "../order/PostRequestReturnProduct.vue";

export default {
  components: {
    ReturnRequestModal
  },
  props: {
    Order: {},
    Product:{}
  },
  name: "MyOrderDetailsModal",
  data() {
    return {
      orderNumber: this.Order.orderNumber
    }
  },
  methods: {

    findMainImage(gallery) {
      const mainImage = gallery.find(image => image.status.status === 'Principal');
      return mainImage.image;
    },
    calculateProductPrice(product) {
      const price = new Big(product.product.price);
      const quantity = new Big(product.amount);
      return price.times(quantity);
    },

    openPrivacyPolicy() {
      window.open('/privacy-policy', '_blank');
    },
    openReturnRequestModal() {
      if (this.Order && this.Product && this.Product.status.status === 'Entregado') {
        this.$refs.returnRequestModal.openModal();
      }
    }
  }
  }
</script>


<style scoped>

</style>