import axios from "../../config/http-client.gateway"
import {decodeCrypto} from "@/utils/security/cryptoJs";

const getProductsByCategory = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/products/get-by-category", payload)
        return response.data;
    } catch (e) {
        console.error(e)
    }
}

const getProductById = async (productId) => {
    try {
        const decodedId = decodeCrypto(productId)
        const response = await axios.doPost("/venta-ropa/api/products/get-product", {idProduct: decodedId})
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
    getProductsBySubcategory,
    getProductById
}