package mx.edu.utez.services_clothing_shop.service.sopphing_car;


import mx.edu.utez.services_clothing_shop.model.shopping_car.BeanShopingCar;
import mx.edu.utez.services_clothing_shop.model.shopping_car.IShoppingCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SopphingCarServices {
    @Autowired
    IShoppingCar repository;


    public List<BeanShopingCar> findShoppingCarsByUserEmail(String userEmail) {
        // Aquí asumo que tienes un método en tu repositorio para obtener los carritos de compras por el correo electrónico del usuario
        return repository.findByUserEmail(userEmail);
    }
}
