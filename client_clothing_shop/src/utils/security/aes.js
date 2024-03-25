import forge from "node-forge";
import {showInfoAlert} from "@/components/alerts/alerts";


const key = import.meta.env.VITE_AES_KEY;

export const encrypt = (data) => {
    const jsonString = JSON.stringify(data);

    const cipher = forge.cipher.createCipher("AES-CBC", key);

    const iv = forge.random.getBytesSync(16);
    cipher.start({ iv });

    cipher.update(forge.util.createBuffer(jsonString, "utf8"));
    cipher.finish();

    const encryptedData = forge.util.encode64(iv + cipher.output.getBytes());
    const encodedDataPost = encodeURIComponent(encryptedData);
    
    return encodedDataPost;
};

export const decrypt = (data) => {
    const decodedEncryptedData = forge.util.decode64(data);

    const iv = decodedEncryptedData.slice(0, 16);
    const encryptedBytes = decodedEncryptedData.slice(16);

    const encryptedBuffer = forge.util.createBuffer(encryptedBytes, "raw");

    const decipher = forge.cipher.createDecipher("AES-CBC", key);

    decipher.start({ iv: iv });

    decipher.update(encryptedBuffer);

    decipher.finish();

    const decryptedString = decipher.output.toString("utf8");

    return JSON.parse(decryptedString);

}