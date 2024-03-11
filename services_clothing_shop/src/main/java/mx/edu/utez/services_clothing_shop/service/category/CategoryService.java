package mx.edu.utez.services_clothing_shop.service.category;

import mx.edu.utez.services_clothing_shop.model.category.BeanCategory;
import mx.edu.utez.services_clothing_shop.model.category.ICategory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;


@Service
public class CategoryService {
    private final ICategory iCategory;
    public CategoryService(ICategory iCategory) {
        this.iCategory = iCategory;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<BeanCategory>> getCategories() {
        try {
            List<BeanCategory> categories = iCategory.findAll();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BeanCategory> getCategory(BeanCategory category) {
        try {
            return ResponseEntity.ok(iCategory.findByIdCategory(category.getIdCategory()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanCategory> postCategory(BeanCategory category) {
        try {
            return ResponseEntity.status(201).body(iCategory.save(category));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<BeanCategory> putCategory(BeanCategory category) {
        try {
            if (iCategory.existsByIdCategory(category.getIdCategory())) {
                return ResponseEntity.status(201).body(iCategory.save(category));
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Boolean> putStatusCategory(BeanCategory category) {
        try {
            if (iCategory.existsByIdCategory(category.getIdCategory())) {
                BeanCategory beanCategory = iCategory.findByIdCategory(category.getIdCategory());
                beanCategory.setStatus(!beanCategory.isStatus());
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
