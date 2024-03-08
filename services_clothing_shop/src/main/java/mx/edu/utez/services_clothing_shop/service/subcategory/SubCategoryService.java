package mx.edu.utez.services_clothing_shop.service.subcategory;

import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import mx.edu.utez.services_clothing_shop.model.subcategory.ISubCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class SubCategoryService {
    private final ISubCategory iSubCategory;
    public SubCategoryService(ISubCategory iSubCategory) {
        this.iSubCategory = iSubCategory;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<BeanSubcategory>> getSubcategories() {
        try {
            List<BeanSubcategory> subcategories = iSubCategory.findAll();
            return ResponseEntity.ok(subcategories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BeanSubcategory> getSubcategory(BeanSubcategory subcategory) {
        try {
            if (iSubCategory.existsByIdSubcategory(subcategory.getIdSubcategory())) {
                return ResponseEntity.ok(iSubCategory.findByIdSubcategory(subcategory.getIdSubcategory()));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanSubcategory> postSubcategory(BeanSubcategory subcategory) {
        try {
            return ResponseEntity.status(201).body(iSubCategory.save(subcategory));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanSubcategory> putSubcategory(BeanSubcategory subcategory) {
        try {
            if (iSubCategory.existsByIdSubcategory(subcategory.getIdSubcategory())) {
                return ResponseEntity.status(201).body(iSubCategory.save(subcategory));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Boolean> putStatusSubcategory(BeanSubcategory subcategory) {
        try {
            if (iSubCategory.existsByIdSubcategory(subcategory.getIdSubcategory())) {
                BeanSubcategory beanSubcategory = iSubCategory.findByIdSubcategory(subcategory.getIdSubcategory());
                beanSubcategory.setStatus(!beanSubcategory.isStatus());
                iSubCategory.save(beanSubcategory);
                return ResponseEntity.status(200).body(true);
            } else {
                return ResponseEntity.status(400).body(false);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
