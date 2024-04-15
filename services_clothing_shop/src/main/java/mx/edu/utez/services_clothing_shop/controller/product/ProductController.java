package mx.edu.utez.services_clothing_shop.controller.product;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.product.dto.*;
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

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get-products")
    public ResponseEntity<Object> getProducts(Pageable page) {
        try {
            Page<BeanProduct> beanProductPage = productService.getProducts(page);
            return getCustomResponseResponseEntity(beanProductPage);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener los productos: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-by-category")
    public ResponseEntity<CustomResponse<Page<ResponseProductDTO>>> getProductsByCategory(@Valid @RequestBody RequestProductByCategoryDTO payload, Pageable page) {
        try {
            Page<BeanProduct> beanProductList = productService.getProductsByCategory(payload.getCategory(), page);
            Page<ResponseProductDTO> responseProductDTOList = beanProductList.map(ResponseProductDTO::toProductDTO);
            return ResponseEntity.ok(new CustomResponse<>(responseProductDTOList, "Productos por categoria encontrados", false, 200));
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener los productos por categoría: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-by-subcategory")
    public ResponseEntity<CustomResponse<Page<ResponseProductDTO>>> getProductsBySubcategory(@Valid @RequestBody RequestProductBySubcategoryDTO payload, Pageable page) {
        try {
            Page<BeanProduct> beanProductList = productService.getProductsBySubcategory(payload.getCategory(), payload.getSubcategory(), page);
            Page<ResponseProductDTO> responseProductDTOList = beanProductList.map(ResponseProductDTO::toProductDTO);
            return ResponseEntity.ok(new CustomResponse<>(responseProductDTOList, "Productos por subcategoria encontrados", false, 200));
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener los productos por subcategoría: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/get-products-by-product-name")
    public ResponseEntity<CustomResponse<Page<ResponseProductDTO>>> getPageProductByName(@RequestBody RequestProductByNameDTO requestProductByNameDTO, Pageable pageable) {
        try {
            Page<BeanProduct> beanProductPage = productService.getProductsByName(requestProductByNameDTO.getProductName(), requestProductByNameDTO.getUserEmail(), pageable);
            Page<ResponseProductDTO> responseProductDTOList = beanProductPage.map(ResponseProductDTO::toProductDTO);
            return ResponseEntity.ok(new CustomResponse<>(responseProductDTOList, "Productos encontrados", false, 200));
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener los productos: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/get-by-search-query")
    public ResponseEntity<CustomResponse<Page<ResponseProductDTO>>> getProductsBySearchQuery(@Valid @RequestBody RequestProductBySearchQueryDTO payload, Pageable page) {
        try {
            Page<BeanProduct> beanProductList = productService.getProductsBySearchQuery(payload, page);
            Page<ResponseProductDTO> responseProductDTOList = beanProductList.map(ResponseProductDTO::toProductDTO);
            return ResponseEntity.ok(new CustomResponse<>(responseProductDTOList, "Productos por búsqueda encontrados", false, 200));
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener los productos por búsqueda: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-products-by-user")
    public ResponseEntity<Object> getProductsByUserEmail(@Valid @RequestBody RequestProductByUserEmailDTO requestDTO, Pageable page) {
        try {
            Page<BeanProduct> beanProductPage = productService.getProductsByUserEmail(requestDTO.getEmail(), page);
            return getCustomResponseResponseEntity(beanProductPage);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener los productos: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-product")
    public ResponseEntity<Object> getProduct(@Valid @RequestBody RequestProductByIdDTO product) {
        try {
            BeanProduct retrievedProduct = productService.getProduct(product.getIdProduct());
            return retrievedProduct != null ? ResponseEntity.ok(new CustomResponse<>(ResponseProductDTO.toProductDTO(retrievedProduct), "Producto encontrado", false, 200)) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(null, "Producto no encontrado", true, 404));
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener el producto: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/get-product-to-edit")
    public ResponseEntity<Object> getProductToEdit(@Valid @RequestBody RequestProductByIdDTO product) {
        try {
            BeanProduct retrievedProduct = productService.getProduct(product.getIdProduct());
            return retrievedProduct != null ? ResponseEntity.ok(new CustomResponse<>(ResponseProductToEditDTO.toProductDTO(retrievedProduct), "Producto encontrado", false, 200)) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(null, "Producto no encontrado", true, 404));
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al obtener el producto: " + e.getMessage(), true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post-product")
    public ResponseEntity<Object> postProduct(@Valid @RequestBody RequestProductDTO product) {
       return new ResponseEntity<>(new CustomResponse<>(productService.postProduct(product), "Producto registrado correctamente", false, 201), HttpStatus.CREATED);
    }

    @PutMapping("/put-product")
    public ResponseEntity<Object> putProduct(@Valid @RequestBody RequestPutProductDTO product) {
        return new ResponseEntity<>(new CustomResponse<>(productService.putProduct(product), "Producto actualizado correctamente", false, 200), HttpStatus.OK);
    }


    @PutMapping("/put-status-product")
    public ResponseEntity<Object> putStatusProduct(@Valid @RequestBody RequestProductByIdDTO product) {
        try {
            productService.putStatusProduct(product.getIdProduct());
            return new ResponseEntity<>(new CustomResponse<>(true, "Estatus del producto actualizado correctamente", false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(false, "Se produjo un error al actualizar el estatus del producto", true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Object> getCustomResponseResponseEntity(Page<BeanProduct> beanProductPage) {
        return beanProductPage != null ? ResponseEntity.ok(new CustomResponse<>(beanProductPage.map(ResponseProductDTO::toProductDTO), "Productos encontrados", false, 200)) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(null, "Productos no encontrados", true, 404));
    }




}
