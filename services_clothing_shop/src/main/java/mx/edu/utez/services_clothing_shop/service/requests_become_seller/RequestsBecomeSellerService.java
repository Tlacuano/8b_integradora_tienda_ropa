package mx.edu.utez.services_clothing_shop.service.requests_become_seller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto.RequestBecomeSellerGetByIdResponseDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto.UserSellerInformation;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.BeanRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.IRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;

import mx.edu.utez.services_clothing_shop.model.seller_information.BeanSellerInformation;
import mx.edu.utez.services_clothing_shop.service.email_service.EmailService;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestsBecomeSellerService {

    private final IRequestsBecomeSeller IRequestsBecomeSeller;
    private final IRequestStatus IRequestStatus;
    private final EntityManager entityManager;
    private final EmailService emailService;

    public RequestsBecomeSellerService(IRequestsBecomeSeller IRequestsBecomeSeller, IRequestStatus IRequestStatus, EntityManager entityManager, EmailService emailService) {
        this.IRequestsBecomeSeller = IRequestsBecomeSeller;
        this.IRequestStatus = IRequestStatus;
        this.entityManager = entityManager;
        this.emailService = emailService;
    }

    @Transactional
    public Boolean existsRequestBecomeSellerByUserEmail(String email) {
        Long exists = IRequestsBecomeSeller.existsRequestBecomeSellerByUserEmail(email);
        return exists == 1;
    }

    @Transactional
    public Page<IRequestsBecomeSeller.RequestBecomeSellerProjection> getPageRequestBecomeSellerByUserEmail(String email, Pageable pageable) {
        return IRequestsBecomeSeller.findByUserEmail(email, pageable);
    }

    @Transactional
    public void putRequestBecomeSeller(UUID requestId, String status, String rejectionReason){
        if(!rejectionReason.isEmpty() && !rejectionReason.matches(RegexPatterns.REJECTION_REASON_REGEX)) {
            throw new CustomException("requestBecomeSeller.rejectionReason.invalid");
        }

        IRequestsBecomeSeller.updateRequestBecomeSeller(requestId, status, rejectionReason);
        Optional<BeanRequestsBecomeSeller> requestOptional = IRequestsBecomeSeller.findById(requestId);
        if (status.equals("Aprobado")) {
            if (requestOptional.isPresent()) {
                BeanRequestsBecomeSeller request = requestOptional.get();
                BeanPerson person = IRequestsBecomeSeller.findPersonByRequestId(requestId);
                if (person != null) {
                    IRequestsBecomeSeller.insertSellerRole(requestId);
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
                                userSellerInformation.getCurp() != null) {

                            sellerInformation.setTaxIdentificationNumber(userSellerInformation.getTaxIdentificationNumber());
                            sellerInformation.setSecondaryPhoneNumber(userSellerInformation.getSecondaryPhoneNumber());
                            sellerInformation.setPrivacyPolicyAgreement(userSellerInformation.getPrivacyPolicyAgreement());
                            sellerInformation.setImageIdentification(userSellerInformation.getImageIdentification());
                            sellerInformation.setCurp(userSellerInformation.getCurp());
                            sellerInformation.setPerson(person);
                            sellerInformation.setBlockSell(false);


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
                emailService.sendEmail(request.getUser().getEmail(),
                        "Solicitud de vendedor aprobada",
                        "Solicitud de vendedor aprobada",
                        rejectionReason,
                        "");
            }
        } else {
            requestOptional.ifPresent(beanRequestsBecomeSeller -> emailService.sendEmail(beanRequestsBecomeSeller.getUser().getEmail(),
                    "Solicitud de vendedor rechazada",
                    "Solicitud de vendedor rechazada",
                    "Un administrador ha revisado tu solicitud y ha decidido rechazarla por la siguiente razón: " + rejectionReason,
                    ""));
        }
    }




    @Transactional
    public void postRequestBecomeSeller(String email, UserSellerInformation userSellerInformation) {
        if (userSellerInformation == null ||
                StringUtils.isEmpty(userSellerInformation.getTaxIdentificationNumber()) ||
                StringUtils.isEmpty(userSellerInformation.getSecondaryPhoneNumber()) ||
                (userSellerInformation.getPrivacyPolicyAgreement() == null || !userSellerInformation.getPrivacyPolicyAgreement()) ||
                StringUtils.isEmpty(userSellerInformation.getImageIdentification()) ||
                StringUtils.isEmpty(userSellerInformation.getCurp())) {
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

    @Transactional
    public Page<IRequestsBecomeSeller.RequestBecomeSellerProjection> getPageRequestBecomeSeller(Pageable pageable) {
        return IRequestsBecomeSeller.findAllStatusesWithDetails(pageable);
    }

    @Transactional
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
        dto.setPersonSecondLastName(request.getUser().getPerson().getSecondLastName());
        dto.setPhoneNumber(request.getUser().getPerson().getPhoneNumber());
        dto.setAddress(request.getUser().getPerson().getAddresses().get(0).getAddress() + ", " +
                request.getUser().getPerson().getAddresses().get(0).getStreet() + ", " +
                request.getUser().getPerson().getAddresses().get(0).getNeighborhood() + ", " +
                request.getUser().getPerson().getAddresses().get(0).getPostalCode() + ", " +
                request.getUser().getPerson().getAddresses().get(0).getState());
        dto.setPicture(request.getUser().getPerson().getPicture());
        dto.setStatusId(request.getStatus().getIdStatus());
        dto.setStatus(request.getStatus().getStatus());
        dto.setUserEmail(request.getUser().getEmail());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            dto.setUserSellerInformation(objectMapper.readValue(request.getUserSellerInformation(), UserSellerInformation.class));
        } catch (Exception e) {
            throw new CustomException("requestBecomeSeller.JSON.invalid");
        }

        return dto;
    }
}
