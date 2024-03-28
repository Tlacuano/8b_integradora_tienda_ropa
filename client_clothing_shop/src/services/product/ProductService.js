import axios from "../../config/http-client.gateway"

const getProductsByCategory = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/products/get-by-category", payload)
        return response.data;
    } catch (e) {
        console.error(e)
    }
}

const getProductsBySubcategory = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/products/get-by-subcategory", payload)
        return response.data;
    } catch (e) {
        console.error(e)
    }
}

export default {
    getProductsByCategory,
    getProductsBySubcategory
}