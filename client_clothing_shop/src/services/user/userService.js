import axios from "../../config/http-client.gateway"

const getPageUsersService = async (pagination) => {
    try {
        const { page, size } = pagination;
        const response = await axios.doGet(`/venta-ropa/api/users/get-page?size=${size}&page=${page - 1}`);
        return response.data
    }catch (e){
        console.log(e)
    }
}

const putStatusUserService = async (payload) => {
    try{
        const response = await axios.doPost("/venta-ropa/api/users/put-status", payload);
        return response.data
    }catch (e) {
        console.log(e)
    }
}

const getUserDetailsByEmailAdminService = async (email) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/users/get-user-detaiil-by-email-admin`, email);
        return response.data
    }catch (e){
        console.log(e)
    }
}


export default {
    getPageUsersService,
    putStatusUserService,
    getUserDetailsByEmailAdminService
};