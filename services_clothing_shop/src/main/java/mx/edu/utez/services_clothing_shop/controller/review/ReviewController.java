package mx.edu.utez.services_clothing_shop.controller.review;

import mx.edu.utez.services_clothing_shop.model.review.BeanReview;
import mx.edu.utez.services_clothing_shop.service.review.ReviewService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/get-reviews")
    public ResponseEntity<List<BeanReview>> getReviews() {
        return  reviewService.getReviews();
    }

    @PostMapping("/get-review")
    public ResponseEntity<BeanReview> getReview(@RequestBody BeanReview review) {
        return reviewService.getReview(review);
    }

    @PostMapping("/post-review")
    public ResponseEntity<BeanReview> postReview(@RequestBody BeanReview review){
        return reviewService.postReview(review);
    }

    @PutMapping("/put-review")
    public ResponseEntity<BeanReview> putReview(@RequestBody BeanReview review){
        return reviewService.putReview(review);
    }

}
