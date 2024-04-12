package mx.edu.utez.services_clothing_shop.service.requests_become_seller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto.RequestBecomeSellerGetByIdResponseDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto.UserSellerInformation;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.BeanRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.IRequestsBecomeSeller;

import mx.edu.utez.services_clothing_shop.model.seller_information.BeanSellerInformation;
import mx.edu.utez.services_clothing_shop.service.email_service.EmailService;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class RequestsBecomeSellerService {

    private final IRequestsBecomeSeller requestBecomeSellerRepository;
    private final EntityManager entityManager;
    private final EmailService emailService;

    public RequestsBecomeSellerService(IRequestsBecomeSeller requestBecomeSellerRepository, EntityManager entityManager, EmailService emailService) {
        this.requestBecomeSellerRepository = requestBecomeSellerRepository;
        this.entityManager = entityManager;
        this.emailService = emailService;
    }

    @Transactional
    public Boolean existsRequestBecomeSellerByUserEmail(String email) {
        Long exists = requestBecomeSellerRepository.existsRequestBecomeSellerByUserEmail(email);
        return exists == 1;
    }

    @Transactional
    public Page<IRequestsBecomeSeller.RequestBecomeSellerProjection> getPageRequestBecomeSellerByUserEmail(String email, Pageable pageable) {
        return requestBecomeSellerRepository.findByUserEmail(email, pageable);
    }

    @Transactional
    public void putRequestBecomeSeller(UUID requestId, String status, String rejectionReason) {
        try {

            if (!rejectionReason.isEmpty() && !rejectionReason.matches(RegexPatterns.REJECTION_REASON_REGEX)) {
                throw new CustomException("requestBecomeSeller.rejectionReason.invalid");
            }

            requestBecomeSellerRepository.updateRequestBecomeSeller(requestId, status, rejectionReason);
            Optional<BeanRequestsBecomeSeller> requestOptional = requestBecomeSellerRepository.findById(requestId);

            if (!status.equals("Aprobado")) {
                requestOptional.ifPresent(beanRequestsBecomeSeller -> emailService.sendEmail(beanRequestsBecomeSeller.getUser().getEmail(),
                        "Solicitud de vendedor rechazada",
                        "Solicitud de vendedor rechazada",
                        "Un administrador ha revisado tu solicitud y ha decidido rechazarla por la siguiente razón: " + rejectionReason,
                        ""));
            }

            BeanRequestsBecomeSeller request = requestOptional.get();
            BeanPerson person = requestBecomeSellerRepository.findPersonByRequestId(requestId);

            if (person == null) {
                throw new CustomException("requestBecomeSeller.person.notFound");
            }

            requestBecomeSellerRepository.insertSellerRole(requestId);
            BeanSellerInformation sellerInformation = new BeanSellerInformation();
            String userSellerInformationJSON = request.getUserSellerInformation();
            ObjectMapper objectMapper = new ObjectMapper();
            UserSellerInformation userSellerInformation = objectMapper.readValue(userSellerInformationJSON, UserSellerInformation.class);

            if (userSellerInformation == null ||
                    userSellerInformation.getTaxIdentificationNumber() == null ||
                    userSellerInformation.getSecondaryPhoneNumber() == null ||
                    userSellerInformation.getPrivacyPolicyAgreement() == null ||
                    userSellerInformation.getImageIdentification() == null ||
                    userSellerInformation.getCurp() == null) {
                throw new CustomException("requestBecomeSeller.userSellerInformation.empty");
            }

            sellerInformation.setTaxIdentificationNumber(userSellerInformation.getTaxIdentificationNumber());
            sellerInformation.setSecondaryPhoneNumber(userSellerInformation.getSecondaryPhoneNumber());
            sellerInformation.setPrivacyPolicyAgreement(userSellerInformation.getPrivacyPolicyAgreement());
            sellerInformation.setImageIdentification(userSellerInformation.getImageIdentification());
            sellerInformation.setCurp(userSellerInformation.getCurp());
            sellerInformation.setPerson(person);
            sellerInformation.setBlockSell(false);

            entityManager.persist(sellerInformation);
            entityManager.flush();
        } catch (Exception e) {
            throw new CustomException("requestBecomeSeller.put.error");
        }
    }

    @Transactional
    public void postRequestBecomeSeller(String email, UserSellerInformation userSellerInformation) {
        if (userSellerInformation == null ||
                !StringUtils.hasText(userSellerInformation.getTaxIdentificationNumber()) ||
                !StringUtils.hasText(userSellerInformation.getSecondaryPhoneNumber()) ||
                (userSellerInformation.getPrivacyPolicyAgreement() == null || !userSellerInformation.getPrivacyPolicyAgreement()) ||
                !StringUtils.hasText(userSellerInformation.getImageIdentification()) ||
                !StringUtils.hasText(userSellerInformation.getCurp())) {
            throw new CustomException("requestBecomeSeller.userSellerInformation.empty");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String userSellerInformationJSON;
        try {
            userSellerInformationJSON = objectMapper.writeValueAsString(userSellerInformation);
        } catch (Exception e) {
            throw new CustomException("requestBecomeSeller.JSON.invalid");
        }
        requestBecomeSellerRepository.insertRequestBecomeSeller(email, userSellerInformationJSON);
    }

    @Transactional
    public Page<IRequestsBecomeSeller.RequestBecomeSellerProjection> getPageRequestBecomeSeller(Pageable pageable) {
        return requestBecomeSellerRepository.findAllStatusesWithDetails(pageable);
    }

    @Transactional
    public Optional<RequestBecomeSellerGetByIdResponseDTO> getRequestBecomeSellerById(UUID requestId) {
        Optional<BeanRequestsBecomeSeller> requestOptional = requestBecomeSellerRepository.findById(requestId);
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
