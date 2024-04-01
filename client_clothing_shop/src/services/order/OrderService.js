import axios from "../../config/http-client.gateway";

import {showWarningToast} from "@/components/alerts/alerts";

const error = 'Ocurrió un error inesperado, por favor inténtelo más tarde';

const getPageOrdersService = async (pagination) => {
    try {
        const { page, size } = pagination;
        const response = await axios.doGet(`/venta-ropa/api/orders/get-orders?size=${size}&size=${page}`);
        return response.data.data;
    } catch (e) {
        console.log(e)
    }
}

const getOrdersByEmailService = async (payload, pagination) => {
    try {
        const { page, size } = pagination;
        const response = await axios.doPost(`/venta-ropa/api/orders/get-orders-by-user-email?size=${size}&page=${page}`, payload);
        return response.data;
    } catch (e) {
        showWarningToast('',error)
    }
}

export default {
    getPageOrdersService,
    getOrdersByEmailService,
}