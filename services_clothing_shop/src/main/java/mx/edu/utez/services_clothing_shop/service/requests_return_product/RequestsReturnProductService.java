package mx.edu.utez.services_clothing_shop.service.requests_return_product;

import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto.RequestsReturnProductDTO;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.order_has_products.IOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.request_return_product.BeanRequestReturnProduct;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import mx.edu.utez.services_clothing_shop.model.request_return_product.IRequestsReturnProduct;

import java.util.Optional;
import java.util.UUID;

@Service
public class RequestsReturnProductService {

    private final IRequestsReturnProduct IRequestsReturnProduct;
    private final IRequestStatus IRequestStatus;

    public RequestsReturnProductService(IRequestsReturnProduct IRequestsReturnProduct, IRequestStatus IRequestStatus) {
        this.IRequestsReturnProduct = IRequestsReturnProduct;
        this.IRequestStatus = IRequestStatus;
    }

    @Transactional
    public RequestsReturnProductDTO putRequestStatus(UUID requestId, String status, String rejectionReason) {
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
            throw new RequestsNotFoundException("La solicitud no fue encontrada.");
        }
    }

    public Optional<RequestsReturnProductDTO> getRequestByIdOrderProduct(UUID idOrder) {
        Optional<BeanRequestReturnProduct> requestOptional = IRequestsReturnProduct.findByOrderHasProduct_IdOrderProduct(idOrder);
        return requestOptional.map(this::convertToDTO);
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





    public Page<IRequestsReturnProduct.ReturnStatusProjection> findAllStatuses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
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
