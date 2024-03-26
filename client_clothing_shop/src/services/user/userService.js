import axios from "../../config/http-client.gateway"
import {showWarningToast} from "@/components/alerts/alerts";

const error = 'Ocurrió un error inesperado, por favor inténtelo más tarde';

const getPageUsersService = async (pagination) => {
    try {
        const { page, size } = pagination;
        const response = await axios.doGet(`/venta-ropa/api/users/get-page?size=${size}&page=${page - 1}`);
        return response.data
    }catch (e){
        showWarningToast('', error)
    }
}

const putStatusUserService = async (payload) => {
    try{
        const response = await axios.doPost("/venta-ropa/api/users/put-status", payload);
        return response.data
    }catch (e) {
        showWarningToast('', error)
    }
}

const getUserDetailsByEmailAdminService = async (email) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/users/get-user-detaiil-by-email-admin`, email);
        return response.data
    }catch (e){
        showWarningToast('', error)
        return e.data
    }
}

const existUserByEmailService = async (email) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/users/exist-by-email`, email);
        return response.data
    }catch (e){
        showWarningToast('', error)
    }
}

const postUserService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/users/post-account", payload);
        return response.data
    }catch (e){
        showWarningToast('', error)
    }
}

const verifyCodeService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/users/verify-email", payload);
        return response.data
    }catch (e){
        showWarningToast('', error)
    }
}


export default {
    getPageUsersService,
    putStatusUserService,
    getUserDetailsByEmailAdminService,
    existUserByEmailService,
    postUserService,
    verifyCodeService
};