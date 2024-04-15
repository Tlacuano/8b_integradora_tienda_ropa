<template>
  <b-modal
      id="addressManagementRegisterModal"
      centered
      size="lg"
      hide-footer
      hide-header
      ok-only
      @hidden="resetFormData"
  >
    <b-container fluid>

      <b-row class="mb-2">
        <b-col class="text-right">
          <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('addressManagementRegisterModal')"/>
        </b-col>
      </b-row>

      <!-- Título del Modal -->
      <b-row class="mb-4">
        <b-col class="text-center">
          <h3 class="modal-title">Registro de direccion</h3>
        </b-col>
      </b-row>
      <validation-observer ref="observer" v-slot="{ invalid }">
      <b-form id="formulario">

        <b-row>
          <b-col cols="6">
            <b-form-group label="Nombre de la dirección:">
              <validation-provider rules="required|min:5|max:100" v-slot="{ errors }">
                <b-form-input v-model="address.address"></b-form-input>
                <span class="text-danger">{{ errors[0] }}</span>
              </validation-provider>
            </b-form-group>
          </b-col>
          <b-col cols="6">
            <b-form-group label="Colonia:">
              <validation-provider rules="required|max:50" v-slot="{ errors }">
                <b-form-input v-model="address.neighborhood"></b-form-input>
                <span class="text-danger">{{ errors[0] }}</span>
              </validation-provider>
            </b-form-group>
          </b-col>
          <b-col cols="6">
            <b-form-group label="Calle y numero:">
              <validation-provider rules="required|max:50" v-slot="{ errors }">
                <b-form-input v-model="address.street"></b-form-input>
                <span class="text-danger">{{ errors[0] }}</span>
              </validation-provider>
            </b-form-group>
          </b-col>
          <b-col cols="6">
            <b-form-group label="Referencias:">
              <validation-provider rules="max:255" v-slot="{ errors }">
                <b-form-input v-model="address.referencesAddress"></b-form-input>
                <span class="text-danger">{{ errors[0] }}</span>
              </validation-provider>
            </b-form-group>
          </b-col>
          <b-col cols="6">
            <b-form-group label="Código Postal:">
              <validation-provider rules="required|digits:5" v-slot="{ errors }">
                <b-form-input v-model="address.postalCode"></b-form-input>
                <span class="text-danger">{{ errors[0] }}</span>
              </validation-provider>
            </b-form-group>
          </b-col>
          <b-col cols="6">
            <b-form-group label="Estado:">
              <validation-provider rules="required|max:100" v-slot="{ errors }">
                <b-form-input v-model="address.state"></b-form-input>
                <span class="text-danger">{{ errors[0] }}</span>
              </validation-provider>
            </b-form-group>
          </b-col>
        </b-row>
        <b-row class="mt-4">
          <b-col class="text-right action-buttons">
            <b-button class="btn-accept mr-2" @click="registerAddress">Registrar direccion</b-button>
            <b-button variant="danger" @click="hideModal">Cancelar</b-button>
          </b-col>
        </b-row>
      </b-form>
      </validation-observer>
    </b-container>
  </b-modal>
</template>

<script>
import AddressesManagement from "@/services/adressess-management/AddressesManagement";
import {showInfoAlert, showSuccessToast} from "../../components/alerts/alerts";
import { ValidationProvider, ValidationObserver } from 'vee-validate';

export default {
  name: "AddressManagementRegisterModal",
  components: {
    ValidationProvider,
    ValidationObserver
  },
  props: {
    email: String,
  },
  data() {
    return {
      address: this.defaultAddress()
    }
  },
  methods: {
    defaultAddress() {
      return {
        address: '',
        neighborhood: '',
        street: '',
        referencesAddress: '',
        postalCode: '',
        state: ''
      };
    },
    hideModal() {
      this.$bvModal.hide('addressManagementRegisterModal');
    },
    resetFormData() {
      this.address = this.defaultAddress();
    },
    async registerAddress() {
      const isValid = await this.$refs.observer.validate();
      if (isValid) {
        const addressToRegister = {
          address: this.address.address,
          neighborhood: this.address.neighborhood,
          street: this.address.street,
          referencesAddress: this.address.referencesAddress,
          postalCode: this.address.postalCode,
          state: this.address.state,
          email: this.email
        };

        try {
          const response = await AddressesManagement.postAddressService(addressToRegister);
          if (response.status === 201) {
            this.hideModal();
            showSuccessToast('Registro Exitoso', 'La dirección ha sido registrada correctamente');
            this.$emit('registered', response.data);
          } else {
            throw new Error('La respuesta no tiene el estado esperado.');
          }
        } catch (error) {
          showWarningToast('Error', 'No se pudo registrar la dirección');
        }
      }
    },
    show() {
      this.$bvModal.show('addressManagementRegisterModal');
    },
  },
};
</script>

<style scoped>
.modal-title {
  font-weight: bold;
}

</style>