package mx.edu.utez.services_clothing_shop.controller.order_has_products;

import mx.edu.utez.services_clothing_shop.controller.order_has_products.dto.RequestGetPageSalesDTO;
import mx.edu.utez.services_clothing_shop.controller.order_has_products.dto.RequestOrderHasProductsByOrderIdDTO;
import mx.edu.utez.services_clothing_shop.controller.order_has_products.dto.ResponseOrderHasProductsDTO;
import mx.edu.utez.services_clothing_shop.controller.review.dto.RequestComprobationToReviewDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestActionByEmailDTO;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.service.order_has_products.OrderHasProductsService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

    @PostMapping("/get-orders-has-products-by-buyer")
    public ResponseEntity<Object> getOrdersHasProductsByBuyer(@RequestBody RequestComprobationToReviewDTO requestBody) {
        return new ResponseEntity<>(
                new CustomResponse<>(orderHasProductsService.getOrdersHasProductsByBuyer(requestBody), "Order has products found", false, HttpStatus.OK.value()),
                HttpStatus.OK
        );
    }

    @PostMapping("/get-orders-has-products-by-seller-and-status")
    public ResponseEntity<Object> getOrdersHasProductsBySeller(@RequestBody RequestGetPageSalesDTO requestBody, Pageable pageable) {
        return new ResponseEntity<>(
                new CustomResponse<>(orderHasProductsService.getOrdersHasProductsBySeller(requestBody, pageable), "Order has products found", false, HttpStatus.OK.value()),
                HttpStatus.OK
        );
    }

}
