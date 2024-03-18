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

    @PostMapping("/get-by-order")
    public ResponseEntity<CustomResponse<RequestsReturnProductDTO>> getRequestByOrder(@RequestBody RequestsReturnProductGetDTO requestDTO) {
        UUID idOrder = requestDTO.getIdRequestReturnProduct();
        RequestsReturnProductDTO requestData = requestsReturnProductService.getRequestsById(idOrder);

        if (requestData != null) {
            String status = requestsReturnProductService.getRequestStatusById(requestData.getStatusId());
            requestData.setStatus(status);

            return ResponseEntity.ok(new CustomResponse<>(requestData, "Request found", false, 200));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomResponse<>(null, "Request not found", true, 404));
        }
    }



    @PostMapping("/post")
    public ResponseEntity<CustomResponse<RequestsReturnProductDTO>> postRequest(@RequestBody RequestsReturnProductPostDTO requestDTO) {
        RequestsReturnProductDTO requestData = requestsReturnProductService.postRequest(requestDTO.getOrderHasProductId());
        return ResponseEntity.ok(new CustomResponse<>(requestData, "Request created", false, 200));
    }

    @PutMapping("/put")
    public ResponseEntity<CustomResponse<RequestsReturnProductDTO>> putRequestStatus(@RequestBody RequestsReturnProductPutDTO requestDTO) {
        RequestsReturnProductDTO updatedRequest = requestsReturnProductService.putRequestStatus(requestDTO.getRequestId(), requestDTO.getStatus(), requestDTO.getRejectionReason());
        return ResponseEntity.ok(new CustomResponse<>(updatedRequest, "Request status updated", false, 200));
    }

    @GetMapping("/get-all")
    public Page<IRequestsReturnProduct.ReturnStatusProjection> getAllRequests(Pageable pageable) {
        return requestsReturnProductService.findAllStatuses(pageable);
    }

}
