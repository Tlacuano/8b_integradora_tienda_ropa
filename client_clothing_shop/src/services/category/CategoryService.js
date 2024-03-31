import axios from "../../config/http-client.gateway"

const getCategories = async () => {
    try {
        const response = await axios.doGet("/venta-ropa/api/categories/get-categories")
        console.log('response.data: ', response.data)
        return response.data;
    } catch (e) {
        console.error(e)
    }
}
        
const getPageCategoriesService = async (pagination) => {
    try {
        const response = await axios.doGet(`/venta-ropa/api/categories/get-categories`, {
            pagination
        } );
        return response.data;
    } catch (e) {
        console.log(e);
    }
}

const postCategoryService = async (category) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/categories/post-category", category);
        return response.data;
    } catch (e) {
        console.error(e);
    }
}

const putCategoryService = async (category) => {
    try {
        const response = await axios.doPut("/venta-ropa/api/categories/put-category", category);
        return response.data;
    } catch (e) {
        console.error(e);
    }
}

const putStatusCategoryService = async (category) => {
    try {
        const response = await axios.doPut("/venta-ropa/api/categories/put-status-category", {idCategory: category});
        return response.data;
    } catch (e) {
        console.error(e);
    }
}

export default {
    getCategories,
    getPageCategoriesService,
    postCategoryService,
    putCategoryService,
    putStatusCategoryService,
}

