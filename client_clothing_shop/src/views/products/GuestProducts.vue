<template>
  <div>
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
          sm="6" s
          md="4"
          lg="3"
          class="mb-4 p-3"
      >
        <b-card
            :img-src="product.productGallery[0].imageUrl"
            img-alt="Image"
            img-top
            tag="article"
            class="mb-2 selectable zoom-on-hover"
        >
          <b-row no-gutters>
            <b-col cols="12" lg="8">
              <b-card-text class="text-left">
                <p class="mb-0 font-weight-bold">{{ product.productName }}</p>
                <p class="mb-0">{{ product.price }}</p>
                <p>{{ product.amount }} disponibles</p>
              </b-card-text>
            </b-col>
            <b-col cols="12" lg="4" class="">
              <b-row class="h-50" no-gutters>
                <b-col cols="12" class="text-right">
                  <b-icon icon="heart"/>
                </b-col>
              </b-row>
            </b-col>
          </b-row>
        </b-card>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import ProductService from "@/services/product/ProductService";

export default {
  name: "GuestProducts",
  data() {
    return {
      search: "",
      selectedCategory: this.$route.params.category || "",
      selectedSubcategory: this.$route.params.subcategory || "",
      products: [],
    };
  },

  methods: {
    async getCategoryProducts() {
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

    getSubcategoryProducts() {
      if (!this.selectedCategory || !this.selectedSubcategory) {
        return;
      }
      const payload = {
        category: this.selectedCategory,
        subcategory: this.selectedSubcategory
      };
      this.showOverlay();
      ProductService.getProductsBySubcategory(payload)
          .then(response => {
            if (response.status === 200) {
              this.products = response.data;
            }
            this.showOverlay();
          });
    },

    showOverlay() {
      this.$store.dispatch('changeStatusOverlay');
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
    if (this.selectedCategory && this.selectedSubcategory) {
      this.getSubcategoryProducts();
    } else if (this.selectedCategory) {
      this.getCategoryProducts();
    }
  }
}
</script>

<style scoped>

</style>