package mx.edu.utez.services_clothing_shop.controller.requests_become_seller;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto.*;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.IRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.service.requests_become_seller.RequestsBecomeSellerService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("venta-ropa/api/requests-become-seller")
@CrossOrigin(origins = "*")
public class RequestsBecomeSellerController {

    private final RequestsBecomeSellerService requestsBecomeSellerService;
    private static final String ERRORMESSAGE = "Bad request";

    public RequestsBecomeSellerController(RequestsBecomeSellerService requestsBecomeSellerService) {
        this.requestsBecomeSellerService = requestsBecomeSellerService;
    }

    @PostMapping("/post-request-become-seller")
    public ResponseEntity<CustomResponse<String>> postRequestBecomeSeller(@RequestBody RequestsBecomeSellerPostDTO requestData) {
        String email = requestData.getEmail();
        UserSellerInformation userSellerInformation = requestData.getUserSellerInformation();
        requestsBecomeSellerService.postRequestBecomeSeller(email, userSellerInformation);
        return ResponseEntity.ok(new CustomResponse<>("Request created successfully", "Request created", false, 200));
    }

    @PostMapping("/put-request-become-seller")
    public ResponseEntity<CustomResponse<String>> putRequestBecomeSeller(@RequestBody RequestsBecomeSellerPutDTO requestData) {
        UUID requestId = requestData.getIdRequestBecomeSeller();
        String status = requestData.getStatus();
        String rejectionReason = requestData.getRejectionReason();

        try {
            requestsBecomeSellerService.putRequestBecomeSeller(requestId, status, rejectionReason);
            return ResponseEntity.ok(new CustomResponse<>("Request updated successfully", "Request updated", false, 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(e.getMessage(), ERRORMESSAGE, true, 400));
        }
    }

    @GetMapping("/get-page")
    public Page<IRequestsBecomeSeller.RequestBecomeSellerProjection> getPageRequestBecomeSeller(Pageable pageable) {
        return requestsBecomeSellerService.getPageRequestBecomeSeller(pageable);
    }

    @PostMapping("/get-by-id-request-become-seller")
    public ResponseEntity<CustomResponse<Object>> getRequestBecomeSellerById(@RequestBody RequestBecomeSellerGetByIdDTO getByIdDTO) {
        UUID requestId = getByIdDTO.getIdRequestBecomeSeller();
        RequestBecomeSellerGetByIdResponseDTO requestData = requestsBecomeSellerService.getRequestBecomeSellerById(requestId).orElse(null);

        if (requestData != null) {
            return ResponseEntity.ok(new CustomResponse<>(requestData, "Request found by id", false, 200));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(null, "Request not found", true, 404));
        }
    }

    @PostMapping("/get-by-user-email")
    public ResponseEntity<CustomResponse<Object>> getRequestBecomeSellerByUserEmail(@Valid @RequestBody RequestBecomeSellerGetByUserEmailDTO getByUserEmailDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(result.getAllErrors(), ERRORMESSAGE, true, 400));
        }
        String email = getByUserEmailDTO.getEmail();
        Boolean exists = requestsBecomeSellerService.existsRequestBecomeSellerByUserEmail(email);
        if (Boolean.TRUE.equals(exists)) {
            return ResponseEntity.ok(new CustomResponse<>(true, "Request found by email", false, 200));
        } else {
            return ResponseEntity.ok(new CustomResponse<>(false, "Request not found", false, 200));
        }
    }

    @PostMapping("/get-page-by-user-email")
    public ResponseEntity<CustomResponse<Object>> getPageRequestBecomeSellerByUserEmail(Pageable page, @Valid @RequestBody RequestBecomeSellerGetByUserEmailDTO getByUserEmailDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(result.getAllErrors(), ERRORMESSAGE, true, 400));
        }
        String email = getByUserEmailDTO.getEmail();
        Page<IRequestsBecomeSeller.RequestBecomeSellerProjection> requestData = requestsBecomeSellerService.getPageRequestBecomeSellerByUserEmail(email, page);
        return ResponseEntity.ok(new CustomResponse<>(requestData, "Request found by email (page)", false, 200));
    }
}