package mx.edu.utez.services_clothing_shop.controller.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RequestDeleteReview {
    @NotNull(message = "review.idReview.notnull")
    private UUID idReview;
}
