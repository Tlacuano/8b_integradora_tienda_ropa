import axios from "../../config/http-client.gateway";

const getProductByUser = async (pagination,email) => {
    try {
        const {page,size}=pagination
        const response = await axios.doPost(`/venta-ropa/api/products/get-products-by-user?size=${size}&page=${page - 1}`,{email:email})
        console.log(response.data)
        return response.data
    } catch (e) {
        console.log(e)
    }
}
export default {
    getProductByUser
}