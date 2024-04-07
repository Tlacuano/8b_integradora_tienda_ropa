package mx.edu.utez.services_clothing_shop.controller.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTransactionDTO {
    private double total;
    private String description;
    private String email;
}
