package mx.edu.utez.services_clothing_shop.service.product;

import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product.IProduct;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final IProduct iProduct;

    public ProductService(IProduct iProduct) {
        this.iProduct = iProduct;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<BeanProduct>> getProducts() {
        try {
            List<BeanProduct> products = iProduct.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<BeanProduct>> getProductsByUser(BeanUser user) {
        try {
            List<BeanProduct> products = iProduct.findByUser(user);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BeanProduct> getProduct(UUID idProduct) {
        try {
            BeanProduct product = iProduct.findById(idProduct).orElse(null);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanProduct> postProduct(BeanProduct product) {
        try {
            return ResponseEntity.status(201).body(iProduct.save(product));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanProduct> putProduct(BeanProduct product) {
        try {
            if (iProduct.existsByIdProduct(product.getIdProduct())) {
                return ResponseEntity.status(201).body(iProduct.save(product));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Boolean> putStatusProduct(UUID idProduct) {
        try {
            if (iProduct.existsByIdProduct(idProduct)) {
                BeanProduct product = iProduct.findByIdProduct(idProduct);
                product.setStatus(!product.isStatus());
                iProduct.save(product);
                return ResponseEntity.status(200).body(true);
            } else {
                return ResponseEntity.status(400).body(false);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
