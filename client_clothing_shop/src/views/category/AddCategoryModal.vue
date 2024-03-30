<template>
    <section>
        <b-modal id="addCategoryModal" hide-header hide-footer centered size="lg">
            <b-row>
                <b-col class="text-center">
                    <h3>Registrar categoria</h3>
                </b-col>
            </b-row>

            <b-row class="mt-3">
                <b-col>
                    <!--<b-form @submit="addSubcategory">-->
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
                                :state="Boolean(form.image)" placeholder="Seleccione una imagen"
                                @input="handleFileUpload" name="image"
                                v-validate="'required|image|mimes:jpeg,jpg,png|image_size'" />
                            <span class="text-danger" v-show="errors.has('image')">
                                {{ errors.first("image") }}
                            </span>
                        </b-form-group>

                        <b-form-group label="Estado:" label-for="status">
                            <b-form-select id="status" v-model="form.status" :options="statusOptions" value-field="value"
                                text-field="text" name="status" v-validate="'required'" required/>
                            <span class="text-danger" v-show="errors.has('status')">
                                {{ errors.first("status") }}
                            </span>
                        </b-form-group>

                    </b-form>
                </b-col>

                <b-col class="text-center">
                    <b-img v-if="imgPreview" :src="imgPreview" thumbnail style="max-width: 50%; max-height: 95%" />
                </b-col>
            </b-row>

            <b-row class="justify-content-center">
                <b-button variant="dark" @click="addCategory" class="w-25 mx-5"
                    style="border-radius: 0.5rem">Registrar</b-button>
                <b-button variant="dark" @click="closeModal" class="w-25 mx-5" style="
            border-radius: 0.5rem;
            background-color: red;
            border-color: red;
          ">Cancelar</b-button>
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
    name: "AddCategoryModal",
    data() {
        return {
            form: {
                category: null,
                image: null,
                status: true,
            },
            imgPreview: null,
            statusOptions: [
                { value: true, text: 'Habilitado'},
                { value: false, text: 'Deshabilitado'}
            ]
        };
    },
    methods: {
        async addCategory() {
            await this.$validator.validateAll().then(async (result) => {
                if (result) {
                    await showInfoAlert(
                        "¿Estas seguro de registrar la categoría?",
                        "Se registrará la categoría",
                        "Registrar",
                        async () => {
                            const payload = this.form.image;
                            console.log("contenido de payload", payload);
                            const imageUrl = await CloudinaryService.uploadImage(payload);
                            if (imageUrl.status === 200) {
                                const payload = {
                                    category: this.form.category,
                                    image: imageUrl.data.data,
                                    status: this.form.status,
                                };

                                const response = await CategoriesService.postCategoryService(
                                    payload
                                );

                                if (response && response.status === 201) {
                                    this.clean();
                                    this.$emit("category-added");
                                    this.$bvModal.hide("addCategoryModal");
                                } else {
                                    showWarningToast(
                                        "Error al registrar la categoría",
                                        "No se pudo registrar la categoría"
                                    );
                                }
                            } else {
                                showWarningToast(
                                    "Error al registrar la categoría",
                                    "No se pudo registrar la subcategoría"
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
            this.imgPreview = URL.createObjectURL(this.form.image);
        },
        closeModal() {
            this.clean();
            this.$bvModal.hide("addCategoryModal");
        },
        clean() {
            this.form = {
                category: null,
                image: null,
                status: true,
            };
            this.imgPreview = null;
        },
    },
    mounted() {
        this.getCategories();
    },
});
</script>
