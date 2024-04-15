<template>
  <div>
    <b-row v-if="product.productName" class="p-4" no-gutters>
      <b-col cols="12" xl="5" lg="4">
        <b-carousel id="product-carousel" v-model="slide" :interval="10000" controls indicators class="mb-3">
          <b-carousel-slide v-for="(image, index) in product.productGallery" :key="index" :img-src="image.image"
                            alt="article" img-height="100%"></b-carousel-slide>
        </b-carousel>
      </b-col>
      <b-col cols="12" xl="7" lg="8" class="pl-4">
        <b-row no-gutters>
          <h3>
            <b>{{ product.productName }} </b>
          </h3>
        </b-row>
        <h4 class="mb-4">$ {{ product.price }} MXN</h4>
        <div v-if="product.amount > 0">
          <p class="m-0"><b>Stock disponible</b></p>
          <p v-if="product.amount !== 1">Cantidad: {{ product.amount }}</p>
          <p v-else>¡Última pieza!</p>
        </div>
        <div v-else>
          <p class="mb-4 text-black-50"><b>Producto agotado</b></p>
        </div>
        <h4 class="m-0">Descripción</h4>
        <p class="mb-4">{{ product.description }}</p>
        <p class="mb-0">Categoría: {{ product.category }}</p>
        <p>Subcategoría: {{ product.subcategory }}</p>
        <b-row no-gutters>
          <b-col cols="12" lg="4">
            <b-button variant="dark" @click="addToCart(product)" :disabled="product.amount <= 0">
              <span v-if="product.amount > 0">Añadir al carrito</span>
              <span v-else>Producto agotado</span>
            </b-button>
            <b-button pill variant="light" class="wishlist-btn ml-4 p-0" @click="wishlistProduct(product)">
              <b-icon class="icon-container" :icon="isWishlisted ? 'heart-fill' : 'heart'"/>
            </b-button>
          </b-col>
        </b-row>
      </b-col>

      <b-col cols="12">
        <ReviewsProduct :idProduct="product.idProduct"/>
      </b-col>
    </b-row>
    <b-row v-else class="p-4" no-gutters>
      <b-col cols="12" class="text-center">
        <h3>El producto que intentaste buscar no existe</h3>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import ProductService from "@/services/product/ProductService";
import {mapGetters} from "vuex";
import ShoppingCartService from "@/services/shopping-cart/ShoppingCartService";
import WishListService from '@/services/wish-list/WishListService';
import {showSuccessToast, showWarningToast} from "@/components/alerts/alerts";

export default {
  name: "ProductDetails",
  components: {
    ReviewsProduct: () => import("@/views/reviews/ReviewsProduct.vue")
  },
  data() {
    return {
      // Product details
      selectedProductId: this.$route.params.id || "",
      product: {},
      productIsWishlisted: false,
      // Carousel
      slide: 0
    };
  },

  computed: {
    isWishlisted() {
      return this.productIsWishlisted;
    },
    ...mapGetters(["isLoggedIn"]),
  },

  methods: {
    async getProduct() {
      if (!this.selectedProductId) {
        await this.$router.push({name: "GuestProducts"});
        return;
      }
      const response = await ProductService.getProductById(this.selectedProductId);
      if (response.status === 200) {
        this.product = response.data;
        await this.checkProductIsWishlisted();
      }
    },

    async wishlistProduct(product) {
      if (!this.isLoggedIn) {
        this.$bvModal.show("login-modal");
      } else {
        try {
          const payload = {
            email: this.$store.getters.getEmail,
            idProduct: product.idProduct,
          }

          if (this.isWishlisted) {
            const response = await WishListService.getWishList(this.$store.getters.getEmail)
            const wishList = response.data;
            if (wishList && wishList.length > 0) {
              const wishListId = wishList.find(item => item.product.idProduct === product.idProduct).idWish;
              await WishListService.deleteWishList(wishListId)
              if (response.data) {
                showSuccessToast('', 'Producto eliminado de la lista de deseos');
                this.productIsWishlisted = false;
                this.hideOverlay();
              }
            }
          } else {
            const response = await WishListService.postWishList(payload);
            if (response.data) {
              showSuccessToast('', 'Producto añadido a la lista de deseos');
              this.productIsWishlisted = true;
              await this.checkProductIsWishlisted();
              this.hideOverlay();
            }
          }
        } catch (error) {
          showWarningToast('Error', 'Error al agregar el producto a la lista de deseos');
        } finally {
          this.showOverlay();
        }
      }
    },

    async addToCart(product) {
      if (!this.isLoggedIn) {
        this.$bvModal.show("login-modal");
      } else {
        this.showOverlay()
        const payload = {
          email: this.$store.getters.getEmail,
          idProduct: product.idProduct,
        }
        const response = await ShoppingCartService.postShoppingCartService(payload);
        this.showOverlay()
        if (response.data) showSuccessToast('', 'Producto añadido al carrito');
      }
    },
    async checkProductIsWishlisted() {
      if (this.$store.getters.isLoggedIn) {
        try {
          const response = await WishListService.getWishList(this.$store.getters.getEmail)
          if (response && response.data) {
            const wishlist = response.data;
            const isWishlisted = wishlist.some(item => item.product.idProduct === this.product.idProduct);
            this.productIsWishlisted = isWishlisted;
            return isWishlisted;
          } else {
            return false;
          }
        } catch (error) {
          return false;
        }
      }
    },

    showOverlay() {
      this.$store.dispatch('changeStatusOverlay');
    },
    hideOverlay() {
      this.$store.dispatch('changeStatusOverlay');
    },
  },
  created() {
    this.getProduct();
  }
};
</script>

<style scoped>
.wishlist-btn {
  background-color: transparent;
  border: none;
  font-size: 1.5rem;
}

.icon-container {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>