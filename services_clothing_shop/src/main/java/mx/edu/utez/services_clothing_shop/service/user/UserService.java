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

    //save new user
    @Transactional(rollbackOn = {Exception.class})
    public Page<IGetPageUsers> getPageUsers(Pageable pageable){
        return userRepository.findAllBy(pageable);
    }

}
