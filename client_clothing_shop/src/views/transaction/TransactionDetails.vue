<template>
  <div class="interface">
    <b-row no-gutters>
      <b-col class="text-center mb-3">
        <h1>Finalizar compra</h1>
      </b-col>
    </b-row>
    <b-row no-gutters align-h="between">
      <b-col cols="12" lg="6" class="container-products">
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
      <b-col cols="12" lg="5">
        <b-card no-body class="h-100 mt-2">
          <b-list-group flush>
            <b-list-group-item>
              <b-row no-gutters>
                <b-col cols="12">
                  <b>Dirección de envío</b>
                </b-col>
                <b-col cols="11">
                  <p class="text-muted mb-0">{{ selectedAddress.address }}
                    {{ selectedAddress.status === 'Predeterminada' ? '(Predeterminada)' : '' }}</p>
                  <p class="text-muted">
                    {{ selectedAddress.street }}, {{ selectedAddress.neighborhood }} C.P.
                    {{ selectedAddress.postalCode }}, {{ selectedAddress.state }}.
                    {{ selectedAddress.referencesAddress }}
                  </p>
                </b-col>
                <b-col cols="1" class="text-right selector">
                  <b-dropdown variant="link" toggle-class="text-decoration-none" no-caret>
                    <template #button-content>
                      <font-awesome-icon class="text-black-50" icon="chevron-down"/>
                    </template>
                    <b-dropdown-item v-for="address in addresses" :key="address.idAddress"
                                     @click="selectedAddress = address">
                      {{ address.address }}
                    </b-dropdown-item>
                  </b-dropdown>
                </b-col>
                <b-col cols="12">
                  <b>Método de pago</b>
                </b-col>
                <b-col cols="11">
                  <p class="text-muted mb-0">{{ censorCardNumber(selectedPaymentCard.cardNumber) }}
                    {{ selectedPaymentCard.status === 'Predeterminada' ? '(Predeterminada)' : '' }}</p>
                  <p class="text-muted">1
                    {{ selectedPaymentCard.cardholderName }}. Fecha de expiración: {{
                      selectedPaymentCard.expirationDate
                    }}
                  </p>
                </b-col>
                <b-col cols="1" class="text-right selector">
                  <b-dropdown variant="link" toggle-class="text-decoration-none" no-caret>
                    <template #button-content>
                      <font-awesome-icon class="text-black-50" icon="chevron-down"/>
                    </template>
                    <b-dropdown-item v-for="card in paymentCards" :key="card.idPaymentCard"
                                     @click="selectedPaymentCard = card">
                      {{ censorCardNumber(card.cardNumber) }}
                    </b-dropdown-item>
                  </b-dropdown>
                </b-col>
                <b-col cols="12">
                  <b>Fecha estimada de entrega</b>
                </b-col>
                <b-col cols="12">
                  <p class="text-muted mb-0">
                    {{ calculateShippingDate }}
                  </p>
                </b-col>
              </b-row>
            </b-list-group-item>
            <b-list-group-item>
              <b-row no-gutters>
                <b-col cols="6">
                  <b>Subtotal</b>
                </b-col>
                <b-col cols="6" class="text-right">
                  <p>${{ total }}</p>
                </b-col>
                <b-col cols="6">
                  <b>Envío</b>
                </b-col>
                <b-col cols="6" class="text-right">
                  <p class="mb-0">GRATIS</p>
                </b-col>
              </b-row>
            </b-list-group-item>
            <b-list-group-item>
              <b-row no-gutters>
                <b-col cols="6">
                  <h4><b>Total</b></h4>
                </b-col>
                <b-col cols="6" class="text-right mb-2">
                  <h4>${{ total }}</h4>
                </b-col>
                <b-col cols="12">
                  <b-button @click="createCheckoutSession" variant="dark" block>Finalizar compra</b-button>
                </b-col>
              </b-row>
            </b-list-group-item>
            <b-list-group-item>
              <b-row no-gutters>
                <b-col cols="12">
                  <p class="text-muted mb-0">Al hacer clic en "Finalizar compra", aceptas los Términos y Condiciones
                    de uso y la Política de privacidad de la empresa.</p>
                </b-col>
                <b-col class="mb-3" cols="12">
                  <b-link @click.prevent="openPrivacyPolicy()">
                    <span
                        class="font-weight-bold small underline">Leer términos, condiciones y políticas de privacidad</span>
                  </b-link>
                </b-col>
                <b-col cols="12">
                  <p class="text-muted"> Recuerda que tienes 30 días naturales para realizar la devolución del
                    pedido a partir de la fecha de la entrega. Conoce más sobre nuestras políticas de devolución en el
                    siguiente enlace:</p>
                </b-col>
                <b-col cols="12" class="mb-3">
                  <b-link @click.prevent="openPrivacyPolicy()">
                    <span class="font-weight-bold small underline">Políticas de devolución</span>
                  </b-link>
                </b-col>
                <b-col cols="12">
                  <p class="text-muted mb-0">
                    De parte de Klein & Iversen, agradecemos tu preferencia y confianza en nuestros servicios.
                  </p>
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
import PaymentCardService from "@/services/payment-card/PaymentCardService";
import TransactionService from "@/services/transaction/TransactionService";

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
      // Payment card
      paymentCards: [],
      selectedPaymentCard: {}
    }
  },

  computed: {
    calculateShippingDate() {
      const today = new Date();
      const shippingDate = new Date(today.getTime() + 14 * 24 * 60 * 60 * 1000);
      const formatOptions = {weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'};
      return shippingDate.toLocaleDateString('es-ES', formatOptions).replace(/^\w/, c => c.toUpperCase());
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

    async getUserPaymentCards() {
      const payload = {
        email: this.$store.getters.getEmail
      };
      const response = await PaymentCardService.getPaymentCardsByUserEmail(payload);
      if (response.status === 200) {
        this.paymentCards = response.data.content;
        this.selectedPaymentCard = this.paymentCards[0];
        this.paymentCards = this.paymentCards.filter(card => card.status !== 'Deshabilitada');
      }
    },

    censorCardNumber(cardNumber) {
      if (!cardNumber) return '';
      return cardNumber.replace(/\d(?=\d{4})/g, "*");
    },

    async createCheckoutSession() {
      this.showOverlay();
      const payload = {
        total: this.total,
        description: `Orden de ${this.totalProducts} artículos`,
        email: this.$store.getters.getEmail,
        idAddress: this.selectedAddress.idAddress,
        idPaymentCard: this.selectedPaymentCard.idPaymentCard
      };
      const response = await TransactionService.createCheckoutSession(payload);
      if (response.status === 200) {
        await this.$store.dispatch('prepareForReload')
        window.location.href = response.data.transactionUrl;
      }
      this.showOverlay();
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

    openPrivacyPolicy() {
      window.open('/privacy-policy', '_blank');
    },

    showOverlay() {
      this.$store.dispatch('changeStatusOverlay');
    },
  },

  mounted() {
    if (!this.$store.getters.isLoggedIn) {
      this.$router.push({name: 'Login'});
    }
    this.getShoppingCartProducts();
    if (this.shoppingCart.length === 0) {
      this.$router.push({name: 'Home'});
    }
    this.getUserAddresses();
    this.getUserPaymentCards();
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