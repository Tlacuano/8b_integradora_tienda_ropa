import axios from "../../config/http-client.gateway"
import {showWarningToast} from "@/components/alerts/alerts";

const error = 'Ocurrió un error inesperado, por favor inténtelo más tarde';

const getOrdersByEmailService = async (payload) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/shopping-carts/get-shopping-cart`, payload);
        return response.data;
    } catch (e) {
        showWarningToast('',error)
    }
}

const putShoppingCartService = async (payload) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/shopping-carts/put-shopping-cart`, payload);
        return response.data;
    } catch (e) {
        showWarningToast('',error)
    }
}

const deleteShoppingCartService = async (payload) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/shopping-carts/delete-shopping-cart`, payload);
        return response.data;
    } catch (e) {
        showWarningToast('',error)
    }
}

const postShoppingCartService = async (payload) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/shopping-carts/post-shopping-cart`, payload);
        return response.data;
    } catch (e) {
        showWarningToast('',error)
    }
}

export default {
    getOrdersByEmailService,
    putShoppingCartService,
    deleteShoppingCartService,
    postShoppingCartService
}