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
            <b-row v-if="cart.length >0">
              <b-col class="mb-3" cols="12" v-for="product in cart" :id="product.idShoppingCart">
                <b-card
                    no-body
                    draggable="true"
                    @dragstart="dragStar($event, product)"
                >
                  <b-row>
                    <b-col cols="4 my-auto">
                      <b-img :src="findMainImage(product.product.gallery)" class="img-fluid"/>
                    </b-col>
                    <b-col cols="7">
                      <b-row class="mt-3">
                        <b-col>
                          <h5 class="mb-1 d-none d-lg-block">
                            {{ product.product.productName }}
                          </h5>
                          <b class="d-lg-none">
                            {{ product.product.productName }}
                          </b>
                        </b-col>
                      </b-row>
                      <b-row>
                        <b-col>
                          <div class="text-black-50 text-truncate">
                            {{ product.product.category }}
                          </div>
                        </b-col>
                      </b-row>
                      <b-row class="mt-3 d-none d-lg-block">
                        <b-col cols="12">
                          <div class="text-truncate text-secondary small">
                            {{ product.product.description }}
                          </div>
                          <hr/>
                        </b-col>
                      </b-row>
                      <b-row>
                        <b-col cols="auto my-auto">
                          <span class="pr-4">Cantidad</span>
                            <font-awesome-icon icon="fa-solid fa-minus-circle" class="selectable"
                                                 @click="decreaseAmount(product)"/>
                              <span class="mx-2">{{ product.amount }}</span>
                            <font-awesome-icon icon="fa-solid fa-plus-circle" class="selectable"
                                                 @click="increaseAmount(product)"/>
                        </b-col>
                      </b-row>

                      <b-row class="mt-lg-5">
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
                          <span><b>${{ calculateProductPrice(product) }}</b></span>
                        </b-col>
                      </b-row>
                    </b-col>
                  </b-row>
                </b-card>
              </b-col>
            </b-row>
            <b-row v-else>
              <b-col>
                <h3 class="text-center">No hay productos en el carrito</h3>
              </b-col>
            </b-row>
          </b-col>

          <b-col lg="4">
            <b-row>
              <b-col>
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
                        <span>Productos ({{ products }})</span>
                      </b-col>
                      <b-col class="text-right">
                        <span>${{ total }}</span>
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
                        <span><b>${{ total }}</b></span>
                      </b-col>
                    </b-row>
                    <b-row class="mt-3">
                      <b-col>
                        <b-button @click="finishOrder" class="mt-3 main-button">Continuar compra</b-button>
                      </b-col>
                    </b-row>
                  </b-list-group-item>
                </b-list-group>
              </b-col>
            </b-row>
            <b-row class="mt-2">
              <b-col>
                <b-card
                    class="text-center"
                    @drop="drop($event)"
                    @dragover.prevent
                    @dragenter.prevent
                >
                  <span class="text-secondary">Arrastra aqui para eliminar del carrito</span>
                </b-card>
              </b-col>
            </b-row>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
  </section>
</template>

<script>
import ShoppingCartService from "@/services/shopping-cart/ShoppingCartService";
import Big from "big.js";
import Vue from "vue";
import {codeCrypto} from "@/utils/security/cryptoJs";

export default {
  name: "ShoppingCart",
  data() {
    return {
      cart: [],
      products: null,
      total: null
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
      this.cart = response.data;
      this.products = response.data.reduce((acumulado, producto) => acumulado + producto.amount, 0);
      this.total = this.calcTotal(response.data);
    },

    async increaseAmount(product) {
      const payload = {
        idShoppingCart: product.idShoppingCart,
        amount: product.amount + 1,
        product: product.product
      }
      this.showOverlay()
      const response = await ShoppingCartService.putShoppingCartService(payload);
      this.showOverlay()
      if (response) {
        this.getCart();
      }
    },

    async decreaseAmount(product) {
      if (product.amount > 1) {
        const payload = {
          idShoppingCart: product.idShoppingCart,
          amount: product.amount - 1,
        }
        this.showOverlay()
        const response = await ShoppingCartService.putShoppingCartService(payload);
        this.showOverlay()
        if (response) {
          await this.getCart();
        }
      } else {
        await this.deleteProductInCart(product.idShoppingCart)
      }
    },

    getProductDetails(product) {
      const encodedId = codeCrypto(product.product.idProduct);
      this.$router.push({name: 'UserProductDetails', params: {id: encodedId}});
    },

    async deleteProductInCart(id) {
      Vue.swal({
        title: '¿Estás seguro?',
        text: "¿Deseas eliminar el producto del carrito?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: 'var( --black-base)',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar',
      }).then(async (result) => {
        if (result.isConfirmed) {
          this.showOverlay()
          const payload = {
            idShoppingCart: id,
          }
          const response = await ShoppingCartService.deleteShoppingCartService(payload);
          this.showOverlay()
          if (response.status === 200) {
            await this.getCart();
          }
        }
      })
    },

    finishOrder() {
      this.$router.push({name: 'TransactionDetails'});
    },

    dragStar(event, item) {
      event.dataTransfer.dropEffect = "move";
      event.dataTransfer.effectAllowed = "move";
      event.dataTransfer.setData("itemID", item.idShoppingCart);
    },

    async drop(event) {
      event.preventDefault();
      const id = event.dataTransfer.getData("itemID");

      await this.deleteProductInCart(id);

    },

    showOverlay() {
      this.$store.dispatch('changeStatusOverlay');
    },

    findMainImage(gallery) {
      const mainImage = gallery.find(image => image.status.status === 'Principal');
      return mainImage ? mainImage.image : 'url_de_imagen_por_defecto_si_no_hay_principal';
    },

    calcTotal(products) {
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
.container-products {
  max-height: calc(100vh - 175px);
  overflow-y: auto;
  overflow-x: hidden;
}

</style>