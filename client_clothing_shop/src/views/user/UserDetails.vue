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
                            v-model="user.person.name"

                        ></b-form-input>
                      </b-form-group>
                    </b-col>

                    <b-col>
                      <b-form-group label="Primer apellido">
                        <b-form-input
                            v-model="user.person.lastName"

                        ></b-form-input>
                      </b-form-group>
                    </b-col>

                    <b-col>
                      <b-form-group label="Segundo apellido">
                        <b-form-input
                            v-model="user.person.secondLastName"

                        ></b-form-input>
                      </b-form-group>
                    </b-col>
                  </b-row>

                  <b-row>
                    <b-col>
                      <b-form-group label="Género">
                        <b-form-select v-model="user.person.gender">
                          <b-form-select-option value="masculino">Hombre</b-form-select-option>
                          <b-form-select-option value="femenino">Mujer</b-form-select-option>
                          <b-form-select-option value="otros">Otro</b-form-select-option>
                        </b-form-select>
                      </b-form-group>
                    </b-col>

                    <b-col>
                      <b-form-group label="Fecha de nacimiento">
                        <b-form-input
                            v-model="user.person.birthday"
                            type="date"

                        ></b-form-input>
                      </b-form-group>
                    </b-col>

                    <b-col>
                      <b-form-group label="Teléfono">
                        <b-form-input
                            v-model="user.person.phoneNumber"
                        ></b-form-input>
                      </b-form-group>
                    </b-col>
                  </b-row>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>

        <b-row class="mt-2" v-if="user.sellerInformation.taxIdentificationNumber">
          <b-col>
            <b-card>
              <b-row>
                <b-col class="text-center ">
                  <h4>Datos fiscales</h4>
                </b-col>
              </b-row>

              <b-row class="mt-2">
                <b-col>
                  <b-form-group label="Número de identificación fiscal">
                    <b-form-input
                        v-model="user.sellerInformation.taxIdentificationNumber"
                    ></b-form-input>
                  </b-form-group>
                </b-col>

                <b-col>
                  <b-form-group label="CURP">
                    <b-form-input
                        v-model="user.sellerInformation.curp"
                    ></b-form-input>
                  </b-form-group>
                </b-col>
              </b-row>

              <b-row>
                <b-col>
                  <b-row>
                    <b-col>
                      <b-form-group label="Teléfono secundario">
                        <b-form-input
                            v-model="user.sellerInformation.secondaryPhoneNumber"
                        ></b-form-input>
                      </b-form-group>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col>
                      <b-form-group label="Acuerdo de política de privacidad">
                        <b-form-checkbox
                            v-model="user.sellerInformation.privacyPolicyAgreement"
                        ></b-form-checkbox>
                      </b-form-group>
                    </b-col>
                  </b-row>
                </b-col>
                <b-col>
                  <b-row>
                    <b-col>
                      <b-img
                          v-if="user.sellerInformation.imageIdentification"
                          :src="user.sellerInformation.imageIdentification"
                          alt="Image"
                          fluid
                          thumbnail
                      />
                    </b-col>
                  </b-row>
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
        email:null,
        person: {
          name: null,
          lastName: null,
          secondLastName: null,
          gender: null,
          birthday: null,
          phoneNumber: null
        },
        sellerInformation:{
          imageIdentification: null,
          curp: null,
          secondaryPhoneNumber: null,
          privacyPolicyAgreement: null,
          taxIdentificationNumber: null,
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
      this.user.email = response.data.email;

      this.user.person.name = response.data.name;
      this.user.person.lastName = response.data.lastName;
      this.user.person.secondLastName = response.data.secondLastName;
      this.user.person.gender = response.data.gender;
      this.user.person.birthday =  new Date(response.data.birthday).toISOString().split('T')[0];
      this.user.person.phoneNumber = response.data.phoneNumber;

      this.user.sellerInformation.imageIdentification = response.data.imageIdentification;
      this.user.sellerInformation.curp = response.data.curp;
      this.user.sellerInformation.secondaryPhoneNumber = response.data.secondaryPhoneNumber;
      this.user.sellerInformation.privacyPolicyAgreement = response.data.privacyPolicyAgreement;
      this.user.sellerInformation.taxIdentificationNumber = response.data.taxIdentificationNumber;


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