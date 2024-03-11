package mx.edu.utez.services_clothing_shop.service.sopphing_cart;

import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.GetShoppingCartDTO;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.IShoppingCart;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
    public CustomResponse<BeanShoppingCart>  saveShoppingCar(BeanShoppingCart shoppingCart) {
        return new CustomResponse<>(
                shoppingCartRepository.save(shoppingCart),
                "ok",
                false,
                200
        );
    }

    @Transactional(rollbackFor = {Exception.class})
    public CustomResponse<BeanShoppingCart> deleteShoppingCartById(UUID shoppingCartId) {
        try{
            BeanShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElse(null);
            if(shoppingCart != null){
                return new CustomResponse<>(null, "ok",false, 200);
            }else{
                return new CustomResponse<>(null,"Not found",true,404);
            }
        }catch(Exception e){
            return new CustomResponse<>(null,"Error",true,500);

        }
    }
    @Transactional(rollbackFor = {Exception.class})
    public CustomResponse<BeanShoppingCart> updateShoppingCartById(BeanShoppingCart shoppingCart) {
        try{
            BeanShoppingCart shoppingCart1 = shoppingCartRepository.findById(shoppingCart.getIdShoppingCart()).orElse(null);
            if(shoppingCart1 != null){
                return new CustomResponse<>(shoppingCartRepository.save(shoppingCart), "ok",false, 200);
            }else{
                return new CustomResponse<>(null,"Not found",true,404);
            }
        }catch(Exception e){
            return new CustomResponse<>(null,"Error",true,500);
        }
    }


}
