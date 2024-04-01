package mx.edu.utez.services_clothing_shop.controller.order_has_products;

import mx.edu.utez.services_clothing_shop.controller.order_has_products.dto.RequestOrderHasProductsByOrderIdDTO;
import mx.edu.utez.services_clothing_shop.controller.order_has_products.dto.ResponseOrderHasProductsDTO;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.service.order_has_products.OrderHasProductsService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("venta-ropa/api/order-has-products")
@CrossOrigin(origins = "*")
public class OrderHasProductsController {
    private final OrderHasProductsService orderHasProductsService;

    public OrderHasProductsController(OrderHasProductsService orderHasProductsService) {
        this.orderHasProductsService = orderHasProductsService;
    }

    @PostMapping("/get-orders-has-products-by-order-id")
    public ResponseEntity<CustomResponse<List<ResponseOrderHasProductsDTO>>> getOrdersHasProductsByOrderId(@RequestBody RequestOrderHasProductsByOrderIdDTO requestBody) {
        List<BeanOrderHasProducts> ordersHasProducts = orderHasProductsService.getOrdersHasProductsByOrder_IdOrder(requestBody.getIdOrder());
        List<ResponseOrderHasProductsDTO> dtoPage = ordersHasProducts.stream().map(ResponseOrderHasProductsDTO::toOrderHasProductsDTO).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(dtoPage, "Orders has products found", false, HttpStatus.OK.value()));
    }
}
