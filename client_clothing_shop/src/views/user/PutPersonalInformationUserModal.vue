<template>
  <b-modal id="put-personal-information-user-modal" hide-header hide-footer centered @shown="getPersonalInformation">
    <b-container>
      <b-overlay :show="showOverlay">

        <section v-if="page === 1" >
          <b-row>
            <b-col class="text-right">
              <font-awesome-icon icon="times" class="selectable text-secondary" @click="$bvModal.hide('put-personal-information-user-modal')"/>
            </b-col>
          </b-row>
          <b-row class="mt-3">
            <b-col class="text-center">
              <h1>Información personal</h1>
            </b-col>
          </b-row>
          <b-row class="mt-3">
            <b-col >
              <b-form-group label="Nombre(s)" label-for="input-name">
                <b-form-input
                    id="input-name"
                    type="text"
                    v-model="form.name"
                    name="name"
                    v-validate="'required|alpha_spaces'"
                ></b-form-input>
                <span v-show="errors.has('name')" class="text-danger">{{ errors.first('name') }}</span>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row>
            <b-col >
              <b-form-group label="Primer apellido" label-for="input-last-name">
                <b-form-input
                    id="input-last-name"
                    type="text"
                    v-model="form.lastName"
                    name="lastName"
                    v-validate="'required|alpha_spaces'"
                ></b-form-input>
                <span v-show="errors.has('lastName')" class="text-danger">{{ errors.first('lastName') }}</span>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row>
            <b-col >
              <b-form-group label="Segundo apellido" label-for="input-second-last-name">
                <b-form-input
                    id="input-second-last-name"
                    type="text"
                    v-model="form.secondLastName"
                    name="secondLastName"
                    v-validate="'required|alpha_spaces'"
                ></b-form-input>
                <span v-show="errors.has('secondLastName')" class="text-danger">{{ errors.first('secondLastName') }}</span>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row>
            <b-col >
              <b-form-group label="Fecha de nacimiento" label-for="input-birthday">
                <b-form-input
                    id="input-birthday"
                    type="date"
                    v-model="form.birthday"
                    name="birthday"
                    min="1940-01-01"
                    :max="today"
                    v-validate="'required|over_18'"
                ></b-form-input>
                <span v-show="errors.has('birthday')" class="text-danger">{{ errors.first('birthday') }}</span>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row>
            <b-col>
              <b-form-group label="Género" label-for="select-gender">
                <b-form-select
                    id="select-gender"
                    v-model="form.gender"
                    name="gender"
                    v-validate="'required'"
                >
                  <b-form-select-option value="MASCULINO">Masculino</b-form-select-option>
                  <b-form-select-option value="FEMENINO">Femenino</b-form-select-option>
                  <b-form-select-option value="OTROS">Otro</b-form-select-option>
                </b-form-select>
                <span v-show="errors.has('gender')" class="text-danger">{{ errors.first('gender')}}</span>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row class="my-4">
            <b-col>
              <b-button class="main-button  px-5" @click="putPersonalInformation()">
                Guardar
              </b-button>
            </b-col>
          </b-row>
        </section>


      </b-overlay>
    </b-container>
  </b-modal>
</template>

<script>
import PersonService from "@/services/person/personService";
import {mapGetters} from "vuex";
import Vue from "vue";
import {showSuccessToast} from "@/components/alerts/alerts";

export default {
  name: 'PutPersonalInformationUserModal',
  props: {
    email: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      page: 1,

      code : null,
      form: {
        name: null,
        lastName: null,
        secondLastName: null,
        phone: null,
        gender: null,
        birthday:null
      },
      copyForm: {},
    }
  },
  methods: {
    putPersonalInformation() {
      Promise.all([
        this.$validator.validate('name'),
        this.$validator.validate('lastName'),
        this.$validator.validate('secondLastName'),
        this.$validator.validate('birthday'),
        this.$validator.validate('gender'),
      ]).then(async (result) => {
        const payload = {
          email: this.email,
          name: this.form.name,
          lastName: this.form.lastName,
          secondLastName: this.form.secondLastName,
          phoneNumber: this.form.phone,
          gender: this.form.gender,
          birthday: this.form.birthday
        }

        Vue.swal({
          title: "¿Estás seguro?",
          text: "¿Deseas guardar los cambios?",
          icon: "question",
          showCancelButton: true,
          confirmButtonText: "Sí, estoy seguro",
          confirmButtonColor: "#212529",
          cancelButtonText: 'Cancelar',
        }).then(async (result) => {
          if (result.isConfirmed) {
            this.changeStatusOverlay()
            const response = await PersonService.putPersonalInformationService(payload);
            this.changeStatusOverlay()

            if(response.data){

              showSuccessToast('',"Información personal actualizada correctamente");
              this.$bvModal.hide('put-personal-information-profile-modal');

              setTimeout(() => {
                location.reload();
              }, 1000);

            }
          }
        });
      });
    },

    async getPersonalInformation() {
      this.changeStatusOverlay()
      const payload = {
        email: this.email
      }

      const response = await PersonService.getPersonalInformationService(payload);

      this.form.name = response.data.name;
      this.form.lastName = response.data.lastName;
      this.form.secondLastName = response.data.secondLastName;
      this.form.phone = response.data.phoneNumber;
      let birthdayArray = response.data.birthday;
      this.form.birthday = `${birthdayArray[0]}-${String(birthdayArray[1]).padStart(2, '0')}-${String(birthdayArray[2]).padStart(2, '0')}`;
      this.form.gender = response.data.gender;
      this.copyForm = {...this.form};
      this.changeStatusOverlay()
    },

    changeStatusOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    },
  },
  computed: {
    ...mapGetters(['showOverlay']),
    today() {
      const today = new Date();
      const month = `0${today.getMonth() + 1}`.slice(-2); // Ensure two digits
      const day = `0${today.getDate()}`.slice(-2); // Ensure two digits
      return `${today.getFullYear()}-${month}-${day}`;
    }
  }
}
</script>


<style scoped>

</style>