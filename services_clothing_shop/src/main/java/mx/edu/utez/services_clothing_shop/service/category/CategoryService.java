package mx.edu.utez.services_clothing_shop.service.category;

import mx.edu.utez.services_clothing_shop.model.category.BeanCategory;
import mx.edu.utez.services_clothing_shop.model.category.ICategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class CategoryService {
    private final ICategory iCategory;
    public CategoryService(ICategory iCategory) {
        this.iCategory = iCategory;
    }

    @Transactional
    public Page<BeanCategory> getCategories(Pageable page) {
        return iCategory.findAll(page);
    }

    @Transactional
    public BeanCategory getCategory(UUID idCategory) {
        return iCategory.findByIdCategory(idCategory);
    }

    @Transactional
    public BeanCategory postCategory(BeanCategory category) {
        return iCategory.saveAndFlush(category);
    }

    @Transactional
    public BeanCategory putCategory(BeanCategory category) {
        return iCategory.saveAndFlush(category);
    }

    @Transactional
    public void putStatusCategory(UUID idCategory) {
        BeanCategory category = iCategory.findByIdCategory(idCategory);
        category.setStatus(!category.isStatus());
        iCategory.saveAndFlush(category);
    }
}
