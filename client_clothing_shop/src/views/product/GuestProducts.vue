<template>
  <div>
    <div v-if="!selectedCategory">
      <b-row class="full-page" no-gutters>
        <b-col v-for="category in categories" :key="category.idCategory" cols="12" lg="4" class="p-4">
          <b-card
              :img-src="category.image"
              class="mb-2 selectable zoom-on-hover category-card"
              :title="category.category"
              @click="selectCategory(category.category)"
          >
          </b-card>
        </b-col>
      </b-row>
    </div>
    <div v-else>
      <b-row class="mt-4 px-3" align-h="between">
        <b-col cols="12" lg="4">
          <b-form-group>
            <div class="position-relative">
              <b-form-input id="search" type="text" placeholder="Buscar..." class="pr-5"></b-form-input>
              <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
            </div>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row v-if="products.length > 0" no-gutters>
        <b-col
            v-for="product in products"
            :key="product.idProduct"
            cols="12"
            sm="6"
            md="4"
            lg="3"
            class="p-3"
        >
          <b-card
              :img-src="product.productGallery[0].image"
              img-alt="Image"
              img-top
              tag="article"
              class="mb-2 selectable zoom-on-hover h-100"
              @click="selectProduct(product.idProduct)"
          >
            <b-card-text class="text-left">
              <b-row no-gutters>
                <b-col cols="8">
                  <p class="mb-0 font-weight-bold">{{ product.productName }}</p>
                </b-col>
                <b-col cols="4" class="text-right">
                  <b-button pill variant="light" class="wishlist-btn p-0" @click.stop="wishlistProduct">
                    <b-icon class="icon-container" :icon="false ? 'heart-fill' : 'heart'"/>
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
    </div>

    <LoginModal/>
  </div>
</template>

<script>
import ProductService from "@/services/product/ProductService";
import CategoryService from "@/services/category/CategoryService";
import {codeCrypto} from "@/utils/security/cryptoJs";
import {mapGetters} from "vuex";

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
      categories: []
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

    async getCategoryProducts() {
      if (!this.selectedCategory) {
        await this.getCategories();
        return;
      }
      const payload = {
        category: this.selectedCategory
      };
      this.showOverlay();
      const response = await ProductService.getProductsByCategory(payload);
      if (response.status === 200) {
        this.products = response.data;
      }
      this.showOverlay();
    },

    async getSubcategoryProducts() {
      if (!this.selectedCategory || !this.selectedSubcategory) {
        await this.getCategoryProducts();
        return;
      }
      const payload = {
        category: this.selectedCategory,
        subcategory: this.selectedSubcategory
      };
      this.showOverlay();
      const response = await ProductService.getProductsBySubcategory(payload);
      if (response.status === 200) {
        this.products = response.data;
      }
      this.showOverlay();
    },

    wishlistProduct() {
      if (!this.isLoggedIn) {
        this.$bvModal.show("login-modal");
      }
    },

    selectProduct(productId) {
      const encodedId = codeCrypto(productId);
      this.$router.push({name: 'UserProductDetails', params: {id: encodedId}});
    },

    showOverlay() {
      this.$store.dispatch('changeStatusOverlay');
    },

    selectCategory(category) {
      this.selectedCategory = category;
      this.$router.push({name: 'UserProductsCategory', params: {category: category}});
    }
  },

  watch: {
    '$route.params.category'(newCategory) {
      this.selectedCategory = newCategory;
      this.selectedSubcategory = "";
      this.getCategoryProducts();
    },

    '$route.params.subcategory'(newSubcategory) {
      this.selectedSubcategory = newSubcategory;
      this.getSubcategoryProducts();
    }
  },

  created() {
    // if there is a selected category and subcategory, get the products
    if (this.selectedCategory && this.selectedSubcategory) {
      this.getSubcategoryProducts();
      // if there is a selected category, get the products
    } else if (this.selectedCategory) {
      this.getCategoryProducts();
      // if there is no selected category, get the categories
    } else {
      this.getCategories();
    }
  },
}
</script>

<style scoped>
.full-page {
  height: 80vh;
}

.wishlist-btn {
  background-color: transparent;
  border: none;
  font-size: 1.2rem;
}

.icon-container {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>