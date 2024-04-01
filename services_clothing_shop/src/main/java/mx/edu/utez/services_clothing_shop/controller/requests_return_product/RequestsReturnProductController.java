package mx.edu.utez.services_clothing_shop.controller.requests_return_product;


import mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto.*;
import mx.edu.utez.services_clothing_shop.model.request_return_product.IRequestsReturnProduct;
import mx.edu.utez.services_clothing_shop.service.requests_return_product.RequestsReturnProductService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("venta-ropa/api/requests-return-product")
@CrossOrigin(origins = "*")
public class RequestsReturnProductController {

    private final RequestsReturnProductService requestsReturnProductService;

    public RequestsReturnProductController(RequestsReturnProductService requestsReturnProductService) {
        this.requestsReturnProductService = requestsReturnProductService;
    }

    @PostMapping("/get-by-id-request-return-product")
    public ResponseEntity<CustomResponse<RequestsReturnProductGetByIdResponseDTO>> getRequestReturnProductById(@RequestBody RequestsReturnProductGetByIdRequestDTO requestDTO) {
        UUID idRequest = requestDTO.getIdRequestReturnProduct();
        RequestsReturnProductGetByIdResponseDTO requestData = requestsReturnProductService.getRequestReturnProductInfoById(idRequest);

       if (requestData == null) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Request not found", true, 404), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new CustomResponse<>(requestData, "Request found", false, 200));
    }

    @PostMapping("/post-request-return-product")
    public ResponseEntity<CustomResponse<RequestsReturnProductDTO>> postRequestReturnProduct(@RequestBody RequestsReturnProductPostDTO requestDTO) {
        RequestsReturnProductDTO requestData = requestsReturnProductService.postRequestReturnProduct(requestDTO.getOrderHasProductId());
        return ResponseEntity.ok(new CustomResponse<>(requestData, "Request created", false, 200));
    }

    @PutMapping("/put-request-return-product-status")
    public ResponseEntity<CustomResponse<RequestsReturnProductDTO>> putRequestReturnProduct(@RequestBody RequestsReturnProductPutDTO requestDTO) {
        RequestsReturnProductDTO updatedRequest = requestsReturnProductService.putRequestReturnProduct(requestDTO.getRequestId(), requestDTO.getStatus(), requestDTO.getRejectionReason());
        return ResponseEntity.ok(new CustomResponse<>(updatedRequest, "Request status updated", false, 200));
    }

    @GetMapping("/get-page")
    public ResponseEntity<Page<RequestsReturnGetPageResponseDTO>> getPageRequests(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String searchTerm) {
        Page<IRequestsReturnProduct.ReturnRequestProjection> requests = requestsReturnProductService.findRequestsByPageAndSearchTerm(pageable, searchTerm);
        Page<RequestsReturnGetPageResponseDTO> response = requests.map(this::convertToDTO);
        return ResponseEntity.ok(response);
    }

    private RequestsReturnGetPageResponseDTO convertToDTO(IRequestsReturnProduct.ReturnRequestProjection request) {
        RequestsReturnGetPageResponseDTO dto = new RequestsReturnGetPageResponseDTO();
        dto.setIdRequestReturnProduct(request.getIdRequestReturnProduct());
        dto.setStatus(request.getStatus());
        dto.setOrderDate(request.getOrderDate());
        dto.setOrderNumber(request.getOrderNumber());
        return dto;
    }

}
