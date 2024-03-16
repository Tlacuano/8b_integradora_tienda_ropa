package mx.edu.utez.services_clothing_shop.service.category;

import mx.edu.utez.services_clothing_shop.model.category.BeanCategory;
import mx.edu.utez.services_clothing_shop.model.category.ICategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.UUID;


@Service
public class CategoryService {
    private final ICategory iCategory;
    public CategoryService(ICategory iCategory) {
        this.iCategory = iCategory;
    }

    @Transactional(readOnly = true)
    public Page<BeanCategory> getCategories(Pageable page) {
        return iCategory.findAll(page);
    }

    @Transactional(readOnly = true)
    public BeanCategory getCategory(UUID idCategory) {
        return iCategory.findByIdCategory(idCategory);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public BeanCategory postCategory(BeanCategory category) {
        return iCategory.save(category);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public BeanCategory putCategory(BeanCategory category) {
        return iCategory.save(category);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Boolean putStatusCategory(UUID idCategory) {
        BeanCategory category1 = iCategory.findByIdCategory(idCategory);
        if (category1 != null) {
            category1.setStatus(!category1.isStatus());
            iCategory.save(category1);
            return true;
        }
        return false;
    }
}
