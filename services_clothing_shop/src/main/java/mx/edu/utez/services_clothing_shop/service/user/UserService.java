package mx.edu.utez.services_clothing_shop.service.user;

import jakarta.transaction.Transactional;

import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.model.user.projections.IGetPageUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UserService {
    @Autowired
    private IUser userRepository;

    //exist
    @Transactional(rollbackOn = {Exception.class})
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    //get
    @Transactional(rollbackOn = {Exception.class})
    public Page<IGetPageUsers> getPageUsers(Pageable pageable){
        return userRepository.findAllBy(pageable);
    }


    //post
    @Transactional(rollbackOn = {Exception.class})
    public BeanUser postAccount(BeanUser user){
        return userRepository.saveAndFlush(user);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void postRoleUser(String roleId, String userId){
        userRepository.postRoleUser(roleId, userId);
    }





}