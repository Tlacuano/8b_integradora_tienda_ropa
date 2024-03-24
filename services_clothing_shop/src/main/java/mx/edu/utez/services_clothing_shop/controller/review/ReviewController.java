package mx.edu.utez.services_clothing_shop.controller.review;

import mx.edu.utez.services_clothing_shop.controller.review.dto.RequestActionByIdOrderHasProductDTO;
import mx.edu.utez.services_clothing_shop.controller.review.dto.RequestPostReviewDTO;
import mx.edu.utez.services_clothing_shop.controller.review.dto.RequestPutReviewDTO;
import mx.edu.utez.services_clothing_shop.controller.review.dto.ResponseAllReviewDTO;
import mx.edu.utez.services_clothing_shop.model.review.BeanReview;
import mx.edu.utez.services_clothing_shop.service.review.ReviewService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venta-ropa/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/get-reviews-by-order-product-id")
    public ResponseEntity<Object> getReviewsByIdOrderProduct(@Validated @RequestBody RequestActionByIdOrderHasProductDTO payload) {
            List<ResponseAllReviewDTO> responseDTOs = reviewService.getReviewsByOrderProductId(payload.getIdOrderHasProduct());
            return new ResponseEntity<>(
                    new CustomResponse<>(responseDTOs, "Reviews retrieved successfully", false, 200),
                    HttpStatus.OK
            );
    }

    @PostMapping("/post-review")
    public ResponseEntity<Object> postReview(@Validated @RequestBody RequestPostReviewDTO payload) {
            BeanReview newReview = reviewService.postReview(payload);
            return new ResponseEntity<>(
                    new CustomResponse<>(newReview, "Review created successfully", false, 201),
                    HttpStatus.CREATED
            );
    }

    @PutMapping("/put-review")
    public ResponseEntity<Object> putReview(@Validated @RequestBody RequestPutReviewDTO payload){
            BeanReview updatedReview = reviewService.putReview(payload);
            return new ResponseEntity<>(
                    new CustomResponse<>(updatedReview, "Review updated successfully", false, 200),
                    HttpStatus.OK
            );
    }

    @DeleteMapping("/delete-review")
    public ResponseEntity<Object> deleteReview(@Validated @RequestBody RequestActionByIdOrderHasProductDTO payload){
        try {
            reviewService.deleteReview(payload.getIdOrderHasProduct());
            return ResponseEntity.ok(new CustomResponse<>(null, "Review deleted successfully", false, HttpStatus.OK.value()));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(null, "Error deleting review: " + e.getMessage(), true, HttpStatus.BAD_REQUEST.value()));
        }
    }

}
