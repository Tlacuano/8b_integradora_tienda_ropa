package mx.edu.utez.services_clothing_shop.service.shopping_cart;


import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.*;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product.IProduct;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.IShoppingCart;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns.EMAIL_REGEX;

@Service
public class ShoppingCartServices {

    private final IShoppingCart shoppingCartRepository;
    private final IProduct productRepository;
    private final IUser userRepository;

    public ShoppingCartServices(IShoppingCart shoppingCartRepository, IProduct productRepository, IUser userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<ResponseShoppingCartDTO> findShoppingCartsByUserEmail(String userEmail) {
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
    public boolean saveShoppingCar(RequestPostCartDTO shoppingCart) {
        BeanUser user = userRepository.findByEmail(shoppingCart.getEmail());

        if(user == null){
            throw new CustomException("user.email.not.found");
        }

        BeanProduct product = productRepository.findByIdProduct(shoppingCart.getIdProduct());
        if (product == null) {
            throw new CustomException("product.id.notfound");
        }

        if(shoppingCartRepository.existsByProductAndUser(product,user)){
            throw new CustomException("shoppingCart.product.exists");
        }

        if(!product.isStatus() || product.getAmount() == 0){
            throw new CustomException("shoppingCart.product.invalidate");
        }

        BeanShoppingCart newCart = new BeanShoppingCart();
        newCart.setAmount(1);
        newCart.setProduct(product);
        newCart.setUser(user);

        shoppingCartRepository.save(newCart);

        return true;
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


    @Transactional
    public ResponsePutShoppingCartDTO updateShoppingCartById(RequestPutShoppingCartDTO shoppingCart) {
        if (shoppingCart.getIdShoppingCart() == null) {
            throw new CustomException("shoppingCart.id.notnull");
        } else{
            BeanShoppingCart existingShoppingCart = shoppingCartRepository.findById(shoppingCart.getIdShoppingCart()).orElse(null);
            if (existingShoppingCart != null) {
                BeanProduct product = productRepository.findByIdProduct(existingShoppingCart.getProduct().getIdProduct());
                if(shoppingCart.getAmount() <= 0){
                    throw new CustomException("shoppingCart.amount.notnull");
                }else if (shoppingCart.getAmount() == existingShoppingCart.getAmount()){
                    throw new CustomException("shoppingCart.update.amount.error");
                }else{
                    if(shoppingCart.getAmount() > product.getAmount()){
                        throw new CustomException("shoppingCart.amount.error");
                    }
                    existingShoppingCart.setAmount(shoppingCart.getAmount());
                    BeanShoppingCart response = shoppingCartRepository.save(existingShoppingCart);
                    return ResponsePutShoppingCartDTO.fromPutShoppingCart(response);
                }
            } else {
                throw new CustomException("shoppingCart.id.notfound");
            }
        }
    }
}
