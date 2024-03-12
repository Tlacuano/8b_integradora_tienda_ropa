package mx.edu.utez.services_clothing_shop.utils.validations;

public class RegexPatterns {
    public static final String CURP_REGEX = "[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9]{2}";
    public static final String NAME_REGEX = "^([A-Za-zÁÉÍÓÚÑáéíóúñ]+)(\\s([A-Za-zÁÉÍÓÚÑáéíóúñ]+|[A-Za-zÁÉÍÓÚÑáéíóúñ][a-záéíóúñ]*))*$";
    public static final String PHONE_REGEX = "^\\d{10}$";

}