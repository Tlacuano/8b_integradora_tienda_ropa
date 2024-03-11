package mx.edu.utez.services_clothing_shop.service.person;

import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.person.IPerson;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class PersonService {
    private final IPerson personRepository;

    public PersonService(IPerson personRepository) {
        this.personRepository = personRepository;
    }

    //get
    @Transactional(rollbackOn = {Exception.class})
    public BeanPerson getPersonalInformationById(UUID id) {
        return personRepository.findById(id).get();
    }


    //post
    @Transactional(rollbackOn = {Exception.class})
    public BeanPerson postPersonalInformation(BeanPerson person) {
        return personRepository.saveAndFlush(person);
    }
}
