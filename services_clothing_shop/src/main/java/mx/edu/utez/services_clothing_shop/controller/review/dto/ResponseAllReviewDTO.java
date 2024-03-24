package mx.edu.utez.services_clothing_shop.controller.review.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseAllReviewDTO {
    private String comment;
    private LocalDate reviewDate;
    private int assessment;

}
