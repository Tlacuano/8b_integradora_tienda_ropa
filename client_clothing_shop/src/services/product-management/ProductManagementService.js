import axios from "../../config/http-client.gateway";
const getProductByUser = async (pagination,email) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/products/get-products-by-user?`,
            {
                email: email,
                pagination
            });
        return response.data
    } catch (e) {
        console.log(e)
    }
}
export default {
    getProductByUser
}