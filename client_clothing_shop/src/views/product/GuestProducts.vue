<template>
  <div class="interface pb-3">
    <div v-show="!selectedCategory">
      <b-row class="full-page" no-gutters>
        <b-col v-for="category in categories" :key="category.idCategory" cols="12" lg="4" class="">
          <b-card
              :img-src="category.image"
              class="mb-2 selectable zoom-on-hover"
              :title="category.category"
              header-class="text-center"
              @click="selectCategory(category.category)"
              overlay
              img-height="850px"
          >
          </b-card>
        </b-col>
      </b-row>
    </div>
    <div v-show="selectedCategory" class="pb-5">
      <b-row class="mt-2 px-3" align-h="between">
        <b-col cols="12" lg="4">
          <b-form-group>
            <div class="position-relative">
              <b-form-input @keyup.enter="getProducts()" v-model="searchQuery" id="search" type="text"
                placeholder="Buscar..." class="pr-5"></b-form-input>
              <font-awesome-icon icon="magnifying-glass" class="search-icon" />
            </div>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row v-if="products.length > 0" no-gutters>
        <b-col v-for="product in products" :key="product.idProduct" cols="12" sm="6" md="4" lg="3" class="p-3">
          <b-card :img-src="product.productGallery[0].image" img-alt="Image" img-top tag="article"
            class="mb-2 selectable zoom-on-hover h-100" @click="selectProduct(product.idProduct)" img-height="350px"
            style="width: 100%">
            <b-card-text class="text-left">
              <b-row no-gutters>
                <b-col cols="8">
                  <p class="mb-0 font-weight-bold">{{ product.productName }}</p>
                </b-col>
                <b-col cols="4" class="text-right">
                  <b-button pill variant="light" class="wishlist-btn p-0" @click.stop="wishlistProduct(product)">
                    <b-icon class="icon-container" :icon="product.isInWishlist ? 'heart-fill' : 'heart'" />
                  </b-button>
                </b-col>
              </b-row>
              <p class="mb-0">$ {{ product.price }} MXN</p>
              <p class="mb-0">{{ product.amount }} disponibles</p>
            </b-card-text>
          </b-card>
        </b-col>
      </b-row>
      <b-row v-else class="full-page" no-gutters>
        <b-col class="text-center" cols="12">
          <h3>No existen productos con los datos que especificaste</h3>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-pagination v-model="objectPagination.page" :total-rows="objectPagination.elements"
            :per-page="objectPagination.size" aria-controls="my-table"></b-pagination>
        </b-col>
      </b-row>
    </div>

    <LoginModal />
  </div>
</template>

<script>
import ProductService from "@/services/product/ProductService";
import WishListService from "@/services/wish-list/WishListService";
import CategoryService from "@/services/category/CategoryService";
import { codeCrypto } from "@/utils/security/cryptoJs";
import { mapGetters } from "vuex";

