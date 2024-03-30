package mx.edu.utez.services_clothing_shop.service.user;


import mx.edu.utez.services_clothing_shop.controller.user.dto.*;
import mx.edu.utez.services_clothing_shop.model.role.BeanRole;
import mx.edu.utez.services_clothing_shop.model.user_roles.IUserRoles;
import mx.edu.utez.services_clothing_shop.service.email_service.EmailService;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import mx.edu.utez.services_clothing_shop.model.role.IRole;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.utils.security.SecurityCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {
    private final IUser userRepository;
    private final IRole roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final IUserRoles userRolesRepository;

    public UserService(IUser userRepository, IRole roleRepository, PasswordEncoder passwordEncoder, EmailService emailService, IUserRoles userRolesRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.userRolesRepository = userRolesRepository;
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

    @Transactional
    public ResponseGetUserDetailsByEmailAdminDTO getUserDetailsByEmailAdmin(String email){
        BeanUser user = userRepository.findByEmail(email);
        if(user == null){
            throw new CustomException("user.email.exists");
        }

        return ResponseGetUserDetailsByEmailAdminDTO.fromUser(user);
    }

    //post
    @Transactional
    public String postAccount(RequestPostAccountDTO user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new CustomException("user.email.exists");
        }

        BeanUser newUser = new BeanUser();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setStatus(true);

        newUser = userRepository.saveAndFlush(newUser);

        //assign role
        BeanRole role = roleRepository.findByRoleName(user.getRoleToAssign());
        userRepository.postRoleUser(role.getIdRole().toString(), newUser.getIdUser().toString());

        //send code to email
        String code = SecurityCode.generateCode();
        emailService.sendEmail(user.getEmail(), "Código de verificación", "Código de verificación", "Su código de verificación es: ", code);

        System.out.println(newUser.getIdUser().toString());

        newUser.setVerificationCode(code);
        userRepository.save(newUser);

        return newUser.getIdUser().toString();
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


    @Transactional
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public boolean verifyCode(RequestCodeDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());
        if(user == null){
            throw new CustomException("user.email.not.found");
        }

        boolean result = user.getVerificationCode().equals(payload.getCode());

        if(result){
            user.setEmailVerified(true);
            userRepository.save(user);
        }

        return result;
    }

    @Transactional
    public ResponseGetProfileDTO getProfile(String email) {
        BeanUser user = userRepository.findByEmail(email);
        if(user == null){
            throw new CustomException("user.email.not.found");
        }

        return ResponseGetProfileDTO.fromUser(user);
    }

    @Transactional
    public boolean resendEmailCode(String email) {
        BeanUser user = userRepository.findByEmail(email);

        if(user == null){
            throw new CustomException("user.email.not.found");
        }

        String code = SecurityCode.generateCode();
        emailService.sendEmail(email, "Código de verificación", "Código de verificación", "Su código de verificación es: ", code);

        user.setVerificationCode(code);
        userRepository.save(user);

        return true;
    }

    @Transactional
    public boolean deleteIncompleteAccount(String email) {
        BeanUser user = userRepository.findByEmail(email);
        if(user == null){
            throw new CustomException("user.email.not.found");
        }

        if(user.isPrivacyPolicy()){
            throw new CustomException("user.privacy.policy.accepted");
        }
        userRolesRepository.deleteByUser(user);
        userRepository.delete(user);
        return true;
    }



}
