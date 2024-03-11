package mx.edu.utez.services_clothing_shop.service.requests_become_seller;


import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto.RequestsBecomeSellerDTO;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.BeanRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.IRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public RequestsBecomeSellerDTO putRequestStatus(UUID requestId, String status, String rejectionReason) {
        Optional<BeanRequestsBecomeSeller> existingRequestOptional = IRequestsBecomeSeller.findById(requestId);
        if (existingRequestOptional.isPresent()) {
            BeanRequestsBecomeSeller existingRequest = existingRequestOptional.get();
            BeanRequestStatus requestStatus = IRequestStatus.findByStatus(status)
                    .orElseThrow(() -> new IllegalArgumentException("Estado no válido: " + status));

            existingRequest.setStatus(requestStatus);
            existingRequest.setRejectionReason(rejectionReason);
            BeanRequestsBecomeSeller updatedRequest = IRequestsBecomeSeller.save(existingRequest);

            return convertToDTO(updatedRequest);
        } else {
            throw new RequestsNotFoundException("La solicitud no fue encontrada.");
        }
    }

    public Optional<RequestsBecomeSellerDTO> getRequestByEmail(String email) {
        Optional<BeanRequestsBecomeSeller> requestOptional = IRequestsBecomeSeller.findByUserEmail(email);
        return requestOptional.map(this::convertToDTO);
    }

    @Transactional
    public RequestsBecomeSellerDTO postRequest(String email) {
        Optional<BeanRequestsBecomeSeller> existingRequestOptional = IRequestsBecomeSeller.findByUserEmail(email);
        if (existingRequestOptional.isPresent()) {
            BeanRequestsBecomeSeller existingRequest = existingRequestOptional.get();
            BeanUser user = existingRequest.getUser();
            if (user == null) {
                throw new UserNotFoundException("El usuario no fue encontrado.");
            }
            BeanRequestsBecomeSeller request = new BeanRequestsBecomeSeller();
            request.setUser(user);

            Optional<BeanRequestStatus> pendingStatusOptional = IRequestStatus.findByStatus("Pendiente");
            BeanRequestStatus pendingStatus = pendingStatusOptional.orElseThrow(() -> new IllegalStateException("El estado 'Pendiente' no se encontró en la base de datos"));

            request.setStatus(pendingStatus);

            BeanRequestsBecomeSeller savedRequest = IRequestsBecomeSeller.save(request);

            return convertToDTO(savedRequest);
        } else {
            throw new RequestsNotFoundException("La solicitud no fue encontrada.");
        }
    }


    public Page<RequestsBecomeSellerDTO> getAllRequests(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BeanRequestsBecomeSeller> pageResult = IRequestsBecomeSeller.findAll(pageable);

        return pageResult.map(this::convertToDTO);
    }

    private RequestsBecomeSellerDTO convertToDTO(BeanRequestsBecomeSeller request) {
        RequestsBecomeSellerDTO dto = new RequestsBecomeSellerDTO();
        dto.setIdRequestBecomeSeller(request.getIdRequestBecomeSeller());
        dto.setRejectionReason(request.getRejectionReason());
        dto.setUserId(request.getUser().getIdUser());
        dto.setStatusId(request.getStatus().getIdStatus());
        return dto;
    }

    public class RequestsNotFoundException extends RuntimeException {
        public RequestsNotFoundException(String message) {
            super(message);
        }
    }

    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
