import axios from "../../config/http-client.gateway"

const getPageProductSalesRequests = async (pagination)=>{
    try{
        const { page, size } = pagination;
        const response = await axios.doGet(`/venta-ropa/api/requests-sell-product/get-page?size=${size}&page=${page - 1}`);
        console.log(response.data)
        return response.data
    }catch (e){
        console.log(e)
    }
}
const getByIdProductSalesRequest = async (id)=>{
    try{
        const response = await axios.doPost(`/venta-ropa/api/requests-sell-product/get-by-id-request-sell-product`, {id});
        console.log(response.data)
        return response.data
    }catch (e) {
        console.log(e)
    }
}
export default{
    getPageProductSalesRequests,
    getByIdProductSalesRequest
};