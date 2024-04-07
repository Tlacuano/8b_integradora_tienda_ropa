import axios from "../../config/http-client.gateway";

const getPageRequestsReturnProductService = async (pagination, searchTerm = '') => {
    try {
        const { page, size } = pagination;
        const response = await axios.doGet(`/venta-ropa/api/requests-return-product/get-page?size=${size}&page=${page - 1}&searchTerm=${searchTerm}`);
        return response.data;
    } catch (e) {
        console.log(e);
    }
}

const getRequestReturnProductByIdService = async (requestId) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/requests-return-product/get-by-id-request-return-product`,{
            idRequestReturnProduct: requestId
        });
        return response.data;
    } catch (e) {
        console.log(e);
    }
};


export default {
    getPageRequestsReturnProductService,
    getRequestReturnProductByIdService
}