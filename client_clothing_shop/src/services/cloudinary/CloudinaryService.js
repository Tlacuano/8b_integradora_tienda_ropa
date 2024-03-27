import axios from "../../config/http-client.gateway"

const uploadImage = async (file) => {
    try {
        console.log(file);
        const formData = new FormData();
        formData.append('file', file);

        return await axios.doPostImage("/venta-ropa/api/images/upload-image", formData);
    } catch (e) {
        console.log(e.response.data);
        throw new Error("Error al cargar la imagen")
    }
}

export default {
    uploadImage
}