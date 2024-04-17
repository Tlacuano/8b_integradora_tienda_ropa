package mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckPendingRequestDTO {
    private UUID idOrderProduct;

}
