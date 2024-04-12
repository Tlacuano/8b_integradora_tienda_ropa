<template>
  <section class="interface">

    <b-row class="mt-2">
      <b-col lg="4" class="my-auto pb-3">
        <b-row>
          <b-col class="text-center">
            <div
                @mousemove="showEditPicture = true"
                @mouseleave="showEditPicture = false"
            >
              <b-avatar
                  :src="user.person.picture || null"
                  alt="Image"
                  size="180px"
                  fluid
                  variant="dark"
                  thumbnail
                  class="custom-avatar-badge"
                  badge-variant="secondary"
                  @mouseenter="showEditPicture = true"
                  @mouseleave="showEditPicture = false"
              >
                <template #badge v-if="showEditPicture"  >
                  <font-awesome-icon icon="fa-solid fa-pen" class="selectable" v-b-modal:put-picture-profile-by-admin-modal/>
                </template>
              </b-avatar>
            </div>
          </b-col>
        </b-row>

        <b-row class="mt-4">
          <b-col>
            <b-card no-body class="selectable highlight-on-hover" v-b-modal:put-personal-information-user-modal>
              <b-row align-h="between" class="p-2 mx-1">
                <b-col>
                  <b>
                    Cambiar información personal
                  </b>
                </b-col>
                <b-col cols="auto" class="text-right">
                  <font-awesome-icon icon="fa-solid fa-angle-right"/>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>

        <b-row class="mt-1" v-if="user.roles.includes('ROLE_SELLER')">
          <b-col>
            <b-card no-body class="selectable highlight-on-hover" @click="blockSell(user.email)" >
              <b-row align-h="between" class="p-2 mx-1">
                <b-col>
                  <b>
                    {{ user.sellerInformation.blockSell === true ? 'Desbloquear venta' : 'Bloquear venta' }}
                  </b>
                </b-col>
                <b-col cols="auto" class="text-right">
                  <font-awesome-icon icon="fa-solid fa-angle-right"/>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>

        <b-row class="mt-1">
          <b-col>
            <b-card no-body class="selectable highlight-on-hover" @click="changeStatusUser(user.email)">
              <b-row align-h="between" class="p-2 mx-1">
                <b-col>
                  <b>
                    {{ user.status === true ? 'Deshabilitar cuenta' : 'Habilitar cuenta' }}
                  </b>
                </b-col>
                <b-col cols="auto" class="text-right">
                  <font-awesome-icon icon="fa-solid fa-angle-right"/>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>

        <b-row class="mt-1">
          <b-col>
            <b-card no-body class="selectable highlight-on-hover" v-b-modal:delete-account-admin-modal>
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

      </b-col>

      <b-col>
        <b-row class="container-right mt-sm-5 mt-md-0">
          <b-col>
            <b-row>
              <b-col>
                <b-card>
                  <b-row>
                    <b-col class="text-center">
                      <h4>Datos de usuario</h4>
                    </b-col>
                  </b-row>
                  <b-row class="mt-2">
                    <b-col>
                      <b-form-group label="Correo electrónico">
                        <b-form-input
                            v-model="user.email"
                            disabled
                        ></b-form-input>
                      </b-form-group>
                    </b-col>
                    <b-col>
                      <b-form-group label="Estado">
                        <b-input
                            :value="user.status ? 'Activo': 'Deshabilitado'"
                            disabled
                        >
                        </b-input>
                      </b-form-group>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <b-form-group label="Roles"  >
                        <b-input
                            v-for="role in user.roles"
                            :value="role"
                            class="mt-2"
                            disabled
                        >
                        </b-input>
                      </b-form-group>
                    </b-col>
                  </b-row>
                  <b-row>
                  </b-row>
                </b-card>
              </b-col>
            </b-row>

            <b-row class="mt-2" >
               <b-col>
                <b-card>
                  <b-row>
                    <b-col>
                      <b-row>
                        <b-col class="text-center">
                          <h4>Datos personales</h4>
                        </b-col>
                      </b-row>
                      <section v-if="user.person.name !== null">
                        <b-row class="mt-2">
                          <b-col md="4">
                            <b-form-group label="Nombre completo">
                              <b-form-input
                                  v-model="user.person.name"
                                  disabled
                              ></b-form-input>
                            </b-form-group>
                          </b-col>

                          <b-col md="4">
                            <b-form-group label="Primer apellido">
                              <b-form-input
                                  v-model="user.person.lastName"
                                  disabled
                              ></b-form-input>
                            </b-form-group>
                          </b-col>

                          <b-col md="4">
                            <b-form-group label="Segundo apellido">
                              <b-form-input
                                  v-model="user.person.secondLastName"
                                  disabled
                              ></b-form-input>
                            </b-form-group>
                          </b-col>
                        </b-row>

                        <b-row>
                          <b-col md="4">
                            <b-form-group label="Género">
                              <b-form-select v-model="user.person.gender" disabled>
                                <b-form-select-option value="masculino">Hombre</b-form-select-option>
                                <b-form-select-option value="femenino">Mujer</b-form-select-option>
                                <b-form-select-option value="otros">Otro</b-form-select-option>
                              </b-form-select>
                            </b-form-group>
                          </b-col>

                          <b-col md="4">
                            <b-form-group label="Fecha de nacimiento">
                              <b-form-input
                                  v-model="user.person.birthday"
                                  type="date"
                                  disabled
                              ></b-form-input>
                            </b-form-group>
                          </b-col>

                          <b-col md="4">
                            <b-form-group label="Teléfono">
                              <b-form-input
                                  v-model="user.person.phoneNumber"
                                  disabled
                              ></b-form-input>
                            </b-form-group>
                          </b-col>
                        </b-row>
                      </section>
                      <section v-else>
                        <b-row>
                          <b-col class="text-center">
                            <span class="text-black-50">No hay información personal registrada</span>
                          </b-col>
                        </b-row>
                      </section>

                    </b-col>
                  </b-row>
                </b-card>
              </b-col>
            </b-row>

            <b-row class="mt-2">
              <b-col>
                <b-card>
                  <b-row>
                    <b-col class="text-center">
                      <h4>Historial de ordenes</h4>
                    </b-col>
                  </b-row>
                  <section v-if="orders.elements.length > 0">
                    <b-row>
                      <b-col cols="12" lg="4">
                        <b-form-group>
                          <div class="position-relative">
                            <b-form-input id="search" type="text" placeholder="Buscar..." class="pr-5"></b-form-input>
                            <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
                          </div>
                        </b-form-group>
                      </b-col>
                      <b-col class="text-right">
                        <span>Total de ordenes: {{orders.pagination.elements}}</span>
                      </b-col>
                    </b-row>
                    <b-row class="mt-1">
                      <b-col md="4" v-for="order in orders.elements" :id="order.idOrder" >
                        <b-card class="selectable highlight-on-hover">
                          <b-row>
                            <b-col>
                              <div class="small text-truncate"><p class="p-0 m-0"><span ><b>Numero de orden: </b>{{order.orderNumber}}</span></p></div>
                              <p  class="p-0 m-0"><span class="small text-truncate"><b>Fecha: </b>{{order.orderDate}}</span></p>
                            </b-col>
                          </b-row>
                        </b-card>
                      </b-col>
                    </b-row>
                    <b-row class="mt-3">
                      <b-col class="text-center">
                        <b-pagination
                            v-model="orders.pagination.page"
                            :total-rows="orders.pagination.elements"
                            :per-page="orders.pagination.size"
                            aria-controls="my-table"
                        ></b-pagination>
                      </b-col>
                    </b-row>
                  </section>
                  <section v-else>
                    <b-row>
                      <b-col class="text-center">
                        <span class="text-black-50">No hay ordenes registradas</span>
                      </b-col>
                    </b-row>
                  </section>

                </b-card>
              </b-col>
            </b-row>

            <b-row class="mt-2">
              <b-col>
                <b-card>
                  <b-row>
                    <b-col class="text-center ">
                      <h4>Datos fiscales</h4>
                    </b-col>
                  </b-row>
                  <b-row v-if="user.sellerInformation.taxIdentificationNumber !== null">
                    <b-col md="6">
                      <b-row class="mt-2">
                        <b-col>
                          <b-form-group label="Número de identificación fiscal">
                            <b-form-input
                                v-model="user.sellerInformation.taxIdentificationNumber"
                                disabled
                            ></b-form-input>
                          </b-form-group>
                        </b-col>
                      </b-row>
                      <b-row>
                        <b-col>
                          <b-form-group label="CURP">
                            <b-form-input
                                v-model="user.sellerInformation.curp"
                                disabled
                            ></b-form-input>
                          </b-form-group>
                        </b-col>
                      </b-row>

                      <b-row>
                        <b-col>
                          <b-form-group label="Teléfono secundario">
                            <b-form-input
                                v-model="user.sellerInformation.secondaryPhoneNumber"
                                disabled
                            ></b-form-input>
                          </b-form-group>
                        </b-col>
                      </b-row>
                    </b-col>
                    <b-col md="6" class="text-center">
                      <b-row>
                        <b-col>
                          <b-img
                              v-if="user.sellerInformation.imageIdentification"
                              :src="user.sellerInformation.imageIdentification"
                              alt="Image"
                              fluid

                              style="height: 250px"
                          />
                        </b-col>
                      </b-row>
                    </b-col>
                  </b-row>
                  <b-row v-else>
                    <b-col class="text-center">
                      <span class="text-black-50">El usuario no cuenta con el rol de vendedor</span>
                    </b-col>
                  </b-row>
                </b-card>
              </b-col>
            </b-row>

          </b-col>
        </b-row>
      </b-col>
    </b-row>

    <DeleteAccountAdminModal :email="decodeCrypto(email)"/>
    <PutPersonalInformationUserModal :email="decodeCrypto(email)"/>
    <BlockSellModal :email="decodeCrypto(email)"/>
    <PutPictureProfileByAdminModal :email="decodeCrypto(email)"/>
  </section>
