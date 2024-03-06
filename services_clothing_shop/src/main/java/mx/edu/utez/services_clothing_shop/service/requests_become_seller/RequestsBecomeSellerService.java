package mx.edu.utez.services_clothing_shop.service.requests_become_seller;


import mx.edu.utez.services_clothing_shop.model.request_become_seller.BeanRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.RequestsBecomeSellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public BeanRequestsBecomeSeller createRequest(BeanRequestsBecomeSeller request) {
        return requestsBecomeSellerRepository.save(request);
    }

    public BeanRequestsBecomeSeller updateRequest(UUID id, BeanRequestsBecomeSeller request) {
        if (requestsBecomeSellerRepository.existsById(id)) {
            return requestsBecomeSellerRepository.save(request);
        } else {
            throw new RequestsNotFoundException("La solicitud con el ID " + id + " no fue encontrada.");
        }
    }

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
