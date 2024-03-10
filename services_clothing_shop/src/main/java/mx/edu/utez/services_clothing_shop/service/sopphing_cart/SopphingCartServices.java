package mx.edu.utez.services_clothing_shop.service.sopphing_cart;

import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.IShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SopphingCartServices {
    @Autowired
    IShoppingCart shoppingCartRepository;


    public List<BeanShoppingCart> findShoppingCartsByUserEmail(String userEmail) {
        return shoppingCartRepository.findAllByUser_Email(userEmail);
    }


    public BeanShoppingCart saveShoppingCar(BeanShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }
    public void deleteShoppingCartById(UUID shoppingCartId) {
        shoppingCartRepository.deleteById(shoppingCartId);
    }
    public List<BeanShoppingCart> getAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }
}