import axios from "../../config/http-client.gateway";

const getPageRequestsReturnProductService = async (pagination, searchTerm = '') => {
    try {
        const { page, size } = pagination;
        const response = await axios.doGet(`/venta-ropa/api/requests-return-product/get-page?size=${size}&page=${page - 1}&searchTerm=${searchTerm}`);
        return response.data;
    } catch (e) {
    }
}

const getRequestReturnProductByIdService = async (requestId) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/requests-return-product/get-by-id-request-return-product`,{
            idRequestReturnProduct: requestId
        });
        return response.data;
    } catch (e) {
    }
}

const postRequestReturnProductService = async (requestData) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/requests-return-product/post-request-return-product`, requestData);
        return response.data;
    } catch (error) {
        if (error.response) {
            throw error.response;
        }
        throw new Error("Error desconocido al enviar la solicitud de devolución");
    }
};

const putRequestReturnProductStatusService = async (requestData) => {
    try {
        const response = await axios.doPut(`/venta-ropa/api/requests-return-product/put-request-return-product-status`, requestData);
        return response.data;
    } catch (error) {
        if (error.response) {
            throw error.response;
        }
        throw new Error("Error desconocido al actualizar el estado de la solicitud de devolución");
    }
};




export default {
    getPageRequestsReturnProductService,
    getRequestReturnProductByIdService,
    postRequestReturnProductService,
    putRequestReturnProductStatusService
}