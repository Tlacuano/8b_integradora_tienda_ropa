import { showWarningToast } from "../../components/alerts/alerts";
import axios from "../../config/http-client.gateway"

const getWishList = async (email) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/wishes-list/get-wish-list", { email });
        return response.data;
    } catch (e) {
        showWarningToast('',e.message)
    }
}

const deleteWishList = async (wishList) => {
    try {
        const response = await axios.doPost("/venta-ropa/api/wishes-list/delete-wish-list", wishList);
        return response.data;
    } catch (e) {
        showWarningToast('',e.message)
    }
}

export default {
    getWishList,
    deleteWishList,
}