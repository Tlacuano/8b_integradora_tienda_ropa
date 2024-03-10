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
        errorMessages.put("user.email.invalid", "El email no es válido.");
        errorMessages.put("user.password.notnull", "La contraseña es obligatoria.");
        //error messages payment card
        errorMessages.put("payment.cardholderName.notnull", "El nombre del titular de la tarjeta es obligatorio.");
        errorMessages.put("payment.cardNumber.notnull", "El número de la tarjeta es obligatorio.");
        errorMessages.put("payment.cardNumber.size", "El número de la tarjeta debe tener 16 dígitos.");
        errorMessages.put("payment.cardNumber.invalid", "El número de la tarjeta no es válido.");
        errorMessages.put("payment.expirationDate.notnull", "La fecha de expiración es obligatoria.");
        errorMessages.put("payment.expirationDate.invalid", "La fecha de expiración no es válida.");
        errorMessages.put("payment.cvv.notnull", "El código de seguridad es obligatorio.");
        errorMessages.put("payment.cvv.invalid", "El código de seguridad no es válido.");
        errorMessages.put("payment.cvv.size", "El código de seguridad debe tener 3 o 4 dígitos.");
        errorMessages.put("payment.minimum.card", "El usuario debe tener registrada al menos una tarjeta de crédito.");
        errorMessages.put("payment.card.notFound", "La tarjeta de crédito no fue encontrada.");
        errorMessages.put("payment.card.registered", "La tarjeta de crédito ya está registrada.");
        //error messages order
        errorMessages.put("order.orderDate.notnull", "La fecha de la orden es obligatoria.");
    }

    public String getErrorMessage(String errorCode) {
        return errorMessages.getOrDefault(errorCode, "Error desconocido.");
    }
}
