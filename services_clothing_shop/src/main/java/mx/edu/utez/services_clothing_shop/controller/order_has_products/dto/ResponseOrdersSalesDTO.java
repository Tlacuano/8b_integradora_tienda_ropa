package mx.edu.utez.services_clothing_shop.controller.order_has_products.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ResponseOrdersSalesDTO {
    private String address;
    private String orderNumber;
    private String orderDate;

    private UUID idOrderProduct;
    private String productName;
    private String productDescription;
    private int amount;
    private List<BeanProductGallery> productGallery;
    private double price;

    private String status;


    public static ResponseOrdersSalesDTO toOrderSalesDTO(BeanOrderHasProducts orderHasProducts){
        ResponseOrdersSalesDTO dto = new ResponseOrdersSalesDTO();
        dto.setAddress(orderHasProducts.getOrder().getAddress().getAddress()
                + ", " + orderHasProducts.getOrder().getAddress().getStreet()
                + ", " + orderHasProducts.getOrder().getAddress().getNeighborhood()
                + ", " + orderHasProducts.getOrder().getAddress().getState() + ", CP: "
                + orderHasProducts.getOrder().getAddress().getPostalCode());

        dto.setOrderNumber(orderHasProducts.getOrder().getOrderNumber());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = orderHasProducts.getOrder().getOrderDate().format(formatter);
        dto.setOrderDate(formattedDate);

        dto.setIdOrderProduct(orderHasProducts.getIdOrderProduct());
        dto.setProductName(orderHasProducts.getProduct().getProductName());
        dto.setProductDescription(orderHasProducts.getProduct().getDescription());
        dto.setAmount(orderHasProducts.getAmount());
        dto.setProductGallery(orderHasProducts.getProduct().getProductGallery());
        dto.setPrice(orderHasProducts.getProduct().getPrice());
        dto.setStatus(orderHasProducts.getStatus().getStatus());

        return dto;
    }
}
