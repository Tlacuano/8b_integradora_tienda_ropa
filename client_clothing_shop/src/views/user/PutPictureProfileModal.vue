<template>
  <b-modal id="put-picture-profile-modal" hide-header centered hide-footer @hidden="resetModal">
    <b-container>
      <b-overlay :show="showOverlay">
        <b-row>
          <b-col class="text-right">
            <font-awesome-icon icon="times" class="text-secondary selectable" @click="$bvModal.hide('put-picture-profile-modal')"/>
          </b-col>
        </b-row>
        <b-row class="mt-3">
          <b-col class="text-center">
            <h1>Actualizar foto de perfil</h1>
          </b-col>
        </b-row>
        <b-row class="my-3">
          <b-col class="text-center">
            <b-avatar v-if="image === null || error.show" size="300"  class="" variant="dark"/>
            <img v-else :src="image" alt="Imagen seleccionada" class=" rounded-circle" style="width: 300px; height: 300px"/>
          </b-col>
        </b-row>
        <b-row class="mt-5 mb-3">
          <b-col>
            <b-form-group
            >
              <b-form-file
                  v-model="image"
                  accept=".jpg, .jpeg, .png, .gif"
                  placeholder="Seleccione una imagen"
                  @change="handleFileChange($event)"
                  lang="es"
              >
              </b-form-file>
            </b-form-group>
            <span v-if="error.show" class="text-danger small">{{error.message}}</span>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <b-button class="main-button px-5" @click="putPictureProfile()" >
              Guardar
            </b-button>
          </b-col>
        </b-row>
      </b-overlay>
    </b-container>
  </b-modal>
</template>

<script>
import {mapGetters} from "vuex";
import CloudinaryService from "@/services/cloudinary/CloudinaryService";
import PersonService from "@/services/person/personService";
import {showSuccessToast} from "@/components/alerts/alerts";
export default {
  name: 'PutPictureProfileModal',
  data() {
    return {
      image: null,
      file: null,
      error:{
        show: false,
        message: ''
      }
    }
  },
  methods: {
    async handleFileChange(event) {
      const fileInput = event.target;

      this.image = null;
      this.file = null;
      this.error.show = false;
      this.error.message = '';

      if (fileInput.files.length === 0) {
          return;
      }
      const selectedFile = fileInput.files[0];
      const validExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;

      if (!validExtensions.exec(selectedFile.name)) {
        this.error.show = true;
        this.error.message = 'Formato de archivo no válido. Solo se permiten archivos .jpg, .jpeg, .png, .gif.';
        this.image = null;
        return;
      }

      if (selectedFile.size > 2097152) {
        this.error.show = true;
        this.error.message = 'El archivo es demasiado grande, debe ser menor de 2MB.';
        this.image = null;
        return;
      }

      const reader = new FileReader();
      reader.onload = (e) => {
        this.image = e.target.result;
        this.file = selectedFile;
      };
      reader.readAsDataURL(selectedFile);

    },
    async putPictureProfile() {
      if(this.error.show){
        return;
      }

      this.changeStatusOverlay()
      if(this.file !== null){
        const responseImage = await CloudinaryService.uploadImage(this.file);
        if(!responseImage.data.error){
          const payload = {
            email: this.$store.getters.getEmail,
            picture: responseImage.data.data
          }

          const response = await PersonService.putPictureService(payload);

          if(response.data.data){
            showSuccessToast('',"Foto de perfil actualizada correctamente");
            this.$bvModal.hide('put-picture-profile-modal');
            setTimeout(() => {
              location.reload();
            }, 1000);
          }
        }
      }else{
        const payload = {
          email: this.$store.getters.getEmail,
          picture: null
        }

        const response = await PersonService.putPictureService(payload);

        if(response.data.data){
          showSuccessToast('',"Foto de perfil actualizada correctamente");
          this.$bvModal.hide('put-picture-profile-modal');
          setTimeout(() => {
            location.reload();
          }, 1000);
        }
      }

      this.changeStatusOverlay()
    },

    resetModal(){
      this.image = null;
      this.file = null;
      this.error.show = false;
      this.error.message = '';
    },

    changeStatusOverlay(){
      this.$store.dispatch('changeStatusOverlay');
    },
  },
  computed: {
    ...mapGetters(['getEmail','showOverlay'])
  }
}

</script>


<style scoped>

</style>