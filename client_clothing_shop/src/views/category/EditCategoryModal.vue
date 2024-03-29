<template>
    <section>
        <b-modal id="editCategoryModal" hide-header hide-footer centered size="lg">
            <b-row>
                <b-col class="text-center">
                    <h3>Editar categoria</h3>
                </b-col>
            </b-row>

            <b-row class="my-3">
                <b-col>
                    <b-form>
                        <b-form-group label="Nombre de la categoria:" laber-for="category">
                            <b-form-input id="category" v-model="form.category"
                                v-validate="'required|alpha_spaces|min:5|max:15'" name="category" />
                            <span class="text-danger" v-show="errors.has('category')">
                                {{ errors.first("category") }}
                            </span>
                        </b-form-group>

                        <b-form-group label="Imagen de la categoria:" label-for="image">
                            <b-form-file id="image" v-model="form.image" accept="image/jpeg, image/png, image/jpg"
                                :state="Boolean(newImage)" placeholder="Seleccione una imagen" @input="handleFileUpload"
                                name="image" v-validate="'required|image|mimes:jpeg,jpg,png|image_size'" />
                            <span class="text-danger" v-show="errors.has('image')">
                                {{ errors.first("image") }}
                            </span>
                        </b-form-group>

                        <b-form-group label="Estado:" label-for="status">
                            <b-form-select id="status" v-model="form.status" :options="statusOptions"
                                value-field="value" text-field="text" name="status" v-validate="'required'" required />
                            <span class="text-danger" v-show="errors.has('status')">
                                {{ errors.first("status") }}
                            </span>
                        </b-form-group>

                    </b-form>
                    
                    <div class="text-right">
                    <router-link :to="{ name: 'ADMINSubcategoryManagement' }" class="my-3">
                        Ir a Gestionar subcategorias
                    </router-link> 
                    </div>
                    
                </b-col>

                <b-col class="text-center">
                    <b-img :src="imgPreview ? imgPreview : category.image" thumbnail
                        style="max-width: 50%; max-height: 95%" />
                </b-col>
            </b-row>

            <b-row class="justify-content-center mt-2">
                <b-button variant="dark" @click="editCategory" class="w-25 mx-5"
                    style="border-radius: 0.5rem">Guardar</b-button>
                <b-button variant="dark" @click="closeModal" class="w-25 mx-5" style="
            border-radius: 0.5rem;
            background-color: red;
            border-color: red;">Cancelar</b-button>
            </b-row>
        </b-modal>
    </section>
</template>

<script>
import Vue from "vue";
import CategoriesService from "../../services/category/CategoryService";
import { showInfoAlert, showWarningToast } from "@/components/alerts/alerts";
import CloudinaryService from "../../services/cloudinary/CloudinaryService";

export default Vue.extend({
    name: "EditCategoryModal",
    props: {
        category: Object
    },
    data() {
        return {
            form: {
                idCategory: null,
                category: null,
                image: null,
                status: true,
            },
            imgPreview: null,
            newImage: null,
            statusOptions: [
                { value: true, text: 'Habilitado' },
                { value: false, text: 'Deshabilitado' }
            ]
        };
    },
    methods: {
        async editCategory() {
            await this.$validator.validateAll().then(async (result) => {
                if (result) {
                    await showInfoAlert(
                        "¿Estas seguro de editar la categoría?",
                        "Se guardaran los cambios",
                        "Sí, guardar",
                        async () => {
                            if (this.newImage) {
                                const payload = this.newImage;
                                const imageUrl = await CloudinaryService.uploadImage(payload);

                                if (imageUrl.status === 200) {
                                    this.form.image = imageUrl.data.data;
                                } else {
                                    showWarningToast(
                                        "Error al editar la categoría",
                                        "No se pudo editar la categoría"
                                    );
                                }
                            } else {
                                this.form.image = this.category.image;
                            }

                            const payload = {
                                idCategory: this.category.idCategory,
                                category: this.form.category,
                                image: this.form.image,
                                status: this.form.status,
                            };

                            const response = await CategoriesService.putCategoryService(category);  //con post porque no hay put 

                    if (response && response.status === 201) {
                        this.clean();
                        this.$emit("category-edited");
                        this.$bvModal.hide("editCategoryModal");
                    } else {
                        showWarningToast(
                            "Error al editar la categoría",
                            "No se pudo editar la categoría"
                        );
                    }
                }
            );
        }
    });
    },

        async getCategories() {
    const pagination = {
        page: 1,
        size: 100,
    };
    const response = await CategoriesService.getPageCategoriesService(
        pagination
    );
    this.categories = response.data.content;
},
handleFileUpload() {
    this.imgPreview = URL.createObjectURL(this.newImage);
},
closeModal() {
    this.clean();
    this.$bvModal.hide("editCategoryModal");
},
clean() {
    this.newImage = null;
    this.imgPreview = null;
},
    },
mounted() {
   // Verifica si la categoría y su imagen están disponibles
   if (this.category && this.category.image) {
        // Crea un nuevo objeto File con la URL de la imagen
        const imageFile = new File([this.category.image], 'image.jpg', { type: 'image/jpeg' });
        // Asigna el objeto File al formulario
        this.form.image = imageFile;

        // Asigna otros valores del formulario
        this.form.idCategory = this.category.idCategory;
        this.form.category = this.category.category;
        this.form.status = this.category.status;
        this.imgPreview = this.category.image;
        console.log("Valor de image:", this.form.image);
    } else {
        // Si la imagen de la categoría no está disponible, maneja este caso adecuadamente
        console.warn("La imagen de la categoría no está disponible.");
        // Puedes mostrar un mensaje al usuario o tomar alguna otra acción apropiada
    }
},
});
</script>
