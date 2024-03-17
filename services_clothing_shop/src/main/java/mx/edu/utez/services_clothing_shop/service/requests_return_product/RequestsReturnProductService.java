package mx.edu.utez.services_clothing_shop.service.requests_return_product;

import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto.RequestsReturnProductDTO;
import mx.edu.utez.services_clothing_shop.exception.ErrorDictionary;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.request_return_product.BeanRequestReturnProduct;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;
import mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import mx.edu.utez.services_clothing_shop.model.request_return_product.IRequestsReturnProduct;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RequestsReturnProductService {

    private final IRequestsReturnProduct IRequestsReturnProduct;
    private final IRequestStatus IRequestStatus;
    private final ErrorDictionary errorDictionary;

    public RequestsReturnProductService(IRequestsReturnProduct IRequestsReturnProduct, IRequestStatus IRequestStatus, ErrorDictionary errorDictionary) {
        this.IRequestsReturnProduct = IRequestsReturnProduct;
        this.IRequestStatus = IRequestStatus;
        this.errorDictionary = errorDictionary;
    }

    @Transactional
    public RequestsReturnProductDTO putRequestStatus(UUID requestId, String status, String rejectionReason) {
        if (!isValidRejectionReason(rejectionReason)) {
            throw new IllegalArgumentException("Motivo de rechazo no válido: " + rejectionReason);
        }
        Optional<BeanRequestReturnProduct> existingRequestOptional = IRequestsReturnProduct.findById(requestId);
        if (existingRequestOptional.isPresent()) {
            BeanRequestReturnProduct existingRequest = existingRequestOptional.get();
            BeanRequestStatus requestStatus = IRequestStatus.findByStatus(status)
                    .orElseThrow(() -> new IllegalArgumentException("Estado no válido: " + status));

            existingRequest.setStatus(requestStatus);
            existingRequest.setRejectionReason(rejectionReason);
            BeanRequestReturnProduct updatedRequest = IRequestsReturnProduct.save(existingRequest);

            return convertToDTO(updatedRequest);
        } else {
            throw new RequestsNotFoundException(errorDictionary.getErrorMessage("requestReturnProduct.id.notnull"));
        }
    }

    private boolean isValidRejectionReason(String rejectionReason) {
        Pattern pattern = Pattern.compile(RegexPatterns.REJECTION_REASON_REGEX);
        Matcher matcher = pattern.matcher(rejectionReason);
        return matcher.matches();
    }

    public RequestsReturnProductDTO getRequestsById(UUID idRequestReturnProduct) {
        Optional<BeanRequestReturnProduct> request = IRequestsReturnProduct.findById(idRequestReturnProduct);
        if (request.isPresent()) {
            return convertToDTO(request.get());
        } else {
            throw new RequestsNotFoundException(errorDictionary.getErrorMessage("requestReturnProduct.id.notnull"));
        }
    }

    public String getRequestStatusById(UUID statusId) {
        BeanRequestStatus requestStatus = IRequestStatus.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado con ID: " + statusId));
        return requestStatus.getStatus();
    }


    @Transactional
    public RequestsReturnProductDTO postRequest(UUID orderHasProductId) {
        BeanOrderHasProducts orderHasProduct = new BeanOrderHasProducts();
        orderHasProduct.setIdOrderProduct(orderHasProductId);

        BeanRequestStatus requestStatus = IRequestStatus.findByStatus("Pendiente")
                .orElseThrow(() -> new IllegalArgumentException("Estado no válido: Pendiente"));

        BeanRequestReturnProduct request = new BeanRequestReturnProduct();
        request.setOrderHasProduct(orderHasProduct);
        request.setStatus(requestStatus);

        BeanRequestReturnProduct savedRequest = IRequestsReturnProduct.save(request);
        return convertToDTO(savedRequest);
    }


    @Transactional
    public Page<IRequestsReturnProduct.ReturnStatusProjection> findAllStatuses(Pageable pageable) {
        return IRequestsReturnProduct.findAllStatuses(pageable);
    }


    private RequestsReturnProductDTO convertToDTO(BeanRequestReturnProduct request) {
        RequestsReturnProductDTO dto = new RequestsReturnProductDTO();
        dto.setIdRequestReturnProduct(request.getIdRequestReturnProduct());
        dto.setOrderHasProductId(request.getOrderHasProduct().getIdOrderProduct());
        dto.setStatusId(request.getStatus().getIdStatus());
        dto.setRejectionReason(request.getRejectionReason());
        return dto;
    }

    public static class RequestsNotFoundException extends RuntimeException {
        public RequestsNotFoundException(String message) {
            super(message);
        }
    }
}
