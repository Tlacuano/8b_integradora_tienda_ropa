package mx.edu.utez.services_clothing_shop.utils.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final String errorCode;

    public CustomException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

}
