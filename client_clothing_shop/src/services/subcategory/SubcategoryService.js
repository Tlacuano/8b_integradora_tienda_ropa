import axios from "../../config/http-client.gateway"

const getSubcategoriesByCategory = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/subcategories/get-by-category", payload);
        return response.data;
    } catch (e) {
        console.error(e);
    }
}

export default {
    getSubcategories: getSubcategoriesByCategory
}