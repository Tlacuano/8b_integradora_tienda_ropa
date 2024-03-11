package mx.edu.utez.services_clothing_shop.service.requests_become_seller;


import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.BeanRequestsBecomeSeller;
<<<<<<< Updated upstream
import mx.edu.utez.services_clothing_shop.model.request_become_seller.RequestsBecomeSellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
=======
import mx.edu.utez.services_clothing_shop.model.request_become_seller.IRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_status.BeanRequestStatus;
import mx.edu.utez.services_clothing_shop.model.request_status.IRequestStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
>>>>>>> Stashed changes
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestsBecomeSellerService {
    private final RequestsBecomeSellerRepository requestsBecomeSellerRepository;

    @Autowired
    public RequestsBecomeSellerService(RequestsBecomeSellerRepository requestsBecomeSellerRepository) {
        this.requestsBecomeSellerRepository = requestsBecomeSellerRepository;
    }


    public Optional<BeanRequestsBecomeSeller> getRequestById(UUID id) {
        return requestsBecomeSellerRepository.findById(id);
    }

    @Transactional
    public BeanRequestsBecomeSeller postRequest(BeanRequestsBecomeSeller request) {
        return requestsBecomeSellerRepository.save(request);
    }

    @Transactional
    public BeanRequestsBecomeSeller putRequest(UUID id, BeanRequestsBecomeSeller request) {
        if (requestsBecomeSellerRepository.existsById(id)) {
            return requestsBecomeSellerRepository.save(request);
        } else {
            throw new RequestsNotFoundException("La solicitud no fue encontrada.");
        }
    }

    @Transactional
    public void deleteRequest(UUID id) {
        requestsBecomeSellerRepository.deleteById(id);
    }

    public Page<BeanRequestsBecomeSeller> getAllRequestsBecomeSeller(Pageable pageable) {
        return requestsBecomeSellerRepository.findAll(pageable);
    }

    public class RequestsNotFoundException extends RuntimeException {
        public RequestsNotFoundException(String message) {
            super(message);
        }
    }

}
