package mx.edu.utez.services_clothing_shop.controller.user.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;

import java.util.UUID;

@Data
public class ResponseShoppingCartUserDTO {
    private UUID idUser;
    private String email;
    private String name;
    private String lastName;
    private String secondLastName;

    public ResponseShoppingCartUserDTO(){

    }

    public static ResponseShoppingCartUserDTO fromUser(BeanUser user){
        ResponseShoppingCartUserDTO userDTO = new ResponseShoppingCartUserDTO();
        userDTO.setIdUser(user.getIdUser());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getPerson().getName());
        userDTO.setLastName(user.getPerson().getLastName());
        userDTO.setSecondLastName(user.getPerson().getSecondLastName());
        return userDTO;
    }
}
