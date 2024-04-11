import axios from "../../config/http-client.gateway"
import {showWarningToast} from "@/components/alerts/alerts";


const uploadImages = async (files) => {
    try {
        const formData = new FormData();
        files.forEach(file => {
            formData.append('files', file);
        });

        return await axios.doPostImage("/venta-ropa/api/images/upload-images", formData);
    } catch (e) {
        console.log(e.response.data);
        throw new Error("Error al cargar las imagenes")
    }
}
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
    uploadImage,
    uploadImages
}