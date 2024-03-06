package mx.edu.utez.services_clothing_shop.controller.requests_become_seller;


import mx.edu.utez.services_clothing_shop.model.request_become_seller.BeanRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.service.requests_become_seller.RequestsBecomeSellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/requests-become-seller")
@CrossOrigin(origins = "*")
public class RequestsBecomeSellerController {

    private final RequestsBecomeSellerService requestsBecomeSellerService;

    @Autowired
    public RequestsBecomeSellerController(RequestsBecomeSellerService requestBecomeSellerService) {
        this.requestsBecomeSellerService = requestBecomeSellerService;
    }


    @GetMapping("/{id}")
    public BeanRequestsBecomeSeller getRequestById(@PathVariable UUID id) {
        return requestsBecomeSellerService.getRequestById(id).orElse(null);
    }

    @PostMapping
    public BeanRequestsBecomeSeller postRequest(@RequestBody BeanRequestsBecomeSeller request) {
        return requestsBecomeSellerService.postRequest(request);
    }

    @PutMapping("/{id}")
    public BeanRequestsBecomeSeller putRequest(@PathVariable UUID id, @RequestBody BeanRequestsBecomeSeller request) {
        return requestsBecomeSellerService.putRequest(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable UUID id) {
        requestsBecomeSellerService.deleteRequest(id);
    }

    @GetMapping
    public Page<BeanRequestsBecomeSeller> getAllRequests(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return requestsBecomeSellerService.getAllRequestsBecomeSeller(pageable);
    }
}
