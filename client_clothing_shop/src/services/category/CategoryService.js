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

export default {
    getCategories,
    getPageCategoriesService
}

