<template>
  <div>
    <b-nav v-if="selectedCategory" align="center" class="">
      <b-nav-item v-for="subcategory in subcategories" :key="subcategory.subcategory"
                  @click="selectSubcategory(subcategory)"
                  :active="selectedSubcategory === subcategory.subcategory"
      >
        {{ subcategory.subcategory }}
      </b-nav-item>
    </b-nav>
  </div>
</template>

<script>
import SubcategoryService from "@/services/subcategory/SubcategoryService";

export default {
  name: "NavBuyer",
  data() {
    return {
      selectedCategory: this.$route.params.category || "",
      selectedSubcategory: "",
      subcategories: []
    };
  },

  methods: {
    async getSubcategories() {
      if (!this.selectedCategory) {
        return;
      }
      const payload = {
        category: this.selectedCategory
      };
      this.showOverlay();
      const response = await SubcategoryService.getSubcategories(payload);
      if (response.status === 200) {
        this.subcategories = response.data;
      }
      this.showOverlay();
    },

    selectSubcategory(subcategory) {
      this.selectedSubcategory = subcategory.subcategory;
      this.$router.push({name: 'UserProductsSubcategory', params: {subcategory: subcategory.subcategory}});
    },

    showOverlay() {
      this.$store.dispatch('changeStatusOverlay');
    }
  },

  watch: {
    '$route.params.category'(newCategory) {
      this.selectedSubcategory = "";
      this.selectedCategory = newCategory;
      this.getSubcategories();
    }
  },

  created() {
    this.getSubcategories();
  }
}
</script>


<style scoped>
.nav {
  max-width: 100%;
}

.nav-item {
  padding: 0 10px;
}

.nav-link {
  color: #000;
}

.nav-link.active {
  color: #000;
  font-weight: bold;
  transition: 0.2s;
}

.nav-link:hover {
  background-color: #f8f9fa;
  transition: 0.2s;
  border-radius: 5px;
}
</style>