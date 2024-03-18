package mx.edu.utez.services_clothing_shop.service.requests_sell_product;

import mx.edu.utez.services_clothing_shop.controller.requests_sell_product.dto.RequestsSellProductDTO;
import mx.edu.utez.services_clothing_shop.exception.ErrorDictionary;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.request_sell_product.BeanRequestSellProduct;
import mx.edu.utez.services_clothing_shop.model.request_sell_product.IRequestsSellProduct;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;
import mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RequestsSellProductService {

    private final IRequestsSellProduct IRequestsSellProduct;
    private final IRequestStatus IRequestStatus;
    private final ErrorDictionary errorDictionary;


    public RequestsSellProductService(IRequestsSellProduct IRequestsSellProduct, IRequestStatus IRequestStatus, ErrorDictionary errorDictionary) {
        this.IRequestsSellProduct = IRequestsSellProduct;
        this.IRequestStatus = IRequestStatus;
        this.errorDictionary = errorDictionary;
    }

    @Transactional
    public RequestsSellProductDTO putRequestsSellProduct(UUID requestId, String status, String rejectionReason) {
        if (!isValidRejectionReason(rejectionReason)) {
            throw new IllegalArgumentException("Motivo de rechazo no válido: " + rejectionReason);
        }
        Optional<BeanRequestSellProduct> existingRequestOptional = IRequestsSellProduct.findById(requestId);
        if (existingRequestOptional.isPresent()) {
            BeanRequestSellProduct existingRequest = existingRequestOptional.get();
            BeanRequestStatus requestStatus = IRequestStatus.findByStatus(status)
                    .orElseThrow(() -> new IllegalArgumentException("Estado no válido: " + status));

            existingRequest.setStatus(requestStatus);
            existingRequest.setRejectionReason(rejectionReason);
            BeanRequestSellProduct updatedRequest = IRequestsSellProduct.save(existingRequest);

            return convertToDTO(updatedRequest);
        } else {
            throw new RequestsNotFoundException(errorDictionary.getErrorMessage("requestSellProduct.id.notnull"));
        }
    }

    private boolean isValidRejectionReason(String rejectionReason) {
        Pattern pattern = Pattern.compile(RegexPatterns.REJECTION_REASON_REGEX);
        Matcher matcher = pattern.matcher(rejectionReason);
        return matcher.matches();
    }

    public RequestsSellProductDTO getRequestById(UUID idRequestSellProduct){
        Optional<BeanRequestSellProduct> request = IRequestsSellProduct.findById(idRequestSellProduct);
        if(request.isPresent()){
            return convertToDTO(request.get());
        }else{
            throw new RequestsNotFoundException(errorDictionary.getErrorMessage("requestSellProduct.id.notnull"));
        }
    }

    public String getRequestStatusById(UUID statusID){
        BeanRequestStatus requestStatus = IRequestStatus.findById(statusID)
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado con ID: " + statusID));
        return requestStatus.getStatus();
    }

    @Transactional
    public RequestsSellProductDTO postRequestSellProduct(UUID productId){
        BeanProduct product = new BeanProduct();
        product.setIdProduct(productId);
        BeanRequestStatus requestStatus = IRequestStatus.findByStatus("Pendiente")
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado: Pendiente"));
        BeanRequestSellProduct requestSellProduct = new BeanRequestSellProduct();
        requestSellProduct.setProduct(product);
        requestSellProduct.setStatus(requestStatus);

        BeanRequestSellProduct newRequest = IRequestsSellProduct.save(requestSellProduct);
        return convertToDTO(newRequest);
    }

    @Transactional
    public Page<IRequestsSellProduct.RequestSellStatusProjection> findAllStatuses(Pageable pageable){
        return IRequestsSellProduct.findAllStatuses(pageable);
    }

    private RequestsSellProductDTO convertToDTO(BeanRequestSellProduct requestSellProduct){
        RequestsSellProductDTO requestSellProductDTO = new RequestsSellProductDTO();
        requestSellProductDTO.setIdRequestSellProduct(requestSellProduct.getIdRequestSellProduct());
        requestSellProductDTO.setRejectionReason(requestSellProduct.getRejectionReason());
        requestSellProductDTO.setIdProduct(requestSellProduct.getProduct().getIdProduct());
        requestSellProductDTO.setIdStatus(requestSellProduct.getStatus().getIdStatus());
        requestSellProductDTO.setStatus(requestSellProduct.getStatus().getStatus());
        return requestSellProductDTO;
    }

    public static class RequestsNotFoundException extends RuntimeException {
        public RequestsNotFoundException(String message) {
            super(message);
        }
    }
}