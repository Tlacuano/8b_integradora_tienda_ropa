package mx.edu.utez.services_clothing_shop.controller.requests_become_seller;

import mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto.*;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.IRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.service.requests_become_seller.RequestsBecomeSellerService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("venta-ropa/api/requests-become-seller")
@CrossOrigin(origins = "*")
public class RequestsBecomeSellerController {

    private final RequestsBecomeSellerService requestsBecomeSellerService;

    public RequestsBecomeSellerController(RequestsBecomeSellerService requestsBecomeSellerService) {
        this.requestsBecomeSellerService = requestsBecomeSellerService;
    }


    @PostMapping("/post-request-become-seller")
    public ResponseEntity<CustomResponse<String>> postRequestBecomeSeller(@RequestBody RequestsBecomeSellerPostDTO requestData){
        String email = requestData.getEmail();
        UserSellerInformation userSellerInformation = requestData.getUserSellerInformation();
        requestsBecomeSellerService.postRequestBecomeSeller(email, userSellerInformation);
        return ResponseEntity.ok(new CustomResponse<>("Request created successfully", "Request created", false, 200));
    }

    @PutMapping("/put-request-become-seller")
    public ResponseEntity<CustomResponse<String>> putRequestBecomeSeller(@RequestBody RequestsBecomeSellerPutDTO requestData) {
        UUID requestId = requestData.getIdRequestBecomeSeller();
        String status = requestData.getStatus();
        String rejectionReason = requestData.getRejectionReason();

        try {
            requestsBecomeSellerService.putRequestBecomeSeller(requestId, status, rejectionReason);
            return ResponseEntity.ok(new CustomResponse<>("Request updated successfully", "Request updated", false, 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(e.getMessage(), "Bad request", true, 400));
        }
    }

    @GetMapping("/get-page")
    public Page<IRequestsBecomeSeller.RequestBecomeSellerProjection> getPageRequestBecomeSeller(Pageable pageable) {
        return requestsBecomeSellerService.getPageRequestBecomeSeller(pageable);
    }

    @PostMapping("/get-by-email")
    public ResponseEntity<CustomResponse<RequestBecomeSellerGetByIdResponseDTO>> getRequestBecomeSellerById(@RequestBody RequestBecomeSellerGetByIdDTO getByIdDTO) {
        UUID requestId = getByIdDTO.getRequestId();
        RequestBecomeSellerGetByIdResponseDTO requestData = requestsBecomeSellerService.getRequestBecomeSellerById(requestId).orElse(null);

        if (requestData != null) {
            return ResponseEntity.ok(new CustomResponse<>(requestData, "Request found", false, 200));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomResponse<>(null, "Request not found", true, 404));
        }
    }
}