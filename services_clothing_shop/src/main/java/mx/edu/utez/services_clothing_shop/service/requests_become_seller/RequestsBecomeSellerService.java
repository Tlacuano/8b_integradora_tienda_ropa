package mx.edu.utez.services_clothing_shop.service.requests_become_seller;


import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto.RequestsBecomeSellerDTO;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.BeanRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.IRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;

import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public RequestsBecomeSellerDTO putRequestBecomeSeller(UUID requestId, String status, String rejectionReason) {
        Optional<BeanRequestsBecomeSeller> existingRequestOptional = IRequestsBecomeSeller.findById(requestId);
        if (existingRequestOptional.isPresent()) {
            BeanRequestsBecomeSeller existingRequest = existingRequestOptional.get();
            BeanRequestStatus requestStatus = IRequestStatus.findByStatus(status)
                    .orElseThrow(() -> new CustomException("requestBecomeSeller.status.notnull"));

            existingRequest.setStatus(requestStatus);
            existingRequest.setRejectionReason(rejectionReason);
            BeanRequestsBecomeSeller updatedRequest = IRequestsBecomeSeller.save(existingRequest);

            return convertToDTO(updatedRequest);
        } else {
            throw new CustomException("requestBecomeSeller.status.invalid");
        }
    }

    public Optional<RequestsBecomeSellerDTO> getRequestBecomeSellerByEmail(String email) {
        Optional<BeanRequestsBecomeSeller> requestOptional = IRequestsBecomeSeller.findByUserEmail(email);
        return requestOptional.map(this::convertToDTO);
    }

    @Transactional
    public RequestsBecomeSellerDTO postRequestBecomeSeller(String email) {
        Optional<BeanRequestsBecomeSeller> existingRequestOptional = IRequestsBecomeSeller.findByUserEmail(email);
        if (existingRequestOptional.isPresent()) {
            BeanRequestsBecomeSeller existingRequest = existingRequestOptional.get();
            BeanUser user = existingRequest.getUser();
            if (user == null) {
                throw new CustomException("requestBecomeSeller.email.invalid");
            }
            BeanRequestsBecomeSeller request = new BeanRequestsBecomeSeller();
            request.setUser(user);

            Optional<BeanRequestStatus> pendingStatusOptional = IRequestStatus.findByStatus("Pendiente");
            BeanRequestStatus pendingStatus = pendingStatusOptional.orElseThrow(() -> new IllegalStateException("requestBecomeSeller.status.invalid"));

            request.setStatus(pendingStatus);

            BeanRequestsBecomeSeller savedRequest = IRequestsBecomeSeller.save(request);

            return convertToDTO(savedRequest);
        } else {
            throw new CustomException("requestBecomeSeller.request.notFound");
        }
    }


    public Page<IRequestsBecomeSeller.StatusProjection> getPageRequestBecomeSeller(Pageable pageable) {
        return IRequestsBecomeSeller.findAllStatuses(pageable);
    }

    private RequestsBecomeSellerDTO convertToDTO(BeanRequestsBecomeSeller request) {
        RequestsBecomeSellerDTO dto = new RequestsBecomeSellerDTO();
        dto.setIdRequestBecomeSeller(request.getIdRequestBecomeSeller());
        dto.setRejectionReason(request.getRejectionReason());
        dto.setUserId(request.getUser().getIdUser());
        dto.setStatusId(request.getStatus().getIdStatus());
        return dto;
    }
}
