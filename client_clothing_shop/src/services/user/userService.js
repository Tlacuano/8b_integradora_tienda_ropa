import axios from "../../config/http-client.gateway"
import {showWarningToast} from "@/components/alerts/alerts";

const getPageUsersService = async (pagination) => {
    try {
        const { page, size } = pagination;
        const response = await axios.doGet(`/venta-ropa/api/users/get-page?size=${size}&page=${page - 1}`);
        return response.data
    }catch (e){
    }
}

const getPageUsersByEmailService = async (pagination, payload) => {
    try {
        const { page, size } = pagination;
        const response = await axios.doPost(`/venta-ropa/api/users/get-page-by-email?size=${size}&page=${page - 1}`, payload);
        return response.data
    }catch (e){
    }
}

const getPageAdminsService = async (pagination) => {
    try {
        const { page, size } = pagination;
        const response = await axios.doGet(`/venta-ropa/api/users/get-page-admins?size=${size}&page=${page - 1}`);
        return response.data
    }catch (e){
    }

}

const getPageAdminsByEmailService = async (pagination, payload) => {
    try {
        const { page, size } = pagination;
        const response = await axios.doPost(`/venta-ropa/api/users/get-page-admins-by-email?size=${size}&page=${page - 1}`, payload);
        return response.data
    }catch (e){
    }

}

const putStatusUserService = async (payload) => {
    try{
        const response = await axios.doPost("/venta-ropa/api/users/put-status", payload);
        return response.data
    }catch (e) {
    }
}

const getUserDetailsByEmailAdminService = async (email) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/users/get-user-detaiil-by-email-admin`, email);
        return response.data
    }catch (e){
        return e.data
    }
}

const existUserByEmailService = async (email) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/users/exist-by-email`, email);
        return response.data
    }catch (e){
    }
}

const postUserService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/users/post-account", payload);
        return response.data
    }catch (e){
    }
}

const postAdminService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/users/post-admin-account", payload);
        return response.data
    }catch (e){
    }
}

const verifyCodeService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/users/verify-email", payload);
        return response.data
    }catch (e){
    }
}

const getProfileService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/users/get-profile", payload);
        return response.data
    }catch (e){
    }
}

const resendEmailCode = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/users/resend-email-code", payload);
        return response.data
    }catch (e){

    }
}

const deleteIncompleteAccountService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/users/delete-incomplete-account", payload);
        return response.data
    }catch (e){
    }
}

const restorePasswordService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/users/restore-password", payload);
        return response.data
    }catch (e){
    }
}

const changePasswordService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/users/change-password", payload);
        return response.data
    }catch (e){
    }
}

const deleteAccountService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/users/delete-account", payload);
        return response.data
    } catch (e) {
    }
}

const deleteAccountAdminService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/users/delete-account-admin", payload);
        return response.data
    } catch (e) {
    }
}

const blockSellService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/sellers-information/block-sell", payload);
        return response.data
    } catch (e) {
    }
}

const unblockSellService = async (payload) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/sellers-information/unblock-sell", payload);
        return response.data
    } catch (e) {
    }
}


export default {
    getPageUsersService,
    putStatusUserService,
    getUserDetailsByEmailAdminService,
    existUserByEmailService,
    postUserService,
    verifyCodeService,
    getProfileService,
    resendEmailCode,
    deleteIncompleteAccountService,
    getPageUsersByEmailService,
    restorePasswordService,
    changePasswordService,
    deleteAccountService,
    getPageAdminsService,
    getPageAdminsByEmailService,
    postAdminService,
    deleteAccountAdminService,
    blockSellService,
    unblockSellService
};