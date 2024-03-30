import axios from "../../config/http-client.gateway";


const MESSAGE_ACCEPTED = "Tu solicitud de cambio de datos ha sido aprobada. " +
                                "Revisa tus nuevos datos, si tienes alguna duda contacta al equipo de soporte."

const getPageRequestsDataChangeService = async (pagination) => {
    try {
        const {page, size} = pagination;
        const response = await axios.doGet(`/venta-ropa/api/requests-data-change/get-page?size=${size}&page=${page - 1}`);
        return response.data
    } catch (e) {
        console.log(e)
    }
}

const getRequestDataChangeByIdService = async (idRequestDataChange) => {
    try {
        const response = await axios.doPost('/venta-ropa/api/requests-data-change/get-by-id-request-data-change', {
            idRequestDataChange
        });
        return response.data;
    } catch (e) {
        console.log(e);
    }
}

const putRequestDataChangeService = async (requestData) => {
    try {
        const response = await axios.doPut('/venta-ropa/api/requests-data-change/put-request-data-change', requestData);
        return response.data;
    } catch (e) {
        console.log(e);
    }
}

export default {
    getPageRequestsDataChangeService,
    getRequestDataChangeByIdService,
    putRequestDataChangeService
}
