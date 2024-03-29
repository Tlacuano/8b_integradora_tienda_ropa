<template>
    <section class="interface">
        <b-row>
            <b-col class="text-center">
                <h1>Categorias</h1>
            </b-col>
        </b-row>

        <b-row class="mt-4" align-h="between">
            <b-col cols="12" lg=4>
                <b-form-group>
                    <div class="position-relative">
                        <b-form-input class="pr-5" id="search" type="text" placeholder="Buscar..."/>
                        <font-awesome-icon icon="magnifying-glass" class="search-icon"/>
                    </div>
                </b-form-group>
            </b-col>

            <b-col class="text-right" cols="auto">
                <b-button variant="dark" @click="openAddCategoryModal">Registrar</b-button>
            </b-col>
        </b-row>

        <b-row class="mt-4 container-categories py-4">
            <b-col>
                <b-row></b-row>

                <b-row>
                    <b-col cols="auto" v-for="category in categories" :key="category.id">
                        <b-card
                            class="highlight-on-hover mb-2"
                            :img-src="category.image"
                            img-bottom
                            style="max-width: 14rem;"
                        >
                            <b-card-text class="d-flex justify-content-between align-items-center" style="max-height: 0.1rem;">
                                <h5>{{category.category}}</h5>
                                <b-dropdown
                                    variant="link-dark"
                                    toggle-class="text-decoration-none"
                                    no-caret
                                    style="margin-bottom: 0.5rem;"
                                >
                                    <template v-slot:button-content>
                                        <font-awesome-icon icon="ellipsis-v" />
                                    </template>
                                    <b-dropdown-item @click="openEditCategoryModal(category)">Editar</b-dropdown-item>
                                    <b-dropdown-item @click="changeStatusCategory(category)">
                                        <div v-if="!category.status">
                                            Habilitar
                                        </div>
                                        <div v-else>
                                            Deshabilitar
                                        </div>
                                    </b-dropdown-item>
                                </b-dropdown>
                            </b-card-text>
                        </b-card>
                    </b-col>
                </b-row>
            </b-col>
            <b-col class="align-seld-start" cols="auto" style="margin-top: 1rem">
                <font-awesome-icon icon="fa-solid fa-filter" />
            </b-col>
        </b-row>

        <b-row class="mt-5">
            <b-col>
                <b-pagination 
                    v-model="objectPagination.page"
                    :total-rows="objectPagination.elements"
                    :per-page="objectPagination.size"
                    aria-controls="my-table"
                />
            </b-col>
        </b-row>

        <AddCategoryModal @category-added="refreshCategories"/>
        <EditCategoryModal :category="selectedCategory" @category-edited="refreshCategories"/>
    </section>
</template>
<script>
import Vue from 'vue';
import CategoriesService from "../../services/category/CategoryService";
import {showInfoAlert} from "@/components/alerts/Alerts";

export default Vue.extend({
    name: "CategoryManagement",
    components: {
        AddCategoryModal: () => import("@/views/category/AddCategoryModal.vue"),
        EditCategoryModal: () => import("@/views/category/EditCategoryModal.vue"),
    },
    data() {
        return {
            categories: [],
            objectPagination: {
                page: 1,
                size: 24,
                elements: 0
            },
            selectedCategory: {},
        }
    },
    methods: {
        async getCategories() {
            //this.showOverlay();
            const response = await CategoriesService.getCategories();
            console.log("response... ", response.data.content);
            //this.showOverlay();
            //this.objectPagination.elements = response.totalElements;
            this.categories = response.data.content;
        },
        async changeStatusCategory(category) {
            await showInfoAlert(
                "¿Estas seguro de " + (category.status ? "deshabilitar" : "habilitar") + " esta categoria?",
                "Los cambios serán reflejados en el sistema",
                "Cambiar",
                async () => {
                    await CategoriesService.putStatusCategoryService(category.idCategory);
                }
            )
        },
        openAddCategoryModal() {
            this.$nextTick(() => {
                this.$bvModal.show('addCategoryModal');
            });
        },
        openEditCategoryModal(category) {
            this.selectedCategory = category;
            this.$nextTick(() => {
                this.$bvModal.show('editCategoryModal');
            })
        },
        showOverlay(){
            this.$store.dispatch('changeStatusOverlay')
        },
        refreshCategories(){
        this.getCategories();
        },
    },
    
    mounted() {
        this.getCategories();
    }
})
</script>
<style>
.container-categories {
    border: 1px solid black;
    margin-left: 0.1rem;
    margin-right: 0.1rem;
    border-radius: 0.5rem;
}
    
</style>