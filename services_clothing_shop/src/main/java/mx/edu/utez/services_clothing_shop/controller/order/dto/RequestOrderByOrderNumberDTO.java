package mx.edu.utez.services_clothing_shop.controller.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestOrderByOrderNumberDTO {
    @NotBlank
    private String orderNumber;
}
