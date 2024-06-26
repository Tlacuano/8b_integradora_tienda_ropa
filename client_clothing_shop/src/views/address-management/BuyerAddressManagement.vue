<template>
  <section class="interface">
    <b-row>
      <b-col class="text-center">
        <h1 class="tittle">Gestion de Direcciones</h1>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-row>
          <b-col text-right>
            <b-button @click="showRegistrationModal" class="register-button" variant="primary">Registrar</b-button>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
    <b-row class="mt-3 container-address">
      <b-col>
        <b-row>
          <b-col v-for="address in addresses" :key="address.idAddress" cols="12" sm="6" lg="3">
            <b-card class="address-card highlight-on-hover" :class="address.status.toLowerCase()">
              <div class="d-flex flex-column flex-md-row justify-content-between">
                <div class="address-info">
                  <div><strong>Dirección:</strong> {{ address.address }}</div>
                <div><strong>Colonia:</strong> {{ address.neighborhood }}</div>
                <div><strong>Calle:</strong> {{ address.street }}</div>
                <div><strong>Referencias:</strong> {{ address.referencesAddress }}</div>
                <div><strong>Código Postal:</strong> {{ address.postalCode }}</div>
                <div><strong>Estado:</strong> {{ address.state }}</div>
                </div>
                <div class="mt-2 mt-md-0">
                  <div class="status-badge">{{ address.status }}</div>
                </div>
                <b-dropdown variant="link" no-caret class="card-dropdown">
                  <template #button-content>
                    <font-awesome-icon icon="ellipsis-v" class="text-dark" />
                  </template>
                  <!-- Mostrar si el usuario es vendedor -->
                  <b-dropdown-item
                      v-if="role === 'SELLER'"
                      @click="setAsSale(address)"
                      v-show="address.status !== 'Venta'"
                  >
                    Marcar como venta
                  </b-dropdown-item>
                  <!-- Mostrar si el usuario es comprador -->
                  <b-dropdown-item
                      v-if="role === 'BUYER'"
                      @click="setAsDefault(address)"
                      v-show="address.status !== 'Predeterminada'"
                  >
                    Marcar como predeterminada
                  </b-dropdown-item>
                  <b-dropdown-item @click="enableAddress(address.idAddress)">Habilitar</b-dropdown-item>
                  <b-dropdown-item @click="deleteAddress(address.idAddress)">Deshabilitar</b-dropdown-item>
                  <b-dropdown-item @click="openEditModal(address)">Modificar</b-dropdown-item>
                </b-dropdown>

              </div>
            </b-card>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
    <address-edit-modal ref="editModal" :address="currentAddress"  @update:success="handleUpdateSuccess" @registered="fetchAddresses"/>
    <address-registration-modal ref="registrationModal" :email="email" @registered="fetchAddresses"></address-registration-modal>
  </section>
</template>


<script>
import {mapGetters} from 'vuex';
import AddressService from '../../services/adressess-management/AddressesManagement';
import { showSuccessToast, showWarningToast } from '../../components/alerts/alerts';
import AddressEditModal from '../../views/address-management/AdressManagementEditModal.vue';
import AddressRegistrationModal from '../../views/address-management/AddressManagementRegisterModal.vue';

export default {
  components: {
    AddressEditModal,
    AddressRegistrationModal,
  },
  data() {
    return {
      addresses: [],
      currentAddress: {},
    };
  },
  computed: {
    ...mapGetters(['getEmail', "getRole"]),
    email() {
      return this.getEmail;
    },
    role() {
      return this.getRole;
    }
  },
  methods: {
    async fetchAddresses() {
      const email = this.getEmail;
      try {
        const response = await AddressService.getAddressByEmailService(email);
        this.addresses = response.data
      } catch (error) {
      }
    },
    async deleteAddress(idAddress) {
      try {
        await AddressService.disableAddressService(idAddress);
        showSuccessToast('Éxito', 'Dirección deshabilitada correctamente');
        this.fetchAddresses();
      } catch (error) {
        showWarningToast('Error', 'No se pudo deshabilitar la dirección');
      }
    },

    async setAsDefault(address) {
      try {
        const response = await AddressService.putAddressStatusService({
          idAddress: address.idAddress,
          status: "Predeterminada",
          idPerson: address.idPerson
        });
        showSuccessToast('Éxito', 'Dirección marcada como predeterminada');
        this.fetchAddresses();
      } catch (error) {
        showWarningToast('Error', 'No se pudo marcar como predeterminada');
      }
    },
    async setAsSale(address) {
      try {
        const response = await AddressService.putAddressStatusService({
          idAddress: address.idAddress,
          status: "Venta",
          idPerson: address.idPerson
        });
        showSuccessToast('Éxito', 'Dirección marcada como venta');
        this.fetchAddresses();
      } catch (error) {
        showWarningToast('Error', 'No se pudo marcar como venta');
      }
    },

    async enableAddress(idAddress) {
      try {
        await AddressService.enableAddressService(idAddress);
        showSuccessToast('Éxito', 'Dirección habilitada correctamente');
        this.fetchAddresses();
      } catch (error) {
        showWarningToast('Error', 'No se pudo habilitar la dirección');
      }
    },

    openEditModal(address) {
      this.currentAddress = address;
      this.$refs.editModal.show();
    },

    handleUpdateSuccess(updatedAddress) {
      this.fetchAddresses();
    },

    showRegistrationModal() {
      this.currentAddress = {
        idAddress: null,
        address: '',
        neighborhood: '',
        street: '',
        referencesAddress: '',
        postalCode: '',
        state: '',
      };
      this.$refs.registrationModal.show();
    },
  },

  mounted() {
    this.fetchAddresses();
  },

};
</script>

<style scoped>
.container-address {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;

}


.address-card {
  background-color: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 15px;
  position: relative;
  width: calc(105% - 1rem);
  box-sizing: border-box;
  margin-bottom: 20px;


}


.status-badge {
  font-size: 0.9em;
  font-weight: bold;
  padding: 4px 8px;
  bottom: -5px;
  right: 10px;
  border-radius: 8px;
  color: #fff;
  margin-right: 10px;
  position: absolute;
}

@media (max-width: 767.98px) {
  .address-card .status-badge {
    position: static;
    margin-top: 10px;
    width: 100%;
    text-align: center;
  }
}

.habilitada .status-badge { background-color: #4CAF50; }
.deshabilitada .status-badge { background-color: #F44336; }
.predeterminada .status-badge { background-color: #2196F3; }
.venta .status-badge { background-color: #FFC107; }




.card-dropdown {
  position: absolute;
  top: 0;
  right: 0;
}

.card-dropdown .dropdown-toggle {
  border: none;
  padding: 0;
  background: transparent;
}

.card-dropdown .dropdown-toggle .fa-ellipsis-v {
  color: black;
}

.register-button {
  background-color: #353942;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 10px 20px;
  font-size: 16px;
  font-weight: bold;
  display: block;
  width: auto;
  margin-left: auto;
  margin-right: 0;

}

.highlight-on-hover:hover {
  background-color: #f0f0f0;
}


.tittle{
  margin-bottom: 50px;
}
</style>
