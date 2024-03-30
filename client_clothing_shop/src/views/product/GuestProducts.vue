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
      <b-row no-gutters>
        <b-col class="px-3" cols="12" xl="3" lg="4">
          <b-form-group class="mb-0">
            <b-input-group>
              <b-input-group-prepend is-text>
                <b-icon icon="search"/>
              </b-input-group-prepend>
              <b-form-input
                  id="search"
                  v-model="search"
                  type="search"
                  placeholder="Buscar producto..."
              ></b-form-input>
            </b-input-group>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row no-gutters>
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
                  <b-icon icon="heart"/>
                </b-col>
              </b-row>
              <p class="mb-0">{{ product.price }}</p>
              <p class="mb-0">{{ product.amount }} disponibles</p>
            </b-card-text>
          </b-card>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
import ProductService from "@/services/product/ProductService";
import CategoryService from "@/services/category/CategoryService";

export default {
  name: "GuestProducts",
  data() {
    return {
      search: "",
      selectedCategory: this.$route.params.category || "",
      selectedSubcategory: this.$route.params.subcategory || "",
      products: [],
      categories: []
    };
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
      console.log("Getting category products")
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

    selectProduct(productId) {

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
</style>