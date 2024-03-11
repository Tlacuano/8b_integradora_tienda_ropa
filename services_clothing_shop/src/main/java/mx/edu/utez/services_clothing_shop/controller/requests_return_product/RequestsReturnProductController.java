package mx.edu.utez.services_clothing_shop.controller.requests_return_product;


import mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto.RequestsReturnProductDTO;
import mx.edu.utez.services_clothing_shop.model.request_return_product.IRequestsReturnProduct;
import mx.edu.utez.services_clothing_shop.service.requests_return_product.RequestsReturnProductService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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
    public ResponseEntity<CustomResponse<RequestsReturnProductDTO>> getRequestByOrder(@RequestBody Map<String, String> payload) {
        UUID idOrder = UUID.fromString(payload.get("idOrder"));
        RequestsReturnProductDTO requestData = requestsReturnProductService.getRequestByIdOrderProduct(idOrder).orElse(null);

        if (requestData != null) {
            return ResponseEntity.ok(new CustomResponse<>(requestData, "Request found", false, 200));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomResponse<>(null, "Request not found", true, 404));
        }
    }

    @PostMapping("/post")
    public ResponseEntity<CustomResponse<RequestsReturnProductDTO>> postRequest(@RequestBody Map<String, String> payload) {
        UUID orderHasProductId = UUID.fromString(payload.get("orderHasProductId"));
        RequestsReturnProductDTO requestData = requestsReturnProductService.postRequest(orderHasProductId);
        return ResponseEntity.ok(new CustomResponse<>(requestData, "Request created", false, 200));
    }

    @PutMapping("/put")
    public ResponseEntity<CustomResponse<RequestsReturnProductDTO>> putRequestStatus(@RequestBody Map<String, String> payload) {
        UUID requestId = UUID.fromString(payload.get("requestId"));
        String status = payload.get("status");
        String rejectionReason = payload.get("rejectionReason");
        RequestsReturnProductDTO updatedRequest = requestsReturnProductService.putRequestStatus(requestId, status, rejectionReason);
        return ResponseEntity.ok(new CustomResponse<>(updatedRequest, "Request status updated", false, 200));
    }

    @GetMapping("/get-all")
    public Page<IRequestsReturnProduct.ReturnStatusProjection> getAllRequests(@RequestParam int page, @RequestParam int size) {
        return requestsReturnProductService.findAllStatuses(page, size);
    }
}
