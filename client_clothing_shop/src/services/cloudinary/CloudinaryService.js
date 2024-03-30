import axios from "../../config/http-client.gateway"
import {showWarningToast} from "@/components/alerts/alerts";

const uploadImage = async (file) => {
    try {
        const formData = new FormData();
        formData.append('file', file);

        return await axios.doPostImage("/venta-ropa/api/images/upload-image", formData);
    } catch (e) {
        showWarningToast( "Error al cargar la imagen")
        throw new Error("Error al cargar la imagen")
    }
}

export default {
    uploadImage
}