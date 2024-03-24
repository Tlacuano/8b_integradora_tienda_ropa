package mx.edu.utez.services_clothing_shop.controller.requests_sell_product;


import mx.edu.utez.services_clothing_shop.controller.requests_sell_product.dto.RequestsSellProductDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_sell_product.dto.RequestsSellProductGetDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_sell_product.dto.RequestsSellProductPostDTO;
import mx.edu.utez.services_clothing_shop.controller.requests_sell_product.dto.RequestsSellProductPutDTO;
import mx.edu.utez.services_clothing_shop.model.request_sell_product.IRequestsSellProduct;
import mx.edu.utez.services_clothing_shop.service.requests_sell_product.RequestsSellProductService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("venta-ropa/api/requests-sell-product")
@CrossOrigin(origins = "*")
public class RequestsSellProductController {

    private final RequestsSellProductService requestsSellProductService;

    public RequestsSellProductController(RequestsSellProductService requestsSellProductService) {
        this.requestsSellProductService = requestsSellProductService;
    }

    @PostMapping("/get-by-id-request-sell-product")
    public ResponseEntity<CustomResponse<RequestsSellProductDTO>> getRequestSellProductById(@RequestBody RequestsSellProductGetDTO requestDTO) {
        UUID idRequest = requestDTO.getIdRequestSellProduct();
        RequestsSellProductDTO requestData = requestsSellProductService.getRequestById(idRequest);

        if (requestData != null) {
            String status = requestsSellProductService.getRequestSellProductById(requestData.getIdStatus());
            requestData.setStatus(status);

            return ResponseEntity.ok(new CustomResponse<>(requestData, "Request found", false, 200));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomResponse<>(null, "Request not found", true, 404));
        }
    }

    @PostMapping("/post-request-sell-product")
    public ResponseEntity<CustomResponse<RequestsSellProductDTO>> postRequestSellProduct(@RequestBody RequestsSellProductPostDTO requestDTO) {
        RequestsSellProductDTO requestData = requestsSellProductService.postRequestSellProduct(requestDTO.getIdProduct());
        return ResponseEntity.ok(new CustomResponse<>(requestData, "Request created", false, 200));
    }

    @PutMapping("/put-request-sell-product")
    public ResponseEntity<CustomResponse<RequestsSellProductDTO>> putRequestSellProduct(@RequestBody RequestsSellProductPutDTO requestDTO) {
        RequestsSellProductDTO updatedRequest = requestsSellProductService.putRequestSellProduct(requestDTO.getIdRequestSellProduct(), requestDTO.getStatus(), requestDTO.getRejectionReason());
        return ResponseEntity.ok(new CustomResponse<>(updatedRequest, "Request status updated", false, 200));
    }

    @GetMapping("/get-page")
    public Page<IRequestsSellProduct.RequestSellStatusProjection> getPageRequestSellProduct(Pageable pageable) {
        return requestsSellProductService.getPageRequestSellProduct(pageable);
    }

}
