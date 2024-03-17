package mx.edu.utez.services_clothing_shop.controller.requests_data_change;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto.*;
import mx.edu.utez.services_clothing_shop.exception.ErrorDictionary;
import mx.edu.utez.services_clothing_shop.model.request_data_change.BeanRequestDataChange;
import mx.edu.utez.services_clothing_shop.model.request_data_change.IRequestsDataChange;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;
import mx.edu.utez.services_clothing_shop.service.requests_data_change.RequestsDataChangeService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns;
import org.springframework.asm.TypeReference;
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
    private final IRequestsDataChange IRequestsDataChange;

    public RequestsDataChangeController(RequestsDataChangeService requestsDataChangeService, IRequestsDataChange IRequestsDataChange) {
        this.requestsDataChangeService = requestsDataChangeService;
        this.IRequestsDataChange = IRequestsDataChange;
    }

    @PostMapping("/post")
    public ResponseEntity<CustomResponse<String>> postRequest(@RequestBody RequestChangeInformationRequest request) {
        String email = request.getEmail();
        NewInformation newInformation = request.getNewInformation();
        requestsDataChangeService.postRequest(email, newInformation);
        return ResponseEntity.ok(new CustomResponse<>("Request created successfully", "Request created", false, 200));
    }

    @PutMapping("/put")
    public ResponseEntity<CustomResponse<String>> updateRequestDataChange(@RequestBody RequestDataChangePutDTO requestData) {
        UUID requestId = requestData.getIdRequestDataChange();
        String status = requestData.getStatus();
        String rejectionReason = requestData.getRejectionReason();

        try {
            requestsDataChangeService.updateRequestDataChange(requestId, status, rejectionReason);
            return ResponseEntity.ok(new CustomResponse<>("Request updated successfully", "Request updated", false, 200));
        } catch (RequestsDataChangeService.InvalidRequestException e) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(e.getMessage(), "Bad request", true, 400));
        }
    }

    @GetMapping("/get-all")
    public Page<String> getAllRequests(Pageable pageable) {
        return IRequestsDataChange.findAllStatusNames(pageable);
    }

    @PostMapping("/get-by-id")
    public ResponseEntity<RequestDataChangeIdDTO> getRequestByID(@RequestBody RequestDataChangeIdDTO requestBody) {
        UUID requestId = requestBody.getRequestId();

        try {
            RequestDataChangeIdDTO response = requestsDataChangeService.getRequestByID(requestId);
            return ResponseEntity.ok(response);
        } catch (RequestsDataChangeService.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

