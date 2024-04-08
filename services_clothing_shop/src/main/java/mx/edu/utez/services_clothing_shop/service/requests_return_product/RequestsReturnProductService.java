package mx.edu.utez.services_clothing_shop.service.requests_return_product;

import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto.RequestsReturnProductDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto.RequestsReturnProductGetByIdResponseDTO;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.request_return_product.BeanRequestReturnProduct;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;
import mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import mx.edu.utez.services_clothing_shop.model.request_return_product.IRequestsReturnProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mx.edu.utez.services_clothing_shop.model.request_return_product.IRequestsReturnProduct.ReturnRequestProjection;

@Service
public class RequestsReturnProductService {

    private final IRequestsReturnProduct IRequestsReturnProduct;
    private final IRequestStatus IRequestStatus;

    public RequestsReturnProductService(IRequestsReturnProduct IRequestsReturnProduct, IRequestStatus IRequestStatus) {
        this.IRequestsReturnProduct = IRequestsReturnProduct;
        this.IRequestStatus = IRequestStatus;
    }

    @Transactional
    public RequestsReturnProductDTO putRequestReturnProduct(UUID requestId, String status, String rejectionReason) {
        if (!isValidRejectionReason(rejectionReason)) {
            throw new CustomException("requestsReturnProduct.rejectionReason.invalid");
        }
        Optional<BeanRequestReturnProduct> existingRequestOptional = IRequestsReturnProduct.findById(requestId);
        if (existingRequestOptional.isPresent()) {
            BeanRequestReturnProduct existingRequest = existingRequestOptional.get();
            BeanRequestStatus requestStatus = IRequestStatus.findByStatus(status)
                    .orElseThrow(() -> new CustomException("requestsReturnProduct.status.invalid"));

            existingRequest.setStatus(requestStatus);
            existingRequest.setRejectionReason(rejectionReason);
            BeanRequestReturnProduct updatedRequest = IRequestsReturnProduct.save(existingRequest);

            return convertToDTO(updatedRequest);
        } else {
            throw new CustomException("requestReturnProduct.id.notnull");
        }
    }

    private boolean isValidRejectionReason(String rejectionReason) {
        Pattern pattern = Pattern.compile(RegexPatterns.REJECTION_REASON_REGEX);
        Matcher matcher = pattern.matcher(rejectionReason);
        return matcher.matches();
    }

    public RequestsReturnProductGetByIdResponseDTO getRequestReturnProductInfoById(UUID idRequestReturnProduct) {
        List<IRequestsReturnProduct.ReturnProductInfoProjection> results = IRequestsReturnProduct.findReturnProductInfoById(idRequestReturnProduct);
        if (results.isEmpty()) {
            throw new CustomException("requestReturnProduct.id.notFound");
        }
        return convertToReturnProductInfoDTO(results.get(0));
    }

    private RequestsReturnProductGetByIdResponseDTO convertToReturnProductInfoDTO(IRequestsReturnProduct.ReturnProductInfoProjection request) {
        RequestsReturnProductGetByIdResponseDTO dto = new RequestsReturnProductGetByIdResponseDTO();
        dto.setIdRequestReturnProduct(request.getIdRequestReturnProduct());
        dto.setReturnReason(request.getReturnReason());
        dto.setIdOrderProduct(request.getIdOrderProduct());
        dto.setIdOrder(request.getIdOrder());
        dto.setOrderNumber(request.getOrderNumber());
        dto.setAmount(request.getAmount());
        dto.setEmail(request.getEmail());
        dto.setImage(request.getImage());
        dto.setPrice(request.getPrice());
        dto.setProductName(request.getProductName());
        return dto;
    }

    public String getRequestStatusById(UUID statusId) {
        BeanRequestStatus requestStatus = IRequestStatus.findById(statusId)
                .orElseThrow(() -> new CustomException("requestsReturnProduct.status.notFound"));
        return requestStatus.getStatus();
    }

    @Transactional
    public RequestsReturnProductDTO postRequestReturnProduct(String orderNumber) {
        BeanOrderHasProducts orderHasProduct = IRequestsReturnProduct.findFirstByOrderNumber(orderNumber)
                .orElseThrow(() -> new CustomException("OrderHasProducts not found"));

        BeanRequestStatus requestStatus = IRequestStatus.findByStatus("Pendiente")
                .orElseThrow(() -> new CustomException("Request status not found"));

        BeanRequestReturnProduct request = new BeanRequestReturnProduct();
        request.setOrderHasProduct(orderHasProduct);
        request.setStatus(requestStatus);

        BeanRequestReturnProduct savedRequest = IRequestsReturnProduct.save(request);
        return convertToDTO(savedRequest);
    }



    public Page<ReturnRequestProjection> findRequestsByPageAndSearchTerm(Pageable pageable, String searchTerm) {
        return IRequestsReturnProduct.findRequestsWithOrderInfo(pageable, searchTerm);
    }


    private RequestsReturnProductDTO convertToDTO(BeanRequestReturnProduct request) {
        RequestsReturnProductDTO dto = new RequestsReturnProductDTO();
        dto.setIdRequestReturnProduct(request.getIdRequestReturnProduct());
        dto.setOrderHasProductId(request.getOrderHasProduct().getIdOrderProduct());
        dto.setStatusId(request.getStatus().getIdStatus());
        dto.setRejectionReason(request.getRejectionReason());
        return dto;
    }
}
