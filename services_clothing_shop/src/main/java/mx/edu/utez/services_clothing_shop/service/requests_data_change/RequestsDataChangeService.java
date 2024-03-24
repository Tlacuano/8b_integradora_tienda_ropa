package mx.edu.utez.services_clothing_shop.service.requests_data_change;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto.NewInformation;
import mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto.RequestDataChangeIdDTO;
import mx.edu.utez.services_clothing_shop.model.request_data_change.BeanRequestDataChange;
import mx.edu.utez.services_clothing_shop.model.request_data_change.IRequestsDataChange;
import mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestsDataChangeService {
    private final IRequestsDataChange IRequestsDataChange;

    public RequestsDataChangeService(IRequestsDataChange IRequestsDataChange) {
        this.IRequestsDataChange = IRequestsDataChange;
    }

    @Transactional
    public void putRequestDataChange(UUID requestId, String status, String rejectionReason) {
        if (!rejectionReason.isEmpty() && !rejectionReason.matches(RegexPatterns.REJECTION_REASON_REGEX)) {
            throw new CustomException("dataChange.rejectionReason.invalid");
        }

        IRequestsDataChange.updateRequestDataChange(requestId, status, rejectionReason);
    }

    @Transactional
    public void postRequestDataChange(String email, NewInformation newInformation) {
        ObjectMapper objectMapper = new ObjectMapper();
        String newUserInfoJSON;
        try {
            newUserInfoJSON = objectMapper.writeValueAsString(newInformation);
        } catch (JsonProcessingException e) {
            throw new CustomException("dataChange.JSON.invalid");
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
            throw new CustomException("dataChange.requestId.notFound");
        }
    }

    @Transactional
    public Page<IRequestsDataChange.RequestDataChangeStatusProjection> getPageRequestDataChange(Pageable pageable) {
        return IRequestsDataChange.findAllStatuses(pageable);
    }

    private Map<String, Object> convertJsonToMap(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new CustomException("dataChange.JSON.invalid");
        }
    }
}
