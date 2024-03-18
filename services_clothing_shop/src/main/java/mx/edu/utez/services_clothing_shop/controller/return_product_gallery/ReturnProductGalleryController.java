package mx.edu.utez.services_clothing_shop.controller.return_product_gallery;

import mx.edu.utez.services_clothing_shop.controller.return_product_gallery.dto.RequestActionByIdDTO;
import mx.edu.utez.services_clothing_shop.controller.return_product_gallery.dto.RequestPostReturnProductGalleryDTO;
import mx.edu.utez.services_clothing_shop.controller.return_product_gallery.dto.ResponseAllReturnProductGalleryDTO;
import mx.edu.utez.services_clothing_shop.model.return_product_gallery.BeanReturnProductGallery;
import mx.edu.utez.services_clothing_shop.service.return_product_gallery.ReturnProductGalleryService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venta-ropa/api/return-product-galleries")
@CrossOrigin(origins = "*")
public class ReturnProductGalleryController {
    private final ReturnProductGalleryService returnProductGalleryService;

    public ReturnProductGalleryController(ReturnProductGalleryService returnProductGalleryService){
        this.returnProductGalleryService = returnProductGalleryService;
    }

    @GetMapping("/get-return-product-galleries")
    public ResponseEntity<Object> getReturnProductGalleries(){
        try {
            List<ResponseAllReturnProductGalleryDTO> responseDTOs = returnProductGalleryService.getReturnProductGalleries();
            return ResponseEntity.ok(new CustomResponse<>(responseDTOs, "Return product galleries retrieved successfully", false, 200));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(null, e.getMessage(), true, 400));
        }

    }

    @PostMapping("/get-return-product-gallery")
    public ResponseEntity<Object> getReturnProductGallery(@Validated @RequestBody RequestActionByIdDTO payload){
        try {
            BeanReturnProductGallery returnProductGallery = returnProductGalleryService.getReturnProductGallery(payload.getIdImage());
            return ResponseEntity.ok(new CustomResponse<>(returnProductGallery, "Return product gallery retrieved successfully", false, 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(null, e.getMessage(), true, 400));
        }
    }

    @PostMapping("/post-return-product-gallery")
    public ResponseEntity<Object> postReturnProductGallery(@Validated @RequestBody RequestPostReturnProductGalleryDTO payload) {
        try {
            BeanReturnProductGallery newReturnProductGallery = returnProductGalleryService.postReturnProductGallery(payload);
            return ResponseEntity.ok(new CustomResponse<>(newReturnProductGallery, "Return product gallery created successfully", false, 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(null, "Error creating return product gallery: " + e.getMessage(), true, 400));
        }

    }

    @PutMapping("/put-return-product-gallery")
    public ResponseEntity<BeanReturnProductGallery> putReturnProductGallery(@RequestBody BeanReturnProductGallery returnProductGallery){
        return returnProductGalleryService.putReturnProductGallery(returnProductGallery);
    }

}
