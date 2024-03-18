package mx.edu.utez.services_clothing_shop.controller.review;

import mx.edu.utez.services_clothing_shop.controller.review.dto.RequestActionByIdOrderHasProductDTO;
import mx.edu.utez.services_clothing_shop.controller.review.dto.RequestPostReviewDTO;
import mx.edu.utez.services_clothing_shop.controller.review.dto.ResponseAllReviewDTO;
import mx.edu.utez.services_clothing_shop.model.review.BeanReview;
import mx.edu.utez.services_clothing_shop.service.review.ReviewService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.context.annotation.Bean;
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
        try {
            List<ResponseAllReviewDTO> responseDTOs = reviewService.getReviewsByOrderProductId(payload.getIdOrderHasProduct());
            return ResponseEntity.ok(new CustomResponse<>(responseDTOs, "Reviews retrieved successfully", false, HttpStatus.OK.value()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(null, e.getMessage(), true, HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/get-review")
    public ResponseEntity<BeanReview> getReview(@RequestBody BeanReview review) {
        return reviewService.getReview(review);
    }

    @PostMapping("/post-review")
    public ResponseEntity<Object> postReview(@Validated @RequestBody RequestPostReviewDTO payload) {
        try {
            BeanReview newReview = reviewService.postReview(payload);
            return ResponseEntity.ok(new CustomResponse<>(newReview, "Review created successfully", false, HttpStatus.OK.value()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(null, "Error creating review: " + e.getMessage(), true, HttpStatus.BAD_REQUEST.value()));
        }

    }

    @PutMapping("/put-review")
    public ResponseEntity<BeanReview> putReview(@RequestBody BeanReview review){
        return reviewService.putReview(review);
    }

}
