<template>
  <b-modal id="put-user-information-modal" hide-header hide-footer centered @hidden="resetModal">
    <b-overlay :show="showOverlay">
      <b-container fluid>
        <b-row>
          <b-col class="text-right">
            <font-awesome-icon icon="times" class="text-secondary selectable" @click="$bvModal.hide('put-user-information-modal')"/>
          </b-col>
        </b-row>
        <section v-show="page===1">
          <b-row class="mt-2">
            <b-col class="text-center">
              <h1>Información de tu cuenta</h1>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col>
              <b-card no-body class="selectable highlight-on-hover" @click="changePasswordMenu">
                <b-row align-h="between" class="p-2 mx-1">
                  <b-col>
                    <b>
                      Cambiar contraseña
                    </b>
                  </b-col>
                  <b-col cols="auto" class="text-right">
                    <font-awesome-icon icon="fa-solid fa-angle-right"/>
                  </b-col>
                </b-row>
              </b-card>
            </b-col>
          </b-row>
          <b-row class="mt-2 mb-3" v-if="this.$store.getters.getRole !== 'SUPERADMIN'">
            <b-col>
              <b-card no-body class="selectable highlight-on-hover" @click="deleteAccountMenu">
                <b-row align-h="between" class="p-2 mx-1">
                  <b-col>
                    <b>
                      Eliminar cuenta
                    </b>
                  </b-col>
                  <b-col cols="auto" class="text-right">
                    <font-awesome-icon icon="fa-solid fa-angle-right"/>
                  </b-col>
                </b-row>
              </b-card>
            </b-col>
          </b-row>
        </section>

        <section v-show="page===2.1">
          <b-row>
            <b-col class="text-center">
              <h3>Cambiar contraseña</h3>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col>
              <b-form-group label="Contraseña actual" label-for="input-password">
                <b-form-input
                    id="input-password"
                    type="password"
                    v-model="form.Password"
                    required
                    name="password"
                    v-validate="'required'"
                    ref="password"
                ></b-form-input>
                <span v-show="errors.has('password')" class="text-danger">{{ errors.first('password') }}</span>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row class="mt-2">
            <b-col>
              <b-form-group label-for="input-new-password">
                <template #label>
                  <span class="pr-3">Nueva contraseña</span>
                  <b-tooltip target="input-new-password-tooltip" placement="right">
                <span >
                  <small>
                    La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula y un número.
                  </small>
                </span>
                  </b-tooltip>
                  <font-awesome-icon id="input-new-password-tooltip" icon="question-circle" />
                </template>
                <b-form-input
                    id="input-new-password"
                    type="password"
                    v-model="form.newPassword"
                    required
                    name="new-password"
                    v-validate="'required|password_strength'"
                    ref="new-password"
                ></b-form-input>
                <span v-show="errors.has('new-password')" class="text-danger">{{ errors.first('new-password') }}</span>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row >
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
                    v-validate="'required|confirmed_password:new-password'"
                ></b-form-input>
                <span v-show="errors.has('confirmPassword')" class="text-danger">{{ errors.first('confirmPassword') }}</span>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col>
              <b-button class="main-button" @click="changePassword()" >
                Guardar
              </b-button>
            </b-col>
          </b-row>
        </section>

        <section v-show="page===2.2">
          <b-row>
            <b-col class="text-center">
              <h3>Eliminar cuenta</h3>
            </b-col>
          </b-row>
          <b-row class="mt-4">
            <b-col>
              <b-form-group label="Contraseña" label-for="input-password-delete">
                <b-form-input
                    id="input-password-delete"
                    type="password"
                    v-model="form.Password"
                    required
                    name="password-delete"
                    v-validate="'required'"
                    ref="password"
                ></b-form-input>
                <span v-show="errors.has('password-delete')" class="text-danger">{{ errors.first('password-delete') }}</span>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row class="mt-4 mb-2">
            <b-col>
              <b-button class="main-button" @click="deleteAccount()" >
                Eliminar cuenta
              </b-button>
            </b-col>
          </b-row>
        </section>

      </b-container>
    </b-overlay>
  </b-modal>
</template>

<script>
import {mapGetters} from "vuex";
import UserService from "@/services/user/userService";
import {showSuccessToast} from "@/components/alerts/alerts";
import Vue from "vue";

export default {
  name: "PutUserInformationModal",
  data() {
    return {
      page:1,
      form:{
        newPassword: '',
        confirmPassword: '',
        Password: ''
      }
    };
  },
  methods: {


    changePassword(){
      Promise.all([
        this.$validator.validate('password'),
        this.$validator.validate('new-password'),
        this.$validator.validate('confirmPassword')
      ]).then(async (values) => {
        if(values.every(value => value)){

          Vue.swal({
            title: '¿Estás seguro?',
            text: "¿Deseas cambiar tu contraseña?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: 'var(--black-base)',
            confirmButtonText: 'Sí, cambiar',
            cancelButtonText: 'Cancelar',
          }).then(async (result) => {
            if (result.isConfirmed) {
              this.changeStatusOverlay()

              const payload = {
                oldPassword: this.form.Password,
                password: this.form.newPassword,
                email: this.$store.getters.getEmail
              }
              const response = await UserService.changePasswordService(payload);

              this.changeStatusOverlay()
              if(response.data){
                this.$bvModal.hide('put-user-information-modal');
                showSuccessToast('', 'Contraseña actualizada correctamente')
              }
            }
          });
        }
      });
    },

    deleteAccount(){
      Promise.all([
        this.$validator.validate('password-delete')
      ]).then(async (values) => {
        if(values.every(value => value)){
          Vue.swal({
            title: '¿Estás seguro?',
            text: "Esta acción no se puede deshacer. ¿Estás seguro de que quieres eliminar tu cuenta?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: 'var(--black-base)',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar',
          }).then(async (result) => {
            if (result.isConfirmed) {
              this.changeStatusOverlay()
              const payload = {
                email: this.$store.getters.getEmail,
                password: this.form.Password
              }
              const response = await UserService.deleteAccountService(payload);
              this.changeStatusOverlay()
              if(response.data){
                this.$store.dispatch('logout');
              }
            }
          });
        }
      });
    },

    deleteAccountMenu(){
      this.page = 2.2;
    },


    changePasswordMenu(){
      this.page = 2.1;
    },
    changeStatusOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    },
    resetModal(){
      this.page = 1;
      this.form={
        newPassword: '',
        confirmPassword: '',
        Password: ''
      }
    }
  },
  computed: {
    ...mapGetters(['showOverlay']),
  }
};
</script>


<style scoped>

</style>