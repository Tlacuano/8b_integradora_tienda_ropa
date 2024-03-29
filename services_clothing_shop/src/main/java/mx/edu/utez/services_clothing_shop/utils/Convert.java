package mx.edu.utez.services_clothing_shop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;

import java.util.logging.Logger;

public class Convert {
    public static String toJSON(Object object)  {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            Logger.getLogger(EncryptionFunctions.class.getName())
                    .severe("Error al convertir en JSON: " + e.getMessage());
        }
        return null;
    }
}
