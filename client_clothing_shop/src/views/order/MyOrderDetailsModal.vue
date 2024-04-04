<template>
  <b-modal id="my-order-details-modal" hide-header hide-footer centered size="md">
    <b-row>
      <b-col class="text-right">
        <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('my-order-details-modal')"/>
      </b-col>
    </b-row>
    <b-row>
      <b-col class="text-center">
          <h1>Orden</h1>
      </b-col>
    </b-row>
    <b-row class="mt-3">
      <b-col cols="4">
        <b-row>
          <b-img class="ml-3" :src="findMainImage(Order.product.productGallery)" style="width: 150px"/>
        </b-row>
      </b-col>
      <b-col cols="8">
        <b-row >
          <b-col cols="12">
            <h5>
              {{Order.product.productName}}
            </h5>
          </b-col>
          <b-col cols="12">
            <div class="text-truncate">
              {{Order.product.description}}
            </div>
          </b-col>
        </b-row>
        <b-row class="mt-auto">
          <b-col cols="12">
            <div class="text-secondary">
              ${{Order.product.price}}
            </div>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
    <hr/>
    <b-row>
      <b-col>
        <b-row>
          <b-col class="text-center mb-3">
            <b>
              Fecha de compra:
            </b>
            {{fecha}}
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <b-card class="card-description">
              <b-row>
                <b-col class="text-center">
                  <b>Detalles de precío</b>
                  <hr class="m-0"/>
                </b-col>
              </b-row>
              <b-row class="mt-2">
                <b-col >
                  <b>
                    Cantidad:
                  </b>
                  {{Order.amount}}
                </b-col>
              </b-row>
              <b-row class="mt-2">
                <b-col >
                  <b>
                    Total:
                  </b>
                  ${{Order.amount * Order.product.price}}
                </b-col>
              </b-row>

            </b-card>
          </b-col>
          <b-col>
            <b-card class="card-description">
              <b-row>
                <b-col class="text-center">
                  <b>Detalles de envío</b>
                  <hr class="m-0"/>
                </b-col>
              </b-row>
              <b-row class="mt-2">
                <b-col >
                  <b>
                    Dirección:
                  </b>
                  <small>{{address.address + ", " + address.street  + ", " + address.neighborhood + ", " + address.state + ", CP: " + address.postalCode}}</small>
                </b-col>
              </b-row>

            </b-card>
          </b-col>
        </b-row>
      </b-col>

    </b-row>
  </b-modal>
</template>

<script >
export default {
  props: {
    Order: {},
    fecha: '',
    address : {}
  },
  name: "MyOrderDetailsModal",
  data() {
    return {

    }
  },
  methods: {

    findMainImage(gallery) {
      const mainImage = gallery.find(image => image.status.status === 'Principal');
      return mainImage ? mainImage.image : 'url_de_imagen_por_defecto_si_no_hay_principal';
    },
  },
}
</script>


<style scoped>
.card-description{
  height: 180px;
  overflow: hidden;
}
</style>