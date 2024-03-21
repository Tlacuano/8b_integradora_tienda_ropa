package mx.edu.utez.services_clothing_shop.controller.review.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RequestPostReviewDTO {
    @NotBlank(message = "review.comment.notnull")
    @Size(min = 5, max = 255, message = "review.comment.size")
    private String comment;

    @Min(value = 1, message = "review.assessment.min")
    @Max(value = 5, message = "review.assessment.max")
    private int assessment;

    @NotNull(message = "review.orderHasProductId.notnull")
    private UUID orderHasProductId;
}
