import axios from "../../config/http-client.gateway"

const getPageProductSalesRequests = async (pagination) => {
    try {
        const {page, size} = pagination;
        const response = await axios.doGet(`/venta-ropa/api/requests-sell-product/get-page?size=${size}&page=${page - 1}`);
        return response.data
    } catch (e) {
        console.log(e)
    }
}

const getPageProductSalesRequestsByUserEmail = async (pagination, email) => {
    try {
        const {page, size} = pagination;
        const response = await axios.doPost(`/venta-ropa/api/requests-sell-product/get-page-by-user-email?size=${size}&page=${page - 1}`, email);
        return response.data
    } catch (e) {
        console.log(e)
    }
}

const getByIdProductSalesRequest = async (id) => {
    try {
        const response = await axios.doPost('/venta-ropa/api/requests-sell-product/get-by-id-request-sell-product', {idRequestSellProduct: id})
        return response.data
    } catch (e) {
        console.log(e)
    }
}
const putProductSalesRequestStatus = async (id,status,rejectionReason) => {
    try {
        const response = await axios.doPut('/venta-ropa/api/requests-sell-product/put-request-sell-product', {idRequestSellProduct: id, status:status,rejectionReason:rejectionReason})
    } catch (e) {
        console.log(e)
    }
}

export default {
    getPageProductSalesRequests,
    getByIdProductSalesRequest,
    putProductSalesRequestStatus,
    getPageProductSalesRequestsByUserEmail
};