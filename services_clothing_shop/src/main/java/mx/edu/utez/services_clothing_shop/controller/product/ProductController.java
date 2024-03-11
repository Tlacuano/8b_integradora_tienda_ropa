package mx.edu.utez.services_clothing_shop.controller.product;

import mx.edu.utez.services_clothing_shop.controller.product.dto.ResponseProductDTO;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.service.product.ProductService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("venta-ropa/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {this.productService = productService;}

    @GetMapping("/get-products")
    public ResponseEntity<CustomResponse<List<ResponseProductDTO>>> getProducts() {
        List<BeanProduct> beanProducts = productService.getProducts().getBody();
        return getCustomResponseResponseEntity(beanProducts);
    }

    private ResponseEntity<CustomResponse<List<ResponseProductDTO>>> getCustomResponseResponseEntity(List<BeanProduct> beanProducts) {
        if (beanProducts != null) {
            List<ResponseProductDTO> productDTOS = beanProducts.stream().map(ResponseProductDTO::toProductDTO).toList();
            return new ResponseEntity<>(new CustomResponse<>(productDTOS, "Productos encontrados", false, 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Productos no encontrados", true, 404), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/get-products-by-user")
    public ResponseEntity<CustomResponse<List<ResponseProductDTO>>> getProductsByUser(@RequestBody BeanUser user) {
        List<BeanProduct> beanProducts = productService.getProductsByUser(user).getBody();
        return getCustomResponseResponseEntity(beanProducts);
    }

    @PostMapping("/get-product")
    public ResponseEntity<CustomResponse<ResponseProductDTO>> getProduct(@RequestBody BeanProduct product) {
        BeanProduct retrievedProduct = productService.getProduct(product.getIdProduct()).getBody();
        if (retrievedProduct != null) {
            ResponseProductDTO responseDTO = ResponseProductDTO.toProductDTO(retrievedProduct);
            return new ResponseEntity<>(new CustomResponse<>(responseDTO, "Producto encontrado", false, 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Producto no encontrado", true, 404), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/post-product")
    public ResponseEntity<CustomResponse<BeanProduct>> postProduct(@RequestBody BeanProduct product) {
        ResponseEntity<BeanProduct> newProduct = productService.postProduct(product);
        try {
            return new ResponseEntity<>(new CustomResponse<>(Objects.requireNonNull(newProduct.getBody()), "Producto registrado correctamente", false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, e.getMessage(), true, 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/put-product")
    public ResponseEntity<CustomResponse<BeanProduct>> putProduct(@RequestBody BeanProduct product) {
        ResponseEntity<BeanProduct> updatedProduct = productService.putProduct(product);
        try {
            return new ResponseEntity<>(new CustomResponse<>(Objects.requireNonNull(updatedProduct.getBody()), "Producto actualizado correctamente", false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, e.getMessage(), true, 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/put-status-product")
    public ResponseEntity<CustomResponse<Boolean>> putStatusProduct(@RequestBody BeanProduct product) {
        ResponseEntity<Boolean> status = productService.putStatusProduct(product.getIdProduct());
        try {
            return new ResponseEntity<>(new CustomResponse<>(true, Objects.requireNonNull(status.getBody()).toString(), false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), true, 400), HttpStatus.BAD_REQUEST);
        }
    }
}
