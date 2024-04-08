package mx.edu.utez.services_clothing_shop.service.user;


import mx.edu.utez.services_clothing_shop.controller.user.dto.*;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.order_has_products.IOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.order_status.IOrderStatus;
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

import java.util.List;

@Transactional
@Service
public class UserService {
    private final IUser userRepository;
    private final IRole roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final IUserRoles userRolesRepository;
    private final IOrderHasProducts orderHasProductsRepository;

    public UserService(IUser userRepository, IRole roleRepository, PasswordEncoder passwordEncoder, EmailService emailService, IUserRoles userRolesRepository, IOrderHasProducts orderHasProductsRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.userRolesRepository = userRolesRepository;
        this.orderHasProductsRepository = orderHasProductsRepository;
    }


    //get
    @Transactional
    public Page<ResponsePageUsersDTO> getPageUsers(Pageable pageable){
        Page<BeanUser> users = userRepository.findAllByOrderByStatusDesc(pageable);
        return users.map(ResponsePageUsersDTO::fromUser);
    }

    @Transactional
    public Page<ResponsePageUsersDTO> getPageAdmins(Pageable pageable){
        Page<BeanUser> users = userRepository.findAllAdminsByOrderByStatusDesc(pageable);
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

        List<BeanOrderHasProducts> orders = orderHasProductsRepository.findBySeller(payload.getEmail());
        boolean hasOrderPending = false;

        for (BeanOrderHasProducts order : orders) {
            if(order.getStatus().getStatus().equals("Preparación") || order.getStatus().getStatus().equals("Enviado")){
                hasOrderPending = true;
                break;
            }
        }

        if(hasOrderPending){
            throw new CustomException("user.order.pending");
        }


        user.setStatus(!user.isStatus());

        userRepository.saveAndFlush(user);

        emailService.sendEmail(user.getEmail(),
                "Cuanta "+(user.isStatus()?"activada":"desactivada"),
                "Cambios en tu cuenta",
                "Tu cuenta ha sido "+(user.isStatus()?"activada":"desactivada")+" por un administrador",
                "");

        return user.isStatus();
    }

    @Transactional
    public void postRoleUser(String roleId, String userId){
        userRepository.postRoleUser(roleId, userId);
    }

    @Transactional
    public void deleteAccount(RequestRestorePasswordDTO payload){
        BeanUser user = userRepository.findByEmail(payload.getEmail());
        if(user == null){
            throw new CustomException("user.email.not.found");
        }

        if(!passwordEncoder.matches(payload.getPassword(), user.getPassword())){
            throw new CustomException("user.password.incorrect");
        }

        List<BeanOrderHasProducts> orders = orderHasProductsRepository.findBySeller(payload.getEmail());
        boolean hasOrderPending = false;

        for (BeanOrderHasProducts order : orders) {
            if(order.getStatus().getStatus().equals("Preparación") || order.getStatus().getStatus().equals("Enviado")){
                hasOrderPending = true;
                break;
            }
        }

        if(hasOrderPending){
            throw new CustomException("user.own.order.pending");
        }



        userRepository.deleteAccount(payload.getEmail());

        emailService.sendEmail(user.getEmail(),
                "Cuenta eliminada",
                "Lamentamos que te vayas",
                "Tu cuenta ha sido eliminada exitosamente",
                "");
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

    @Transactional
    public Page<ResponsePageUsersDTO> getPageUserByEmail(String email,Pageable pageable){
        String emailModified = "%" + email + "%";
        Page<BeanUser> users = userRepository.findAllByEmailLikeIgnoreCase(emailModified,pageable);
        return users.map(ResponsePageUsersDTO::fromUser);
    }

    @Transactional
    public Page<ResponsePageUsersDTO> getPageAdminByEmail(String email,Pageable pageable){
        String emailModified = "%" + email + "%";
        Page<BeanUser> users = userRepository.findAllAdminsByEmailLikeIgnoreCase(emailModified,pageable);
        return users.map(ResponsePageUsersDTO::fromUser);
    }

    @Transactional
    public boolean restorePassword(RequestRestorePasswordDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());
        if(user == null){
            throw new CustomException("user.email.not.found");
        }

        if(!user.getVerificationCode().equals(payload.getCode())){
            throw new CustomException("user.code.incorrect");
        }

        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        userRepository.save(user);

        emailService.sendEmail(user.getEmail(),
                "Contraseña restaurada",
                "Cambios en tu cuenta",
                "Tu contraseña ha sido restaurada exitosamente",
                "");
        return true;

    }

    @Transactional
    public boolean changePassword(RequestRestorePasswordDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());
        if(user == null){
            throw new CustomException("user.email.not.found");
        }

        if(!passwordEncoder.matches(payload.getOldPassword(), user.getPassword())){
            throw new CustomException("user.password.incorrect");
        }

        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        userRepository.save(user);

        emailService.sendEmail(user.getEmail(),
                "Contraseña cambiada",
                "Cambios en tu cuenta",
                "Tu contraseña ha sido cambiada exitosamente",
                "");
        return true;
    }

    @Transactional
    public boolean deleteAccountAdmin(RequestRestorePasswordDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());
        if(user == null){
            throw new CustomException("user.email.not.found");
        }

        List<String> roles = user.getRoles().stream().map(role -> role.getRole().getRoleName()).toList();

        if(roles.contains("ROLE_SUPERADMIN")){
            throw new CustomException("user.admin.not.delete");
        }


        List<BeanOrderHasProducts> orders = orderHasProductsRepository.findBySeller(payload.getEmail());
        boolean hasOrderPending = false;

        for (BeanOrderHasProducts order : orders) {
            if(order.getStatus().getStatus().equals("Preparación") || order.getStatus().getStatus().equals("Enviado")){
                hasOrderPending = true;
                break;
            }
        }

        if(hasOrderPending){
            throw new CustomException("user.order.pending");
        }


        userRepository.deleteAccount(payload.getEmail());

        emailService.sendEmail(user.getEmail(),
                "Cuenta eliminada",
                "Tu cuenta ha sido eliminada por un administrador",
                payload.getReazon(),
                "Atentamente, "+payload.getAdmin());

        return true;
    }
}
