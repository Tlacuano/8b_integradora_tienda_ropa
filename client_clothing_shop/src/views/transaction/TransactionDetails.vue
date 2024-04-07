<template>
  <div class="interface">
    <b-row no-gutters>
      <b-col class="text-center mb-3">
        <h1>Finalizar compra</h1>
      </b-col>
    </b-row>
    <b-row no-gutters>
      <b-col cols="12" lg="5" class="container-products">
        <b-col cols="12">
          <h4>Resumen de la compra</h4>
          <p>Por favor, revisa los productos que deseas comprar.</p>
        </b-col>
        <b-row no-gutters>
          <b-col v-for="cartElement in shoppingCart" :key="cartElement.idShoppingCart" cols="12" class="mb-2">
            <b-card
                :title="cartElement.product.productName"
                :img-src="cartElement.product.gallery[0].image"
                img-alt="article-img"
                img-left
                img-width="100"
                style="max-height: 8rem;"
                class="h-100"
            >
              <b-card-text>
                <b-row no-gutters>
                  <b-col cols="6">
                    <span><b>${{ calculateProductTotal(cartElement) }}</b></span>
                  </b-col>
                  <b-col cols="6" class="text-right">
                    <span v-if="cartElement.amount > 1">{{ cartElement.amount }} unidades</span>
                    <span v-else>{{ cartElement.amount }} unidad</span>
                  </b-col>
                </b-row>
              </b-card-text>
            </b-card>
          </b-col>
        </b-row>
      </b-col>
      <b-col cols="12" lg="7">
        <b-card no-body class="h-100 mt-2">
          <b-list-group flush>
            <b-list-group-item>
              <b-row no-gutters>
                <b-col cols="12">
                  <b>Dirección de envío</b>
                </b-col>
                <b-col cols="11">
                  <p>
                    <p class="text-muted mb-0">{{ selectedAddress.address }} {{ selectedAddress.status === 'Predeterminada' ? '(Predeterminada)' : ''}}</p>
                    <p class="text-muted">
                      {{ selectedAddress.street }}, {{ selectedAddress.neighborhood }} C.P.
                      {{ selectedAddress.postalCode }}, {{ selectedAddress.state }}.
                      {{ selectedAddress.referencesAddress }}
                    </p>
                  </p>
                </b-col>
                <b-col cols="1" class="text-right selector">
                  <b-dropdown variant="link" toggle-class="text-decoration-none" no-caret>
                    <template #button-content>
                      <font-awesome-icon class="text-black-50" icon="chevron-down" />
                    </template>
                    <b-dropdown-item v-for="address in addresses" :key="address.idAddress" @click="selectedAddress = address">
                      {{ address.address }}
                    </b-dropdown-item>
                  </b-dropdown>
                </b-col>
                <b-col cols="12">
                  <b>Método de pago</b>
                </b-col>
              </b-row>
            </b-list-group-item>
          </b-list-group>
        </b-card>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import ShoppingCartService from "@/services/shopping-cart/ShoppingCartService";
import Big from "big.js";
import AddressesManagement from "@/services/adressess-management/AddressesManagement";

export default {
  name: 'TransactionDetails',
  data() {
    return {
      // Shopping cart
      shoppingCart: [],
      totalProducts: 0,
      total: 0,
      // Address
      addresses: [],
      selectedAddress: {},
    }
  },

  methods: {
    async getShoppingCartProducts() {
      this.showOverlay()
      const payload = {
        email: this.$store.getters.getEmail
      };
      const response = await ShoppingCartService.getOrdersByEmailService(payload);
      this.showOverlay()
      if (response.status === 200) {
        this.shoppingCart = response.data;
        this.totalProducts = response.data.reduce((acumulado, producto) => acumulado + producto.amount, 0);
        this.total = this.calculateTotal(response.data);
      }
    },

    async getUserAddresses() {
      const email = this.$store.getters.getEmail
      const response = await AddressesManagement.getAddressByEmailService(email);
      if (response.status === 200) {
        this.addresses = response.data;
        this.selectedAddress = this.addresses[0];
        this.addresses = this.addresses.filter(address => address.status !== 'Deshabilitada');
      }
    },

    calculateTotal(products) {
      let total = new Big(0);
      products.forEach(producto => {
        const price = new Big(producto.product.price);
        const quality = new Big(producto.amount);
        total = total.plus(price.times(quality));
      });
      return total
    },

    calculateProductTotal(product) {
      const price = new Big(product.product.price);
      const quantity = new Big(product.amount);
      return price.times(quantity);
    },

    showOverlay() {
      this.$store.dispatch('changeStatusOverlay');
    },
  },

  mounted() {
    this.getShoppingCartProducts();
    this.getUserAddresses();
  }
}
</script>

<style scoped>
.container-products {
  max-height: calc(100vh - 175px);
  overflow-y: auto;
  overflow-x: hidden;
}

.selector {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>