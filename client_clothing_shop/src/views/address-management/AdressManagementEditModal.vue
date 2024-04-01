<template>
  <b-modal
      id="addressManagementEditModal"
      centered
      size="lg"
      hide-footer
      hide-header
      ok-only
  >
    <b-container fluid>

      <b-row class="mb-2">
        <b-col class="text-right">
          <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('addressManagementEditModal')"/>
        </b-col>
      </b-row>

      <!-- Título del Modal -->
      <b-row class="mb-4">
        <b-col class="text-center">
          <h3 class="modal-title">Edicion de direccion</h3>
        </b-col>
      </b-row>

    <b-form id="formulario">
      <b-row>
        <b-col cols="6">
          <b-form-group label="Dirección:">
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
          <b-form-group label="Calle:">
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
          <b-button class="btn-accept mr-2" @click="saveAddress">Guardar cambios</b-button>
          <b-button variant="danger" @click="hideModal">Cancelar</b-button>
        </b-col>
      </b-row>
    </b-form>
    </b-container>
  </b-modal>
</template>

<script>
import AddressesManagement from "@/services/adressess-management/AddressesManagement";
import {showInfoAlert, showSuccessToast} from "../../components/alerts/alerts";
import { ValidationProvider } from 'vee-validate';

export default {
  name: "AddressManagementEditModal",
  components: {
    ValidationProvider
  },
  props: {
    address: Object
  },
  methods: {
    hideModal() {
      this.$bvModal.hide('addressManagementEditModal');
    },
    async saveAddress() {
      try {
        const addressData = {
          idAddress: this.address.idAddress,
          address: this.address.address,
          neighborhood: this.address.neighborhood,
          street: this.address.street,
          referencesAddress: this.address.referencesAddress,
          postalCode: this.address.postalCode,
          state: this.address.state
        };

        const response = await AddressesManagement.putAddressService(addressData);

        if (response.status === 200) {
          this.hideModal();
          showSuccessToast('Actualizado', 'La dirección se actualizó correctamente');
          this.$emit('updated', response.data);
        } else {
          throw new Error('La respuesta no tiene el estado esperado.');
        }
      } catch (error) {
        console.error("Error al guardar los cambios de la dirección:", error);
        showWarningToast('Error', 'No se pudo actualizar la dirección');
      }
    },
    show() {
      this.$bvModal.show('addressManagementEditModal');
    },
  },
};
</script>

<style scoped>
.modal-title {
  font-weight: bold;
}

</style>