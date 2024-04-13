import axios from "../../config/http-client.gateway";

const getPageRequestsDataChangeService = async (pagination, searchTerm = '') => {
    try {
        const { page, size } = pagination;
        const response = await axios.doGet(`/venta-ropa/api/requests-data-change/get-page?size=${size}&page=${page - 1}&searchTerm=${searchTerm}`);
        return response.data;
    } catch (e) {
    }
}

const getRequestDataChangeByIdService = async (idRequestDataChange) => {
    try {
        const response = await axios.doPost('/venta-ropa/api/requests-data-change/get-by-id-request-data-change', {
            idRequestDataChange
        });
        return response.data;
    } catch (e) {
    }
}

const putRequestDataChangeService = async (requestData) => {
    try {
        const response = await axios.doPut('/venta-ropa/api/requests-data-change/put-request-data-change', requestData);
        return response.data;
    } catch (e) {
    }
}

export default {
    getPageRequestsDataChangeService,
    getRequestDataChangeByIdService,
    putRequestDataChangeService
}
