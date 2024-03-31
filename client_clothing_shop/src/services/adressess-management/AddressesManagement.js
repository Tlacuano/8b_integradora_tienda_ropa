import axios from "../../config/http-client.gateway";


const postAddressService = async (addressData) => {
    try {
        const response = await axios.doPost('/venta-ropa/api/addresses/post-address', addressData);
        return response.data;
    } catch (e) {
        console.log(e);
        throw e;
    }
}

const getAddressByEmailService = async (email) => {
    try {
        const response = await axios.doPost('/venta-ropa/api/addresses/get-addresses-by-email', { email });
        return response.data;
    } catch (e) {
        console.log(e);
        throw e;
    }
}

const putAddressService = async (addressData) => {
    try {
        const response = await axios.doPut('/venta-ropa/api/addresses/put-address', addressData);
        return response.data;
    } catch (e) {
        console.log(e);
        throw e;
    }
}

const putAddressStatusService = async (statusData) => {
    try {
        const response = await axios.doPut('/venta-ropa/api/addresses/put-status-address', statusData);
        return response.data;
    } catch (e) {
        console.log(e);
        throw e;
    }
}

export default {
    postAddressService,
    getAddressByEmailService,
    putAddressService,
    putAddressStatusService,
}
