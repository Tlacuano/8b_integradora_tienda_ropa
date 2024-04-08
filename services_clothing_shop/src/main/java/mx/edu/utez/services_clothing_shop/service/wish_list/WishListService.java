package mx.edu.utez.services_clothing_shop.service.wish_list;




import com.cloudinary.utils.StringUtils;
import mx.edu.utez.services_clothing_shop.controller.wish_list.dto.ResponseInformationWishListDTO;
import mx.edu.utez.services_clothing_shop.controller.wish_list.dto.ResponseWishListDTO;

import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product.IProduct;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;
import mx.edu.utez.services_clothing_shop.model.wish_list.BeanWishList;
import mx.edu.utez.services_clothing_shop.model.wish_list.IWishList;
import mx.edu.utez.services_clothing_shop.service.email_service.EmailService;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns.EMAIL_REGEX;

@Service
public class WishListService {
    private final IWishList wishListRepository;
    private final IProduct productRepository;
    private final EmailService emailService;

    public WishListService(IWishList wishListRepository,IProduct productRepository, EmailService emailService) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.emailService = emailService;
    }


    public List<ResponseInformationWishListDTO> findWishListByUserEmail(String userEmail) {
        if (StringUtils.isEmpty(userEmail)) {
            throw new CustomException("user.email.notnull");
        } else if (!userEmail.matches(EMAIL_REGEX)) {
            throw new CustomException("user.email.invalid");
        } else {
            List<BeanWishList> wishList = wishListRepository.findAllByUser_email(userEmail);
            if (wishList != null && !wishList.isEmpty()) {
                List<ResponseInformationWishListDTO> wishListDTOS = new ArrayList<>();
                for (BeanWishList wish : wishList) {
                    wishListDTOS.add(ResponseInformationWishListDTO.fromWish(wish));
                }
                return wishListDTOS;
            } else {
                return Collections.emptyList();
            }
        }
    }


    @Transactional
    public ResponseWishListDTO saveWishList(BeanWishList wishList) {
        if (wishList == null) {
            throw new CustomException("wishList.notnull");
        } else if (wishList.getIdWish() != null) {
            throw new CustomException("wishList.id.automatic");
        } else if (wishList.getAmount() <= 0) {
            throw new CustomException("shoppingCart.amount.notnull");
        } else if (wishList.getProduct().getIdProduct() == null) {
            throw new CustomException("wishList.product.notnull");
        } else if (wishList.getUser().getIdUser() == null) {
            throw new CustomException("wishList.user.notnull");
        } else {
            BeanProduct product = productRepository.findByIdProduct(wishList.getProduct().getIdProduct());
            if(product == null){
                throw new CustomException("wishList.product.notExists");
            }
            int amount = product.getAmount();
            if(wishList.getAmount() > amount){
                throw new CustomException("wishList.amount.error");
            }
            List<BeanWishList> existingWishList = wishListRepository.findByProduct_idProductAndUser_idUser(wishList.getProduct().getIdProduct(), wishList.getUser().getIdUser());
            if (!existingWishList.isEmpty()) {
                throw new CustomException("wishList.product.exists");
            }
            BeanWishList response = wishListRepository.save(wishList);
            return ResponseWishListDTO.fromWishList(response);
        }
    }


    @Transactional
    public void deleteWishListById(UUID wishListId) {
        if (wishListId == null) {
            throw new CustomException("wishList.id.notnull");
        } else {
            BeanWishList wishList = wishListRepository.findById(wishListId).orElse(null);
            if (wishList != null) {
                wishListRepository.deleteById(wishListId);
                if (wishListRepository.findById(wishListId).isPresent()) {
                    throw new CustomException("wishList.delete.error");
                }
            } else {
                throw new CustomException("wishList.id.notfound");
            }
        }
    }


    @Transactional(rollbackFor = {Exception.class})
    public BeanWishList updateWishListById(@RequestBody BeanWishList wishList) {
        if (wishList == null) {
            throw new CustomException("wishList.notnull");
        } else {
            BeanWishList existingWishList = wishListRepository.findById(wishList.getIdWish()).orElse(null);
            if (existingWishList != null) {
                BeanProduct product = productRepository.findByIdProduct(existingWishList.getProduct().getIdProduct());
                if (wishList.getAmount() <= 0) {
                    throw new CustomException("wishList.amount.notnull");
                } else if (wishList.getAmount() == existingWishList.getAmount()) {
                    throw new CustomException("wishList.update.amount.error");
                } else {
                    if(wishList.getAmount() > product.getAmount()){
                        throw new CustomException("wishList.amount.error");
                    }
                    existingWishList.setAmount(wishList.getAmount());
                    return wishListRepository.save(existingWishList);
                }
            } else {
                throw new CustomException("wishList.id.notfound");
            }
        }
    }

}
