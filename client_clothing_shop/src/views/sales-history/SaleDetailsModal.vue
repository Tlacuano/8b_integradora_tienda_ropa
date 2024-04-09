<template>
  <b-modal id="sale-details-modal" hide-header hide-footer centered>
    <b-row>
      <b-col class="text-right">
        <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('sale-details-modal')"/>
      </b-col>
    </b-row>

    <b-row class="mt-2">
      <b-col class="text-center">
        <h1>Detalles de la venta</h1>
      </b-col>
    </b-row>

    <section v-if="order != null && order.productGallery != null && order.price != null">
      <b-row class="mt-3">
        <b-col>
          <b-list-group>
            <b-list-group-item>
              <b-row>
                <b-col class="text-center">
                  <b>
                    Detalles de la venta
                  </b>
                </b-col>
              </b-row>
            </b-list-group-item>

            <b-list-group-item>
              <b-row>
                <b-col>
                  <b>
                    Total
                  </b>
                </b-col>
                <b-col class="text-right">
                  <span>
                    ${{calculateProductPrice()}}
                  </span>
                </b-col>
              </b-row>
            </b-list-group-item>

            <b-list-group-item>
              <b-row>
                <b-col>
                  <b>
                    Numero de orden
                  </b>
                </b-col>
                <b-col class="text-right">
                  <span>
                    {{order.orderNumber}}
                  </span>
                </b-col>
              </b-row>
            </b-list-group-item>

            <b-list-group-item>
              <b-row>
                <b-col>
                  <b>
                    fecha de compra
                  </b>
                </b-col>
                <b-col class="text-right">
                  <span>
                    {{order.orderDate}}
                  </span>
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
                <b-img :src="findMainImage(order.productGallery)" class="img-fluid"/>
              </b-col>
              <b-col cols="8">
                <b-card-text class="mt-2">
                  <h5 class="mb-1">
                    {{order.productName}}
                  </h5>
                  <span class="text-secondary">
                      Cantidad: {{order.amount}}
                  </span>
                  <p class="mt-4">
                    ${{order.price}}
                  </p>
                </b-card-text>

              </b-col>
            </b-row>
          </b-card>
        </b-col>
      </b-row>

      <b-row class="mt-4">
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
              {{order.address}}
            </b-list-group-item>
          </b-list-group>
        </b-col>
      </b-row>

      <b-row class="mt-4">
        <b-col>
          <b-card no-body class="selectable highlight-on-hover" >
            <b-row align-h="between" class="p-2 mx-1">
              <b-col>
                <b>
                  Marcar como enviado
                </b>
              </b-col>
              <b-col class="text-right">
                <font-awesome-icon icon="fa-solid fa-angle-right"/>
              </b-col>
            </b-row>
          </b-card>
        </b-col>
      </b-row>

      <b-row class="my-2">
        <b-col>
          <b-card no-body class="selectable highlight-on-hover" >
            <b-row align-h="between" class="p-2 mx-1 text-danger">
              <b-col>
                Cancelar compra
              </b-col>
              <b-col class="text-right">
                <font-awesome-icon icon="fa-solid fa-angle-right"/>
              </b-col>
            </b-row>
          </b-card>
        </b-col>
      </b-row>
    </section>
  </b-modal>
</template>

<script>
import Big from "big.js";

export default {
  name: 'SaleDetailsModal',
  props: {
    order: {
      type: Object,
      required: true
    }
  },
  methods: {



    calculateProductPrice() {
      const price = new Big(this.order.price);
      const quantity = new Big(this.order.amount);
      return price.times(quantity);
    },
    findMainImage(gallery) {
      const mainImage = gallery.find(image => image.status.status === 'Principal');
      return mainImage.image;
    },
  },
  //watcher para que
  watch: {



  }
}
</script>


<style scoped>

</style>