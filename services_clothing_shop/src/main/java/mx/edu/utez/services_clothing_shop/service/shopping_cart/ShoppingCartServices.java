package mx.edu.utez.services_clothing_shop.service.shopping_cart;


import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.ResponseShoppingCartDTO;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.IShoppingCart;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns.EMAIL_REGEX;

@Service
public class ShoppingCartServices {

    private final IShoppingCart shoppingCartRepository;

    public ShoppingCartServices(IShoppingCart shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Transactional
    public List<ResponseShoppingCartDTO> findShoppingCartsByUserEmail(String userEmail) {
        System.out.println("userEmail: " + userEmail);
        if (userEmail == null || userEmail.isEmpty()) {
            throw new CustomException("user.email.notnull");
        } else if (!userEmail.matches(EMAIL_REGEX)) {
            throw new CustomException("user.email.invalid");
        } else {
            List<BeanShoppingCart> shoppingCart = shoppingCartRepository.findAllByUser_Email(userEmail);
            if (shoppingCart != null) {
                List<ResponseShoppingCartDTO> shoppingCartDTOs = new ArrayList<>();
                for (BeanShoppingCart cart : shoppingCart) {
                    shoppingCartDTOs.add(ResponseShoppingCartDTO.fromShoppingCart(cart));
                }
                return shoppingCartDTOs;
            } else {
                throw new CustomException("shoppingCart.notfound");
            }
        }
    }

    @Transactional
    public ResponseShoppingCartDTO saveShoppingCar(BeanShoppingCart shoppingCart) {
        if (shoppingCart == null) {
            throw new CustomException("shoppingCart.notnull");
        } else if (shoppingCart.getAmount() == 0) {
            throw new CustomException("shoppingCart.amount.notnull");
        } else if (shoppingCart.getProduct() == null) {
            throw new CustomException("shoppingCart.product.notnull");
        } else {
            BeanShoppingCart response = shoppingCartRepository.save(shoppingCart);
            return ResponseShoppingCartDTO.fromShoppingCart(response);
        }
    }

    @Transactional
    public void deleteShoppingCartById(UUID shoppingCartId) {
        if (shoppingCartId == null) {
            throw new CustomException("shoppingCart.id.notnull");
        } else {
            BeanShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElse(null);
            if (shoppingCart != null) {
                shoppingCartRepository.delete(shoppingCart);
                if (shoppingCartRepository.findById(shoppingCartId).isPresent()) {
                    throw new CustomException("shoppingCart.delete.error");
                }
            } else {
                throw new CustomException("shoppingCart.id.notfound");
            }
        }
    }


    @Transactional(rollbackFor = {Exception.class})
    public BeanShoppingCart updateShoppingCartById(@RequestBody BeanShoppingCart shoppingCart) {
        if (shoppingCart == null) {
            throw new CustomException("shoppingCart.notnull");
        } else{
            BeanShoppingCart existingShoppingCart = shoppingCartRepository.findById(shoppingCart.getIdShoppingCart()).orElse(null);
            if (existingShoppingCart != null) {
                if(shoppingCart.getAmount() == 0){
                    throw new CustomException("shoppingCart.amount.notnull");
                }else if (shoppingCart.getAmount() == existingShoppingCart.getAmount()){
                    throw new CustomException("shoppingCart.update.amount.error");
                }else{
                    existingShoppingCart.setAmount(shoppingCart.getAmount());
                    return shoppingCartRepository.save(existingShoppingCart);
                }
            } else {
                throw new CustomException("shoppingCart.id.notfound");
            }
        }
    }
}
