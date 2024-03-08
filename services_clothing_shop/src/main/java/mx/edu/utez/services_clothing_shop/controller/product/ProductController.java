package mx.edu.utez.services_clothing_shop.controller.product;

import mx.edu.utez.services_clothing_shop.controller.product.dto.ResponseProductDTO;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("venta-ropa/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {this.productService = productService;}

    @GetMapping("/get-products")
    public ResponseEntity<List<BeanProduct>> getProducts() {return productService.getProducts();}

    @PostMapping("/get-products-by-user")
    public ResponseEntity<List<BeanProduct>> getProductsByUser(@RequestBody BeanUser user) {
        return productService.getProductsByUser(user);
    }

    @PostMapping("/get-product")
    public ResponseEntity<ResponseProductDTO> getProduct(@RequestBody BeanProduct product) {
        BeanProduct retrievedProduct = productService.getProduct(product.getIdProduct()).getBody();
        if (retrievedProduct != null) {
            ResponseProductDTO responseDTO = new ResponseProductDTO().toProductDTO(retrievedProduct);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/post-product")
    public ResponseEntity<BeanProduct> postProduct(@RequestBody BeanProduct product) {
        return productService.postProduct(product);
    }

    @PutMapping("/put-product")
    public ResponseEntity<BeanProduct> putProduct(@RequestBody BeanProduct product) {
        return productService.putProduct(product);
    }

    @PutMapping("/put-status-product")
    public ResponseEntity<Boolean> putStatusProduct(@RequestBody BeanProduct product) {
        return productService.putStatusProduct(product.getIdProduct());
    }
}
