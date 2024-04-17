import { encrypt, decrypt } from "@/utils/security/aes";
import Vue from 'vue';
import axios from "axios";
import store from '../store/store'
import {showWarningToast} from "@/components/alerts/alerts";

const SERVER_URL = import.meta.env.VITE_API_URL;

const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 10000,
    //regresar
});

const baseURL = import.meta.env.VITE_API_URL;

instance.interceptors.request.use(
    (config) => {
        if(config.url.startsWith(baseURL)) {
            const auth_token = store.getters.getToken
            if (auth_token.length > 0) {
                config.headers["Authorization"] = `Bearer ${auth_token}`;
            }

            if(config.headers["Content-Type"] === "application/x-www-form-urlencoded"){
                const data = config.data;
                if (data) {
                    config.data = encrypt(data);
                }
            }

            return config;
        }
    }
);

instance.interceptors.response.use(
    (response) => {
        if(response.status >= 200 && response.status < 300) {


            if(response.config.url.startsWith(baseURL)) {
                const data = response.data;
    
                if (data) {
                    response.data = decrypt(data);
                }
                return response;
            }
        }else{
            return Promise.reject(response.data);
        }
    },
    (error) => {
        if (!error.response) {
            window.location.href = "/no-response";
        }

        if(error.response.status === 500){
            Vue.swal.fire({
                title: "Error en el servidor",
                text: "Por favor inténtelo de nuevo",
                icon: "error",
                showConfirmButton: false,
                allowOutsideClick: false,
                allowEscapeKey: false,
                timer: 3000,
            });
        }

        if(error.response.status === 401){
            store.dispatch("logout");
        }
        const {data, message} = decrypt(error.response.data);

        if(data) {
            if(!data.error){
                showWarningToast('',data);
            }else{
                showWarningToast('',data.error);
            }

        }else{
            showWarningToast('',message);
        }

        return Promise.reject(error.response);


    }
);

export default {
    async doPost(url, data) {
        return await instance.post(SERVER_URL+url, data,{
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            }
        });
    },

    async doGet(url) {
        return await instance.get(SERVER_URL+url);
    },

    async doPut(url, data) {
        return await instance.put(SERVER_URL+url, data);
    },

    async doPostImage(url, data){
        return await instance.post(SERVER_URL+url, data, {
            headers: {
                "Content-Type": "multipart/form-data",
            }
        });
    }

}