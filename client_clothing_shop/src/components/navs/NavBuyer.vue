<template>
  <div>
    <b-nav align="center" class="">
      <b-nav-item @click="selectedCategoryID = category.id" :active="selectedCategoryID === category.id"
                  v-for="category in categories" :key="category.id">
        {{ category.text }}
      </b-nav-item>
    </b-nav>
  </div>
</template>

<script>
export default {
  name: "NavBuyer",
  data() {
    return {
      selectedCategoryID: 1,
      categories: [
        {text: "Playeras", id: 1},
        {text: "Chamarras", id: 2},
        {text: "Pantalones", id: 3},
        {text: "Vestidos", id: 4},
        {text: "Zapatos", id: 5},
        {text: "Accesorios", id: 6},
        {text: "Ropa Interior", id: 7},
      ]
    };
  },
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