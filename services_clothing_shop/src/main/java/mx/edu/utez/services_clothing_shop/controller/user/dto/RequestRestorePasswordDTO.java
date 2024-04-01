package mx.edu.utez.services_clothing_shop.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestRestorePasswordDTO {
    private String email;
    private String password;
}
