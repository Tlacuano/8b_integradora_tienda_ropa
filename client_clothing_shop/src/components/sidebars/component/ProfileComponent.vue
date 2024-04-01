<template>
  <b-row>
    <b-col>
      <section v-if="isLogged">
        <b-row>
          <b-col cols="auto">
            <b-avatar
                :src="user.picture || null"
                alt="Image"
                fluid
                variant="dark"
                thumbnail
            />
          </b-col>
          <b-col class="my-auto">
            <b-row>
              <b-col>
                <h5 class="m-0 p-0">
                  {{user.fullName}}
                </h5>
              </b-col>
            </b-row>
          </b-col>
        </b-row>
      </section>
      <section v-else>
        <b-row v-b-modal:login-modal>
          <b-col cols="auto">
            <b-avatar
                alt="Image"
                fluid
                variant="dark"
                thumbnail
            />
          </b-col>
          <b-col class="my-auto">
            <b-row>
              <b-col>
                <h5 class="m-0 p-0">
                  Iniciar sesión
                </h5>
              </b-col>
            </b-row>
          </b-col>
        </b-row>
      </section>
    </b-col>
  </b-row>
</template>

<script>
import UserService from "@/services/user/userService";

export default {
  name: 'ProfileComponent',
  data() {
    return {
      user: {},
      isLogged: false
    }
  },
  methods: {
    async getProfile() {
      if(this.$store.getters.isLoggedIn){
        const payload = {
          email: this.$store.getters.getEmail
        }
        const response = await UserService.getProfileService(payload);
        this.user = response.data;

        this.isLogged = true;
      }else{
        this.isLogged = false;
      }
    },

  },
  mounted() {
    this.getProfile();
  },
}

</script>


<style scoped>

</style>