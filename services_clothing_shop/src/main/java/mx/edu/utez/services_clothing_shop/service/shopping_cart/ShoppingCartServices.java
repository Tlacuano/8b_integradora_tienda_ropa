package mx.edu.utez.services_clothing_shop.service.shopping_cart;


import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.RequestPostShoppingCartDTO;
import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.ResponsePutShoppingCartDTO;
import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.ResponseShoppingCartDTO;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product.IProduct;
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
    private final IProduct productRepository;

    public ShoppingCartServices(IShoppingCart shoppingCartRepository, IProduct productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
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
    public RequestPostShoppingCartDTO saveShoppingCar(BeanShoppingCart shoppingCart) {
        if (shoppingCart == null) {
            throw new CustomException("shoppingCart.notnull");
        }else if(shoppingCart.getIdShoppingCart()!=null){
            throw new CustomException("shoppingCart.id.automatic");
        } else if (shoppingCart.getAmount() <= 0) {
            throw new CustomException("shoppingCart.amount.notnull");
        } else if (shoppingCart.getProduct().getIdProduct() == null) {
            throw new CustomException("shoppingCart.product.notnull");
        } else if(shoppingCart.getUser().getIdUser() == null) {
            throw new CustomException("shoppingCart.user.notnull");
        }else {
            BeanProduct product = productRepository.findByIdProduct(shoppingCart.getProduct().getIdProduct());
            if (product == null) {
                throw new CustomException("shoppingCart.product.notFound");
            }
            int amount = product.getAmount();
            if(shoppingCart.getAmount() > amount){
                throw new CustomException("shoppingCart.amount.error");
            }
            List<BeanShoppingCart> existingShoppingCart = shoppingCartRepository.findAllByUser_Email(shoppingCart.getUser().getEmail());
            if(!existingShoppingCart.isEmpty()){
                for (BeanShoppingCart cart : existingShoppingCart) {
                    if(cart.getProduct().getIdProduct().equals(shoppingCart.getProduct().getIdProduct())){
                        throw new CustomException("shoppingCart.product.exists");
                    }
                }
            }
            BeanShoppingCart response = shoppingCartRepository.save(shoppingCart);
            return RequestPostShoppingCartDTO.fromPostShoppingCart(response);
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


    @Transactional
    public ResponsePutShoppingCartDTO updateShoppingCartById(@RequestBody BeanShoppingCart shoppingCart) {
        System.out.println("shoppingCart: " + shoppingCart);
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
