package mx.edu.utez.services_clothing_shop.controller.product;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.product.dto.*;
import mx.edu.utez.services_clothing_shop.model.image_product_status.BeanImageProductStatus;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.service.product.ProductService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("venta-ropa/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/get-products")
    public ResponseEntity<CustomResponse<Page<ResponseProductDTO>>> getProducts(Pageable page) {
        try {
            Page<BeanProduct> beanProductPage = productService.getProducts(page);
            return getCustomResponseResponseEntity(beanProductPage);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener los productos: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-products-by-user")
    public ResponseEntity<CustomResponse<Page<ResponseProductDTO>>> getProductsByUserEmail(@Valid @RequestBody RequestProductByUserEmailDTO requestDTO) {
        try {
            Page<BeanProduct> beanProductPage = productService.getProductsByUserEmail(requestDTO.getEmail(), requestDTO.getPage());
            return getCustomResponseResponseEntity(beanProductPage);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener los productos: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-product")
    public ResponseEntity<CustomResponse<ResponseProductDTO>> getProduct(@Valid @RequestBody RequestProductByIdDTO product) {
        try {
            BeanProduct retrievedProduct = productService.getProduct(product.getIdProduct());
            return retrievedProduct != null ?
                    ResponseEntity.ok(new CustomResponse<>(ResponseProductDTO.toProductDTO(retrievedProduct), "Producto encontrado", false, 200)) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(null, "Producto no encontrado", true, 404));
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener el producto: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post-product")
    public ResponseEntity<CustomResponse<BeanProduct>> postProduct(@Valid @RequestBody RequestProductDTO product) {
        BeanProduct newProduct = new BeanProduct();
        try {
            parseToBeanProduct(newProduct, product.getProductName(), product.getDescription(), product.getPrice(), product.getAmount(), product.getSubcategory());
            BeanUser user = new BeanUser();
            user.setIdUser(product.getUser());
            newProduct.setUser(user);
            newProduct.setStatus(product.isStatus());
            List<String> images = new ArrayList<>(product.getProductGallery());
            newProduct = productService.postProduct(newProduct, images);
            return new ResponseEntity<>(new CustomResponse<>(newProduct, "Producto registrado correctamente", false, 201), HttpStatus.CREATED);
        } catch (Exception e) {
            productService.deleteProductGallery(newProduct);
            productService.deleteProduct(newProduct.getIdProduct());
            return new ResponseEntity<>(new CustomResponse<>(null, "Se produjo un error al registrar el producto", true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/put-product")
    public ResponseEntity<CustomResponse<BeanProduct>> putProduct(@Valid @RequestBody RequestPutProductDTO product) {
        try {
            BeanProduct productUpdated = new BeanProduct();
            productUpdated.setIdProduct(product.getIdProduct());
            parseToBeanProduct(productUpdated, product.getProductName(), product.getDescription(), product.getPrice(), product.getAmount(), product.getSubcategory());
            List<BeanProductGallery> productGallery = getBeanProductGalleries(product);
            productUpdated = productService.putProduct(productUpdated, productGallery);
            return new ResponseEntity<>(new CustomResponse<>(productUpdated, "Producto actualizado correctamente", false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Se produjo un error al actualizar el producto: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/put-status-product")
    public ResponseEntity<CustomResponse<Boolean>> putStatusProduct(@Valid @RequestBody RequestProductByIdDTO product) {
        try {
            productService.putStatusProduct(product.getIdProduct());
            return new ResponseEntity<>(new CustomResponse<>(true, "Estatus del producto actualizado correctamente", false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(false, "Se produjo un error al actualizar el estatus del producto", true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<CustomResponse<Page<ResponseProductDTO>>> getCustomResponseResponseEntity(Page<BeanProduct> beanProductPage) {
        return beanProductPage != null ?
                ResponseEntity.ok(new CustomResponse<>(beanProductPage.map(ResponseProductDTO::toProductDTO), "Productos encontrados", false, 200)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(null, "Productos no encontrados", true, 404));
    }

    private void parseToBeanProduct(BeanProduct newProduct, String productName, String description, double price, int amount, UUID subcategory2) {
        newProduct.setProductName(productName);
        newProduct.setDescription(description);
        newProduct.setPrice(price);
        newProduct.setAmount(amount);
        BeanSubcategory subcategory = new BeanSubcategory();
        subcategory.setIdSubcategory(subcategory2);
        newProduct.setSubcategory(subcategory);
    }

    private static List<BeanProductGallery> getBeanProductGalleries(RequestPutProductDTO product) {
        List<BeanProductGallery> productGallery = new ArrayList<>();
        for (ProductImageDTO gallery : product.getProductGallery()) {
            BeanProductGallery productGalleryBean = new BeanProductGallery();
            productGalleryBean.setIdImage(gallery.getIdImage());
            productGalleryBean.setImage(gallery.getImage());
            BeanImageProductStatus imageProductStatus = new BeanImageProductStatus();
            imageProductStatus.setStatus(gallery.getStatus());
            productGalleryBean.setStatus(imageProductStatus);
            productGallery.add(productGalleryBean);
        }
        return productGallery;
    }
}
