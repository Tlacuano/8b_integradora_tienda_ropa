import axios from "../../config/http-client.gateway"

const getPaymentCardsByUserEmail = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/payment-cards/get-payment-card-by-user-email", payload)
        return response.data;
    } catch (e) {
    }
}

const postPaymentCard = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/payment-cards/post-payment-card", payload)
        return response.data.status;
    } catch (e) {
    }
}

const putPaymentCardStatus = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/payment-cards/put-payment-card-status", payload)
        console.log(response)
        return response.data.status;
    } catch (e) {
    }
}

const deletePaymentCard = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/payment-cards/delete-payment-card", payload)
        return response.data.status;
    } catch (e) {
    }
}
export default {
    getPaymentCardsByUserEmail,
    postPaymentCard,
    putPaymentCardStatus,
    deletePaymentCard
}