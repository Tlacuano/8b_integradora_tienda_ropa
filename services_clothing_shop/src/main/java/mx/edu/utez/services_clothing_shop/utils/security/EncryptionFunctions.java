package mx.edu.utez.services_clothing_shop.utils.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Logger;


@Component
public class EncryptionFunctions {

    private static String key;
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    @Value("${secret-key}")
    public void setKey(String key) {
        EncryptionFunctions.key = key;
    }

    public static String encryptString(String value) {
        String dataEncrypt = "";
        try {
            Key key = generateKey();

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            byte[] initializationVector = new byte[16];

            new SecureRandom().nextBytes(initializationVector);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);

            byte[] encryptedByteValue  = cipher.doFinal(value.getBytes("utf-8"));
            byte[] encryptedValueWithIv = new byte[initializationVector.length + encryptedByteValue.length];

            System.arraycopy(initializationVector, 0, encryptedValueWithIv, 0, initializationVector.length);
            System.arraycopy(encryptedByteValue, 0, encryptedValueWithIv, initializationVector.length, encryptedByteValue.length);

            dataEncrypt = Base64.getEncoder().encodeToString(encryptedValueWithIv);
        } catch (Exception e) {
            Logger.getLogger(EncryptionFunctions.class.getName()).severe("Error al encriptar la cadena: " + e.getMessage());
        }

        return dataEncrypt;
    }

    public static String decryptString(String value) {
        String dataDecrypt = "";
        try {
            String urlDecode = URLDecoder.decode(value.replaceAll("\\+","%2B"), "UTF-8");
            Key key = generateKey();

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            byte[] decodeValue = Base64.getDecoder().decode(urlDecode);
            byte[] initializationVector = new byte[16];

            System.arraycopy(decodeValue, 0, initializationVector, 0, initializationVector.length);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            byte[] decryptedByteValue = cipher.doFinal(decodeValue, initializationVector.length, decodeValue.length - initializationVector.length);

            dataDecrypt = new String(decryptedByteValue, "utf-8");
        } catch (Exception e) {
            Logger.getLogger(EncryptionFunctions.class.getName()).severe("Error al desencriptar la cadena: " + e.getMessage());
        }
        return dataDecrypt;
    }

    private static Key generateKey() {
        return new SecretKeySpec(key.getBytes(), ALGORITHM);
    }
}
