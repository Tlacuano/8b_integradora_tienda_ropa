package mx.edu.utez.services_clothing_shop.service.review;

import mx.edu.utez.services_clothing_shop.model.review.BeanReview;
import mx.edu.utez.services_clothing_shop.model.review.IReview;
import org.apache.coyote.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.util.List;

@Service
public class ReviewService {
    private final IReview iReview;
    public ReviewService(IReview iReview) {
        this.iReview = iReview;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<BeanReview>> getReviews(){
        try {
            List<BeanReview> reviews = iReview.findAll();
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BeanReview> getReview(BeanReview review){
        try {
            if(iReview.existsByIdReview(review.getIdReview())){
                return ResponseEntity.ok(iReview.findByIdReview(review.getIdReview()));
            }else {
                 return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanReview> postReview(BeanReview review){
        try {
            return ResponseEntity.status(200).body(iReview.save(review));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanReview> putReview(BeanReview review){
        try {
            if(iReview.existsById(review.getIdReview())){
                return ResponseEntity.status(200).body(iReview.save(review));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }


    }

}
