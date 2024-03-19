import axios from "../../config/http-client.gateway"

const getPageUsersService = async (pagination) => {
    try {
        const { page, size } = pagination;
        const response = await axios.doGet(`/user/get-page?size=${size}&page=${page - 1}`);
        return response.data
    }catch (e){
        console.log(e)
    }
}

const putStatusUserService = async (payload) => {
    try{
        console.log(payload)
        const response = await axios.doPost("/user/put-status", payload);

        return response.data
    }catch (e) {
        console.log(e)
    }
}


export default {
    getPageUsersService,
    putStatusUserService
};