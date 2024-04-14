import axios from "../../config/http-client.gateway"


const getSellerInformationByEmailService = async (email) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/sellers-information/get-seller-information-by-email`, { email });
        return response.data;
    } catch (e) {
        console.error("Error fetching seller information by email:", e.response.data);
        throw e;
    }
}


export default{
    getSellerInformationByEmailService
};
