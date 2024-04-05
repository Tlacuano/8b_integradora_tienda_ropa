<template>
  <aside>
    <b-sidebar id="sidebar-admin"
      backdrop
      shadow
    >

      <b-nav vertical>
        <b-nav-item class="selectable highlight-on-hover " :to="{name:'Profile'}">
          <ProfileComponent/>
        </b-nav-item>
        <hr>
        <b-nav-item v-if="this.$store.getters.getRole === 'SUPERADMIN'" class="selectable highlight-on-hover " :to="{name:'AdminManagement'}">
          <b>
            <font-awesome-icon icon="fa-solid fa-user-tie" class="mr-2"/>
            Administradores registrados
          </b>
        </b-nav-item>
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
import Profile from "@/views/user/Profile.vue";

export default {
  name: "SidebarAdmin",
  computed: {
    Profile() {
      return Profile
    }
  },
  components: {
    ProfileComponent : () => import('@/components/sidebars/component/ProfileComponent.vue')
  },
  data() {
    return {
      items:[
        {id: 1, title: 'Cuentas registradas', icon: 'fa-solid fa-user', to: 'ADMINUserManagement'},
        {id: 2, title: 'Solicitudes de venta', icon: 'fa-solid fa-shopping-cart', to: 'ProductSaleRequest'},
        {id: 3, title: 'Subcategorías', icon: 'fa-solid fa-folder-tree', to: 'ADMINSubcategoryManagement'},
        {id: 4, title: 'Solcitudes de vendedor', icon: 'fa-solid fa-user-plus', to: 'ADMINRequestBecomeSellerManagement'},
        {id: 5, title: 'Categorías', icon: 'fa-solid fa-layer-group', to: 'ADMINCategoryManagement'},
        {id: 6, title: 'Solicitudes de cambio de datos', icon: 'fa-solid fa-user-edit', to: 'ADMINRequestDataChangeManagement'},
        {id: 7, title: 'Solicitudes de devolución', icon: 'fa-solid fa-box-open', to: 'ADMINRequestsReturnProductManagement'},
        {id: 8, title: "Pedidos", icon: "fa-solid fa-box", to: "ADMINOrderManagement"},
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