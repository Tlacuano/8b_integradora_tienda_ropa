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




export default {
    postPersonalInformationService,
    verifyCodeService
}