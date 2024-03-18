package mx.edu.utez.services_clothing_shop.service.user;


import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestActionByEmailDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestPostAccountDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.ResponsePageUsersDTO;
import mx.edu.utez.services_clothing_shop.model.role.BeanRole;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.person.IPerson;
import mx.edu.utez.services_clothing_shop.model.role.IRole;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.utils.validations.ValidatesFunctions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {
    private final IUser userRepository;
    private final IPerson personRepository;

    private final IRole roleRepository;

    public UserService(IUser userRepository, IPerson personRepository, IRole roleRepository) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
    }

    //exist
    @Transactional
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    //get
    @Transactional
    public Page<ResponsePageUsersDTO> getPageUsers(Pageable pageable){
        Page<BeanUser> users = userRepository.findAllByOrderByStatusDesc(pageable);
        return users.map(ResponsePageUsersDTO::fromUser);
    }


    @Transactional
    public BeanUser getByEmail(String email){
        BeanUser user = userRepository.findByEmail(email);
        if(user == null){
            throw new CustomException("user.email.exists");
        }
        return user;
    }


    //post
    @Transactional
    public String postAccount(RequestPostAccountDTO user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new CustomException("user.email.exists");
        }

        BeanUser newUser = new BeanUser();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setStatus(true);

        newUser = userRepository.saveAndFlush(newUser);

        //assign role
        BeanRole role = roleRepository.findByRoleName(user.getRoleToAssign());
        userRepository.postRoleUser(role.getIdRole().toString(), newUser.getIdUser().toString());

        //assign personal information
        BeanPerson personalInformation = new BeanPerson();
        personalInformation.setName(user.getName());
        personalInformation.setLastName(user.getLastName());
        personalInformation.setSecondLastName(user.getSecondLastName());
        personalInformation.setPhoneNumber(user.getPhoneNumber());
        personalInformation.setPicture(user.getPicture());
        personalInformation.setGender(user.getGender());
        personalInformation.setUser(newUser);

        if(!ValidatesFunctions.isAdult(user.getBirthday())){
            throw new CustomException("person.birthday.age");
        }
        personalInformation.setBirthday(user.getBirthday());

        personRepository.saveAndFlush(personalInformation);

        return "Cuenta registrada correctamente";
    }

    @Transactional
    public boolean changeStatusAccount(RequestActionByEmailDTO payload){
        BeanUser user = userRepository.findByEmail(payload.getEmail());
        if(user == null){
            throw new CustomException("user.email.not.found");
        }
        user.setStatus(!user.isStatus());
        userRepository.saveAndFlush(user);
        return user.isStatus();
    }



    @Transactional
    public void postRoleUser(String roleId, String userId){
        userRepository.postRoleUser(roleId, userId);
    }

    @Transactional
    public void deleteAccount(String email){
        userRepository.deleteAccount(email);
    }




}