export default {
  name: "GuestProducts",
  components: {
    LoginModal: () => import("@/views/auth/LoginModal.vue"),
  },

  data() {
    return {
      search: "",
      selectedCategory: this.$route.params.category || "",
      selectedSubcategory: this.$route.params.subcategory || "",
      products: [],
      categories: [],
      objectPagination: {
        page: 1,
        size: 32,
        elements: 0
      },
      searchQuery: ""
    };
  },

  computed: {
    ...mapGetters(["isLoggedIn"]),
  },

  methods: {
    async getCategories() {
      this.showOverlay();
      const response = await CategoryService.getCategories();
      if (response.status === 200) {
        this.categories = response.data.content;
      }
      this.showOverlay();
    },

    async getProducts() {
      this.showOverlay();
      let response;
      let payload;
      if (this.searchQuery) {
        payload = {
          query: this.searchQuery,
          category: this.selectedCategory,
          subcategory: this.selectedSubcategory,
          email: this.$store.getters.getEmail || ""
        };
        response = await ProductService.getProductsByQuery(payload, this.objectPagination);
      } else if (this.selectedSubcategory) {
        payload = {
          category: this.selectedCategory,
          subcategory: this.selectedSubcategory,
          email: this.$store.getters.getEmail || ""
        };
        response = await ProductService.getProductsBySubcategory(payload, this.objectPagination);
      } else {
        payload = {
          category: this.selectedCategory,
          email: this.$store.getters.getEmail || ""
        };
        response = await ProductService.getProductsByCategory(payload, this.objectPagination);
      }
      if (response.status === 200) {
        this.products = response.data.content;
        this.objectPagination.elements = response.data.totalElements;
        await this.checkWishlist();
      }
      this.showOverlay();
    },

    async checkWishlist() {
      if (this.isLoggedIn) {
        const userEmail = this.$store.getters.getEmail;
        const wishlistResponse = await WishListService.getWishList(userEmail);
        if (wishlistResponse.status === 200 && wishlistResponse.data) {
          const wishlistProducts = wishlistResponse.data;
          this.wishlistProducts = wishlistProducts;
          this.products.forEach(product => {
            this.$set(product, 'isInWishlist', wishlistProducts.some(wishlistProduct => wishlistProduct.product.idProduct === product.idProduct));
          });
        }
      } else {
        this.products.forEach(product => {
          product.isInWishlist = false;
        });
      }
    },

    async addToWishlist(productId) {
      if (!this.isLoggedIn) {
        this.$bvModal.show("login-modal");
      }
      const userEmail = this.$store.getters.getEmail;

      const payload = {
        idProduct: productId,
        email: userEmail
      };

      const response = await WishListService.postWishList(payload);
      if (response && response.status === 200) {
        await this.checkWishlist();
      }
    },

    async removeFromWishlist(productId) {
      if (!this.isLoggedIn) {
        this.$bvModal.show("login-modal");
      }
      if (!this.wishlistProducts) {
        return;
      }

      const entryToRemove = this.wishlistProducts.find(entry => entry.product.idProduct === productId);
      if (!entryToRemove) {
        return;
      }
      const wishlistEntryId = entryToRemove.idWish;
      const response = await WishListService.deleteWishList(wishlistEntryId);
      if (response.status === 200) {
        await this.checkWishlist();
      }
    },

    async wishlistProduct(product) {
      if (!this.isLoggedIn) {
        this.$bvModal.show("login-modal");
      }
      const productId = product.idProduct;
      if (product.isInWishlist) {
        await this.removeFromWishlist(productId);
      } else {
        await this.addToWishlist(productId);
      }
    },

    selectProduct(productId) {
      const encodedId = codeCrypto(productId);
      this.$router.push({ name: 'UserProductDetails', params: { id: encodedId } });
    },

    showOverlay() {
      this.$store.dispatch('changeStatusOverlay');
    },

    selectCategory(category) {
      this.selectedCategory = category;
      this.$router.push({ name: 'UserProductsCategory', params: { category: category } });
    },

    resetFilters() {
      this.selectedSubcategory = "";
      this.searchQuery = "";
    },

    updateCategory(newCategory) {
      this.selectedCategory = newCategory || "";
      this.resetFilters();
      if (newCategory) {
        this.getProducts();
      } else {
        this.getCategories();
      }
    },

    updateSubcategory(newSubcategory) {
      this.selectedSubcategory = newSubcategory || "";
      this.searchQuery = "";
      this.getProducts();
    },
  },

  watch: {
    '$route.params.category'(newCategory) {
      this.updateCategory(newCategory);
    },

    '$route.params.subcategory'(newSubcategory) {
      this.updateSubcategory(newSubcategory);
    },

    objectPagination: {
      handler() {
        this.getProducts();
      },
      deep: true
    }
  },

  created() {
    if (this.selectedCategory) {
      this.getProducts();
    } else {
      this.getCategories();
    }
  },
}
</script>

<style scoped>
.interface {
  padding-bottom: calc(8vh);
}

.category-card {
  height: 100%;
}

.full-page {
  height: calc(100vh - 100px);
}

.wishlist-btn {
  background-color: transparent;
  border: none;
  font-size: 1.2rem;
}

.card-title {
  display: flex;
  justify-content: center;
  color: white;
}

.card-body {
  height: 100%;
}

.icon-container {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>