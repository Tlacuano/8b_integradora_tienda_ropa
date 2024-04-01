<template>
  <aside>
    <b-sidebar id="sidebar-admin"
      backdrop
      shadow
    >
      <b-nav vertical>
        <b-nav-item v-for="item in items" :key="item.id" :to="{name:item.to}" class="selectable highlight-on-hover ">
          <h5>
            <font-awesome-icon :icon="item.icon" class="mr-2"/>
            {{item.title}}
          </h5>
        </b-nav-item>
      </b-nav>
    </b-sidebar>
  </aside>
</template>

<script >
export default {
  name: "SidebarAdmin",
  data() {
    return {
      items:[
        {id: 1, title: 'Cuentas registradas', icon: 'fa-solid fa-user', to: 'ADMINUserManagement'},
        {id: 2, title: 'Categorías', icon: 'fa-solid fa-layer-group', to: 'ADMINCategoryManagement'},
        {id: 3, title: 'Subcategorías', icon: 'fa-solid fa-folder-tree', to: 'ADMINSubcategoryManagement'},
        {id: 4, title: 'Pedidos', icon: 'fa-solid fa-shopping-cart', to: 'ADMINOrderManagement'},
        {id: 5, title: 'Solicitudes de venta', icon: 'fa-solid fa-shopping-cart', to: 'ProductSaleRequest'},
        {id: 6, title: 'Solicitudes de vendedor', icon: 'fa-solid fa-user-plus', to: 'ADMINRequestBecomeSellerManagement'},
        {id: 7, title: 'Solicitudes de cambio de datos', icon: 'fa-solid fa-user-edit', to: 'ADMINRequestDataChangeManagement'},
      ]

    };
  },
}
</script>

<style scoped>

</style>