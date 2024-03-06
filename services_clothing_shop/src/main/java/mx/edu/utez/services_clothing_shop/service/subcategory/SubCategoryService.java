package mx.edu.utez.services_clothing_shop.service.subcategory;

import mx.edu.utez.services_clothing_shop.model.status.BeanStatus;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import mx.edu.utez.services_clothing_shop.model.subcategory.ISubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class SubCategoryService {

    @Autowired
    private ISubCategory iSubCategory;

    @Transactional(readOnly = true)
    public ResponseEntity<List<BeanSubcategory>> findAll() {
        try {
            List<BeanSubcategory> subcategories = iSubCategory.findAll();
            return ResponseEntity.ok(subcategories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanSubcategory> insert(BeanSubcategory subcategory) {
        try {
            return ResponseEntity.status(201).body(iSubCategory.save(subcategory));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanSubcategory> update(BeanSubcategory subcategory) {
        try {
            if (iSubCategory.existsBySubcategory(subcategory.getSubcategory())) {
                return ResponseEntity.status(201).body(iSubCategory.save(subcategory));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Boolean> updateStatus(String subcategory) {
        try {
            if (iSubCategory.existsBySubcategory(subcategory)) {
                BeanSubcategory beanSubcategory = iSubCategory.findBySubcategory(subcategory);
                BeanStatus status = beanSubcategory.getStatus();
                if (status.getStatus().equals("ACTIVE")) {
                    status.setStatus("INACTIVE");
                } else {
                    status.setStatus("ACTIVE");
                }
                beanSubcategory.setStatus(status);
                iSubCategory.save(beanSubcategory);
                return ResponseEntity.status(201).body(true);
            } else {
                return ResponseEntity.status(400).body(false);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
