package mx.edu.utez.services_clothing_shop.controller.product;

import mx.edu.utez.services_clothing_shop.controller.product.dto.RequestProductByUserEmailDTO;
import mx.edu.utez.services_clothing_shop.controller.product.dto.ResponseProductDTO;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.service.product.ProductService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("venta-ropa/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {this.productService = productService;}

    @GetMapping("/get-products")
    public ResponseEntity<CustomResponse<Page<ResponseProductDTO>>> getProducts(Pageable page) {
        Page<BeanProduct> beanProductPage = productService.getProducts(page);
        return getCustomResponseResponseEntity(beanProductPage);
    }

    @PostMapping("/get-products-by-user")
    public ResponseEntity<CustomResponse<Page<ResponseProductDTO>>> getProductsByUserEmail(@RequestBody RequestProductByUserEmailDTO requestDTO) {
        Page<BeanProduct> beanProductPage = productService.getProductsByUserEmail(requestDTO.getEmail(), requestDTO.getPage());
        return getCustomResponseResponseEntity(beanProductPage);
    }

    private ResponseEntity<CustomResponse<Page<ResponseProductDTO>>> getCustomResponseResponseEntity(Page<BeanProduct> beanProductPage) {
        if (beanProductPage != null) {
            Page<ResponseProductDTO> productDTOPage = beanProductPage.map(ResponseProductDTO::toProductDTO);
            return new ResponseEntity<>(new CustomResponse<>(productDTOPage, "Productos encontrados", false, 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Productos no encontrados", true, 404), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/get-product")
    public ResponseEntity<CustomResponse<ResponseProductDTO>> getProduct(@RequestBody BeanProduct product) {
        BeanProduct retrievedProduct = productService.getProduct(product.getIdProduct());
        if (retrievedProduct != null) {
            ResponseProductDTO responseDTO = ResponseProductDTO.toProductDTO(retrievedProduct);
            return new ResponseEntity<>(new CustomResponse<>(responseDTO, "Producto encontrado", false, 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Producto no encontrado", true, 404), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/post-product")
    public ResponseEntity<CustomResponse<BeanProduct>> postProduct(@RequestBody BeanProduct product) {
        BeanProduct newProduct = productService.postProduct(product);
        if (newProduct != null) {
            return new ResponseEntity<>(new CustomResponse<>(newProduct, "Producto registrado correctamente", false, 201), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Producto no registrado", true, 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/put-product")
    public ResponseEntity<CustomResponse<BeanProduct>> putProduct(@RequestBody BeanProduct product) {
        BeanProduct updatedProduct = productService.putProduct(product);
        try {
            return new ResponseEntity<>(new CustomResponse<>(updatedProduct, "Producto actualizado correctamente", false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, e.getMessage(), true, 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/put-status-product")
    public ResponseEntity<CustomResponse<Boolean>> putStatusProduct(@RequestBody BeanProduct product) {
        Boolean statusUpdated = productService.putStatusProduct(product.getIdProduct());
        if (statusUpdated) {
            return new ResponseEntity<>(new CustomResponse<>(statusUpdated, "Estatus del producto actualizado correctamente", false, 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(statusUpdated, "Estatus del producto no actualizado", true, 400), HttpStatus.BAD_REQUEST);
        }
    }
}
