package mx.edu.utez.services_clothing_shop.service.requests_data_change;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto.NewInformation;
import mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto.RequestDataChangeIdDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto.RequestDataChangePutDTO;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.person.GenderEnum;
import mx.edu.utez.services_clothing_shop.model.request_data_change.BeanRequestDataChange;
import mx.edu.utez.services_clothing_shop.model.request_data_change.IRequestsDataChange;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public void putRequestDataChange(RequestDataChangePutDTO requestData) {
        UUID requestId = requestData.getIdRequestDataChange();
        String status = requestData.getStatus();
        String rejectionReason = requestData.getRejectionReason();

        try {
            BeanRequestDataChange requestDataChange = IRequestsDataChange.findById(requestId)
                    .orElseThrow(() -> new CustomException("dataChange.requestId.notFound"));

            if (status != null) {
                Optional<BeanRequestStatus> requestStatus = IRequestsDataChange.findStatusByStatusName(status);
                if (requestStatus.isPresent()) {
                    requestDataChange.setStatus(requestStatus.get());

                    if ("Aprobado".equals(status)) {
                        updatePersonInformation(requestDataChange, requestData);
                    }
                } else {
                    throw new CustomException("dataChange.statusNotFound");
                }
            }
            if (rejectionReason != null) {
                requestDataChange.setRejectionReason(rejectionReason);
            }

            IRequestsDataChange.save(requestDataChange);
        } catch (CustomException e) {
            throw new CustomException("dataChange.putRequestError");
        }
    }

    private void updatePersonInformation(BeanRequestDataChange requestDataChange, RequestDataChangePutDTO requestData) {
        BeanUser user = requestDataChange.getUser();
        BeanPerson person = user.getPerson();

        if (person != null) {
            String newName = requestData.getName();
            if (newName != null) {
                person.setName(newName);
            }
            String newLastName = requestData.getLastName();
            if (newLastName != null) {
                person.setLastName(newLastName);
            }
            String newSecondLastName = requestData.getSecondLastName();
            if (newSecondLastName != null) {
                person.setSecondLastName(newSecondLastName);
            }

            LocalDate newBirthday = requestData.getBirthday();
            if (newBirthday != null) {
                person.setBirthday(newBirthday);
            }
            GenderEnum newGender = requestData.getGender();
            if (newGender != null) {
                person.setGender(newGender);
            }
        } else {
            throw new CustomException("dataChange.personNotFound");
        }
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

            BeanUser user = requestDataChange.getUser();
            BeanPerson person = user.getPerson();
            String genderAsString = person != null ? person.getGender().toString() : null;



            return new RequestDataChangeIdDTO(
                    requestDataChange.getIdRequestDataChange(),
                    newUserInformationMap,
                    requestDataChange.getRejectionReason(),
                    user.getEmail(),
                    requestDataChange.getStatus().getStatus(),
                    person != null ? person.getIdPerson() : null,
                    person != null ? person.getName() : null,
                    person != null ? person.getLastName() : null,
                    person != null ? person.getSecondLastName() : null,
                    person != null ? person.getBirthday() : null,
                    person != null ? person.getPhoneNumber() : null,
                    genderAsString
            );
        } else {
            throw new CustomException("dataChange.requestId.notFound");
        }
    }


    @Transactional
    public Page<IRequestsDataChange.RequestDataChangeStatusPersonProjection> getPageRequestDataChangeWithPersonName(Pageable pageable) {
        return IRequestsDataChange.findAllStatusesWithPersonNameAndLastName(pageable);
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
