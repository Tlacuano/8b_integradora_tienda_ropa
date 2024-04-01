package mx.edu.utez.services_clothing_shop.controller.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestOrderByIdOrderDTO {
    private UUID idOrder;
}
