import { encrypt, decrypt } from "../utils/security/aes";
import Vue from 'vue';
import axios from "axios";

const SERVER_URL = import.meta.env.VITE_API_URL;

const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 10000,
});

const baseURL = import.meta.env.VITE_API_URL;

instance.interceptors.request.use(
    (config) => {
        if(config.url.startsWith(baseURL)) {
            const auth_token = localStorage.getItem("token");
            if (auth_token) {
                config.headers["Authorization"] = `Bearer ${auth_token}`;
            }
            /*
            const data = config.data;
            config.headers = {
                "Content-Type": "application/x-www-form-urlencoded",
            };
            if (data) {
                config.data = encrypt(data);
            }*/

            return config;
        }
    }
);

instance.interceptors.response.use(
    (response) => {
        if(response.status >= 200 && response.status < 300) {
            if(response.config.url.startsWith(baseURL)) {

                /*
                const data = response.data;
    
                if (data) {
                    response.data = decrypt(data);
                }
                */
                return response;
            }
        }else{
            return Promise.reject(response);
        }
    },
    (error) => {
        if (!error.response) {
            Vue.swal.fire({
                title: "El servidor no responde",
                text: "Sin respuesta del servidor, por favor inténtelo de nuevo",
                icon: "error",
                showConfirmButton: false,
                allowOutsideClick: false,
                allowEscapeKey: false,
                timer: 3000,
            });
            return Promise.reject(error);
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
        }else{
            return Promise.reject(error);
        }

    }
);




export default {
    async doPost(url, data) {
        return await instance.post(SERVER_URL+url, data);
    },
    async doGet(url) {
        return await instance.get(SERVER_URL+url);
    },
    async doPut(url) {
        return await instance.put(SERVER_URL+url);
    },
}