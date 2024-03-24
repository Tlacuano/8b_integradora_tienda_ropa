package mx.edu.utez.services_clothing_shop.service.requests_become_seller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto.RequestsBecomeSellerResponseDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto.UserSellerInformation;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.BeanRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.IRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;

import mx.edu.utez.services_clothing_shop.utils.RegexPatterns;
import mx.edu.utez.services_clothing_shop.utils.exeption.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestsBecomeSellerService {

    private final IRequestsBecomeSeller IRequestsBecomeSeller;
    private final IRequestStatus IRequestStatus;

    public RequestsBecomeSellerService(IRequestsBecomeSeller IRequestsBecomeSeller, IRequestStatus IRequestStatus) {
        this.IRequestsBecomeSeller = IRequestsBecomeSeller;
        this.IRequestStatus = IRequestStatus;
    }

    @Transactional
    public void putRequestBecomeSeller(UUID requestId, String status, String rejectionReason){
        if(!rejectionReason.isEmpty() && !rejectionReason.matches(RegexPatterns.REJECTION_REASON_REGEX)) {
            throw new CustomException("requestBecomeSeller.rejectionReason.invalid");
        }
        IRequestsBecomeSeller.updateRequestBecomeSeller(requestId, status, rejectionReason);
    }

    public Optional<RequestsBecomeSellerResponseDTO> getRequestBecomeSellerByEmail(String email) {
        Optional<BeanRequestsBecomeSeller> requestOptional = IRequestsBecomeSeller.findByUserEmail(email);
        return requestOptional.map(this::convertToDTO);
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


    public Page<IRequestsBecomeSeller.StatusProjection> getPageRequestBecomeSeller(Pageable pageable) {
        return IRequestsBecomeSeller.findAllStatuses(pageable);
    }

    private RequestsBecomeSellerResponseDTO convertToDTO(BeanRequestsBecomeSeller request) {
        RequestsBecomeSellerResponseDTO dto = new RequestsBecomeSellerResponseDTO();
        dto.setIdRequestBecomeSeller(request.getIdRequestBecomeSeller());
        dto.setRejectionReason(request.getRejectionReason());
        dto.setUserId(request.getUser().getIdUser());
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
