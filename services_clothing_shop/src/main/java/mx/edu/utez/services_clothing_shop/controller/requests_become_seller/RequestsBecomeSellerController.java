package mx.edu.utez.services_clothing_shop.controller.requests_become_seller;

import mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto.RequestsBecomeSellerDTO;
import mx.edu.utez.services_clothing_shop.service.requests_become_seller.RequestsBecomeSellerService;
import org.springframework.data.domain.Page;
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
    public RequestsBecomeSellerDTO getRequestByEmail(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        return requestsBecomeSellerService.getRequestByEmail(email).orElse(null);
    }

    @PostMapping("/post")
    public RequestsBecomeSellerDTO postRequest(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        return requestsBecomeSellerService.postRequest(email);
    }


    @PutMapping("/put")
    public RequestsBecomeSellerDTO putRequestStatus(@RequestBody Map<String, String> payload) {
        UUID requestId = UUID.fromString(payload.get("requestId"));
        String status = payload.get("status");
        String rejectionReason = payload.get("rejectionReason");
        return requestsBecomeSellerService.putRequestStatus(requestId, status, rejectionReason);
    }

    @GetMapping("/get-all")
    public Page<RequestsBecomeSellerDTO> getAllRequests(@RequestParam int page, @RequestParam int size) {
        return requestsBecomeSellerService.getAllRequests(page, size);
    }
}
