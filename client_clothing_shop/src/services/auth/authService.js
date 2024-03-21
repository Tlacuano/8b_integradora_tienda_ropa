import axios from "../../config/http-client.gateway";
import Vue from "vue";
import {showWarningToast} from "@/components/alerts/alerts";
const loginService = async (payload) => {
    try {
        return await axios.doPost("/login", payload)
    } catch (e) {
        console.log(e)
        showWarningToast('','Email o contraseña incorrectos')
        return e.data
    }
}

export default {
    loginService
}