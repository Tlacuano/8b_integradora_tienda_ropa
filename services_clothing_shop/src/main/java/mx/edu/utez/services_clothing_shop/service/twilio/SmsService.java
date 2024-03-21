package mx.edu.utez.services_clothing_shop.service.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import mx.edu.utez.services_clothing_shop.controller.twilio.dto.sendSmsDTO;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.service.user.UserService;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import mx.edu.utez.services_clothing_shop.utils.security.SecurityCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class SmsService {

    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    @Value("${twilio.phone_number}")
    private String phoneNumber;

    private final IUser userRepository;

    public SmsService(IUser userRepository) {
        this.userRepository = userRepository;
    }


    public boolean sendSms(sendSmsDTO payload) {
        try {
            BeanUser user = userRepository.findByEmail(payload.getEmail());

            if (user == null) {
                throw new CustomException("twilio.service.error");
            }

            //generar numero random seguro de 6 digitos
            String code = SecurityCode.generateCode();

            user.setVerificationCode(code);
            userRepository.saveAndFlush(user);

            //generar mensaje
            String message = "Tu código de verificación es: " + code;


            Twilio.init(accountSid, authToken);

            Message.creator(
                    new PhoneNumber(payload.getTo()),
                    new PhoneNumber(phoneNumber),
                    message

            ).create();

            return true;
        }catch (Exception e){
            throw new CustomException("twilio.service.error");
        }

    }
}
