import axios from "@/config/http-client.gateway";

const MESSAGE_ACCEPTED = "¡Felicidades! Tu solicitud para ser vendedor ha sido aprobada. Ahora puedes comenzar a vender. " +
    "Revisa nuestras políticas y contacta al equipo de soporte si necesitas ayuda. ¡Bienvenido y buena suerte!"

const getPageRequestsService = async (pagination) => {
    try {
        const response = await axios.doGet("/venta-ropa/api/requests-become-seller/get-page", {
            pagination
        })
        return response.data
    } catch (e) {
    }
}

const getRequestByIdService = async (request) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/requests-become-seller/get-by-id-request-become-seller", {
            idRequestBecomeSeller: request
        });
        return response.data.data;
    } catch (e) {
    }
}

const postRequestBecomeSellerService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/requests-become-seller/post-request-become-seller", {
            email: payload.email,
            userSellerInformation: payload.userSellerInformation
        })
        return response.data.data;
    } catch (e) {
    }
}

const putStatusRequestService = async (payload) => {
    try {
        if (payload.status === "Aprobado") {
            payload.rejectionReason = MESSAGE_ACCEPTED;
        }
        await axios.doPost("/venta-ropa/api/requests-become-seller/put-request-become-seller", {
            idRequestBecomeSeller: payload.idRequestBecomeSeller,
            status: payload.status,
            rejectionReason: payload.rejectionReason
        });
    } catch (e) {
    }
}

export default {
    getPageRequestsService,
    getRequestByIdService,
    postRequestBecomeSellerService,
    putStatusRequestService
}