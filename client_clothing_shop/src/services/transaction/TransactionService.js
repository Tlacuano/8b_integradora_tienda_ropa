import axios from "../../config/http-client.gateway"

const createCheckoutSession = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/transactions/create-checkout-session", payload);
        return response.data;
    } catch (e) {
        console.error(e);
    }
}

export default {
    createCheckoutSession
}