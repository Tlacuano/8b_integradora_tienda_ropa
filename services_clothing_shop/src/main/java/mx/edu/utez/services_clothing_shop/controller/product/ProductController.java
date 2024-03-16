package mx.edu.utez.services_clothing_shop.controller.product;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.product.dto.*;
import mx.edu.utez.services_clothing_shop.model.image_product_status.BeanImageProductStatus;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.service.product.ProductService;
import mx.edu.utez.services_clothing_shop.service.product_gallery.ProductGalleryService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("venta-ropa/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;
    private final ProductGalleryService productGalleryService;

    public ProductController(ProductService productService, ProductGalleryService productGalleryService) {
        this.productService = productService;
        this.productGalleryService = productGalleryService;
    }

    @PostMapping("/get-products")
    public ResponseEntity<CustomResponse<Page<ResponseProductDTO>>> getProducts(Pageable page) {
        Page<BeanProduct> beanProductPage = productService.getProducts(page);
        return getCustomResponseResponseEntity(beanProductPage);
    }

    @PostMapping("/get-products-by-user")
    public ResponseEntity<CustomResponse<Page<ResponseProductDTO>>> getProductsByUserEmail(@Valid @RequestBody RequestProductByUserEmailDTO requestDTO) {
        Page<BeanProduct> beanProductPage = productService.getProductsByUserEmail(requestDTO.getEmail(), requestDTO.getPage());
        return getCustomResponseResponseEntity(beanProductPage);
    }

    private ResponseEntity<CustomResponse<Page<ResponseProductDTO>>> getCustomResponseResponseEntity(Page<BeanProduct> beanProductPage) {
        return beanProductPage != null ?
                ResponseEntity.ok(new CustomResponse<>(beanProductPage.map(ResponseProductDTO::toProductDTO), "Productos encontrados", false, 200)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(null, "Productos no encontrados", true, 404));
    }

    @PostMapping("/get-product")
    public ResponseEntity<CustomResponse<ResponseProductDTO>> getProduct(@RequestBody RequestProductByIdDTO product) {
        BeanProduct retrievedProduct = productService.getProduct(product.getIdProduct());
        return retrievedProduct != null ?
                ResponseEntity.ok(new CustomResponse<>(ResponseProductDTO.toProductDTO(retrievedProduct), "Producto encontrado", false, 200)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(null, "Producto no encontrado", true, 404));
    }

    @PostMapping("/post-product")
    public ResponseEntity<CustomResponse<BeanProduct>> postProduct(@Valid @RequestBody RequestProductDTO product) {
        BeanProduct newProduct = new BeanProduct();
        parseToBeanProduct(newProduct, product.getProductName(), product.getDescription(), product.getPrice(), product.getAmount(), product.getSubcategory());
        BeanUser user = new BeanUser();
        user.setIdUser(product.getUser());
        newProduct.setUser(user);
        newProduct.setStatus(product.isStatus());
        try {
            newProduct = productService.postProduct(newProduct);
            if (newProduct != null) {
                List<String> images = new ArrayList<>(product.getProductGallery());
                List<BeanProductGallery> productGallery = productGalleryService.postProductGallery(newProduct, images);
                newProduct.setProductGallery(productGallery);
                return new ResponseEntity<>(new CustomResponse<>(newProduct, "Producto registrado correctamente", false, 201), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new CustomResponse<>(null, "Producto no registrado", true, 400), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            if (newProduct != null) {
                productGalleryService.deleteProductGallery(newProduct);
                productService.deleteProduct(newProduct.getIdProduct());
            }
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(null, "Se produjo un error al registrar el producto", true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @PutMapping("/put-product")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomResponse<BeanProduct>> putProduct(@RequestBody RequestPutProductDTO product) {
        BeanProduct productUpdated = new BeanProduct();
        productUpdated.setIdProduct(product.getIdProduct());
        parseToBeanProduct(productUpdated, product.getProductName(), product.getDescription(), product.getPrice(), product.getAmount(), product.getSubcategory());
        try {
            productUpdated = productService.putProduct(productUpdated);
            if (productUpdated != null) {
                List<BeanProductGallery> productGallery = getBeanProductGalleries(product);
                productGallery = productGalleryService.putProductGalley(productGallery);
                productUpdated.setProductGallery(productGallery);
                return new ResponseEntity<>(new CustomResponse<>(productUpdated, "Producto actualizado correctamente", false, 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CustomResponse<>(null, "Producto no actualizado", true, 400), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Se produjo un error al actualizar el producto: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @PutMapping("/put-status-product")
    public ResponseEntity<CustomResponse<Boolean>> putStatusProduct(@RequestBody RequestProductByIdDTO product) {
        try {
            Boolean statusUpdated = productService.putStatusProduct(product.getIdProduct());
            if (statusUpdated) {
                return new ResponseEntity<>(new CustomResponse<>(true, "Estatus del producto actualizado correctamente", false, 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CustomResponse<>(false, "Estatus del producto no actualizado", true, 400), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(false, "Se produjo un error al actualizar el estatus del producto", true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
