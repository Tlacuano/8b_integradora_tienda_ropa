package mx.edu.utez.services_clothing_shop.service.payment_card;


import mx.edu.utez.services_clothing_shop.model.payment_card.BeanPaymentCard;
import mx.edu.utez.services_clothing_shop.model.payment_card.IPaymentCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentCardService {
    @Autowired
    private IPaymentCard paymentCardRepository;

    public Page<BeanPaymentCard> getPaymentCardByUserEmail(String email, Pageable page) {
        return paymentCardRepository.findAllByUser_Email(email, page);
    }

    public BeanPaymentCard savePaymentCard(BeanPaymentCard paymentCard) {
        return paymentCardRepository.saveAndFlush(paymentCard);
    }
}
