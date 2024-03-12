package mx.edu.utez.services_clothing_shop.service.sopphing_cart;

import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.GetShoppingCartDTO;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.IShoppingCart;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SopphingCartServices {

    private final IShoppingCart shoppingCartRepository;

    public SopphingCartServices(IShoppingCart shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Transactional(rollbackFor = {Exception.class})
    public List<GetShoppingCartDTO> findShoppingCartsByUserEmail(String userEmail) {
        List<BeanShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUser_Email(userEmail);
        List<GetShoppingCartDTO> shoppingCartDTOs = new ArrayList<>();
        for (BeanShoppingCart cart : shoppingCarts) {
            shoppingCartDTOs.add(GetShoppingCartDTO.fromShoppingCart(cart));
        }
        return shoppingCartDTOs;
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
