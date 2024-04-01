<template>
  <aside>
    <b-sidebar id="sidebar-buyer"
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
      <template #footer>
        <b-button class="main-button" @click="logout">
          Cerrar sesión
        </b-button>
      </template>
    </b-sidebar>
  </aside>
</template>

<script >
export default {
  name: "SidebarBuyer",
  components: {
    ProfileComponent : () => import('@/components/sidebars/component/ProfileComponent.vue')
  },
  data() {
    return {
      items:[]
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