import axios from "../../config/http-client.gateway"

const getCategories = async () => {
    try {
        const response = await axios.doPost("/venta-ropa/api/categories/get-categories")
        return response.data;
    } catch (e) {
        console.error(e)
    }
}

export default {
    getCategories
}