package mx.edu.utez.services_clothing_shop.controller.order.dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class UserEmailPageRequestDTO {
    private String email;
    private Pageable page;

    public UserEmailPageRequestDTO() {
    }

    public UserEmailPageRequestDTO(String email, Pageable page) {
        this.email = email;
        this.page = page;
    }
}
