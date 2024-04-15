package mx.edu.utez.services_clothing_shop.service.requests_data_change;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto.NewInformation;
import mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto.RequestDataChangeIdDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto.RequestDataChangePutDTO;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.request_data_change.BeanRequestDataChange;
import mx.edu.utez.services_clothing_shop.model.request_data_change.IRequestsDataChange;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.seller_information.BeanSellerInformation;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.HashMap;

@Service
public class RequestsDataChangeService {
    private final IRequestsDataChange requestsDataChangerepository;
    private static final String REQUEST_NOT_FOUND = "dataChange.requestId.notFound";

    public RequestsDataChangeService(IRequestsDataChange requestsDataChangerepository) {
        this.requestsDataChangerepository = requestsDataChangerepository;
    }

    @Transactional
    public void putRequestDataChange(RequestDataChangePutDTO requestData) {
        UUID requestId = requestData.getIdRequestDataChange();
        String status = requestData.getStatus();
        String rejectionReason = requestData.getRejectionReason();

        try {
            BeanRequestDataChange requestDataChange = requestsDataChangerepository.findById(requestId)
                    .orElseThrow(() -> new CustomException(REQUEST_NOT_FOUND));

            if (status != null) {
                Optional<BeanRequestStatus> requestStatus = requestsDataChangerepository.findStatusByStatusName(status);
                if (requestStatus.isPresent()) {
                    requestDataChange.setStatus(requestStatus.get());

                    if ("Aprobado".equals(status)) {
                        updateSellerInformation(requestDataChange, requestData);
                    }
                } else {
                    throw new CustomException("dataChange.statusNotFound");
                }
            }
            if (rejectionReason != null) {
                requestDataChange.setRejectionReason(rejectionReason);
            }

            requestsDataChangerepository.save(requestDataChange);
        } catch (CustomException e) {
            throw new CustomException("dataChange.putRequestError");
        }
    }

    private void updateSellerInformation(BeanRequestDataChange requestDataChange, RequestDataChangePutDTO requestData) {
        BeanUser user = requestDataChange.getUser();
        BeanPerson person = user.getPerson();
        BeanSellerInformation sellerInfo = person.getSellerInformation();

        if (sellerInfo != null) {
            boolean needsUpdate = false;
            if (requestData.getTaxIdentificationNumber() != null) {
                sellerInfo.setTaxIdentificationNumber(requestData.getTaxIdentificationNumber());
                needsUpdate = true;
            }
            if (requestData.getSecondaryPhoneNumber() != null) {
                sellerInfo.setSecondaryPhoneNumber(requestData.getSecondaryPhoneNumber());
                needsUpdate = true;
            }
            if (requestData.getImageIdentification() != null) {
                sellerInfo.setImageIdentification(requestData.getImageIdentification());
                needsUpdate = true;
            }
            if (requestData.getCurp() != null) {
                sellerInfo.setCurp(requestData.getCurp());
                needsUpdate = true;
            }
            if (needsUpdate) {
                requestsDataChangerepository.save(requestDataChange);
            }
        } else {
            throw new CustomException("dataChange.sellerInfoNotFound");
        }
    }

    @Transactional
    public void postRequestDataChange(String email, NewInformation newInformation) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String newUserInfoJSON;
        try {
            newUserInfoJSON = objectMapper.writeValueAsString(newInformation);
            requestsDataChangerepository.insertRequestDataChange(email, newUserInfoJSON);
        } catch (JsonProcessingException e) {
            throw new CustomException("dataChange.JSON.invalid");
        }
    }


    @Transactional
    public RequestDataChangeIdDTO getRequestByID(UUID idRequestDataChange) {
        try {
            Optional<BeanRequestDataChange> requestDataChangeOptional = requestsDataChangerepository.findById(idRequestDataChange);
            if (requestDataChangeOptional.isEmpty()) {
                throw new CustomException(REQUEST_NOT_FOUND);
            }

            BeanRequestDataChange requestDataChange = requestDataChangeOptional.get();
            Map<String, Object> newUserInformationMap = convertJsonToMap(requestDataChange.getNewUserInformation());

            newUserInformationMap = convertKeysToCamelCase(newUserInformationMap);

            BeanUser user = requestDataChange.getUser();
            BeanSellerInformation sellerInfo = user.getPerson().getSellerInformation();
            if (sellerInfo == null) {
                throw new CustomException("dataChange.sellerInfoNotFound");
            }

            return new RequestDataChangeIdDTO(
                    requestDataChange.getIdRequestDataChange(),
                    newUserInformationMap,
                    requestDataChange.getRejectionReason(),
                    user.getEmail(),
                    requestDataChange.getStatus().getStatus(),
                    sellerInfo.getTaxIdentificationNumber(),
                    sellerInfo.getSecondaryPhoneNumber(),
                    sellerInfo.getImageIdentification(),
                    sellerInfo.getCurp()
            );
        } catch (CustomException e) {
            throw new CustomException(REQUEST_NOT_FOUND);
        }
    }


    @Transactional
    public Page<IRequestsDataChange.RequestDataChangeStatusPersonProjection> getPageRequestDataChangeWithPersonName(Pageable pageable, String searchTerm) {
        return requestsDataChangerepository.findAllStatusesWithPersonNameAndLastName(pageable, searchTerm);
    }

    private Map<String, Object> convertJsonToMap(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new CustomException("dataChange.JSON.invalid");
        }
    }

    public Map<String, Object> convertKeysToCamelCase(Map<String, Object> originalMap) {
        Map<String, Object> convertedMap = originalMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> toCamelCase(entry.getKey()),
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        HashMap::new
                ));
        return convertedMap;
    }

    private String toCamelCase(String field) {
        String[] parts = field.split("_");
        StringBuilder camelCaseString = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            camelCaseString.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1).toLowerCase());
        }
        return camelCaseString.toString();
    }
}
