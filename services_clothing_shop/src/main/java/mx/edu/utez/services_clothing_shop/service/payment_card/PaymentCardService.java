package mx.edu.utez.services_clothing_shop.service.payment_card;


import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.model.card_status.BeanCardStatus;
import mx.edu.utez.services_clothing_shop.model.card_status.ICardStatus;
import mx.edu.utez.services_clothing_shop.model.payment_card.BeanPaymentCard;
import mx.edu.utez.services_clothing_shop.model.payment_card.IPaymentCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class PaymentCardService {
    private final IPaymentCard paymentCardRepository;
    private final ICardStatus cardStatusRepository;

    public PaymentCardService(IPaymentCard paymentCardRepository, ICardStatus cardStatusRepository) {
        this.paymentCardRepository = paymentCardRepository;
        this.cardStatusRepository = cardStatusRepository;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Page<BeanPaymentCard> getPaymentCardByUserEmail(String email, Pageable page) {
        return paymentCardRepository.findAllByUser_EmailAndStatusIsNotNull(email, page);
    }

    @Transactional(rollbackOn = {Exception.class})
    public BeanPaymentCard postPaymentCard(BeanPaymentCard paymentCard, Integer count) {
        BeanCardStatus cardStatus;
        if (count == 0) {
            cardStatus = cardStatusRepository.findByStatus("Predeterminada");
        } else {
            cardStatus = cardStatusRepository.findByStatus("Habilitada");
        }
        paymentCard.setStatus(cardStatus);
        return paymentCardRepository.saveAndFlush(paymentCard);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Map<String, Object> putPaymentCardStatus(UUID idCard, String status) {
        return paymentCardRepository.putPaymentCardStatus(idCard.toString(), status);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deletePaymentCard(UUID idCard) {
        BeanPaymentCard paymentCard = paymentCardRepository.findById(idCard).orElse(null);
        if (paymentCard != null) {
            paymentCard.setCardNumber(null);
            paymentCard.setCardholderName(null);
            paymentCard.setExpirationDate(null);
            paymentCard.setCvv(null);
            paymentCard.setStatus(null);
            paymentCardRepository.save(paymentCard);
        } else {
            throw new RuntimeException("Tarjeta de crédito no encontrada");
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public int countPaymentCardByUserEmail(String email) {
        return paymentCardRepository.countByUser_Email(email);
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean cardIsRegistered(String cardNumber, String email) {
        return paymentCardRepository.existsByCardNumberAndUser_Email(cardNumber, email);
    }
}
