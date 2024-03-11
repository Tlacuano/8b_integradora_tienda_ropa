import axios from "axios";

const SERVER_URL = import.meta.env.VITE_API_URL;


const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 10000,
});

export default instance;