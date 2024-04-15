<template>
  <b-modal id="recover-password-modal" hide-header hide-footer centered @shown="initModal" @hidden="resetModal">
    <b-row>
      <b-col>
        <b-overlay :show="showOverlay">
          <section v-show="page === 1">
            <b-row>
              <b-col class="text-right">
                <font-awesome-icon icon="times" class="selectable text-secondary"  @click="$bvModal.hide('recover-password-modal')"/>
              </b-col>
            </b-row>
            <b-row>
              <b-col class="text-center">
                <h1>Recuperar contraseña</h1>
              </b-col>
            </b-row>
            <b-row class="mt-3">
              <b-col>
                <b-form-group label-for="input-email">
                  <template #label>
                    <span class="pr-3">Correo electrónico</span>
                    <b-tooltip target="input-email-tooltip" placement="right">
                <span >
                  <small>
                    Ingresa el correo electrónico con el que te registraste
                  </small>
                </span>
                    </b-tooltip>
                    <font-awesome-icon id="input-email-tooltip" icon="question-circle" />
                  </template>
                  <b-form-input
                      id="input-email"
                      type="email"
                      v-model="email"
                      name="email"
                      v-validate="'required'"
                  ></b-form-input>
                  <span v-show="errors.has('email')" class="text-danger">{{ errors.first('email') }}</span>
                </b-form-group>
              </b-col>
            </b-row>

            <b-row class="mt-2">
              <b-col>
                <div
                    ref="container"
                    class="frc-captcha"
                    data-sitekey="FCMGAFOETVPP0BIP"
                    data-lang="es"
                ></div>
                <span v-if="verifiedCaptcha.error.length > 0 && verifiedCaptcha.done === false" class="text-danger small">{{verifiedCaptcha.error}}</span>
              </b-col>
            </b-row>

            <b-row class="mt-4">
              <b-col>
                <b-button class="main-button" @click="sendCode()">
                  Envíar codigo
                </b-button>
              </b-col>
            </b-row>

          </section>

          <section v-show="page===2">
            <b-row class="mt-2">
              <b-col class="text-center">
                <h2>Verificar correo electrónico</h2>
              </b-col>
            </b-row>
            <b-row class="mt-4">
              <b-col>
                <b-form-group label-for="input-code-email">
                  <template #label>
                    <span class="pr-3">Codigo de verificación</span>
                    <b-tooltip target="input-code-email-tooltip" placement="right">
                <span >
                  <small>
                    El codigo fue enviado a su correo electrónico.
                  </small>
                </span>
                    </b-tooltip>
                    <font-awesome-icon id="input-code-email-tooltip" icon="question-circle" />
                  </template>
                  <b-form-input
                      id="input-code-email"
                      type="text"
                      v-model="code"
                      required
                      name="codeEmail"
                      v-validate="'required'"
                  />
                  <span v-show="errors.has('codeEmail')" class="text-danger">{{ errors.first('codeEmail') }}</span>
                </b-form-group>
              </b-col>
            </b-row>

            <b-row class="mb-4">
              <b-col>
                <span v-if="!timerActive" class="text-secondary">¿No recibiste el código? </span>
                <b-link v-if="!timerActive" @click.prevent="resentEmailCode()" class="pr-3">
                  <span class="text-secondary small underline">Reenviar código</span>
                </b-link>
                <span v-else class="text-secondary">{{ Math.floor(timerSeconds / 60) }}:{{ ('0' + timerSeconds % 60).slice(-2) }}... Espera antes de poder volver a mandar el código</span>
              </b-col>
            </b-row>


            <b-row class="mt-4">
              <b-col>
                <b-button class="main-button" @click="validateCode()">
                  Continuar
                </b-button>
              </b-col>
            </b-row>
          </section>

          <section v-show="page===3">
            <b-row>
              <b-col class="text-center">
                <h3>Ingresé su nueva contraseña</h3>
              </b-col>
            </b-row>
            <b-row class="mt-4">
              <b-col>
                <b-form-group label-for="input-password">
                  <template #label>
                    <span class="pr-3">Contraseña</span>
                    <b-tooltip target="input-password-tooltip" placement="right">
                <span >
                  <small>
                    La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula y un número.
                  </small>
                </span>
                    </b-tooltip>
                    <font-awesome-icon id="input-password-tooltip" icon="question-circle" />
                  </template>
                  <b-form-input
                      id="input-password"
                      type="password"
                      v-model="form.password"
                      required
                      name="password"
                      v-validate="'required|password_strength'"
                      ref="password"
                  ></b-form-input>
                  <span v-show="errors.has('password')" class="text-danger">{{ errors.first('password') }}</span>
                </b-form-group>
              </b-col>
            </b-row>
            <b-row class="mt-2">
              <b-col>
                <b-form-group label-for="input-confirm-password">
                  <template #label>
                    <span class="pr-3">Confirmar contraseña</span>
                    <b-tooltip target="input-confirm-password-tooltip" placement="right">
                <span >
                  <small>
                    Confirma la contraseña
                  </small>
                </span>
                    </b-tooltip>
                    <font-awesome-icon id="input-confirm-password-tooltip" icon="question-circle" />
                  </template>
                  <b-form-input
                      id="input-confirm-password"
                      type="password"
                      v-model="form.confirmPassword"
                      required
                      name="confirmPassword"
                      v-validate="'required|confirmed_password:password'"
                  ></b-form-input>
                  <span v-show="errors.has('confirmPassword')" class="text-danger">{{ errors.first('confirmPassword') }}</span>
                </b-form-group>
              </b-col>
            </b-row>

            <b-row class="mt-4">
              <b-col>
                <b-button class="main-button" @click="changePassword()">
                  Cambiar contraseña
                </b-button>
              </b-col>
            </b-row>

          </section>
        </b-overlay>

      </b-col>
    </b-row>
  </b-modal>
