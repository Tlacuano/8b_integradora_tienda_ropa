package mx.edu.utez.services_clothing_shop.utils.validations;

public class RegexPatterns {
    public static final String CURP_REGEX = "[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9]{2}";
    public static final String NAME_REGEX = "^([A-Za-zÁÉÍÓÚÑáéíóúñ]+)(\\s([A-Za-zÁÉÍÓÚÑáéíóúñ]+|[A-Za-zÁÉÍÓÚÑáéíóúñ][a-záéíóúñ]*))*$";
    public static final String PHONE_REGEX = "^\\d{10}$";

    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public static final String REJECTION_REASON_REGEX = "^[a-zA-Z0-9\\s¡áéíóúü.,;:¡¿?!()-]*$";
}
