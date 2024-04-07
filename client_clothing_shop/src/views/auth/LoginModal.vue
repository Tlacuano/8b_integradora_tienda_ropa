<template>
  <b-modal id="login-modal" hide-footer hide-header centered @hidden="resetModal" >
    <b-container @keyup.enter="login">
      <b-row>
        <b-col class="text-right">
          <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('login-modal')"/>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-center">
          <h1>Iniciar sesión</h1>
        </b-col>
      </b-row>
      <b-row class="mt-3">
        <b-col>
          <b-form-group label="Correo electrónico" label-for="input-email">
            <b-form-input
                id="input-email"
                type="email"
                v-model="form.email"
                name="email"
                v-validate="'required'"
            ></b-form-input>
            <span v-show="errors.has('email')" class="text-danger">{{ errors.first('email') }}</span>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-form-group label="Contraseña" label-for="input-password">
            <b-form-input
                id="input-password"
                type="password"
                v-model="form.password"
                required
                name="password"
                v-validate="'required'"
            ></b-form-input>
            <span v-show="errors.has('password')" class="text-danger">{{ errors.first('password') }}</span>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row class="mt-2">
        <b-col>
          <div class="mb-3 text-center">
            <b-link @click="changeToRecoverPassword()" >¿Has olvidado tu contraseña?</b-link>
          </div>
        </b-col>
      </b-row>
      <b-row class="mt-1">
        <b-col>
          <b-button class="main-button" @click="login()">
            Iniciar sesión
          </b-button>
        </b-col>
      </b-row>
      <b-row class="mt-3">
        <b-col>
          <div class="mb-3 text-center">
            <b-link @click="changeToPostUser" >¿No tienes cuenta?<span class="font-weight-bold"> Regístrate</span></b-link>
          </div>
        </b-col>
      </b-row>
    </b-container>
  </b-modal>
</template>

<script>
import AuthService from "@/services/auth/authService";
import {codeCrypto} from "@/utils/security/cryptoJs";
export default {
  name: 'LoginModal',

  data() {
    return {
      form: {
        email: '',
        password: ''
      },
    }
  },
  methods: {
    login() {
      this.$validator.validateAll().then(async (result) => {
        if (result) {

          const payload = {
            email: this.form.email,
            password: this.form.password
          };

          this.showOverlay();
          const response = await AuthService.loginService(payload);

          if (response) {
            this.$bvModal.hide('login-modal');

            this.$store.dispatch('login', {
              token: response.data.token,
              email: response.data.email,
              role: response.data.role,
              hasMultipleRoles: response.data.hasMultipleRoles,
            });

            if(response.data.emailVerified !== undefined || response.data.privacyPolicy !== undefined || response.data.verificationPhone !== undefined){
              const verified = {
                emailVerified: response.data.emailVerified,
                privacyPolicy: response.data.privacyPolicy,
                verificationPhone: response.data.verificationPhone,
              }
              const verifiedString = JSON.stringify(verified);
              const verifiedCrypto = codeCrypto(verifiedString);
              localStorage.setItem('verified', verifiedCrypto);

              await this.$router.push({name: 'FinishRegistration'});
            }
            this.showOverlay();
            window.location.reload();

          }
          this.showOverlay();

        }
      });
    },

    resetModal(){
      this.form.email = '';
      this.form.password = '';
    },
    changeToPostUser(){
      this.$bvModal.hide('login-modal');
      this.$bvModal.show('post-user-modal');
    },
    changeToRecoverPassword(){
      this.$bvModal.hide('login-modal');
      this.$bvModal.show('recover-password-modal');
    },


    showOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    }



  },
}
</script>

<style scoped>

</style>