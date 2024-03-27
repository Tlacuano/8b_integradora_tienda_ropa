<template>
  <section class="interface">
    <b-row class="main">
      <b-col md="5" class="text-center my-auto">
        <b-row>
          <b-col
          >
            <div
                @mousemove="showEditPicture = true"
                @mouseleave="showEditPicture = false"
            >
              <b-avatar
                  :src="user.picture || null"
                  alt="Image"
                  size="300px"
                  fluid
                  variant="dark"
                  thumbnail
                  class="custom-avatar-badge"
                  badge-variant="secondary"
                  @mouseenter="showEditPicture = true"
                  @mouseleave="showEditPicture = false"

              >
                <template #badge v-if="showEditPicture"  >
                  <font-awesome-icon icon="fa-solid fa-pen" class="selectable"/>
                </template>
              </b-avatar>
            </div>
          </b-col>
        </b-row>
        <b-row class="mt-4 mb-3">
          <b-col>
            <h3>
              {{user.fullName}}
            </h3>
          </b-col>
        </b-row>
        <b-row >
          <b-col>
            <span>
              {{user.email}}
            </span>
          </b-col>
        </b-row>
        <b-row class="mt-1" l>
          <b-col>
            <span>
              {{user.phone}}
            </span>
          </b-col>
        </b-row>

        <b-row v-if="user.seller" class="mt-3">
          <b-col>
            <span>
              <b>RFC:</b> {{user.taxIdentificationNumber}}<br/>
            </span>
            <span>
              <b>CURP:</b> {{user.curp}}
            </span>
          </b-col>
        </b-row>
      </b-col>

      <b-col class="my-auto">
        <b-row v-if="user.buyer || user.seller" class="my-2">
          <b-col>
            <b-card no-body class="selectable highlight-on-hover">
              <b-row align-h="between" class="p-2 mx-1">
                <b-col>
                  <b>
                    Direcciones
                  </b>
                </b-col>
                <b-col class="text-right">
                  <font-awesome-icon icon="fa-solid fa-angle-right"/>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>

        <b-row v-if="user.buyer || user.seller" class="my-2">
          <b-col>
            <b-card no-body class="selectable highlight-on-hover">
              <b-row align-h="between" class="p-2 mx-1">
                <b-col>
                  <b>
                    Tarjetas
                  </b>
                </b-col>
                <b-col class="text-right">
                  <font-awesome-icon icon="fa-solid fa-angle-right"/>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>

        <b-row v-if="user.buyer || user.seller" class="my-2">
          <b-col>
            <b-card no-body class="selectable highlight-on-hover">
              <b-row align-h="between" class="p-2 mx-1">
                <b-col>
                  <b>
                    Privacidad, terminos y condiciones
                  </b>
                </b-col>
                <b-col class="text-right">
                  <font-awesome-icon icon="fa-solid fa-angle-right"/>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>

        <b-row  class="my-2">
          <b-col>
            <b-card no-body class="selectable highlight-on-hover">
              <b-row align-h="between" class="p-2 mx-1">
                <b-col>
                  <b>
                    Información personal
                  </b>
                </b-col>
                <b-col class="text-right">
                  <font-awesome-icon icon="fa-solid fa-angle-right"/>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>

        <b-row class="my-2">
          <b-col>
            <b-card no-body class="selectable highlight-on-hover">
              <b-row align-h="between" class="p-2 mx-1">
                <b-col>
                  <b>
                    Datos de tu cuenta
                  </b>
                </b-col>
                <b-col class="text-right">
                  <font-awesome-icon icon="fa-solid fa-angle-right"/>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>

        <b-row v-if="user.buyer" class="my-2">
          <b-col>
            <b-card no-body class="selectable highlight-on-hover">
              <b-row align-h="between" class="p-2 mx-1">
                <b-col>
                  <b>
                    Historial de compras
                  </b>
                </b-col>
                <b-col class="text-right">
                  <font-awesome-icon icon="fa-solid fa-angle-right"/>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>

        <b-row v-if="user.seller" class="my-2">
          <b-col>
            <b-card no-body class="selectable highlight-on-hover">
              <b-row align-h="between" class="p-2 mx-1">
                <b-col>
                  <b>
                    Información fiscal
                  </b>
                </b-col>
                <b-col class="text-right">
                  <font-awesome-icon icon="fa-solid fa-angle-right"/>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>

        <b-row v-if="user.seller" class="my-2">
          <b-col>
            <b-card no-body class="selectable highlight-on-hover">
              <b-row align-h="between" class="p-2 mx-1">
                <b-col>
                  <b>
                    Historial de ventas
                  </b>
                </b-col>
                <b-col class="text-right">
                  <font-awesome-icon icon="fa-solid fa-angle-right"/>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>

        

        <b-row class="mt-5">
          <b-col class="text-center">
            <b-button v-if="user.buyer && !user.seller" class="main-button" style="width: 60%">
              Comenzar a vender!!
            </b-button>
            <b-button variant="outline-dark" class=" mt-3"  style="width: 60%">
              Eliminar cuenta
            </b-button>
          </b-col>
        </b-row>

      </b-col>
    </b-row>
  </section>
</template>

<script>
import UserService from "@/services/user/userService";

export default {
  name: 'UserDetails',
  data() {
    return {
      showEditPicture: false,
      user: {}
    }
  },
  methods: {
    async getProfile() {
      const payload = {
        email: this.$store.getters.getEmail
      }

      this.showOverlay();
      console.log(payload);
      const response = await UserService.getProfileService(payload);
      this.showOverlay();
      console.log(response);
      this.user = response.data;
      console.log(this.user);
    },

    showOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    }
  },
  mounted() {
    this.getProfile();
  },
}
</script>


<style scoped>
.main{
  min-height: 86vh;
}
</style>