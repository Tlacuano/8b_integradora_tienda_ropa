import axios from "../../config/http-client.gateway";
import {showWarningToast} from "@/components/alerts/alerts";

const error = 'Ocurrió un error inesperado, por favor inténtelo más tarde';

const postPersonalInformationService = async (payload) => {
    try {
        return await axios.doPost("/venta-ropa/api/person/post-personal-information", payload)
    } catch (e) {
        showWarningToast('',error)
        return e.data
    }
}

const verifyCodeService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/person/verify-phone", payload);
        return response.data
    }catch (e){
        showWarningToast('', error)
    }
}

const deletePersonalInformationIncompleteService = async (payload) => {
    try {
        return await axios.doPost("/venta-ropa/api/person/delete-personal-information-incomplete", payload)
    } catch (e) {
        showWarningToast('',error)
        return e.data
    }
}

const resendPhoneCodeService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/person/resend-phone-code", payload);
        return response.data
    }catch (e){
        showWarningToast('', error)
    }
}

const getPersonalInformationService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/person/get-personal-information",payload)
        return response.data
    } catch (e) {
        showWarningToast('',error)
        return e.data
    }
}

const putPersonalInformationService = async (payload) => {
    try {
        return await axios.doPost("/venta-ropa/api/person/put-personal-information", payload)
    } catch (e) {
        showWarningToast('',error)
        return e.data
    }
}


export default {
    postPersonalInformationService,
    verifyCodeService,
    deletePersonalInformationIncompleteService,
    resendPhoneCodeService,
    getPersonalInformationService,
    putPersonalInformationService
}