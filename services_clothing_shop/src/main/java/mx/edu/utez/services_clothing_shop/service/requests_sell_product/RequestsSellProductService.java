package mx.edu.utez.services_clothing_shop.service.requests_sell_product;

import mx.edu.utez.services_clothing_shop.controller.requests_sell_product.dto.RequestDetailsDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_sell_product.dto.RequestsSellProductDTO;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;
import mx.edu.utez.services_clothing_shop.model.request_sell_product.BeanRequestSellProduct;
import mx.edu.utez.services_clothing_shop.model.request_sell_product.IRequestsSellProduct;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RequestsSellProductService {

    private final IRequestsSellProduct IRequestsSellProduct;
    private final IRequestStatus IRequestStatus;
    private final String STATUS_INVALID = "requestStatus.invalid";

    public RequestsSellProductService(IRequestsSellProduct IRequestsSellProduct, IRequestStatus IRequestStatus) {
        this.IRequestsSellProduct = IRequestsSellProduct;
        this.IRequestStatus = IRequestStatus;
    }

    @Transactional
    public RequestsSellProductDTO putRequestSellProduct(UUID requestId, String status, String rejectionReason) {
        if (!isValidRejectionReason(rejectionReason)) {
            throw new CustomException("requestSellProduct.rejectionReason.invalid");
        }
        Optional<BeanRequestSellProduct> existingRequestOptional = IRequestsSellProduct.findById(requestId);
        if (existingRequestOptional.isPresent()) {
            BeanRequestSellProduct existingRequest = existingRequestOptional.get();
            BeanRequestStatus requestStatus = IRequestStatus.findByStatus(status)
                    .orElseThrow(() -> new CustomException(STATUS_INVALID));

            existingRequest.setStatus(requestStatus);
            existingRequest.setRejectionReason(rejectionReason);
            BeanRequestSellProduct updatedRequest = IRequestsSellProduct.save(existingRequest);

            return convertToDTO(updatedRequest);
        } else {
            throw new CustomException("requestSellProduct.id.notnull");
        }
    }

    private boolean isValidRejectionReason(String rejectionReason) {
        Pattern pattern = Pattern.compile(RegexPatterns.REJECTION_REASON_REGEX);
        Matcher matcher = pattern.matcher(rejectionReason);
        return matcher.matches();
    }

    public RequestDetailsDTO getRequestById(UUID idRequestSellProduct) {
        Optional<BeanRequestSellProduct> requestOptional = IRequestsSellProduct.findById(idRequestSellProduct);

        if (requestOptional.isPresent()) {
            BeanRequestSellProduct request = requestOptional.get();
            RequestDetailsDTO requestDetailsDTO = new RequestDetailsDTO();
            requestDetailsDTO.setIdRequestSellProduct(request.getIdRequestSellProduct());
            requestDetailsDTO.setUserEmail(request.getProduct().getUser().getEmail());
            requestDetailsDTO.setPrice(request.getProduct().getPrice());
            requestDetailsDTO.setDescription(request.getProduct().getDescription());
            requestDetailsDTO.setProductName(request.getProduct().getProductName());
            requestDetailsDTO.setImage(getPrimaryImage(request.getProduct().getProductGallery()));
            requestDetailsDTO.setProductId(request.getProduct().getIdProduct());

            return requestDetailsDTO;
        } else {
            throw new CustomException("Request not found");
        }
    }

    private String getPrimaryImage(List<BeanProductGallery> productGallery) {
        if (productGallery != null && !productGallery.isEmpty()) {
            return productGallery.get(0).getImage();
        } else {
            return null;
        }
    }

    public String getRequestSellProductById(UUID statusID) {
        BeanRequestStatus requestStatus = IRequestStatus.findById(statusID)
                .orElseThrow(() -> new CustomException(STATUS_INVALID));
        return requestStatus.getStatus();
    }

    @Transactional
    public RequestsSellProductDTO postRequestSellProduct(UUID productId) {
        BeanProduct product = new BeanProduct();
        product.setIdProduct(productId);
        BeanRequestStatus requestStatus = IRequestStatus.findByStatus("Pendiente")
                .orElseThrow(() -> new CustomException(STATUS_INVALID));
        BeanRequestSellProduct requestSellProduct = new BeanRequestSellProduct();
        requestSellProduct.setProduct(product);
        requestSellProduct.setStatus(requestStatus);

        BeanRequestSellProduct newRequest = IRequestsSellProduct.save(requestSellProduct);
        return convertToDTO(newRequest);
    }

    @Transactional
    public Page<IRequestsSellProduct.RequestSellStatusProjection> getPageRequestSellProduct(Pageable pageable) {
        return IRequestsSellProduct.findAllStatuses(pageable);
    }

    private RequestsSellProductDTO convertToDTO(BeanRequestSellProduct requestSellProduct) {
        RequestsSellProductDTO requestSellProductDTO = new RequestsSellProductDTO();
        requestSellProductDTO.setIdRequestSellProduct(requestSellProduct.getIdRequestSellProduct());
        requestSellProductDTO.setRejectionReason(requestSellProduct.getRejectionReason());
        requestSellProductDTO.setIdProduct(requestSellProduct.getProduct().getIdProduct());
        requestSellProductDTO.setIdStatus(requestSellProduct.getStatus().getIdStatus());
        requestSellProductDTO.setStatus(requestSellProduct.getStatus().getStatus());
        return requestSellProductDTO;
    }

    @Transactional
    public Page<IRequestsSellProduct.RequestSellStatusProjection> getPageRequestSellProductByEmail(String email, Pageable pageable) {
        return IRequestsSellProduct.findAllByEmailLikeIgnoreCase(email, pageable);
    }

}

