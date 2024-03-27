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

export default {
    getPageRequestsService
}