package mx.edu.utez.services_clothing_shop.utils.security;

import java.security.SecureRandom;

public class SecurityCode {

    public static String generateCode() {
        return String.valueOf(new SecureRandom().nextInt(999999 - 100000) + 100000);
    }
}
