package mx.edu.utez.services_clothing_shop.controller.user.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;

import java.util.UUID;

@Data
public class RequestUserByIdDTO {
    private UUID idUser;

    public RequestUserByIdDTO() {
    }

    public static RequestUserByIdDTO fromUser (BeanUser user){
        RequestUserByIdDTO userDTO = new RequestUserByIdDTO();
        userDTO.setIdUser(user.getIdUser());
        return userDTO;
    }
}
