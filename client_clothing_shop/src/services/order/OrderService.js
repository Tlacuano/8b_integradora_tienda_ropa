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
    }
}

const getOrderDetailsByIdOrderService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/orders/get-order-details", payload);
        return response.data.data;
    } catch (e) {
    }
}

const getOrderHasProductsService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/order-has-products/get-orders-has-products-by-order-id", payload);
        return response.data.data;
    } catch (e) {
    }
}

const getByProductAndBuyerService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/order-has-products/get-orders-has-products-by-buyer", payload);
        return response.data;
    } catch (e) {
    }
}

const getOrderStatusService = async () => {
    try {
        const response = await axios.doGet("/venta-ropa/api/order-status/get-order-status");
        return response.data;
    } catch (e) {
    }
}

const getOrdersBySellerAndStatusService = async (payload, pagination) => {
    try {
        const { page, size } = pagination;

        const response = await axios.doPost(`/venta-ropa/api/order-has-products/get-orders-has-products-by-seller-and-status?size=${size}&page=${page - 1}`, payload);
        return response.data;
    } catch (e) {
    }
}

const cancelSellBySellerService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/order-has-products/cancel-sell-by-seller", payload);
        return response.data;
    } catch (e) {
    }
}

const markAsSentBySellerService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/order-has-products/mark-as-sent-by-seller", payload);
        return response.data;
    } catch (e) {
    }
}


export default {
    getPageOrdersService,
    getOrdersByEmailService,
    getOrderDetailsByIdOrderService,
    getOrderHasProductsService,
    getByProductAndBuyerService,
    getOrderStatusService,
    getOrdersBySellerAndStatusService,
    cancelSellBySellerService,
    markAsSentBySellerService
}