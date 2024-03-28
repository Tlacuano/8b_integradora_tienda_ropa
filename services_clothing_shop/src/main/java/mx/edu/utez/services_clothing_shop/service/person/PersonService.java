package mx.edu.utez.services_clothing_shop.service.person;


import mx.edu.utez.services_clothing_shop.controller.person.dto.RequestPutPersonalInformationDTO;
import mx.edu.utez.services_clothing_shop.controller.twilio.dto.SendSmsDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestCodeDTO;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.person.IPerson;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.service.twilio.SmsService;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import mx.edu.utez.services_clothing_shop.utils.validations.ValidatesFunctions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class PersonService {
    private final IPerson personRepository;
    private final IUser userRepository;
    private final SmsService smsService;

    public PersonService(IPerson personRepository, IUser userRepository, SmsService smsService) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.smsService = smsService;
    }

    //get
    @Transactional
    public BeanPerson getPersonalInformationById(UUID id) {
        return personRepository.findById(id).get();
    }


    //post
    @Transactional
    public boolean postPersonalInformation(RequestPutPersonalInformationDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if(user == null){
            throw new CustomException("user.email.exists");
        }

        if(!payload.isPrivacyPolicy()){
            throw new CustomException("person.privacyPolicy.accepted");
        }

        BeanPerson newPersonalInformation = new BeanPerson();
        newPersonalInformation.setIdPerson(payload.getIdPerson());
        newPersonalInformation.setName(payload.getName());
        newPersonalInformation.setLastName(payload.getLastName());
        newPersonalInformation.setSecondLastName(payload.getSecondLastName());
        newPersonalInformation.setPhoneNumber(payload.getPhoneNumber());
        newPersonalInformation.setPicture(payload.getPicture());
        newPersonalInformation.setGender(payload.getGender());
        newPersonalInformation.setBirthday(payload.getBirthday());

        if(!ValidatesFunctions.isAdult(payload.getBirthday())){
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
    public Object verifyPhone(RequestCodeDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if(user == null){
            throw new CustomException("user.email.exists");
        }

        BeanPerson person = personRepository.findByUser(user);

        if(person == null){
            throw new CustomException("person.not.found");
        }

        boolean result = payload.getCode().equals(user.getVerificationCode());
        if(result){
            person.setVerificationPhone(true);
            personRepository.save(person);
            return true;
        }

        return false;
    }
}
