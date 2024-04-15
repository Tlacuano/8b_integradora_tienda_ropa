<template>
  <b-modal id="post-admin-modal" hide-footer hide-header centered @shown="initModal" @hidden="resetModal" :size="modalSize" no-close-on-backdrop>
    <b-overlay :show="showOverlay">
      <b-container>
        <section v-show="registerPage===1">
          <b-row>
            <b-col class="text-right">
              <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('post-user-modal')"/>
            </b-col>
          </b-row>
          <b-row>
            <b-col class="text-center">
              <h1>Registrar administrador</h1>
            </b-col>
          </b-row>
          <b-row class="mt-3">
            <b-col>
              <b-form-group label="Correo electrónico" label-for="input-email">
                <b-form-input
                    id="input-email"
                    type="email"
                    v-model="form.user.email"
                    name="email"
                    v-validate="'required|email'"
                ></b-form-input>
                <span v-show="errors.has('email')" class="text-danger">{{ errors.first('email') }}</span>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row>
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
                    v-model="form.user.password"
                    required
                    name="password"
                    v-validate="'required|password_strength'"
                    ref="password"
                />
                <span v-show="errors.has('password')" class="text-danger">{{ errors.first('password') }}</span>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row>
            <b-col>
              <b-form-group label-for="input-repeat-password">
                <template #label>
                  <span class="pr-3">Repetir contraseña</span>
                  <b-tooltip target="input-repeat-password-tooltip" placement="right">
                <span >
                  <small>
                    Repita la contraseña que ingresó anteriormente.
                  </small>
                </span>
                  </b-tooltip>
                  <font-awesome-icon id="input-repeat-password-tooltip" icon="question-circle" />
                </template>
                <b-form-input
                    id="input-repeat-password"
                    type="password"
                    v-model="form.user.repeatPassword"
                    required
                    name="repeatPassword"
                    v-validate="'required|confirmed_password:password'"
                ></b-form-input>
                <span v-show="errors.has('repeatPassword')" class="text-danger">{{ errors.first('repeatPassword') }}</span>
              </b-form-group>
            </b-col>
          </b-row>

          <div
              ref="container"
              class="frc-captcha"
              data-sitekey="FCMGAFOETVPP0BIP"
              data-lang="es"
          ></div>
          <span v-if="verifiedCaptcha.error.length > 0 && verifiedCaptcha.done === false" class="text-danger">{{verifiedCaptcha.error}}</span>


          <b-row class="my-4">
            <b-col>
              <b-button class="main-button" @click="validateUserInformation()" >
                Siguiente
              </b-button>
            </b-col>
          </b-row>
        </section>

        <section v-show="registerPage===2">
          <b-row>
            <b-col class="text-right">
              <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('post-user-modal')"/>
            </b-col>
          </b-row>
          <b-row>
            <b-col class="text-center">
              <h1>Verificar correo electrónico</h1>
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

          <b-row class="my-4">
            <b-col class="text-right">
              <span class="text-black-50 px-3 selectable" @click="backPage1()">
                Atrás
              </span>
              <b-button class="small-main-button px-5" @click="validateCode()">
                Verificar
              </b-button>
            </b-col>
          </b-row>
        </section>

        <section v-show="registerPage===3">
          <b-row>
            <b-col class="text-right">
              <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('post-user-modal')"/>
            </b-col>
          </b-row>
          <b-row>
            <b-col class="text-center">
              <h1>Información personal</h1>
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
          </b-row>

          <b-row>
            <b-col >
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
          </b-row>

          <b-row>
            <b-col >
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

          <b-row>
            <b-col >
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
          </b-row>

          <b-row>
            <b-col >
              <b-form-group label="Fecha de nacimiento" label-for="input-birthday">
                <b-form-input
                    id="input-birthday"
                    type="date"
                    v-model="form.person.birthday"
                    name="birthday"
                    min="1940-01-01"
                    :max="today"
                    v-validate="'required|over_18'"
                ></b-form-input>
                <span v-show="errors.has('birthday')" class="text-danger">{{ errors.first('birthday') }}</span>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row>
            <b-col>
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

          <b-row>
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

          <b-row class="my-4">
            <b-col class="text-right">
              <span class="text-black-50 px-3 selectable" @click="backPage1()">
                Atrás
              </span>
              <b-button class="small-main-button  px-5" @click="validatePersonalInformation()">
                Siguiente
              </b-button>
            </b-col>
          </b-row>
        </section>

        <section v-show="registerPage===4">
          <b-row>
            <b-col class="text-center">
              <h1>Verificacion de número de celular</h1>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col>
              <b-form-group label-for="input-phone-code">
                <template #label>
                  <span class="pr-3">Codigo de verificación</span>
                  <b-tooltip target="input-phone-code-tooltip" placement="right">
                <span >
                  <small>
                    El codigo fue enviado a su número de celular.
                  </small>
                </span>
                  </b-tooltip>
                  <font-awesome-icon id="input-phone-code-tooltip" icon="question-circle" />
                </template>
                <b-form-input
                    id="input-phone-code"
                    type="text"
                    v-model="form.code"
                    required
                    name="codePhone"
                    v-validate="'required'"
                />
                <span v-show="errors.has('codePhone')" class="text-danger">{{ errors.first('codePhone') }}</span>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row class="my-4">
            <b-col class="text-right">
              <span class="text-black-50 px-3 selectable" @click="backPage3()">
                Atrás
              </span>
              <b-button class="small-main-button  px-5" @click="validateCodePhone()">
                Verificar
              </b-button>
            </b-col>
          </b-row>
        </section>

      </b-container>
    </b-overlay>
  </b-modal>
