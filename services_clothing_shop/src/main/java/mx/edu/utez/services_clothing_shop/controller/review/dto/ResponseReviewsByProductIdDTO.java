package mx.edu.utez.services_clothing_shop.controller.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.review.BeanReview;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseReviewsByProductIdDTO {
    private UUID idReview;
    private String comment;
    private String reviewDate;
    private Integer assessment;
    private String fullName;
    private String picture;
    private String email;

    public static ResponseReviewsByProductIdDTO fromReview(BeanReview reviews){
        ResponseReviewsByProductIdDTO responseReviewsByProductIdDTO = new ResponseReviewsByProductIdDTO();

        responseReviewsByProductIdDTO.setIdReview(reviews.getIdReview());
        responseReviewsByProductIdDTO.setComment(reviews.getComment());
        responseReviewsByProductIdDTO.setReviewDate(reviews.getReviewDate().toString());
        responseReviewsByProductIdDTO.setAssessment(reviews.getAssessment());
        responseReviewsByProductIdDTO.setFullName(reviews.getOrderHasProduct().getOrder().getAddress().getPerson().getName()
                + " " + reviews.getOrderHasProduct().getOrder().getAddress().getPerson().getLastName()
                + " " + reviews.getOrderHasProduct().getOrder().getAddress().getPerson().getSecondLastName());
        responseReviewsByProductIdDTO.setPicture(reviews.getOrderHasProduct().getOrder().getAddress().getPerson().getPicture());
        responseReviewsByProductIdDTO.setEmail(reviews.getOrderHasProduct().getOrder().getAddress().getPerson().getUser().getEmail());

        return responseReviewsByProductIdDTO;
    }
}
