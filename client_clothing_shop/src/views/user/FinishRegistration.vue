<template>
  <section class="interface">
    <b-row class="main">
      <b-col class="my-auto">
        <b-container >
          <b-overlay :show="showOverlay">
            <b-card class="my-4">
              <b-row>
                <b-col>
              <span class="text-black-50 selectable"  @click="logout()">
                Cerrar sesión
              </span>
                </b-col>
              </b-row>
              <b-row class="mt-3">
                <b-col>
                  <h1>Completa el registro para continuar</h1>
                  <hr>
                </b-col>
              </b-row>


              <section v-if="registerPage === 1" class="py-4">
                <b-row>
                  <b-col>
                    <h3>Verificar correo electrónico</h3>
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
                          v-model="form.code"
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
                    <b-link v-if="!timerActive" @click.prevent="resentEmailCode(true)" class="pr-3">
                      <span class="text-secondary small underline">Reenviar código</span>
                    </b-link>
                    <span v-else class="text-secondary">{{ Math.floor(timerSeconds / 60) }}:{{ ('0' + timerSeconds % 60).slice(-2) }}... Espera antes de poder volver a mandar el código</span>
                  </b-col>
                </b-row>

                <b-row class="mt-4">
                  <b-col class="text-right">
                    <b-button class="small-main-button px-4" @click="validateCode()">
                      Continuar
                    </b-button>
                  </b-col>
                </b-row>
              </section>

              <section v-if="registerPage===2">
                <b-row>
                  <b-col >
                    <h3>Información personal</h3>
                  </b-col>
                </b-row>
                <b-row class="mt-3">
                  <b-col >
                    <b-form-group label="Nombre(s)" label-for="input-name">
                      <b-form-input
                          id="input-name"
                          type="text"
                          v-model="form.person.name"
                          name="name"
                          v-validate="'required|alpha_spaces'"
                      ></b-form-input>
                      <span v-show="errors.has('name')" class="text-danger">{{ errors.first('name') }}</span>
                    </b-form-group>
                  </b-col>

                  <b-col md="4">
                    <b-form-group label="Primer apellido" label-for="input-last-name">
                      <b-form-input
                          id="input-last-name"
                          type="text"
                          v-model="form.person.lastName"
                          name="lastName"
                          v-validate="'required|alpha_spaces'"
                      ></b-form-input>
                      <span v-show="errors.has('lastName')" class="text-danger">{{ errors.first('lastName') }}</span>
                    </b-form-group>
                  </b-col>

                  <b-col md="4">
                    <b-form-group label="Segundo apellido" label-for="input-second-last-name">
                      <b-form-input
                          id="input-second-last-name"
                          type="text"
                          v-model="form.person.secondLastName"
                          name="secondLastName"
                          v-validate="'required|alpha_spaces'"
                      ></b-form-input>
                      <span v-show="errors.has('secondLastName')" class="text-danger">{{ errors.first('secondLastName') }}</span>
                    </b-form-group>
                  </b-col>
                </b-row>
                <hr>
                <b-row>
                  <b-col md="4">
                    <b-form-group label="Teléfono" label-for="input-phone">
                      <b-form-input
                          id="input-phone"
                          type="text"
                          v-model="form.person.phone"
                          name="phone"
                          v-validate="'required|phone_number'"
                      ></b-form-input>
                      <span v-show="errors.has('phone')" class="text-danger">{{ errors.first('phone') }}</span>
                    </b-form-group>
                  </b-col>

                  <b-col md="4">
                    <b-form-group label="Fecha de nacimiento" label-for="input-birthday">
                      <b-form-input
                          id="input-birthday"
                          type="date"
                          v-model="form.person.birthday"
                          name="birthday"
                          min="1940-01-01"
                          :max="today"
                          v-validate="'required|over_18|minor_120'"
                      ></b-form-input>
                      <span v-show="errors.has('birthday')" class="text-danger">{{ errors.first('birthday') }}</span>
                    </b-form-group>
                  </b-col>

                  <b-col md="4">
                    <b-form-group label="Género" label-for="select-gender">
                      <b-form-select
                          id="select-gender"
                          v-model="form.person.gender"
                          name="gender"
                          v-validate="'required'"
                      >
                        <b-form-select-option value="MASCULINO">Masculino</b-form-select-option>
                        <b-form-select-option value="FEMENINO">Femenino</b-form-select-option>
                        <b-form-select-option value="OTROS">Otro</b-form-select-option>
                      </b-form-select>
                      <span v-show="errors.has('gender')" class="text-danger">{{ errors.first('gender')}}</span>
                    </b-form-group>
                  </b-col>
                </b-row>

                <b-row class="mt-4">
                  <b-col>
                    <b-form-group
                        label-for="checkbox-privacy-policy"
                    >
                      <template #label>
                        <b-link @click.prevent="openPrivacyPolicy()" class="pr-3">
                          <span class="font-weight-bold small underline">Leer términos, condiciones y políticas de privacidad</span>
                        </b-link>
                      </template>

                      <b-form-checkbox
                          id="checkbox-privacy-policy"
                          v-model="form.person.privacyPolicy"
                          name="privacyPolicy"
                          v-validate="'required|privacy_policy'"
                      >
                        Acepto los términos y condiciones
                      </b-form-checkbox>
                      <span v-show="errors.has('privacyPolicy')" class="text-danger">{{ errors.first('privacyPolicy')}}</span>
                    </b-form-group>
                  </b-col>
                </b-row>

                <b-row class="my-4 text-right">
                  <b-col>
                    <b-button class="small-main-button px-4" @click="validatePersonalInformation()">
                      Siguiente
                    </b-button>
                  </b-col>
                </b-row>
              </section>

              <section v-if="registerPage === 3">
                <b-row>
                  <b-col>
                    <h3>Verificar número de teléfono</h3>
                  </b-col>
                </b-row>
                <b-row class="mt-4">
                  <b-col>
                    <b-form-group label-for="input-code-phone">
                      <template #label>
                        <span class="pr-3">Codigo de verificación</span>
                        <b-tooltip target="input-code-phone-tooltip" placement="right">
                          <span >
                            <small>
                              El codigo fue enviado a su número de teléfono.
                            </small>
                          </span>
                        </b-tooltip>
                        <font-awesome-icon id="input-code-phone-tooltip" icon="question-circle" />
                      </template>
                      <b-form-input
                          id="input-code-phone"
                          type="text"
                          v-model="form.codePhone"
                          required
                          name="codePhone"
                          v-validate="'required'"
                      />
                      <span v-show="errors.has('codePhone')" class="text-danger">{{ errors.first('codePhone') }}</span>
                    </b-form-group>
                  </b-col>
                </b-row>

                <b-row class="mb-4">
                  <b-col>
                    <span v-if="!timerActive" class="text-secondary">¿No recibiste el código? </span>
                    <b-link v-if="!timerActive" @click.prevent="resendPhoneCode(true)" class="pr-3">
                      <span class="text-secondary small underline">Reenviar código</span>
                    </b-link>
                    <span v-else class="text-secondary">{{ Math.floor(timerSeconds / 60) }}:{{ ('0' + timerSeconds % 60).slice(-2) }}... Espera antes de poder volver a mandar el código</span>
                  </b-col>
                </b-row>

                <b-row class="mt-4">
                  <b-col class="text-right
                  ">
                    <b-button class="small-secondary-button px-4 mx-3" @click="backPage2()">
                      Atrás
                    </b-button>
                    <b-button class="small-main-button px-4" @click="validateCodePhone()">
                      Continuar
                    </b-button>
                  </b-col>
                </b-row>
              </section>

            </b-card>
          </b-overlay>
        </b-container>
      </b-col>
    </b-row>

  </section>
