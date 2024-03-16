package mx.edu.utez.services_clothing_shop.service.order_has_products;

import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.order_has_products.IOrderHasProducts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderHasProductsService {
    private final IOrderHasProducts orderHasProductsRepository;

    public OrderHasProductsService(IOrderHasProducts orderHasProductsRepository) {
        this.orderHasProductsRepository = orderHasProductsRepository;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Page<BeanOrderHasProducts> getOrdersHasProductsByOrder_IdOrder(UUID idOrder, Pageable page) {
        return orderHasProductsRepository.findAllByOrder_IdOrder(idOrder, page);
    }
}
