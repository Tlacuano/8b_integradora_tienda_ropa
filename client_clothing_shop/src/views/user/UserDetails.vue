<template>
  <section class="interface">
    <b-row>
      <b-col class="text-center">
        <h1>Detalles del usuario</h1>
      </b-col>
    </b-row>

    <b-row class="mt-2">
      <b-col lg="4">
        <b-row>
          <b-col class="text-center">
            <b-avatar
                v-if="user.person.picture!==null"
                :src="user.person.picture"
                alt="Image"
                size="200px"
                fluid
                thumbnail
            />
            <b-avatar
                v-else
                size="200px"
                variant="secondary"
                class="text-uppercase"
            />
          </b-col>
        </b-row>
        <b-row class="mt-2">
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
                    <b-col>
                      <b-row>
                        <b-col class="text-center">
                          <h4>Datos personales</h4>
                        </b-col>
                      </b-row>

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
                              <p class="p-0 m-0"><span class="small text-truncate"><b>Numero de orden: </b>{{order.orderNumber}}</span></p>
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
  </section>

</template>

<script>
import PeopleService from "@/services/user/userService";
import {decodeCrypto} from "@/utils/security/cryptoJs";
import OrderService from "@/services/order/OrderService";
export default {
  name: "UserDetails",
  props: {
    email: {
      type: String,
      required: true
    }
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
        }
      },
      orders:{
        pagination:{
          page: 1,
          size: 3,
          elements: 0
        },
        elements: []
      }
    };
  },
  methods: {
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
      console.log(payload);
      const response = await OrderService.getOrdersByEmailService(payload, pagination);

      this.orders.pagination.elements = response.data.totalElements;
      this.orders.elements = response.data.content;
      this.showOverlay()
      console.log(response);

    },


    showOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    }
  },
  mounted() {
    this.getUserDetails();
    this.getOrders();
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
  height: calc(100vh - 175px);
  overflow-y: auto;
  overflow-x: hidden;
}
</style>