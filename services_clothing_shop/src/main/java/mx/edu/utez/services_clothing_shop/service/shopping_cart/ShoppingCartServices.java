package mx.edu.utez.services_clothing_shop.service.shopping_cart;


import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.GetShoppingCartDTO;
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

    @Transactional(rollbackFor = {Exception.class})
    public List<GetShoppingCartDTO> findShoppingCartsByUserEmail(String userEmail) {
        System.out.println("userEmail: " + userEmail);
        if(userEmail == null || userEmail.isEmpty()) {
            throw new CustomException("user.email.notnull");
        }else if(!userEmail.matches(EMAIL_REGEX)){
            throw new CustomException("user.email.invalid");
        }else{
            List<BeanShoppingCart> shoppingCart = shoppingCartRepository.findAllByUser_Email(userEmail);
            if (shoppingCart != null) {
                List<GetShoppingCartDTO> shoppingCartDTOs = new ArrayList<>();
                for (BeanShoppingCart cart : shoppingCart) {
                    shoppingCartDTOs.add(GetShoppingCartDTO.fromShoppingCart(cart));
                }
                return shoppingCartDTOs;
            }else{
                throw new CustomException("shoppingCart.notfound");
            }
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public BeanShoppingCart saveShoppingCar(BeanShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteShoppingCartById(UUID shoppingCartId) {
        BeanShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElse(null);
        if (shoppingCart != null) {
            shoppingCartRepository.delete(shoppingCart);
        }else{
            throw new RuntimeException("El carrito de compras no existe");
        }
    }


    @Transactional(rollbackFor = {Exception.class})
    public BeanShoppingCart updateShoppingCartById(@RequestBody BeanShoppingCart shoppingCart) {
        BeanShoppingCart existingShoppingCart = shoppingCartRepository.findById(shoppingCart.getIdShoppingCart()).orElse(null);
        if (existingShoppingCart != null) {
            existingShoppingCart.setAmount(shoppingCart.getAmount());
            return shoppingCartRepository.save(existingShoppingCart);
        } else {
            return null;
        }
    }


}