</template>

<script>
import { WidgetInstance } from "friendly-challenge";
import {mapGetters} from "vuex";
import CaptchaService from "@/services/captcha/CaptchaService";
import UserService from "@/services/user/userService";
import {showSuccessToast} from "@/components/alerts/alerts";
export default {
  name: 'RecoverPassword',
  data() {
    return {
      page:1,

      email: '',
      verifiedCaptcha: {
        error: '',
        done: false
      },
      code: '',
      form: {
        password: '',
        confirmPassword: ''
      },

      timerActive: false,
      timerSeconds: 60,
    }
  },
  methods: {

    async sendCode(){
      Promise.all([
          this.$validator.validate('email'),
      ]).then(async (result) => {
        if(result.every(e => e)){
          if(this.verifiedCaptcha.done === false){
            this.verifiedCaptcha.error = 'Por favor complete el captcha';
            return;
          }else{
            this.verifiedCaptcha.error = '';
          }

          if(this.verifiedCaptcha.error.length > 0){
            return;
          }

          this.changeStatusOverlay();
          const payload = {
            email: this.email
          };

          await UserService.resendEmailCode(payload)

          this.increaseRegisterPage();

          this.changeStatusOverlay();


        }
      });
    },

    async validateCode(){
      this.$validator.validate('codeEmail').then(async (result) => {
        if (result) {
          const payload = {
            email: this.email,
            code: this.code
          };

          this.changeStatusOverlay();
          const response = await UserService.verifyCodeService(payload);
          this.changeStatusOverlay();

          if(response){
            this.form.code = '';
            this.errors.clear();
            this.increaseRegisterPage();

          }else{
            this.errors.add({
              field: 'codeEmail',
              msg: 'Código incorrecto'
            });
          }
        }
      });
    },

    async changePassword(){
      Promise.all([
        this.$validator.validate('password'),
        this.$validator.validate('confirmPassword'),
      ]).then(async (result) => {
        if(result.every(e => e)){
          const payload = {
            email: this.email,
            password: this.form.password,
            code: this.code
          };

          this.changeStatusOverlay();
          const response = await UserService.restorePasswordService(payload);
          this.changeStatusOverlay();

          if(response.data){
            this.$bvModal.hide('recover-password-modal');
            showSuccessToast('', 'Contraseña cambiada con éxito');
          }
        }
      });
    },


    async resentEmailCode() {
      if (!this.timerActive) {
        this.changeStatusOverlay();
        const payload = {
          email: this.form.user.email
        };
        await UserService.resendEmailCode(payload);
        this.changeStatusOverlay();

        this.startTimer();
      }
    },

    startTimer() {
      this.timerActive = true;
      this.timerSeconds = 60;

      const interval = setInterval(() => {
        this.timerSeconds--;

        if (this.timerSeconds <= 0) {
          clearInterval(interval);
          this.timerActive = false;
        }
      }, 1000);
    },


    doneCapcha(solution){
      this.verifyCaptcha(solution);
    },
    async verifyCaptcha(solution){
      const payload = {
        solution: solution
      };
      const response = await CaptchaService.verifyService(payload);
      this.verifiedCaptcha.done = response.data.success;

      if(!this.verifiedCaptcha){
        this.verifiedCaptcha.error = 'Intentelo de nuevo';

        if (this.widget) {
          this.widget.destroy();
        }
        this.initModal();
      }
    },
    changeStatusOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    },


    initModal(){
      if (this.$refs.container) {
        this.widget = new WidgetInstance(
            this.$refs.container, {
              startMode: "",
              doneCallback: this.doneCapcha,
              errorCallback: this.errorCallback,
            }
        );
      }
    },
    resetModal(){
      this.email = '';
      this.verifiedCaptcha = {
        error: '',
        done: false
      }
      if (this.widget) {
        this.widget.destroy();
      }
      this.page = 1;
      this.form = {
        password: '',
        confirmPassword: ''
      }
      this.code = '';
    },
    increaseRegisterPage(){
      this.page++;
    },
  },
  computed: {
    ...mapGetters(['showOverlay']),
  }
}
</script>

<style scoped>

</style>