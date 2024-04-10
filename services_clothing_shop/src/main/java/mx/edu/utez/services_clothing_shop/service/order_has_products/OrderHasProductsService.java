package mx.edu.utez.services_clothing_shop.service.order_has_products;

import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.order_has_products.dto.RequestGetPageSalesDTO;
import mx.edu.utez.services_clothing_shop.controller.order_has_products.dto.ResponseOrdersSalesDTO;
import mx.edu.utez.services_clothing_shop.controller.review.dto.RequestComprobationToReviewDTO;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.order.IOrder;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.order_has_products.IOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.order_status.BeanOrderStatus;
import mx.edu.utez.services_clothing_shop.model.order_status.IOrderStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderHasProductsService {
    private final IOrderHasProducts orderHasProductsRepository;
    private final IUser userRepository;
    private final IOrderStatus orderStatusRepository;
    private final IOrder orderRepository;

    public OrderHasProductsService(IOrderHasProducts orderHasProductsRepository, IUser userRepository, IOrderStatus orderStatusRepository, IOrder orderRepository) {
        this.orderHasProductsRepository = orderHasProductsRepository;
        this.userRepository = userRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional(rollbackOn = {Exception.class})
    public List<BeanOrderHasProducts> getOrdersHasProductsByOrder_IdOrder(UUID idOrder) {
        return orderHasProductsRepository.findAllByOrder_IdOrder(idOrder);
    }

    @Transactional
    public BeanOrderHasProducts getOrdersHasProductsByBuyer(RequestComprobationToReviewDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if(user == null){
            throw new CustomException("user.email.exists");
        }

        BeanOrderHasProducts order = orderHasProductsRepository.findOrderHasProductByBuyerAndProduct(user.getIdUser(),payload.getIdProduct());

        if(order == null){
            throw new CustomException("order.bybuyer.notfound");
        }

        return order;
    }

    @Transactional
    public Object getOrdersHasProductsBySeller(RequestGetPageSalesDTO requestBody, Pageable pageable) {
        BeanUser user = userRepository.findByEmail(requestBody.getEmail());

        if(user == null){
            throw new CustomException("user.email.exists");
        }

        BeanOrderStatus status = orderStatusRepository.findByStatus(requestBody.getStatus());

        if(status == null){
            throw new CustomException("order.status.notfound");
        }

        Page<BeanOrderHasProducts> orders = orderHasProductsRepository.findAllBySellerAndStatus(user.getEmail(), status, pageable);

        return orders.map(ResponseOrdersSalesDTO::toOrderSalesDTO);
    }
}
