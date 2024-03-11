package mx.edu.utez.services_clothing_shop.utils;

public class RegexPatterns {
    public static final String CURP_REGEX = "[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9]{2}";
    public static final String NAME_REGEX = "^([A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)(\\s([a-záéíóúñ]+|[A-ZÁÉÍÓÚÑ][a-záéíóúñ]*))*$";
    public static final String PHONE_REGEX = "^\\d{10}$";

}
