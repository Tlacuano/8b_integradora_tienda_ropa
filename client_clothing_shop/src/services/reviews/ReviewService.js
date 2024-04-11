import axios from "../../config/http-client.gateway"


const getReviewsByProductIdService = async (payload) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/reviews/get-reviews-by-product-id`, payload);
        return response.data
    }catch (e){
    }
}

const getComprobatToReviewService = async (payload) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/reviews/get-comprobant-to-review`, payload);
        return response.data
    }catch (e){
    }
}

const postReviewService = async (payload) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/reviews/post-review`, payload);
        return response.data
    } catch (e) {
    }
}

const deleteReviewService = async (payload) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/reviews/delete-review`, payload);
        return response.data
    }catch (e){

    }
}

const putReviewService = async (payload) => {
    try {
        const response = await axios.doPost(`/venta-ropa/api/reviews/put-review`, payload);
        return response.data
    }catch (e){

    }
}


export default {
    getReviewsByProductIdService,
    getComprobatToReviewService,
    postReviewService,
    deleteReviewService,
    putReviewService
}