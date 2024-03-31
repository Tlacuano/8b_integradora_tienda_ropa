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

export default {
    getOrdersByEmailService
}