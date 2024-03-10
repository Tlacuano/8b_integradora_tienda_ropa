package mx.edu.utez.services_clothing_shop.controller.order_has_products;

import mx.edu.utez.services_clothing_shop.controller.order_has_products.dto.RequestOrderHasProductsByOrderIdDTO;
import mx.edu.utez.services_clothing_shop.controller.order_has_products.dto.ResponseOrderHasProductsDTO;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.service.order_has_products.OrderHasProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("venta-ropa/api/order-has-products")
@CrossOrigin(origins = "*")
public class OrderHasProductsController {
    @Autowired
    private OrderHasProductsService orderHasProductsService;

    @PostMapping("/get-orders-has-products-by-order-id")
    public ResponseEntity<Page<ResponseOrderHasProductsDTO>> getOrdersHasProductsByOrderId(@RequestBody RequestOrderHasProductsByOrderIdDTO requestBody) {
        Page<BeanOrderHasProducts> ordersHasProducts = orderHasProductsService.getOrdersHasProductsByOrder_IdOrder(requestBody.getIdOrder(), requestBody.getPage());
        Page<ResponseOrderHasProductsDTO> dtoPage = ordersHasProducts.map(orderHasProducts -> new ResponseOrderHasProductsDTO().toOrderHasProductsDTO(orderHasProducts));
        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }
}
