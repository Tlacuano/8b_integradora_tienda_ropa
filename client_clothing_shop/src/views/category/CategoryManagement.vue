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
                        <b-form-input class="pr-5" id="search" type="text" placeholder="Buscar..." v-model="searchTerm" @input="filterCategories"/>
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

                <b-row class="text-center mt-3" v-if="filteredCategories.length === 0" >
                    <b-col>
                        <p>No se encuentra la categoria "{{ searchTerm }}"</p>
                    </b-col>
                </b-row>

                <b-row>
                    <b-col cols="auto" v-for="category in filteredCategories" :key="category.id">
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
            filteredCategories: [],
            objectPagination: {
                page: 1,
                size: 24,
                elements: 0
            },
            selectedCategory: {},
            searchTerm: ''
        }
    },
    methods: {
        async getCategories() {
            const response = await CategoriesService.getCategories();
            console.log("response... ", response.data.content);
            this.categories = response.data.content;
            this.filteredCategories = this.categories;
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
        filterCategories() {
            this.filteredCategories = this.categories.filter(category =>
                category.category.toLowerCase().startsWith(this.searchTerm.trim().toLowerCase())
            );
        }
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