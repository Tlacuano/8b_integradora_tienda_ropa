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
                            <b-form-file id="image" v-model="selectedFile" accept="image/jpeg, image/png, image/jpg"
                                :state="Boolean(selectedFile)" placeholder="Seleccione una imagen" @input="handleFileUpload"
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
            selectedFile: null, // Almacena la imagen seleccionada
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
                            if (this.selectedFile) {
                                const payload = this.selectedFile;
                                const imageUrl = await CloudinaryService.uploadImage(payload);

                                if (imageUrl.status === 200) {
                                    this.form.image = imageUrl.data.data; 
                                    // Evita la caché de la imagen
                                    this.imgPreview = `${this.form.image}?${Math.random()}`; 
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

                            const response = await CategoriesService.putCategoryService(payload);

                            if (response && response.status === 201) {
                                this.clean();
                                this.$emit("category-edited");
                                this.$bvModal.hide("editCategoryModal");
                                this.getCategories();
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
            const response = await CategoriesService.getCategories();
            this.categories = response.data.content;
            this.filteredCategories = this.categories; // Se inicializa el filtro de categorías
        },
        handleFileUpload() {
            if(this.selectedFile && this.selectedFile.length > 0) {
                this.selectedFile = this.selectedFile[0];
                this.imgPreview = URL.createObjectURL(this.selectedFile);
            }
        },
        closeModal() {
            this.clean();
            this.$bvModal.hide("editCategoryModal");
        },
        clean() {
            this.selectedFile = null;
            this.imgPreview = null;
        },
        initializeFormData() {
            // Verifica si la categoría está disponible
            if (this.category) {
                // Asigna los valores de la categoría al formulario presentado
                this.form.idCategory = this.category.idCategory;
                this.form.category = this.category.category;
                this.form.status = this.category.status;

                // Si la categoría tiene una imagen, asigna la imagen al formulario y muestra la vista previa
                if (this.category.image) {
                    this.form.image = this.category.image;
                    this.imgPreview = this.category.image;
                }
            } else {
                // Si la categoría no está disponible, limpia los datos del formulario y la vista previa de la imagen
                this.clean();
            }
        }
    },
    mounted() {
        // Inicializa los datos del formulario y la vista previa de la imagen al cargar el componente modal
        this.initializeFormData();
    },
    watch: {
        // Observador para detectar cambios en la categoría seleccionada
        category: {
            handler(newCategory) {
                // Si cambia la categoría seleccionada, vuelve a inicializar los datos del formulario y la vista previa de la imagen
                this.initializeFormData();
            },
            deep: true // Observa los cambios en las propiedades anidadas de la categoría
        },
        selectedFile: {
            handler(newFile) {
                // Si cambia el archivo seleccionado, muestra la vista previa de la imagen
                if(newFile) {
                    this.imgPreview = URL.createObjectURL(newFile);
            } else {
                // Si no hay archivo seleccionado, limpia la vista previa de la imagen
                this.imgPreview = null;
            }
        }
    }
}
});
</script>