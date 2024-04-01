<template>
    <section class="interface">
        <b-row>
            <b-col class="text-center">
                <h1>Categorías</h1>
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
        </b-row>

        <b-row class="mt-4 container-categories py-4">
            <b-col cols="12">
                <b-row class="text-center mt-3" v-if="filteredCategories.length === 0" >
                    <b-col>
                        <h3>No se encuentra la categoría "{{ searchTerm }}"</h3>
                    </b-col>
                </b-row>

                <b-row>
                    <b-col cols="4" v-for="category in filteredCategories" :key="category.id">
                        <b-card
                            class="highlight-on-hover mb-2"
                            :img-src="category.image"
                            img-bottom
                            :class="{ 'disabled-card': !category.status } "
                            style="max-width: 35rem;"
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
        EditCategoryModal: () => import("@/views/category/EditCategoryModal.vue"),
    },
    data() {
        return {
            categories: [],
            filteredCategories: [],
            selectedCategory: {},
            searchTerm: ''
        }
    },
    methods: {
        async getCategories() {
            this.showOverlay();
            const response = await CategoriesService.getCategories();
            this.showOverlay();
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
    margin-left: 0.1rem;
    margin-right: 0.1rem;
    border-radius: 0.5rem;
}
.b-card img{
    width: 100%;
    height: 200px;
}

.b-card {
    margin: 0.5rem;
}

.disabled-card {
    background-color: lightgray;
}
    
</style>