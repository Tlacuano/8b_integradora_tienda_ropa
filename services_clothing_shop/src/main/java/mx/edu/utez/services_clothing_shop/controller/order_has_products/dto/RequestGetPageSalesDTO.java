package mx.edu.utez.services_clothing_shop.controller.order_has_products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestGetPageSalesDTO {
    private String email;
    private String orderNumber;
    private String status;
}
