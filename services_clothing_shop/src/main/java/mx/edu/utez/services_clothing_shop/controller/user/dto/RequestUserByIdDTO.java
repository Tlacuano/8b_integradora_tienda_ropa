package mx.edu.utez.services_clothing_shop.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserByIdDTO {
    private UUID idUser;

    public static RequestUserByIdDTO fromUser(BeanUser user) {
        RequestUserByIdDTO userDTO = new RequestUserByIdDTO();
        userDTO.setIdUser(user.getIdUser());
        return userDTO;
    }
}
