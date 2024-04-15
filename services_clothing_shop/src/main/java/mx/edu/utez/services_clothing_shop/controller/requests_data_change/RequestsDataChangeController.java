package mx.edu.utez.services_clothing_shop.controller.requests_data_change;


import mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto.*;
import mx.edu.utez.services_clothing_shop.model.request_data_change.IRequestsDataChange;
import mx.edu.utez.services_clothing_shop.service.requests_data_change.RequestsDataChangeService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;

import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.*;

@RestController
@RequestMapping("venta-ropa/api/requests-data-change")
@CrossOrigin(origins = "*")
public class RequestsDataChangeController {

    private final RequestsDataChangeService requestsDataChangeService;

    public RequestsDataChangeController(RequestsDataChangeService requestsDataChangeService) {
        this.requestsDataChangeService = requestsDataChangeService;
    }

    @PostMapping("/post-request-data-change")
    public ResponseEntity<CustomResponse<String>> postRequestDataChange(@RequestBody RequestChangeInformationRequest request) {
        String email = request.getEmail();
        NewInformation newInformation = request.getNewInformation();
        requestsDataChangeService.postRequestDataChange(email, newInformation);
        return ResponseEntity.ok(new CustomResponse<>("Request created successfully", "Request created", false, 200));
    }

    @PutMapping("/put-request-data-change")
    public ResponseEntity<CustomResponse<String>> putRequestDataChange(@RequestBody RequestDataChangePutDTO requestData) {
        try {
            requestsDataChangeService.putRequestDataChange(requestData);
            return ResponseEntity.ok(new CustomResponse<>("Request updated successfully", "Request updated", false, 200));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(e.getMessage(), "Bad request", true, 400));
        }
    }


    @GetMapping("/get-page")
    public Page<IRequestsDataChange.RequestDataChangeStatusPersonProjection> getPageRequestDataChange(Pageable pageable, @RequestParam(defaultValue = "") String searchTerm) {
        return requestsDataChangeService.getPageRequestDataChangeWithPersonName(pageable, searchTerm);
    }


    @PostMapping("/get-by-id-request-data-change")
    public ResponseEntity<RequestDataChangeIdDTO> getRequestDataChangeById(@RequestBody GetRequestDataChangeId requestDTO) {
        UUID requestId = requestDTO.getIdRequestDataChange();
        try {
            RequestDataChangeIdDTO response = requestsDataChangeService.getRequestByID(requestId);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
