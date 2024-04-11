import axios from "../../config/http-client.gateway"

const uploadImages = async (files) => {
    try {
        const formData = new FormData();
        files.forEach(file => {
            formData.append('files', file);
        });

        return await axios.doPostImage("/venta-ropa/api/images/upload-images", formData);
    } catch (e) {
    }
}
const uploadImage = async (file) => {
    try {
        const formData = new FormData();
        formData.append('file', file);

        return await axios.doPostImage("/venta-ropa/api/images/upload-image", formData);
    } catch (e) {
    }
}

export default {
    uploadImage,
    uploadImages
}