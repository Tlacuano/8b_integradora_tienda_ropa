package mx.edu.utez.services_clothing_shop.service.order_status;

import mx.edu.utez.services_clothing_shop.model.order_status.BeanOrderStatus;
import mx.edu.utez.services_clothing_shop.model.order_status.IOrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class OrderStatusService {
    public final IOrderStatus orderStatusRepository;

    public OrderStatusService(IOrderStatus orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }


    @Transactional
    public List<BeanOrderStatus> getAll(){
        return orderStatusRepository.findAll();
    }

}