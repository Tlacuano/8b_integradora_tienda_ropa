<template>
  <b-modal id="delete-account-admin-modal" centered hide-footer hide-header @shown="showModal" @hidden="hiddenModal">
    <b-overlay :show="this.$store.getters.showOverlay">
      <b-container>
        <b-row>
          <b-col class="text-right">
            <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('delete-account-admin-modal')"/>
          </b-col>
        </b-row>
        <b-row class="mt-1">
          <b-col class="text-center">
            <h1>Eliminar cuenta</h1>
          </b-col>
        </b-row>
        <b-row class="mt-3">
          <b-col>
            <b-form-group label="Motivo:" label-for="input-reason">
              <b-form-textarea
                  id="input-reason"
                  v-model="reason"
                  placeholder="Escribe aquí"
                  rows="4"
                  v-validate="'required'"
                  name="reason"
                  ref="reason"
              ></b-form-textarea>
              <span v-show="errors.has('reason')" class="text-danger">{{ errors.first('reason') }}</span>
            </b-form-group>
          </b-col>
        </b-row>
        <b-row class="mt-2">
          <b-col>
            <b-button class="main-button" @click="deleteAccount()" >
              Eliminar cuenta
            </b-button>
          </b-col>
        </b-row>
      </b-container>
    </b-overlay>
  </b-modal>
</template>

<script>
import UserService from "@/services/user/userService";
import {showSuccessToast} from "@/components/alerts/alerts";
import Vue  from "vue";

export default {
  name: 'DeleteAccountAdminModal',
  props: {
    email: {
      required: true
    },
    admin:''
  },
  data() {
    return {
      reason: ''
    }
  },
  methods: {
    async deleteAccount() {
      this.$validator.validateAll().then(async result => {
        if (result) {
          Vue.swal({
            title: '¿Estás seguro?',
            text: "Una vez eliminada la cuenta no se podrá recuperar.",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: 'var(--black-base)',
            confirmButtonText: 'Sí, cambiar',
            cancelButtonText: 'Cancelar',
          }).then(async (result) => {
            if (result.isConfirmed) {

              const payload = {
                email: this.email,
                reazon: this.reason,
                admin: this.admin
              }

              this.changeStatusOverlay()
              const response = await UserService.deleteAccountAdminService(payload);
              this.changeStatusOverlay()

              if (response) {
                showSuccessToast('', 'La cuenta ha sido eliminada correctamente')

                setTimeout(() => {
                  window.location.href = '/user-management';
                }, 1000);
              }
            }
          });
        }
      });
    },
    changeStatusOverlay() {
      this.$store.dispatch('changeStatusOverlay');
    },
    async getProfile() {
      const payload = {
        email: this.$store.getters.getEmail
      }

      const response = await UserService.getProfileService(payload);
      this.admin = response.data.fullName;
    },
    async showModal(){
      await this.getProfile();
    },
    hiddenModal(){
      this.reason = '';
    }
  }
}

</script>

<style scoped>

</style>