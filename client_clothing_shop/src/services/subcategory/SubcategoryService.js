import axios from "../../config/http-client.gateway"

const getSubcategoriesByCategory = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/subcategories/get-by-category", payload);
        return response.data;
    } catch (e) {
        console.error(e);
    }
}

const getPageSubcategoriesService = async (pagination) => {
    try {
        const { page, size } = pagination;
        const response = await axios.doGet(`/venta-ropa/api/subcategories/get-subcategories?size=${size}&page=${page - 1}`);
        return response.data;
    } catch (e) {
        console.log(e);
    }
}

const postSubcategoryService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/subcategories/post-subcategory", payload);
        return response.data;
    } catch (e) {
        console.log(e);
    }
}

const putSubcategoryService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/subcategories/put-subcategory", payload);
        return response.data;
    } catch (e) {
        console.log(e);
    }
}

const putStatusSubcategoryService = async (subcategory) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/subcategories/put-status-subcategory", {
            idSubcategory: subcategory
        });
        return response.data;
    } catch (e) {
        console.log(e);
    }
}

export default {
    getSubcategoriesByCategory,
    getPageSubcategoriesService,
    postSubcategoryService,
    putSubcategoryService,
    putStatusSubcategoryService
}