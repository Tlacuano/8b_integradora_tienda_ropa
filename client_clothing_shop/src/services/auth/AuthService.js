import axios from "../../config/http-client.gateway"

const loginService = async (payload) => {
    try {
        return await axios.doPost("/login", payload)
    } catch (e) {
        console.log(e)
    }
}

export default {
    loginService
}