</template>

<script>
import {codeCrypto, decodeCrypto} from "@/utils/security/cryptoJs";
import UserService from "@/services/user/userService";
import {mapGetters} from "vuex";
import PersonService from "@/services/person/personService";
import {showSuccessToast} from "@/components/alerts/alerts";

export default {
  name: 'FinishRegistration',
  data() {
    return {
      verified: {},
      registerPage: null,

      form: {
        user: {
          email: '',
        },
        person: {
          name: '',
          lastName: '',
          secondLastName: '',
          phone: '',
          birthday: '',
          gender: '',
          privacyPolicy: false,
        },
        code: '',
        codePhone: '',
      },

      timerActive: false,
      timerSeconds: 60,
    }
  },
  methods: {

    async validateCode(){
      Promise.all([
        this.$validator.validate('codeEmail')
      ]).then(async (result) => {
        if (result.every(e => e)) {
          const payload = {
            email: this.form.user.email,
            code: this.form.code
          };

          this.changeStatusOverlay();
          const response = await UserService.verifyCodeService(payload);
          this.changeStatusOverlay();

          if(response){
            this.form.code = '';
            this.errors.clear();
            this.increaseRegisterPage();

            this.verified.emailVerified = true;
            this.newProgress();
            this.clearTimer();
          }else{
            this.errors.add({
              field: 'codeEmail',
              msg: 'Código incorrecto'
            });
          }
        }
      });
    },

    validatePersonalInformation(){
      Promise.all([
        this.$validator.validate('name'),
        this.$validator.validate('lastName'),
        this.$validator.validate('secondLastName'),
        this.$validator.validate('phone'),
        this.$validator.validate('birthday'),
        this.$validator.validate('gender'),
        this.$validator.validate('privacyPolicy')
      ]).then(async (result) => {
        if (result.every(e => e)) {
          const payload = {
            name: this.form.person.name,
            lastName: this.form.person.lastName,
            secondLastName: this.form.person.secondLastName,
            phoneNumber: this.form.person.phone,
            birthday: this.form.person.birthday,
            gender: this.form.person.gender,
            privacyPolicy: this.form.person.privacyPolicy,
            email: this.form.user.email
          };

          this.changeStatusOverlay();
          const response = await PersonService.postPersonalInformationService(payload);
          this.changeStatusOverlay();

          if(response.data) {
            this.verified.privacyPolicy = true;
            this.increaseRegisterPage();
            this.newProgress();
            window.location.reload();
          }
        }
      });
    },

    async validateCodePhone(){
      Promise.all([

        this.$validator.validate('codePhone')
      ]).then(async (result) => {

        if (result.every(e => e)) {
          const payload = {
            email: this.form.user.email,
            code: this.form.codePhone
          };

          this.changeStatusOverlay();
          const response = await PersonService.verifyCodeService(payload);
          this.changeStatusOverlay();

          if(response.data){
            this.form.codePhone = '';
            showSuccessToast('', 'Registro finalizado');
            localStorage.removeItem('verified');
            window.location.reload();
          }else{
            this.errors.add({
              field: 'codePhone',
              msg: 'Código incorrecto'
            });
          }
        }
      });
    },


    async backPage2(){
      this.changeStatusOverlay()
      const payload = {
        email: this.form.user.email
      };
      await PersonService.deletePersonalInformationIncompleteService(payload);
      this.changeStatusOverlay();
      this.registerPage = 2;
      this.clearTimer();
    },


    startTimer() {
      this.timerActive = true;
      const now = Date.now();
      const expirationTime = now + 60000;
      localStorage.setItem('timerExpiration', expirationTime.toString());

      this.updateTimer();
    },

    updateTimer() {
      const interval = setInterval(() => {
        const now = Date.now();
        const expirationTime = parseInt(localStorage.getItem('timerExpiration'), 10);
        const timeLeft = expirationTime - now;

        if (timeLeft > 0) {
          this.timerSeconds = Math.round(timeLeft / 1000);
        } else {
          clearInterval(interval);
          this.timerActive = false;
          localStorage.removeItem('timerExpiration'); // Limpia el localStorage
        }
      }, 1000);
    },

    clearTimer() {
      this.timerActive = false;
      this.timerSeconds = 60;

      localStorage.removeItem('timerExpiration');
    },

    checkTimerActive() {
      const expirationTime = parseInt(localStorage.getItem('timerExpiration'), 10);
      const now = Date.now();
      this.timerActive = expirationTime && expirationTime > now;
      if (this.timerActive) {
        this.updateTimer();
      }
    },



    async resentEmailCode(resendByClick) {
      this.checkTimerActive();
      if (!this.timerActive) {
        this.changeStatusOverlay();
        const payload = {
          email: this.form.user.email,
        };
        await UserService.resendEmailCode(payload);
        this.changeStatusOverlay();

        if (resendByClick) {
          this.startTimer();
        }
      }
    },

    async resendPhoneCode(resendByClick) {
      this.checkTimerActive();
      if (!this.timerActive) {
        this.changeStatusOverlay();
        const payload = {
          email: this.form.user.email,
        };
        await PersonService.resendPhoneCodeService(payload);
        this.changeStatusOverlay();

        if (resendByClick) {
          this.startTimer();
        }
      }
    },


    logout() {
      localStorage.removeItem('timerExpiration');
      this.$store.dispatch('logout');
      window.location.reload();
    },

    onMounted() {
      this.verified = JSON.parse(decodeCrypto(localStorage.getItem('verified')));
      this.form.user.email = this.$store.getters.getEmail;

      if(this.verified.emailVerified === false){
        this.registerPage = 1;
        this.resentEmailCode();
        this.startTimer();
      }else if(this.verified.privacyPolicy === false){
        this.registerPage = 2;
      }else if(this.verified.verificationPhone === false){
        this.registerPage = 3;
        this.startTimer();
        this.resendPhoneCode();
      }
    },

    newProgress(){
      const verifiedString = JSON.stringify(this.verified);
      const verifiedCrypto = codeCrypto(verifiedString);
      localStorage.setItem('verified', verifiedCrypto);
    },

    openPrivacyPolicy() {
      window.open('/privacy-policy', '_blank');
    },

    increaseRegisterPage(){
      this.registerPage++;
    },
    changeStatusOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    },
  },
  mounted() {
    this.onMounted();

    const expirationTime = parseInt(localStorage.getItem('timerExpiration'), 10);
    const now = Date.now();

    if (expirationTime && expirationTime > now) {
      this.timerActive = true;
      this.updateTimer();
    }
  },
  computed: {
    ...mapGetters(['showOverlay']),
    today() {
      const today = new Date();
      const month = `0${today.getMonth() + 1}`.slice(-2); // Ensure two digits
      const day = `0${today.getDate()}`.slice(-2); // Ensure two digits
      return `${today.getFullYear()}-${month}-${day}`;
    }
  }
}
</script>

<style>
  .main{
    min-height: calc(100vh - 50px);
  }
  .interface{
    background-color: #eff1f1;
    overflow-x: hidden;
    overflow-y: auto;
  }
</style>