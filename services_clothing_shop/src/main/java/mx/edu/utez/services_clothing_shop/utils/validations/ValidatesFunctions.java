package mx.edu.utez.services_clothing_shop.utils.validations;

import java.time.LocalDate;
import java.time.Period;

public class ValidatesFunctions {
    public static boolean isAdult(LocalDate age) {
        LocalDate now = LocalDate.now();
        return Period.between(age, now).getYears() >= 18;
    }

    private static String mapRoleName(String roleName) {
        switch (roleName) {
            case "ROLE_ADMIN":
                return "administrador";
            case "ROLE_SELLER":
                return "vendedor";
            case "ROLE_BUYER":
                return "comprador";
            default:
                return roleName;
        }
    }

}
