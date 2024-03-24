package mx.edu.utez.services_clothing_shop.controller.requests_return_product;


import mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto.RequestsReturnProductDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto.RequestsReturnProductGetDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto.RequestsReturnProductPostDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto.RequestsReturnProductPutDTO;
import mx.edu.utez.services_clothing_shop.model.request_return_product.IRequestsReturnProduct;
import mx.edu.utez.services_clothing_shop.service.requests_return_product.RequestsReturnProductService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("venta-ropa/api/requests-return-product")
@CrossOrigin(origins = "*")
public class RequestsReturnProductController {

    private final RequestsReturnProductService requestsReturnProductService;

    public RequestsReturnProductController(RequestsReturnProductService requestsReturnProductService) {
        this.requestsReturnProductService = requestsReturnProductService;
    }

    @PostMapping("/get-by-id-request-return-product")
    public ResponseEntity<CustomResponse<RequestsReturnProductDTO>> getRequestReturnProductById(@RequestBody RequestsReturnProductGetDTO requestDTO) {
        UUID idRequest = requestDTO.getIdRequestReturnProduct();
        RequestsReturnProductDTO requestData = requestsReturnProductService.getRequestReturnProductById(idRequest);

        if (requestData != null) {
            String status = requestsReturnProductService.getRequestStatusById(requestData.getStatusId());
            requestData.setStatus(status);

            return ResponseEntity.ok(new CustomResponse<>(requestData, "Request found", false, 200));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomResponse<>(null, "Request not found", true, 404));
        }
    }

    @PostMapping("/post-request-return-product")
    public ResponseEntity<CustomResponse<RequestsReturnProductDTO>> postRequestReturnProduct(@RequestBody RequestsReturnProductPostDTO requestDTO) {
        RequestsReturnProductDTO requestData = requestsReturnProductService.postRequestReturnProduct(requestDTO.getOrderHasProductId());
        return ResponseEntity.ok(new CustomResponse<>(requestData, "Request created", false, 200));
    }

    @PutMapping("/put-request-return-product-status")
    public ResponseEntity<CustomResponse<RequestsReturnProductDTO>> putRequestReturnProduct(@RequestBody RequestsReturnProductPutDTO requestDTO) {
        RequestsReturnProductDTO updatedRequest = requestsReturnProductService.putRequestReturnProduct(requestDTO.getRequestId(), requestDTO.getStatus(), requestDTO.getRejectionReason());
        return ResponseEntity.ok(new CustomResponse<>(updatedRequest, "Request status updated", false, 200));
    }

    @GetMapping("/get-page")
    public Page<IRequestsReturnProduct.ReturnStatusProjection> getPageRequests(Pageable pageable) {
        return requestsReturnProductService.getPageRequests(pageable);

    }

}
