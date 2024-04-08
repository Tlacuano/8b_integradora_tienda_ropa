package mx.edu.utez.services_clothing_shop.controller.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Data
@NoArgsConstructor
public class ResponseOrderDTO {
    private UUID idOrder;
    private String orderDate;
    private String orderNumber;
    private List<BeanOrderHasProducts> orderHasProducts;
    private BeanAddress address;

    public ResponseOrderDTO toOrderDTO(BeanOrder order) {
        ResponseOrderDTO dto = new ResponseOrderDTO();
        dto.setIdOrder(order.getIdOrder());
        dto.setOrderDate(order.getOrderDate().toString());
        dto.setOrderNumber(order.getOrderNumber());

        order.getAddress().setPerson(null);
        dto.setAddress(order.getAddress());

        AtomicReference<List<BeanOrderHasProducts>> orderHasProductsSubList = new AtomicReference<>(new ArrayList<>());
        for (BeanOrderHasProducts orderHasProduct : order.getOrderHasProducts()) {
            orderHasProduct.getProduct().setUser(null);
            orderHasProduct.getProduct().setSubcategory(null);
            orderHasProductsSubList.get().add(orderHasProduct);
        }

        dto.setOrderHasProducts(orderHasProductsSubList.get());

        return dto;
    }

    public BeanOrder toOrderEntity() {
        BeanOrder order = new BeanOrder();
        order.setIdOrder(this.idOrder);
        order.setOrderDate(java.time.LocalDate.parse(this.orderDate));
        order.setOrderNumber(this.orderNumber);
        order.setAddress(this.address);
        order.setOrderHasProducts(this.orderHasProducts);

        return order;
    }
}
