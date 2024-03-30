<template>
  <section>
    <b-row>
      <b-col class="text-center">
        <h1>Cuentas registradas</h1>
      </b-col>
    </b-row>

    <b-row class="mt-4" align-h="between">
      <b-col cols="12" lg="4">
        <b-form-group>
          <div class="position-relative">
            <b-form-input @input="getPageUsers" v-model="search" id="search" type="text" placeholder="Buscar..." class="pr-5"></b-form-input>
            <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
          </div>
        </b-form-group>
      </b-col>
      <b-col cols="auto" class="text-right">
        <b-button v-b-modal:post-user-modal variant="dark">Registrar</b-button>
      </b-col>
    </b-row>

    <b-row class="mt-3 container-users">
      <b-col>
        <b-row>
          <b-col lg="4" v-for="person in people"  :key="person.id">
            <b-card
              no-body
              class="highlight-on-hover mb-2"
              >
              <b-row class="m-2" no-gutters>
                <b-col cols="auto" class="d-none d-md-block px-2 my-auto">
                  <b-avatar
                      size="2.5rem"
                      :src="person.picture || null"
                      variant="dark"
                  />

                </b-col>

                <b-col cols="8" class="ml-2 ">
                  <b-row>
                    <b-col>
                      <div class="text-truncate font-weight-bold small">{{person.email}}</div>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <div class="text-ellipsis text-secondary small">{{person.fullName}}</div>
                    </b-col>
                  </b-row>

                  <b-row>
                    <b-col>
                      <div class="text-ellipsis text-black-50 small">{{person.roles}}</div>
                    </b-col>
                  </b-row>
                </b-col>

                <b-col  class="text-right">
                  <b-dropdown
                    variant="link-dark"
                    toggle-class="text-decoration-none"
                    no-caret
                    >
                    <template v-slot:button-content>
                      <font-awesome-icon icon="ellipsis-v"/>
                    </template>
                    <b-dropdown-item @click="changeStatusUser(person.email)">
                      <div v-if="!person.status">
                        Habilitar
                      </div>
                      <div v-else>
                        Deshabilitar
                      </div>
                    </b-dropdown-item>
                    <b-dropdown-item @click="seeUserDetails(person.email)">
                      Ver más detalles
                    </b-dropdown-item>
                  </b-dropdown>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>

      </b-col>
    </b-row>

    <b-row>
      <b-col>
        <b-pagination
          v-model="objetPagination.page"
          :total-rows="objetPagination.elements"
          :per-page="objetPagination.size"
          aria-controls="my-table"
        ></b-pagination>
      </b-col>
    </b-row>

    <PostUserModal/>
  </section>
</template>

<script>
import PeopleService from "@/services/user/userService";
import {showInfoAlert} from "@/components/alerts/alerts";
import {codeCrypto} from "@/utils/security/cryptoJs";

export default {
  name: "UserManagement",
  components: {
    PostUserModal: () => import("@/views/auth/PostUser.vue"),
  },
  data() {
    return {
      objetPagination:{
        page: 1,
        size: 24,
        elements: 0,
      },
      people:[],
      search: null
    };
  },
  methods:{
    async getPageUsers(){
      console.log(this.search)

      if(this.search === null || this.search === ""){
        this.showOverlay()
        const response = await PeopleService.getPageUsersService(this.objetPagination);
        this.showOverlay()

        this.objetPagination.elements = response.data.totalElements;
        this.people = response.data.content;
      }else{
        const payload ={
          email : this.search
        }
        const response = await PeopleService.getPageUsersByEmailService(this.objetPagination, payload);

        this.objetPagination.elements = response.data.totalElements;
        this.people = response.data.content;
      }
    },

    async changeStatusUser(dato){
      const payoad = {
        email:dato
      }
      showInfoAlert(
        "¿Estás seguro?",
        "¿Deseas cambiar el estado de la cuenta?",
        "Sí, cambiar",
        async () => {
          await PeopleService.putStatusUserService(payoad);
        }
      )
    },

    seeUserDetails(email){
      const codeParam = codeCrypto(email);
      this.$router.push(`/user-details/${codeParam}`);
    },

    showOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    }
  },
  mounted() {
    this.getPageUsers();
  }
}
</script>

<style scoped>
.container-users{
  height: calc(100vh - 300px);
  overflow-x: hidden;
}
</style>