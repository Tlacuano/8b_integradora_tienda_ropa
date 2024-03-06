package mx.edu.utez.services_clothing_shop.service.requests_become_seller;


import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.BeanRequestsBecomeSeller;
import mx.edu.utez.services_clothing_shop.model.request_become_seller.IRequestsBecomeSeller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RequestsBecomeSellerService {
    private final IRequestsBecomeSeller IRequestsBecomeSeller;

    @Autowired
    public RequestsBecomeSellerService(IRequestsBecomeSeller IRequestsBecomeSeller) {
        this.IRequestsBecomeSeller = IRequestsBecomeSeller;
    }


    public Optional<BeanRequestsBecomeSeller> getRequestById(UUID id) {
        return IRequestsBecomeSeller.findById(id);
    }

    @Transactional
    public BeanRequestsBecomeSeller postRequest(BeanRequestsBecomeSeller request) {
        return IRequestsBecomeSeller.save(request);
    }

    @Transactional
    public BeanRequestsBecomeSeller putRequest(UUID id, BeanRequestsBecomeSeller request) {
        if (IRequestsBecomeSeller.existsById(id)) {
            return IRequestsBecomeSeller.save(request);
        } else {
            throw new RequestsNotFoundException("La solicitud no fue encontrada.");
        }
    }

    @Transactional
    public void deleteRequest(UUID id) {
        IRequestsBecomeSeller.deleteById(id);
    }

    public Page<BeanRequestsBecomeSeller> getAllRequestsBecomeSeller(Pageable pageable) {
        return IRequestsBecomeSeller.findAll(pageable);
    }

    public class RequestsNotFoundException extends RuntimeException {
        public RequestsNotFoundException(String message) {
            super(message);
        }
    }

}
