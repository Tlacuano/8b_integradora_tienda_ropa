<template>
  <section class="interface">
    <b-row>
      <b-col class="text-center">
        <h1>Detalles del usuario</h1>
      </b-col>
    </b-row>

    <b-row class="mt-4">
      <b-col lg="4">
        <b-row>
          <b-col class="text-center">
            <b-img
                v-if="user.picture"
                :src="user.picture"
                alt="Image"
                fluid
                thumbnail
            />
            <b-avatar
                v-else
                size="200px"
                variant="secondary"
                class="text-uppercase"
                :text="user.email.charAt(0) + user.email.charAt(4)"
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

                    ></b-form-input>
                  </b-form-group>
                </b-col>
              </b-row>

              <b-row >
                <b-col >
                  <b-form-group label="Contraseña">
                    <b-form-input
                        v-model="user.password"

                    ></b-form-input>
                  </b-form-group>
                </b-col>
              </b-row>

              <b-row>
                <b-col>
                  <b-form-group label="Confirmar Contraseña">
                    <b-form-input
                        v-model="user.fullName"

                    ></b-form-input>
                  </b-form-group>
                </b-col>
              </b-row>

              <b-row>
                <b-col>
                  <b-button class="main-button">
                    Guardar
                  </b-button>
                </b-col>
              </b-row>
            </b-card>

          </b-col>
        </b-row>
      </b-col>


      <b-col >
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
                    <b-col>
                      <b-form-group label="Nombre completo">
                        <b-form-input
                            v-model="user.name"

                        ></b-form-input>
                      </b-form-group>
                    </b-col>

                    <b-col>
                      <b-form-group label="Primer apellido">
                        <b-form-input
                            v-model="user.lastName"

                        ></b-form-input>
                      </b-form-group>
                    </b-col>

                    <b-col>
                      <b-form-group label="Segundo apellido">
                        <b-form-input
                            v-model="user.secondLastName"

                        ></b-form-input>
                      </b-form-group>
                    </b-col>
                  </b-row>

                  <b-row>
                    <b-col>
                      <b-form-group label="Género">
                        <b-form-select

                        ></b-form-select>
                      </b-form-group>
                    </b-col>

                    <b-col>
                      <b-form-group label="Fecha de nacimiento">
                        <b-form-input
                            v-model="user.birthDate"
                            type="date"

                        ></b-form-input>
                      </b-form-group>
                    </b-col>

                    <b-col>
                      <b-form-group label="Teléfono">
                        <b-form-input
                            v-model="user.phoneNumber"

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
                <b-col class="text-center ">
                  <h4>Datos fiscales</h4>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>








      </b-col>
    </b-row>
  </section>

</template>

<script>
import PeopleService from "@/services/user/userService";
import {decodeCrypto} from "@/utils/security/cryptoJs";
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
        email:'',
        person: {
          name: '',
          lastName: '',
          secondLastName: '',
          gender: '',
          birthday: '',
          phoneNumber: ''
        },
        sellerInformation:{
          imageIdentification: '',
          curp: '',
          secondaryPhoneNumber: '',
          privacyPolicyAgreement: false,
          taxIdentificationNumber: '',
        }
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
      this.user = response.data;

      this.showOverlay()
      console.log(response.data);
    },


    showOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    }
  },
  mounted() {
    this.getUserDetails();
  }
}

</script>

<style scoped>

</style>