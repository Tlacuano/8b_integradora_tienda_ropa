<template>
  <b-container fluid>
    <b-row class="mt-3">
      <b-col class="text-center">
        <h1>Cuentas registradas</h1>
      </b-col>
    </b-row>

    <b-row class="mt-4" align-h="between">
      <b-col cols="12" lg="4">
        <b-form-group>
          <div class="position-relative">
            <b-form-input id="search" type="text" placeholder="Buscar..." class="pr-5"></b-form-input>
            <font-awesome-icon icon="magnifying-glass" class="search-icon"></font-awesome-icon>
          </div>
        </b-form-group>
      </b-col>
      <b-col cols="auto" class="text-right">
        <b-button variant="dark">Registrar</b-button>
      </b-col>
    </b-row>

    <b-row class="mt-3">
      <b-col class="container-users">

      </b-col>
    </b-row>

    <b-row>
      <b-col>
        <b-pagination

        ></b-pagination>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import PeopleService from "@/services/user/UserService";

export default {
  name: "UserManagement",
  data() {
    return {
      objetPagination:{
        page: 1,
        size: 24,
        elements: 0,
      },
      people:[],
    };
  },
  methods:{
    async getPageUsers(){
      const response = await PeopleService.getPageUsersService(this.objetPagination);
      console.log(response)
      this.people = response.data.content;
      console.log(this.people)
    }
  },
  mounted() {
    this.getPageUsers();
  }
}
</script>

<style scoped>
.container-users{
  min-height: 100px !important;
  overflow-x: hidden ;
}
</style>