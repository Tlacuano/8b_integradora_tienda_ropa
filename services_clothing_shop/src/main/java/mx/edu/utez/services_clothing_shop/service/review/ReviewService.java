package mx.edu.utez.services_clothing_shop.service.review;

import mx.edu.utez.services_clothing_shop.controller.review.dto.RequestPostReviewDTO;
import mx.edu.utez.services_clothing_shop.controller.review.dto.RequestPutReviewDTO;
import mx.edu.utez.services_clothing_shop.controller.review.dto.ResponseAllReviewDTO;
import mx.edu.utez.services_clothing_shop.exception.ErrorDictionary;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.review.BeanReview;
import mx.edu.utez.services_clothing_shop.model.review.IReview;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    @Transactional
    public List<ResponseAllReviewDTO> getReviewsByOrderProductId(UUID idOrderProduct){
        List<Object[]> reviewsData = iReview.findEssentialReviewInfo(idOrderProduct);
        if (reviewsData.isEmpty()) {
            throw new CustomException("review.idOrderHasProduct.notfound");
        }
        return reviewsData
                .stream()
                .map(this::mapToResponseAllDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BeanReview putReview(RequestPutReviewDTO payload){
        Optional<BeanReview> optionalBeanReview = iReview.findById(payload.getIdReview());
        if(optionalBeanReview.isEmpty()){
            throw new CustomException(errorDictionary.getErrorMessage("review.idReview.notfound"));
        }

        BeanReview existingReview = optionalBeanReview.get();
        if(payload.getComment() != null){
            existingReview.setComment(payload.getComment());
        }

        Integer assessmentValue = payload.getAssessment();
        if(assessmentValue != null){
            existingReview.setAssessment(assessmentValue);
        }
        existingReview.setReviewDate(LocalDate.now());
        return iReview.saveAndFlush(existingReview);
    }

    @Transactional
    public BeanReview postReview(RequestPostReviewDTO payload){
        UUID orderHasProductId = payload.getOrderHasProductId();
        if (iReview.existsByOrderHasProduct_IdOrderProduct(orderHasProductId)) {
            throw new CustomException("review.orderHasProduct.exists");
        }
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

    public void deleteReview(UUID idReview){
        if(!iReview.existsByIdReview(idReview)){
            throw new CustomException("review.idReview.notfound");
        }
        iReview.deleteById(idReview);
    }

}
