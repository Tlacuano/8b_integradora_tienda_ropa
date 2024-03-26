package mx.edu.utez.services_clothing_shop.service.requests_become_seller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto.RequestBecomeSellerGetByIdResponseDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto.UserSellerInformation;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.BeanRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.IRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;

import mx.edu.utez.services_clothing_shop.model.seller_information.BeanSellerInformation;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestsBecomeSellerService {

    private final IRequestsBecomeSeller IRequestsBecomeSeller;
    private final IRequestStatus IRequestStatus;

    private final EntityManager entityManager;

    public RequestsBecomeSellerService(IRequestsBecomeSeller IRequestsBecomeSeller, IRequestStatus IRequestStatus, EntityManager entityManager) {
        this.IRequestsBecomeSeller = IRequestsBecomeSeller;
        this.IRequestStatus = IRequestStatus;
        this.entityManager = entityManager;
    }

    @Transactional
    public void putRequestBecomeSeller(UUID requestId, String status, String rejectionReason){
        if(!rejectionReason.isEmpty() && !rejectionReason.matches(RegexPatterns.REJECTION_REASON_REGEX)) {
            throw new CustomException("requestBecomeSeller.rejectionReason.invalid");
        }

        IRequestsBecomeSeller.updateRequestBecomeSeller(requestId, status, rejectionReason);

        if (status.equals("Aprobado")) {
            Optional<BeanRequestsBecomeSeller> requestOptional = IRequestsBecomeSeller.findById(requestId);
            if (requestOptional.isPresent()) {
                BeanRequestsBecomeSeller request = requestOptional.get();
                BeanPerson person = IRequestsBecomeSeller.findPersonByRequestId(requestId);
                if (person != null) {
                    BeanSellerInformation sellerInformation = new BeanSellerInformation();
                    String userSellerInformationJSON = request.getUserSellerInformation();
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        UserSellerInformation userSellerInformation = objectMapper.readValue(userSellerInformationJSON, UserSellerInformation.class);

                        if (userSellerInformation != null &&
                                userSellerInformation.getTaxIdentificationNumber() != null &&
                                userSellerInformation.getSecondaryPhoneNumber() != null &&
                                userSellerInformation.getPrivacyPolicyAgreement() != null &&
                                userSellerInformation.getImageIdentification() != null &&
                                userSellerInformation.getcurp() != null) {

                            sellerInformation.setTaxIdentificationNumber(userSellerInformation.getTaxIdentificationNumber());
                            sellerInformation.setSecondaryPhoneNumber(userSellerInformation.getSecondaryPhoneNumber());
                            sellerInformation.setPrivacyPolicyAgreement(userSellerInformation.getPrivacyPolicyAgreement());
                            sellerInformation.setImageIdentification(userSellerInformation.getImageIdentification());
                            sellerInformation.setCurp(userSellerInformation.getcurp());
                            sellerInformation.setPerson(person);
                        } else {
                            throw new CustomException("Todos los campos de UserSellerInformation deben estar llenos");
                        }
                    } catch (IOException e) {
                        throw new CustomException("Error al convertir el JSON de UserSellerInformation");
                    }
                    entityManager.persist(sellerInformation);
                    entityManager.flush();
                } else {
                    throw new CustomException("No se encontró la persona asociada a la solicitud");
                }
            }
        }
    }




    @Transactional
    public void postRequestBecomeSeller(String email, UserSellerInformation userSellerInformation) {
        if (userSellerInformation == null ||
                StringUtils.isEmpty(userSellerInformation.getTaxIdentificationNumber()) ||
                StringUtils.isEmpty(userSellerInformation.getSecondaryPhoneNumber()) ||
                (userSellerInformation.getPrivacyPolicyAgreement() == null || !userSellerInformation.getPrivacyPolicyAgreement()) ||
                StringUtils.isEmpty(userSellerInformation.getImageIdentification()) ||
                StringUtils.isEmpty(userSellerInformation.getcurp())) {
            throw new CustomException("requestBecomeSeller.userSellerInformation.empty");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String userSellerInformationJSON;
        try {
            userSellerInformationJSON = objectMapper.writeValueAsString(userSellerInformation);
        } catch (Exception e) {
            throw new CustomException("requestBecomeSeller.JSON.invalid");
        }
        IRequestsBecomeSeller.insertRequestBecomeSeller(email, userSellerInformationJSON);
    }



    public Page<IRequestsBecomeSeller.RequestBecomeSellerProjection> getPageRequestBecomeSeller(Pageable pageable) {
        return IRequestsBecomeSeller.findAllStatusesWithDetails(pageable);
    }


    public Optional<RequestBecomeSellerGetByIdResponseDTO> getRequestBecomeSellerById(UUID requestId) {
        Optional<BeanRequestsBecomeSeller> requestOptional = IRequestsBecomeSeller.findById(requestId);
        return requestOptional.map(this::convertToDTO);
    }

    private RequestBecomeSellerGetByIdResponseDTO convertToDTO(BeanRequestsBecomeSeller request) {
        RequestBecomeSellerGetByIdResponseDTO dto = new RequestBecomeSellerGetByIdResponseDTO();
        dto.setIdRequestBecomeSeller(request.getIdRequestBecomeSeller());
        dto.setRejectionReason(request.getRejectionReason());
        dto.setUserId(request.getUser().getIdUser());
        dto.setPersonId(request.getUser().getPerson().getIdPerson());
        dto.setPersonName(request.getUser().getPerson().getName());
        dto.setPersonLastName(request.getUser().getPerson().getLastName());
        dto.setStatusId(request.getStatus().getIdStatus());
        dto.setStatus(request.getStatus().getStatus());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            dto.setUserSellerInformation(objectMapper.readValue(request.getUserSellerInformation(), UserSellerInformation.class));
        } catch (Exception e) {
            throw new CustomException("requestBecomeSeller.JSON.invalid");
        }

        return dto;
    }
}
