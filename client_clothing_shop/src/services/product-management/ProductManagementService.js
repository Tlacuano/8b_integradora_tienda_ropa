import axios from "../../config/http-client.gateway";
import {showWarningToast} from "@/components/alerts/alerts";
const error = 'Ocurrió un error inesperado, por favor inténtelo más tarde';
const getProductByUser = async (pagination,email) => {
    try {
        const {page,size}=pagination
        const response = await axios.doPost(`/venta-ropa/api/products/get-products-by-user?size=${size}&page=${page - 1}`,{email:email})
        return response.data
    } catch (e) {
        showWarningToast('', error)
    }
}

const getProduct = async (payload) =>{
    try{
        const response = await axios.doPost(`/venta-ropa/api/products/get-product?`,payload)
        return response.data
    }catch(e){
    }
}
const putStatusProduct = async (productId)=>{
    try {
        const response = await axios.doPut(`/venta-ropa/api/products/put-status-product?`,{idProduct:productId})
        return response.data
    }catch (e){
        showWarningToast('', error)
    }
}
export default {
    getProductByUser,
    putStatusProduct,
    getProduct
}