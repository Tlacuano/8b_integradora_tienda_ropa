<template>
  <b-modal id="block-sell-modal" centered hide-footer hide-header @shown="showModal" @hidden="hiddenModal">
    <b-overlay :show="this.$store.getters.showOverlay">
      <b-container>
        <b-row>
          <b-col class="text-right">
            <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('block-sell-modal')"/>
          </b-col>
        </b-row>
        <b-row class="mt-1">
          <b-col class="text-center">
            <h1>Bloquear ventas</h1>
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
              Continuar
            </b-button>
          </b-col>
        </b-row>
      </b-container>
    </b-overlay>
  </b-modal>
</template>

<script>
import UserService from "@/services/user/userService";
import {showInfoAlert, showSuccessToast} from "@/components/alerts/alerts";
import Vue  from "vue";

export default {
  name: 'BlockSellModal',
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

          const payload = {
            email: this.email,
            reazon: this.reason,
            admin: this.admin
          }

          await showInfoAlert(
              "¿Estás seguro?",
              "Los productos de este usuario no serán visibles y no podrá publicar nuevos productos.",
              "Sí, bloquear",
              async () => {
                await UserService.blockSellService(payload)
              }
          );
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