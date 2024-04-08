package mx.edu.utez.services_clothing_shop.controller.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestComprobationToReviewDTO {
    @NotNull
    private UUID idProduct;
    @NotNull
    private String email;
}
