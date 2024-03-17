package mx.edu.utez.services_clothing_shop.service.person;

import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.person.dto.RequestPutPersonalInformationDTO;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.person.IPerson;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import mx.edu.utez.services_clothing_shop.utils.validations.ValidatesFunctions;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class PersonService {
    private final IPerson personRepository;
    private final IUser userRepository;

    public PersonService(IPerson personRepository, IUser userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    //get
    @Transactional(rollbackOn = {Exception.class})
    public BeanPerson getPersonalInformationById(UUID id) {
        return personRepository.findById(id).get();
    }


    //post
    @Transactional(rollbackOn = {Exception.class})
    public BeanPerson postPersonalInformation(RequestPutPersonalInformationDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if(user == null){
            throw new CustomException("user.email.exists");
        }

        BeanPerson newPersonalInformation = new BeanPerson();
        newPersonalInformation.setIdPerson(user.getIdUser());
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
        newPersonalInformation.setUser(user);

        return personRepository.saveAndFlush(newPersonalInformation);
    }
}
