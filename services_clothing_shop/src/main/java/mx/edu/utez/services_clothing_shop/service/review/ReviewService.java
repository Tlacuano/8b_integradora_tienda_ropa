package mx.edu.utez.services_clothing_shop.service.review;

import mx.edu.utez.services_clothing_shop.controller.review.dto.RequestPostReviewDTO;
import mx.edu.utez.services_clothing_shop.controller.review.dto.ResponseAllReviewDTO;
import mx.edu.utez.services_clothing_shop.exception.ErrorDictionary;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.review.BeanReview;
import mx.edu.utez.services_clothing_shop.model.review.IReview;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final IReview iReview;
    private ErrorDictionary errorDictionary;
    public ReviewService(IReview iReview, ErrorDictionary errorDictionary) {
        this.iReview = iReview;
        this.errorDictionary = errorDictionary;
    }

    @Transactional(readOnly = true)
    public List<ResponseAllReviewDTO> getReviewsByOrderProductId(UUID idOrderProduct){
        List<Object[]> reviewsData = iReview.findEssentialReviewInfo(idOrderProduct);
        if (reviewsData.isEmpty()) {
            throw new CustomException(errorDictionary.getErrorMessage("review.idOrderHasProduct.notfound"));
        }
        return reviewsData
                .stream()
                .map(this::mapToResponseAllDTO)
                .collect(Collectors.toList());
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
    public ResponseEntity<BeanReview> putReview(BeanReview review){
        try {
            return ResponseEntity.status(200).body(iReview.save(review));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    public BeanReview postReview(RequestPostReviewDTO payload){
        BeanReview newReview = new BeanReview();
        newReview.setComment(payload.getComment());
        newReview.setReviewDate(LocalDate.now());
        newReview.setAssessment(payload.getAssessment());
        BeanOrderHasProducts orderHasProducts = new BeanOrderHasProducts();
        orderHasProducts.setIdOrderProduct(payload.getOrderHasProductId());
        newReview.setOrderHasProduct(orderHasProducts);
        return iReview.saveAndFlush(newReview);
    }

    public ResponseAllReviewDTO mapToResponseAllDTO(Object[] row){
        ResponseAllReviewDTO responseDTO = new ResponseAllReviewDTO();
        responseDTO.setComment((String) row[0]);
        responseDTO.setReviewDate((LocalDate) row[1]);
        responseDTO.setAssessment((int) row[2]);
        return responseDTO;
    }

}
