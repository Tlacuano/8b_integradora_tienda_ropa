package mx.edu.utez.services_clothing_shop.service.payment_card;


import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.model.payment_card.BeanPaymentCard;
import mx.edu.utez.services_clothing_shop.model.payment_card.IPaymentCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Logger;

@Service
public class PaymentCardService {
    @Autowired
    private IPaymentCard paymentCardRepository;

    @Transactional(rollbackOn = {Exception.class})
    public Page<BeanPaymentCard> getPaymentCardByUserEmail(String email, Pageable page) {
        return paymentCardRepository.findAllByUser_Email(email, page);
    }

    @Transactional(rollbackOn = {Exception.class})
    public BeanPaymentCard savePaymentCard(BeanPaymentCard paymentCard) {
        return paymentCardRepository.saveAndFlush(paymentCard);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deletePaymentCard(String cardNumber, String email) {
        paymentCardRepository.deleteByCardNumberAndUser_Email(cardNumber, email);
    }

    @Transactional(rollbackOn = {Exception.class})
    public int countPaymentCardByUserEmail(String email) {
        return paymentCardRepository.countByUser_Email(email);
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean cardIsFromUser(String cardNumber, String email) {
        return paymentCardRepository.existsByCardNumberAndUser_Email(cardNumber, email);
    }
}
