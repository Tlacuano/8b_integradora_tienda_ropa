package mx.edu.utez.services_clothing_shop.service.requests_data_change;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto.NewInformation;
import mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto.RequestDataChangeIdDTO;
import mx.edu.utez.services_clothing_shop.exception.ErrorDictionary;
import mx.edu.utez.services_clothing_shop.model.request_data_change.BeanRequestDataChange;
import mx.edu.utez.services_clothing_shop.model.request_data_change.IRequestsDataChange;
import mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestsDataChangeService {
    private final IRequestsDataChange IRequestsDataChange;
    private final ErrorDictionary errorDictionary;

    public RequestsDataChangeService(IRequestsDataChange IRequestsDataChange, ErrorDictionary errorDictionary) {
        this.IRequestsDataChange = IRequestsDataChange;
        this.errorDictionary = errorDictionary;
    }

    @Transactional
    public void updateRequestDataChange(UUID requestId, String status, String rejectionReason) {
        if (!rejectionReason.isEmpty() && !rejectionReason.matches(RegexPatterns.REJECTION_REASON_REGEX)) {
            String errorMessage = errorDictionary.getErrorMessage("dataChange.rejectionReason.invalid");
            throw new InvalidRequestException(errorMessage);
        }

        IRequestsDataChange.updateRequestDataChange(requestId, status, rejectionReason);
    }

    @Transactional
    public void postRequest(String email, NewInformation newInformation) {
        ObjectMapper objectMapper = new ObjectMapper();
        String newUserInfoJSON;
        try {
            newUserInfoJSON = objectMapper.writeValueAsString(newInformation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al convertir NewInformation a JSON", e);
        }

        IRequestsDataChange.insertRequestDataChange(email, newUserInfoJSON);
    }

    public RequestDataChangeIdDTO getRequestByID(UUID requestId) {
        Optional<BeanRequestDataChange> requestDataChangeOptional = IRequestsDataChange.findById(requestId);

        if (requestDataChangeOptional.isPresent()) {
            BeanRequestDataChange requestDataChange = requestDataChangeOptional.get();
            Map<String, Object> newUserInformationMap = convertJsonToMap(requestDataChange.getNewUserInformation());

            return new RequestDataChangeIdDTO(
                    requestDataChange.getIdRequestDataChange(),
                    newUserInformationMap,
                    requestDataChange.getRejectionReason(),
                    requestDataChange.getUser().getEmail(),
                    requestDataChange.getStatus().getStatus()
            );
        } else {
            throw new NotFoundException("Request not found");
        }
    }

    private Map<String, Object> convertJsonToMap(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al convertir JSON a Map", e);
        }
    }

    public static class InvalidRequestException extends RuntimeException {
        public InvalidRequestException(String message) {
            super(message);
        }
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) {
            super(message);
        }
    }
}