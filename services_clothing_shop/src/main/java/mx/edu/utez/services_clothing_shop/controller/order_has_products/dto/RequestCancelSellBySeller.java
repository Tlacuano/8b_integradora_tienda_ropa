package mx.edu.utez.services_clothing_shop.controller.order_has_products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCancelSellBySeller {
    private String email;
    private UUID idOrderProduct;
    private String password;
}
