package mx.edu.utez.services_clothing_shop.utils.validations;

import java.time.LocalDate;
import java.time.Period;

public class ValidatesFunctions {
    public static boolean isAdult(LocalDate age) {
        LocalDate now = LocalDate.now();
        return Period.between(age, now).getYears() >= 18;
    }
}
