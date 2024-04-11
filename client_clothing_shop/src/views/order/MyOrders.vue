<template>
  <b-container class="interface">
    <b-row>
      <b-col>
        <b-row>
          <b-col class="text-center mb-4">
            <h1>Mis compras</h1>
          </b-col>
        </b-row>

        <section v-if="orders.length > 0">
          <b-row v-for="order in orders" :id="order.idOrder" class="mb-4">
            <b-col>
              <b-list-group>
                <b-list-group-item>
                  <b-row>
                    <b-col cols="4">
                      <b>
                        {{order.orderDate}}
                      </b>
                    </b-col>
                    <b-col cols="8" class="text-right">
                      <div class="text-truncate">
                        Numero de orden: {{order.orderNumber}}
                      </div>
                    </b-col>
                  </b-row>
                </b-list-group-item>
                <b-list-group-item v-for="product in order.orderHasProducts" class="highlight-on-hover">
                  <b-row>
                    <b-col cols="auto">
                      <b-img @click="getProductDetails(product)" :src="findMainImage(product.product.productGallery)" class="selectable" style="width: 100px"/>
                    </b-col>
                    <b-col class="my-auto">
                      <b-row>
                        <b-col>
                          <b-row>
                            <b-col cols="8" md="6" >
                              <b>
                                {{product.product.productName}}
                              </b>
                              <div class="text-truncate text-black-50">
                                Cantidad: {{product.amount}}
                              </div>
                            </b-col>
                            <b-col cols="8" md="5" >
                              <div class="text-truncate text-secondary">
                                ${{calculateProductPrice(product)}}
                              </div>
                            </b-col>
                          </b-row>
                        </b-col>

                        <b-col cols="auto">
                          <b-dropdown
                              variant="link-dark"
                              toggle-class="text-decoration-none"
                              no-caret
                          >
                            <template v-slot:button-content>
                              <font-awesome-icon icon="ellipsis-v"/>
                            </template>
                            <b-dropdown-item @click="seeDetails(order, product)">
                              Ver detalles
                            </b-dropdown-item>
                          </b-dropdown>
                        </b-col>
                      </b-row>
                    </b-col>
                  </b-row>
                </b-list-group-item>
              </b-list-group>
            </b-col>
          </b-row>
        </section>

        <section v-else>
          <b-row>
            <b-col class="text-center">
              <h3>No tienes compras realizadas</h3>
            </b-col>
          </b-row>
        </section>

      </b-col>
    </b-row>
    <MyOrderDetailsModal :Order="OrderSelected" :Product="ProductSelected"/>
  </b-container>
</template>

<script>
import OrderService from "@/services/order/OrderService";
import Big from "big.js";
import {codeCrypto} from "@/utils/security/cryptoJs";

export default {
  name: 'MyOrders',
  components: {
    MyOrderDetailsModal: () => import('@/views/order/MyOrderDetailsModal.vue')
  },
  data() {
    return {
      orders: [],
      pagination:{
        page: 1,
        size: 10,
        elements: 0
      },
      OrderSelected: {},
      ProductSelected: {},
    }
  },
  methods: {


    async getOrders(){
      this.showOverlay()
      const payload = {
        email:this.$store.getters.getEmail
      };
      const pagination = {
        page: this.pagination.page - 1,
        size: this.pagination.size
      }

      const response = await OrderService.getOrdersByEmailService(payload, pagination);

      this.pagination.elements = response.data.totalElements;
      this.orders = response.data.content;
      this.showOverlay()

    },

    getProductDetails(product){
      const encodedId = codeCrypto(product.product.idProduct);
      this.$router.push({name: 'UserProductDetails', params: {id: encodedId}});
    },

    seeDetails(order, product){
      this.OrderSelected = order
      this.ProductSelected = product
      this.orderNumberForReturn = order.orderNumber;
      console.log(this.OrderSelected)
      console.log(this.ProductSelected)
      this.$root.$emit('bv::show::modal', 'my-order-details-modal');
    },


    showOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    },
    findMainImage(gallery) {
      const mainImage = gallery.find(image => image.status.status === 'Principal');
      return mainImage ? mainImage.image : 'url_de_imagen_por_defecto_si_no_hay_principal';
    },
    calculateProductPrice(product) {
      const price = new Big(product.product.price);
      const quantity = new Big(product.amount);
      return price.times(quantity);
    },
  },
  mounted() {
    this.getOrders();
  }
}
</script>

<style>

.interface{
  overflow-y: auto;
}

</style>