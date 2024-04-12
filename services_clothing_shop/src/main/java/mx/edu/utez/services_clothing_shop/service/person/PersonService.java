package mx.edu.utez.services_clothing_shop.service.person;


import mx.edu.utez.services_clothing_shop.controller.person.dto.RequestPutPersonalInformationDTO;
import mx.edu.utez.services_clothing_shop.controller.person.dto.RequestPutPictureDTO;
import mx.edu.utez.services_clothing_shop.controller.person.dto.ResponseGetPersonalInformationDTO;
import mx.edu.utez.services_clothing_shop.controller.twilio.dto.SendSmsDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestActionByEmailDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestCodeDTO;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.person.IPerson;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.service.email_service.EmailService;
import mx.edu.utez.services_clothing_shop.service.twilio.SmsService;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import mx.edu.utez.services_clothing_shop.utils.validations.ValidatesFunctions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonService {
    private final IPerson personRepository;
    private final IUser userRepository;
    private final SmsService smsService;
    private final EmailService emailService;
    private static final String USER_EMAIL_EXISTS = "user.email.exists";
    private static final String PERSON_NOT_FOUND = "person.not.found";

    public PersonService(IPerson personRepository, IUser userRepository, SmsService smsService, EmailService emailService) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.smsService = smsService;
        this.emailService = emailService;
    }

    @Transactional
    public ResponseGetPersonalInformationDTO getPersonalInformation(String email) {
        BeanUser user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException(USER_EMAIL_EXISTS);
        }

        BeanPerson person = personRepository.findByUser(user);
        if (person == null) {
            throw new CustomException(PERSON_NOT_FOUND);
        }

        return ResponseGetPersonalInformationDTO.fromPerson(person);
    }

    @Transactional
    public boolean postPersonalInformation(RequestPutPersonalInformationDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if (user == null) {
            throw new CustomException(USER_EMAIL_EXISTS);
        }

        if (!payload.isPrivacyPolicy()) {
            throw new CustomException("person.privacyPolicy.accepted");
        }

        BeanPerson newPersonalInformation = new BeanPerson();
        newPersonalInformation.setName(payload.getName());
        newPersonalInformation.setLastName(payload.getLastName());
        newPersonalInformation.setSecondLastName(payload.getSecondLastName());
        newPersonalInformation.setPhoneNumber(payload.getPhoneNumber());
        newPersonalInformation.setPicture(payload.getPicture());
        newPersonalInformation.setGender(payload.getGender());
        newPersonalInformation.setBirthday(payload.getBirthday());

        if (!ValidatesFunctions.isAdult(payload.getBirthday())) {
            throw new CustomException("person.birthday.age");
        }

        newPersonalInformation.setBirthday(payload.getBirthday());
        newPersonalInformation.setVerificationPhone(false);

        user.setPrivacyPolicy(payload.isPrivacyPolicy());
        userRepository.save(user);

        newPersonalInformation.setUser(user);
        personRepository.save(newPersonalInformation);

        SendSmsDTO sendSmsDTO = new SendSmsDTO();
        sendSmsDTO.setEmail(payload.getEmail());
        sendSmsDTO.setTo(payload.getPhoneNumber());

        smsService.sendSms(sendSmsDTO);

        return true;
    }

    @Transactional
    public boolean verifyPhone(RequestCodeDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if (user == null) {
            throw new CustomException(USER_EMAIL_EXISTS);
        }

        BeanPerson person = personRepository.findByUser(user);

        if (person == null) {
            throw new CustomException(PERSON_NOT_FOUND);
        }

        boolean result = payload.getCode().equals(user.getVerificationCode());
        if (result) {
            person.setVerificationPhone(true);
            personRepository.save(person);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean resendPhoneCode(RequestActionByEmailDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if (user == null) {
            throw new CustomException(USER_EMAIL_EXISTS);
        }

        BeanPerson person = personRepository.findByUser(user);

        if (person == null) {
            throw new CustomException(PERSON_NOT_FOUND);
        }

        SendSmsDTO sendSmsDTO = new SendSmsDTO();
        sendSmsDTO.setEmail(payload.getEmail());
        sendSmsDTO.setTo(person.getPhoneNumber());

        smsService.sendSms(sendSmsDTO);

        return true;
    }

    @Transactional
    public boolean deletePersonalInformationIncomplete(RequestActionByEmailDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if (user == null) {
            throw new CustomException(USER_EMAIL_EXISTS);
        }

        BeanPerson person = personRepository.findByUser(user);

        if (person == null) {
            throw new CustomException(PERSON_NOT_FOUND);
        }

        if (person.isVerificationPhone()) {
            throw new CustomException("person.phone.verified");
        }

        personRepository.delete(person);
        user.setPrivacyPolicy(false);

        userRepository.save(user);

        return true;
    }

    @Transactional
    public boolean putPersonalInformation(RequestPutPersonalInformationDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());
        if (user == null) {
            throw new CustomException(USER_EMAIL_EXISTS);
        }

        BeanPerson person = personRepository.findByUser(user);
        if (person == null) {
            throw new CustomException(PERSON_NOT_FOUND);
        }

        String phoneNumber = person.getPhoneNumber();

        person.setName(payload.getName());
        person.setLastName(payload.getLastName());
        person.setSecondLastName(payload.getSecondLastName());
        person.setPhoneNumber(payload.getPhoneNumber());
        person.setGender(payload.getGender());
        person.setBirthday(payload.getBirthday());

        if (!ValidatesFunctions.isAdult(payload.getBirthday())) {
            throw new CustomException("person.birthday.age");
        }

        boolean result = !phoneNumber.equals(payload.getPhoneNumber());

        if (result) {
            person.setVerificationPhone(false);
            SendSmsDTO sendSmsDTO = new SendSmsDTO();
            sendSmsDTO.setEmail(payload.getEmail());
            sendSmsDTO.setTo(payload.getPhoneNumber());
            smsService.sendSms(sendSmsDTO);
        }


        personRepository.save(person);

        emailService.sendEmail(payload.getEmail(), "Actualización de información", "Cambios en tu cuenta", "Tus datos han sido actualizados correctamente.", "");

        return true;
    }


    @Transactional
    public boolean putPicture(RequestPutPictureDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());
        if (user == null) {
            throw new CustomException(USER_EMAIL_EXISTS);
        }

        BeanPerson person = personRepository.findByUser(user);
        if (person == null) {
            throw new CustomException(PERSON_NOT_FOUND);
        }

        person.setPicture(payload.getPicture());
        personRepository.save(person);

        return true;
    }
}
