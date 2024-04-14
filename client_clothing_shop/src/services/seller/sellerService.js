import axios from "../../config/http-client.gateway"


const getSellerInformationByEmailService = async (email) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/sellers-information/get-seller-information-by-email`, { email });
        console.log(response.data);
        return response.data;
    } catch (e) {
    }
}


export default{
    getSellerInformationByEmailService
};
