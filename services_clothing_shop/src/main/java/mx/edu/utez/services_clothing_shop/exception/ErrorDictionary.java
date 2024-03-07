package mx.edu.utez.services_clothing_shop.exception;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ErrorDictionary {

    private static final Map<String, String> errorMessages = new HashMap<>();

    static {
        //error messages user
        errorMessages.put("user.email.notnull", "El email es obligatorio.");
        errorMessages.put("user.password.notnull", "La contraseña es obligatoria.");
        errorMessages.put("user.email.exists", "El email no esta disponible.");

    }

    public String getErrorMessage(String errorCode) {
        return errorMessages.getOrDefault(errorCode, "Error desconocido.");
    }
}
