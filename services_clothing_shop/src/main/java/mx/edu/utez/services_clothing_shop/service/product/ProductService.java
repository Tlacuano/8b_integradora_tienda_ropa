package mx.edu.utez.services_clothing_shop.service.product;

import mx.edu.utez.services_clothing_shop.model.category.BeanCategory;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product.IProduct;
import mx.edu.utez.services_clothing_shop.model.status.BeanStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private IProduct iProduct;

    @Transactional(readOnly = true)
    public ResponseEntity<List<BeanProduct>> findAll() {
        try {
            List<BeanProduct> products = iProduct.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanProduct> insert(BeanProduct product) {
        try {
            return ResponseEntity.status(201).body(iProduct.save(product));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanProduct> update(BeanProduct product) {
        try {
            if (iCategory.existsByCategory(category.getCategory())) {
                return ResponseEntity.status(201).body(iCategory.save(category));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Boolean> updateStatus(String category) {
        try {
            if (iCategory.existsByCategory(category)) {
                BeanCategory beanCategory = iCategory.findByCategory(category);
                BeanStatus status = beanCategory.getStatus();
                if (status.getStatus().equals("ACTIVE")) {
                    status.setStatus("INACTIVE");
                } else {
                    status.setStatus("ACTIVE");
                }
                beanCategory.setStatus(status);
                iCategory.save(beanCategory);
                return ResponseEntity.status(201).body(true);
            } else {
                return ResponseEntity.status(400).body(false);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
