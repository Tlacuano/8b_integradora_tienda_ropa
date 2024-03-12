package mx.edu.utez.services_clothing_shop.controller.requests_become_seller;

import mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto.RequestsBecomeSellerDTO;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.IRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.service.requests_become_seller.RequestsBecomeSellerService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("venta-ropa/api/requests-become-seller")
@CrossOrigin(origins = "*")
public class RequestsBecomeSellerController {

    private final RequestsBecomeSellerService requestsBecomeSellerService;

    public RequestsBecomeSellerController(RequestsBecomeSellerService requestsBecomeSellerService) {
        this.requestsBecomeSellerService = requestsBecomeSellerService;
    }

    @PostMapping("/get-by-email")
    public ResponseEntity<CustomResponse<RequestsBecomeSellerDTO>> getRequestByEmail(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        RequestsBecomeSellerDTO requestData = requestsBecomeSellerService.getRequestByEmail(email).orElse(null);

        if (requestData != null) {
            return ResponseEntity.ok(new CustomResponse<>(requestData, "Request found", false, 200));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomResponse<>(null, "Request not found", true, 404));
        }
    }

    @PostMapping("/post")
    public ResponseEntity<CustomResponse<RequestsBecomeSellerDTO>> postRequest(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        RequestsBecomeSellerDTO requestData = requestsBecomeSellerService.postRequest(email);
        return ResponseEntity.ok(new CustomResponse<>(requestData, "Request created", false, 200));
    }

    @PutMapping("/put")
    public ResponseEntity<CustomResponse<RequestsBecomeSellerDTO>> putRequestStatus(@RequestBody Map<String, String> payload) {
        UUID requestId = UUID.fromString(payload.get("requestId"));
        String status = payload.get("status");
        String rejectionReason = payload.get("rejectionReason");
        RequestsBecomeSellerDTO updatedRequest = requestsBecomeSellerService.putRequestStatus(requestId, status, rejectionReason);
        return ResponseEntity.ok(new CustomResponse<>(updatedRequest, "Request status updated", false, 200));
    }

    @GetMapping("/get-all")
    public Page<IRequestsBecomeSeller.StatusProjection> getAllRequests(@RequestParam int page, @RequestParam int size) {
        return requestsBecomeSellerService.findAllStatuses(page, size);
    }
}