</template>

<script>
import { WidgetInstance } from "friendly-challenge";
import {mapGetters} from "vuex";
import UserService from "@/services/user/userService";
import PersonService from "@/services/person/personService";
import {showSuccessToast} from "@/components/alerts/alerts";
import CaptchaService from "@/services/captcha/CaptchaService";
export default {
  name: 'PostAdmin',
  data() {
    return {
      registerPage: 1,
      modalSize: 'md',

      form: {
        user: {
          email: '',
          password: '',
          repeatPassword: ''
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
        code: ''
      },

      verifiedCaptcha: {
        error: '',
        done: false
      }


    };
  },
  methods: {
    async validateUserInformation(){
      Promise.all([
        this.$validator.validate('email'),
        this.$validator.validate('password'),
        this.$validator.validate('repeatPassword'),
      ]).then(async (result) => {
        if (result.every(e => e)) {
          //validar captcha
          if(this.verifiedCaptcha.done === false){
            this.verifiedCaptcha.error = 'Por favor complete el captcha';
            return;
          }else{
            this.verifiedCaptcha.error = '';
          }

          if(this.verifiedCaptcha.error.length > 0){
            return;
          }

          const payloadEmail = {
            email: this.form.user.email,
          };

          this.changeStatusOverlay();
          const responseEmail = await UserService.existUserByEmailService(payloadEmail);
          this.changeStatusOverlay();

          if(!responseEmail.data){

            const payload = {
              email: this.form.user.email,
              password: this.form.user.password
            };

            this.changeStatusOverlay();
            await UserService.postAdminService(payload);
            this.changeStatusOverlay();
            this.increaseRegisterPage();

          }else{
            this.errors.add({
              field: 'email',
              msg: 'Correo no disponible'
            });
          }

        }
      });
    },

    async validateCode(){
      this.$validator.validate('codeEmail').then(async (result) => {
        if (result) {
          const payload = {
            email: this.form.user.email,
            code: this.form.code
          };

          this.changeStatusOverlay();
          const response = await UserService.verifyCodeService(payload);
          this.changeStatusOverlay();

          if(response.data){
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
            this.increaseRegisterPage();
          }
        }
      });

    },

    async validateCodePhone(){
      this.$validator.validate('codePhone').then(async (result) => {
        if (result) {
          const payload = {
            email: this.form.user.email,
            code: this.form.code
          };

          this.changeStatusOverlay();
          const response = await PersonService.verifyCodeService(payload);
          this.changeStatusOverlay();

          if(response.data){
            this.form.code = '';
            showSuccessToast('', 'Cuenta creada exitosamente');
            this.$bvModal.hide('post-user-modal');
          }else{
            this.errors.add({
              field: 'codePhone',
              msg: 'Código incorrecto'
            });
          }
        }
      });
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

    async backPage1(){
      this.changeStatusOverlay();
      const payload = {
        email: this.form.user.email
      };
      await UserService.deleteIncompleteAccountService(payload);
      this.changeStatusOverlay();
      this.registerPage = 1;
    },


    async backPage3(){
      this.changeStatusOverlay()
      const payload = {
        email: this.form.user.email
      };
      await PersonService.deletePersonalInformationIncompleteService(payload);
      this.changeStatusOverlay();
      this.registerPage = 3;
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
    resetModal(){
      this.registerPage = 1;
      this.form.user.email = '';
      this.form.user.password = '';
      this.form.user.repeatPassword = '';
      this.form.code = '';
      this.form.person.name = '';
      this.form.person.lastName = '';
      this.form.person.secondLastName = '';
      this.form.person.phone = '';
      this.form.person.birthday = '';
      this.form.person.gender = '';
      this.form.person.privacyPolicy = false;
      this.errors.clear();
      if (this.widget) {
        this.widget.destroy();
      }
      this.verifiedCaptcha = {
        error: '',
        done: false
      }
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
  },
}
</script>

<style scoped>

</style>