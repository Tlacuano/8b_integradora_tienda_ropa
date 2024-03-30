import axios from "../../config/http-client.gateway";
import Vue from "vue";
import {showWarningToast} from "@/components/alerts/alerts";

const verifyService = async (payload) => {
    try {
        return await axios.doPost("/venta-ropa/api/captcha/validate", payload)
    } catch (e) {
        showWarningToast('','Error al validar el captcha')
        return e.data
    }
}

export default {
    verifyService
}