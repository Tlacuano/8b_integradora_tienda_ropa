<template>
  <aside>
    <b-sidebar id="sidebar-seller"
               backdrop
               shadow
    >
      <b-nav vertical>
        <b-nav-item class="selectable highlight-on-hover" :to="{name:'Profile'}">
          <ProfileComponent/>
        </b-nav-item>
        <hr>
        <b-nav-item v-for="item in items" :key="item.id" :to="{name:item.to}" class="selectable highlight-on-hover ">
          <b>
            <font-awesome-icon :icon="item.icon" class="mr-2"/>
            {{item.title}}
          </b>
        </b-nav-item>

      </b-nav>
      <template #footer v-if="$store.getters.isLoggedIn">
        <b-button class="main-button" @click="logout">
          Cerrar sesión
        </b-button>
      </template>
    </b-sidebar>
  </aside>
</template>

<script >
export default {
  name: "SidebarSeller",
  components: {
    ProfileComponent : () => import('@/components/sidebars/component/ProfileComponent.vue'),
    ProductManagement : () => import('@/views/product-management/ProductManagement.vue')

  },
  data() {
    return {
      items:[
        {id:1, title:"Gestión de Productos", icon:"fa-solid fa-box", to:"ProductManagement"},
      ]
    };
  },
  methods: {
    logout() {
      this.$store.dispatch('logout');
    }
  }
}
</script>

<style scoped>

</style>