package mx.edu.utez.services_clothing_shop.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse <T>{
    private T data;
    private String message;
    private boolean error;
    private int status;
}
