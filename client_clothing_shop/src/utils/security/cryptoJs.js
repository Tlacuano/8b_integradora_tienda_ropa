import CryptoJS from "crypto-js";

const key = import.meta.env.VITE_AES_KEY;

export function codeCrypto(data) {
    const code =  CryptoJS.AES.encrypt(data, key).toString();
    return code.toString().replaceAll('/', '-');
}

export function decodeCrypto(data) {
    data = data.replaceAll('-', '/');
    return CryptoJS.AES.decrypt(data, key).toString(CryptoJS.enc.Utf8);
}