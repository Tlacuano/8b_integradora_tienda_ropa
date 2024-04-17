package mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestReturnProductByOrderProductDTO {
    private UUID idOrderProduct;
}
