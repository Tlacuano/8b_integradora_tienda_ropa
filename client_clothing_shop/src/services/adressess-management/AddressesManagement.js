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
    console.log("Datos enviados al servidor:", statusData);

    try {
        const response = await axios.doPut('/venta-ropa/api/addresses/put-status-address', statusData);
        return response.data;
    } catch (e) {
        console.log(e);
        throw e;
    }
}

const disableAddressService = async (idAddress) => {
    try {
        const response = await axios.doPut('/venta-ropa/api/addresses/put-status-address', {
            idAddress,
            status: "Deshabilitada"
        });
        return response.data;
    } catch (e) {
        console.log(e);
        throw e;
    }
}

const enableAddressService = async (idAddress) => {
    try {
        const response = await axios.doPut('/venta-ropa/api/addresses/put-status-address', {
            idAddress,
            status: "Habilitada"
        });
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
    disableAddressService,
    enableAddressService,
}
