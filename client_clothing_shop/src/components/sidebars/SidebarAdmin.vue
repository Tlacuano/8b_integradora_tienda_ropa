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
        {id: 2, title: 'Subcategorías', icon: 'fa-solid fa-folder-tree', to: 'ADMINSubcategoryManagement'}
      ]

    };
  },
}
</script>

<style scoped>

</style>