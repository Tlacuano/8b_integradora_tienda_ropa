package mx.edu.utez.services_clothing_shop.service.wish_list;


import mx.edu.utez.services_clothing_shop.model.wish_list.BeanWishList;
import mx.edu.utez.services_clothing_shop.model.wish_list.IWishList;
import mx.edu.utez.services_clothing_shop.service.email_service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Service
public class WishListService {
    private final IWishList wishListRepository;
    private final EmailService emailService;

    public WishListService(IWishList wishListRepository, EmailService emailService) {
        this.wishListRepository = wishListRepository;
        this.emailService = emailService;
    }


    @Transactional(rollbackFor = {Exception.class})
    public List<BeanWishList> findWishListByUserEmail(String userEmail) {
        emailService.enviarCorreoRestablecimiento(userEmail, "ready");
        return wishListRepository.findAllByUser_email(userEmail);
    }

    @Transactional(rollbackFor = {Exception.class})
    public BeanWishList saveWishList(BeanWishList wishList) {
        return wishListRepository.save(wishList);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteWishListById(UUID wishListId) {
        BeanWishList wishList = wishListRepository.findById(wishListId).orElse(null);
        if (wishList != null) {
            wishListRepository.delete(wishList);
        }else{
            throw new RuntimeException("WishList not found");
        }
    }


    @Transactional(rollbackFor = {Exception.class})
    public BeanWishList updateWishListById(@RequestBody BeanWishList wishList) {
        BeanWishList existingWishList = wishListRepository.findById(wishList.getIdWish()).orElse(null);
        if (existingWishList != null) {
            existingWishList.setAmount(wishList.getAmount());
            return wishListRepository.save(existingWishList);
        } else {
            return null;
        }
    }

}
