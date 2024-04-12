import axios from "../../config/http-client.gateway"

const getPaymentCardsByUserEmail = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/payment-cards/get-payment-card-by-user-email", payload)
        return response.data;
    } catch (e) {
    }
}

export default {
    getPaymentCardsByUserEmail
}