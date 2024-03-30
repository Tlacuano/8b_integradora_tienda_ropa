<template>
  <b-modal id="switch-user-role-modal" hide-header hide-footer centered @shown="getCurrentRole" >
    <b-container>
      <b-row>
        <b-col class="text-right
        ">
          <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('switch-user-role-modal')"/>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-center">
          <h1>Cambiar de rol</h1>
        </b-col>
      </b-row>
      <b-row class="mt-3">
        <b-col>
          <b-card class="selectable highlight-on-hover text-center rol-container zoom-on-hover" @click="changeRole('BUYER')">
            <span class="rol">
              <font-awesome-icon icon="cart-shopping" />
            </span>
            <p class="m-0 p-0">Comprador</p>
            <span v-if="currentRole==='BUYER'" class="text-black-50 small p-0 m-0">
              Rol actual
            </span>
          </b-card>
        </b-col>
        <b-col>
          <b-card class="selectable highlight-on-hover text-center rol-container zoom-on-hover" @click="changeRole('SELLER')">
            <span class="rol">
              <font-awesome-icon icon="store" class="mr-2"/>
            </span>
            <p class="p-0 m-0">Vendedor</p>
            <span v-if="currentRole==='SELLER'" class="text-black-50 small p-0 m-0">
              Rol actual
            </span>
          </b-card>
        </b-col>
      </b-row>
      <b-row class="mt-4 mb-2">
        <b-col class="text-center">
          <span class="text-black-50 small">
            Cambiar de rol provocará la pérdida de todos los cambios que no hayas guardado.
          </span>
        </b-col>
      </b-row>
    </b-container>
  </b-modal>
</template>

<script>
export default {
  name: 'SwitchUserRoleModal',
  data() {
    return {
      currentRole: ''
    }
  },
  methods: {
    changeRole(role) {
      if(role !== this.currentRole){
        this.$store.state.role = role;
        this.$bvModal.hide('switch-user-role-modal');
        window.location.reload();
      }
    },
    getCurrentRole(){
      this.currentRole = this.$store.getters.getRole;
    }
  },
}
</script>

<style scoped>
.rol{
  font-size: calc(90px);
}
.rol-container{
  height: 220px;
}
</style>