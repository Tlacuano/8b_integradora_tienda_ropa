<template>
  <section class="interface">
    <b-row>
      <b-col>
        <b-row>
          <b-col class="text-center">
            <h1>Carrito de compras</h1>
          </b-col>
        </b-row>

        <b-row class="mt-3">
          <b-col class="container-products" lg="8">
            <b-row>
              <b-col class="mb-3" cols="12" v-for="product in cart" :id="product.idShoppingCart">
                <b-card
                  tag="article"
                  no-body
                >
                  <b-row>
                    <b-col cols="auto" class="pr-0 my-auto ml-sm-1">
                      <img :src="findMainImage(product.product.gallery)" class="d-lg-none ml-3" style="width: 100px; height: auto;">
                      <img :src="findMainImage(product.product.gallery)" class="d-none d-lg-block" style="width: 240px; height: auto;">
                    </b-col>
                    <b-col class="mr-5">
                      <b-row class="mt-3">
                        <b-col>
                          <h5 class="text-truncate m-0">{{product.product.productName}}</h5>
                        </b-col>
                      </b-row>
                      <b-row class="mt-3">
                        <b-col>
                          <div class="text-truncate d-none d-lg-block">
                            <span><span><b>{{product.product.category}}</b> / </span><span class="text-black-50">{{product.product.subcategory}}</span></span>
                          </div>
                        </b-col>
                      </b-row>
                      <b-row>
                        <b-col>
                          <div class="text-truncate d-none d-lg-block">
                            {{product.product.description}}
                          </div>
                        </b-col>
                      </b-row>
                      <hr class="d-none d-lg-block">
                      <b-row class="mt-lg-4 mb-2 mb-lg-0">
                        <b-col lg="6">
                          <b-row>
                            <b-col cols="auto my-auto">

                              <span class="pr-4">Cantidad</span>
                              <font-awesome-icon icon="fa-solid fa-minus-circle" class="selectable" @click="decreaseAmount(product)"/>
                              <span class="mx-2">{{product.amount}}</span>
                              <font-awesome-icon icon="fa-solid fa-plus-circle" class="selectable"  @click="increaseAmount(product)"/>
                            </b-col>
                          </b-row>
                        </b-col>
                        <b-col lg="6">
                          <b-row>
                            <b-col>
                              <p class="d-lg-none p-0 m-0"><small>Envío</small></p>
                              <p class="d-none d-lg-block">Envío</p>
                            </b-col>
                            <b-col class="text-right">
                              <span class="text-secondary underline small">Gratis</span>
                            </b-col>
                          </b-row>
                          <b-row>
                            <b-col>
                              <b class="d-lg-none">Total</b>
                              <h5 class="d-none d-lg-block">Total</h5>
                            </b-col>
                            <b-col class="text-right">
                              <span><b>${{calculateProductPrice(product)}}</b></span>
                            </b-col>
                          </b-row>
                        </b-col>
                      </b-row>
                    </b-col>
                  </b-row>
                </b-card>
              </b-col>
            </b-row>
          </b-col>

          <b-col lg="4">
            <b-list-group>
              <b-list-group-item>
                <b-row>
                  <b-col>
                    <h3 class="pt-2">Resumen de compra</h3>
                  </b-col>
                </b-row>
              </b-list-group-item>
              <b-list-group-item>
                <b-row class="mt-3">
                  <b-col>
                    <span>Productos ({{products}})</span>
                  </b-col>
                  <b-col class="text-right">
                    <span>${{total}}</span>
                  </b-col>
                </b-row>
                <b-row class="mt-1 mb-3">
                  <b-col>
                    <span class="small">Envío</span>
                  </b-col>
                  <b-col class="text-right">
                    <span class="text-secondary underline small">Gratis</span>
                  </b-col>
                </b-row>
                <hr>
                <b-row class="mt-2">
                  <b-col>
                    <h5>Total</h5>
                  </b-col>
                  <b-col class="text-right">
                    <span><b>${{total}}</b></span>
                  </b-col>
                </b-row>
                <b-row class="mt-3">
                  <b-col>
                    <b-button class="mt-3 main-button">Continuar compra</b-button>
                  </b-col>
                </b-row>
              </b-list-group-item>
            </b-list-group>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
  </section>
</template>

<script>
import ShoppingCartService from "@/services/shopping-cart/ShoppingCartService";
import Big from "big.js";

export default {
  name: "ShoppingCart",
  data() {
    return {
      cart: [],
      products:null,
      total:null
    };
  },
  methods: {
    async getCart() {
      this.showOverlay()
      const payload = {
        email: this.$store.getters.getEmail
      }
      const response = await ShoppingCartService.getOrdersByEmailService(payload);
      this.showOverlay()
      console.log(response.data)
      this.cart = response.data;
      //calcular el total de productos
      this.products = response.data.reduce((acumulado, producto) => acumulado + producto.amount, 0);
      this.total = this.calcTotal(response.data);

    },
    showOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    },
    findMainImage(gallery) {
      const mainImage = gallery.find(image => image.status.status === 'Principal');
      return mainImage ? mainImage.image : 'url_de_imagen_por_defecto_si_no_hay_principal';
    },
    calcTotal(products){
      let total = new Big(0);
      products.forEach(producto => {
        const price = new Big(producto.product.price);
        const quality = new Big(producto.amount);
        total = total.plus(price.times(quality));
      });
      return total
    },
    calculateProductPrice(product) {
      const price = new Big(product.product.price);
      const quantity = new Big(product.amount);
      return price.times(quantity);
    }
  },
  mounted() {
    this.getCart();
  }
}
</script>

<style>
.container-products{
  max-height: calc(100vh - 175px);
  overflow-y: auto;
  overflow-x: hidden;
}

</style>