</template>

<script>
import PeopleService from "@/services/user/userService";
import {decodeCrypto} from "@/utils/security/cryptoJs";
import OrderService from "@/services/order/OrderService";
import {showInfoAlert} from "@/components/alerts/alerts";
import UserService from "@/services/user/userService";

export default {
  name: "UserDetails",
  components: {
    DeleteAccountAdminModal : () => import('@/views/user/DeleteAccountAdminModal.vue'),
    PutPersonalInformationUserModal : () => import('@/views/user/PutPersonalInformationUserModal.vue'),
    BlockSellModal : () => import('@/views/user/BlockSellAccountModal.vue'),
    PutPictureProfileByAdminModal : () => import('@/views/user/PutPictureProfileByAdminModal.vue')
  },
  props: {
    email: {
      type: String,
      required: true
    },
  },
  data() {
    return {
      user: {
        email:null,
        roles: [],
        status: null,
        person: {
          name: null,
          lastName: null,
          secondLastName: null,
          gender: null,
          birthday: null,
          phoneNumber: null,
          picture: null
        },
        sellerInformation:{
          imageIdentification: null,
          curp: null,
          secondaryPhoneNumber: null,
          privacyPolicyAgreement: null,
          taxIdentificationNumber: null,
          blockSell: null
        }
      },
      orders:{
        pagination:{
          page: 1,
          size: 3,
          elements: 0
        },
        elements: []
      },
      showEditPicture: false,
      admin: ''
    };
  },
  methods: {
    decodeCrypto,
    async getUserDetails(){
      this.showOverlay()

      const payload = {
        email: decodeCrypto(this.email)
      };
      const response = await PeopleService.getUserDetailsByEmailAdminService(payload);

      this.user.email = response.data.email;
      this.user.roles = response.data.roles;
      this.user.status = response.data.status;

      this.user.person.name = response.data.name;
      this.user.person.lastName = response.data.lastName;
      this.user.person.secondLastName = response.data.secondLastName;
      this.user.person.gender = response.data.gender;
      this.user.person.birthday =  new Date(response.data.birthday).toISOString().split('T')[0];
      this.user.person.phoneNumber = response.data.phoneNumber;
      this.user.person.picture = response.data.picture;

      this.user.sellerInformation.imageIdentification = response.data.imageIdentification;
      this.user.sellerInformation.curp = response.data.curp;
      this.user.sellerInformation.secondaryPhoneNumber = response.data.secondaryPhoneNumber;
      this.user.sellerInformation.privacyPolicyAgreement = response.data.privacyPolicyAgreement;
      this.user.sellerInformation.taxIdentificationNumber = response.data.taxIdentificationNumber;
      this.user.sellerInformation.blockSell = response.data.blockSell;

      this.showOverlay()
    },

    async getOrders(){
      this.showOverlay()
      const payload = {
        email:decodeCrypto(this.email)
      };
      const pagination = {
        page: this.orders.pagination.page - 1,
        size: this.orders.pagination.size
      }

      const response = await OrderService.getOrdersByEmailService(payload, pagination);

      this.orders.pagination.elements = response.data.totalElements;
      this.orders.elements = response.data.content;
      this.showOverlay()

    },

    async changeStatusUser(dato){
      const payoad = {
        email:dato
      }
      showInfoAlert(
          "¿Estás seguro?",
          "¿Deseas cambiar el estado de la cuenta?",
          "Sí, cambiar",
          async () => {
            await PeopleService.putStatusUserService(payoad);
          }
      )
    },

    async blockSell(){
      if(!this.user.sellerInformation.blockSell) {
        this.$bvModal.show('block-sell-modal');
      }else {
        const payload = {
          email: this.user.email,
          admin: this.admin
        }
        showInfoAlert(
            "¿Estás seguro?",
            "¿Deseas desbloquear la venta de este usuario?",
            "Sí, desbloquear",
            async () => {
              await UserService.unblockSellService(payload)
            }
        );
      }
    },

    async getProfile() {
      const payload = {
        email: this.$store.getters.getEmail
      }

      const response = await UserService.getProfileService(payload);
      this.admin = response.data.fullName;
    },


    showOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    }
  },
  mounted() {
    this.getUserDetails();
    this.getOrders();
    this.getProfile();
  },
  watch:{
    'orders.pagination.page': function(){
      this.getOrders();
    }
  }
}

</script>

<style>

.interface{
  overflow-x: hidden;
}
.container-right{
  height: calc(100vh - 100px);
  overflow-y: auto;
  overflow-x: hidden;
}
</style>