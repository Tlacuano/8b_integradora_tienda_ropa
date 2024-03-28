import axios from "../../config/http-client.gateway"

const getCategories = async () => {
    try {
        const response = await axios.doGet("/venta-ropa/api/categories/get-categories")
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
    putStatusCategoryService,
}

