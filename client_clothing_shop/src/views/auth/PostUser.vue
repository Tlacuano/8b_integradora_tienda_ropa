<template>
  <b-modal id="post-user-modal" hide-footer hide-header centered @hidden="resetModal">
    <b-container>
      <b-row>
        <b-col class="text-right">
          <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('post-user-modal')"/>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-center">
          <h1>Registrar cuenta</h1>
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

      <b-row class="my-4">
        <b-col>
          <b-button class="main-button" @click="register()">
            Siguiente
          </b-button>
        </b-col>
      </b-row>

    </b-container>
  </b-modal>
</template>

<script>
export default {
  name: 'PostUser',
  data() {
    return {
      form: {
        user: {
          email: '',
          password: '',
          repeatPassword: ''
        }
      }
    };
  },
  methods: {
    resetModal(){
    }
  },
  computed: {
  }
}
</script>

<style scoped>

</style>