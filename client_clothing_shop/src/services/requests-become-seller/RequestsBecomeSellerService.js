import axios from "@/config/http-client.gateway";

const getPageRequestsService = async (pagination) => {
    try {
        const response = await axios.doGet("/venta-ropa/api/requests-become-seller/get-page", {
            pagination
        })
        return response.data
    } catch (e) {
        console.log(e)
    }
}

const getRequestByIdService = async (request) => {
    try {
        console.log(request)
        const response = await axios.doPost("/venta-ropa/api/requests-become-seller/get-by-id-request-become-seller", {
            requestId: request.idRequestBecomeSeller
        });
        console.log(response)
    } catch (e) {
        console.log(decryptData(e.data))
    }
}

export default {
    getPageRequestsService,
    getRequestByIdService
}