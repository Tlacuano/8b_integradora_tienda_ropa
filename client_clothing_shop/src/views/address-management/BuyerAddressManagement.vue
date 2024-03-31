<template>
  <section class="interface">
    <b-row>
      <b-col class="text-center">
        <h1>Gestion de Direcciones</h1>
      </b-col>
    </b-row>
    <b-row class="mt-3 container-address">
      <b-col>
        <b-row>
          <b-col v-for="address in addresses" :key="address.idAddress" cols="12" sm="6" lg="4">
            <div class="address-card" :class="address.status.toLowerCase()">
              <div class="address-info">
                <div class="address-title"><strong>Dirección:</strong> {{ address.address }}</div>
                <div><strong>Colonia:</strong> {{ address.neighborhood }}</div>
                <div><strong>Calle:</strong> {{ address.street }}</div>
                <div><strong>Referencias:</strong> {{ address.referencesAddress }}</div>
                <div><strong>Código Postal:</strong> {{ address.postalCode }}</div>
                <div><strong>Estado:</strong> {{ address.state }}</div>
              </div>
              <div class="status-badge">{{ address.status }}</div>
              <b-dropdown variant="link" no-caret class="card-dropdown">
                <template #button-content>
                  <font-awesome-icon icon="ellipsis-v" class="text-dark" />
                </template>
                <b-dropdown-item @click="deleteAddress(address.idAddress)">Eliminar</b-dropdown-item>
                <b-dropdown-item @click="setAsDefault(address.idAddress)">Marcar como predeterminada</b-dropdown-item>
                <b-dropdown-item @click="editAddress(address.idAddress)">Modificar</b-dropdown-item>
              </b-dropdown>
            </div>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
  </section>
</template>


<script>
import { mapGetters } from 'vuex';
import AddressService from '../../services/adressess-management/AddressesManagement';

export default {
  data() {
    return {
      addresses: [],
    };
  },
  computed: {
    ...mapGetters(['getEmail']),
  },
  methods: {
      async fetchAddresses() {
        const email = this.getEmail;
        try {
          const response = await AddressService.getAddressByEmailService(email);
          console.log("Direcciones recibidas:", response);
          this.addresses = response.data;
        } catch (error) {
          console.error("Error al obtener direcciones:", error);
        }
      },
    deleteAddress(idAddress) {
      // Aquí la lógica para eliminar la dirección
    },
    setAsDefault(idAddress) {
      // Aquí la lógica para marcar como dirección predeterminada
    },
    editAddress(idAddress) {
      // Aquí la lógica para editar la dirección
    },
  },
  mounted() {
    this.fetchAddresses();
  },
};
</script>

<style scoped>
.container-address {
  padding: 20px;
}

.address-card {
  background-color: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  margin: 3px 0;
  padding: 15px;
  position: relative;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  width: 70%;
}

.address-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.status-badge {
  font-size: 0.9em;
  font-weight: bold;
  padding: 4px 8px;
  position: absolute;
  bottom: 10px;
  right: 10px;
  border-radius: 8px;
  color: #fff;
}

.habilitada .status-badge { background-color: #4CAF50; }
.deshabilitada .status-badge { background-color: #F44336; }
.predeterminada .status-badge { background-color: #2196F3; }
.venta .status-badge { background-color: #FFC107; }



.address-title {
  font-size: 1.5em;
  margin-bottom: 10px;
}

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
</style>
