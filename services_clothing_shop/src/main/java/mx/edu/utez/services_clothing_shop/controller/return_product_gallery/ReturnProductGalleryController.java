package mx.edu.utez.services_clothing_shop.controller.return_product_gallery;

import mx.edu.utez.services_clothing_shop.model.return_product_gallery.BeanReturnProductGallery;
import mx.edu.utez.services_clothing_shop.service.return_product_gallery.ReturnProductGalleryService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<BeanReturnProductGallery>> getReturnProductGalleries(){
        return returnProductGalleryService.getReturnProductGalleries();
    }

    @PostMapping("/get-return-product-gallery")
    public ResponseEntity<BeanReturnProductGallery> getReturnProductGallery(@RequestBody BeanReturnProductGallery returnProductGallery){
        return returnProductGalleryService.getReturnProductGallery(returnProductGallery);
    }

    @PostMapping("/post-return-product-gallery")
    public ResponseEntity<BeanReturnProductGallery> postReturnProductGallery(@RequestBody BeanReturnProductGallery returnProductGallery) {
        return returnProductGalleryService.postReturnProductGallery(returnProductGallery);
    }

    @PutMapping("/put-return-product-gallery")
    public ResponseEntity<BeanReturnProductGallery> putReturnProductGallery(@RequestBody BeanReturnProductGallery returnProductGallery){
        return returnProductGalleryService.putReturnProductGallery(returnProductGallery);
    }

}
