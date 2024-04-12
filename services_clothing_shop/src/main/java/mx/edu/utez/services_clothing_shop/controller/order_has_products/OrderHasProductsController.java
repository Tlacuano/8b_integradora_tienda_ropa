package mx.edu.utez.services_clothing_shop.controller.order_has_products;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.order_has_products.dto.*;
import mx.edu.utez.services_clothing_shop.controller.review.dto.RequestComprobationToReviewDTO;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.service.order_has_products.OrderHasProductsService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<Object> getOrdersHasProductsByOrderId(@RequestBody RequestOrderHasProductsByOrderIdDTO requestBody) {
        List<BeanOrderHasProducts> ordersHasProducts = orderHasProductsService.getOrdersHasProductsByOrderIdOrder(requestBody.getIdOrder());
        List<ResponseOrderHasProductsDTO> dtoPage = ordersHasProducts.stream().map(ResponseOrderHasProductsDTO::toOrderHasProductsDTO).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(dtoPage, "Orders has products by id found", false, HttpStatus.OK.value()));
    }

    @PostMapping("/get-orders-has-products-by-buyer")
    public ResponseEntity<Object> getOrdersHasProductsByBuyer(@RequestBody RequestComprobationToReviewDTO requestBody) {
        return new ResponseEntity<>(
                new CustomResponse<>(orderHasProductsService.getOrdersHasProductsByBuyer(requestBody), "Order has products by buyer found", false, HttpStatus.OK.value()),
                HttpStatus.OK
        );
    }

    @PostMapping("/get-orders-has-products-by-seller-and-status")
    public ResponseEntity<Object> getOrdersHasProductsBySeller(@RequestBody RequestGetPageSalesDTO requestBody, Pageable pageable) {
        return new ResponseEntity<>(
                new CustomResponse<>(orderHasProductsService.getOrdersHasProductsBySeller(requestBody, pageable), "Order has products by seller and status found", false, HttpStatus.OK.value()),
                HttpStatus.OK
        );
    }

    @PostMapping("/get-orders-has-products-by-seller-and-order-number")
    public ResponseEntity<Object> getOrdersHasProductsBySellerAndOrderNumber(@RequestBody RequestGetPageSalesDTO requestBody, Pageable pageable) {
        return new ResponseEntity<>(
                new CustomResponse<>(orderHasProductsService.getOrdersHasProductsBySellerAndNumber(requestBody, pageable), "Order has products by seller and order number found", false, HttpStatus.OK.value()),
                HttpStatus.OK
        );
    }

    @PostMapping("/cancel-sell-by-seller")
    public ResponseEntity<Object> cancelSellBySeller(@RequestBody RequestActionBySeller requestBody) {
        return new ResponseEntity<>(new CustomResponse<>(orderHasProductsService.cancelSellBySeller(requestBody), "Sell canceled", false, HttpStatus.OK.value()), HttpStatus.OK);
    }

    @PostMapping("/mark-as-sent-by-seller")
    public ResponseEntity<Object> markAsSentBySeller(@RequestBody RequestActionBySeller requestBody) {
        return new ResponseEntity<>(new CustomResponse<>(orderHasProductsService.markAsSent(requestBody), "Sell marked as sent", false, HttpStatus.OK.value()), HttpStatus.OK);
    }

    @PostMapping("/put-status-order-has-products")
    public ResponseEntity<Object> putStatusOrderHasProducts(@Valid @RequestBody RequestOrderHasProductByIdDTO requestBody, BindingResult result) {
        try {
            if (result.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse<>(
                    "Invalid data",
                    "Invalid data",
                    true,
                    HttpStatus.BAD_REQUEST.value()
            ));
            orderHasProductsService.putStatusOrderHasProducts(requestBody.getIdOrderProduct(), requestBody.getRejectReason());
            return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(
                    "Order has products status updated",
                    "Order has products status updated",
                    false,
                    HttpStatus.OK.value()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse<>(
                    "Order has products status not updated",
                    "Order has products status not updated",
                    true,
                    HttpStatus.BAD_REQUEST.value()
            ));
        }
    }
}
