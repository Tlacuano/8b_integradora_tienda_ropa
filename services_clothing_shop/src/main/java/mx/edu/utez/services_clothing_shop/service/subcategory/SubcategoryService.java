package mx.edu.utez.services_clothing_shop.service.subcategory;

import mx.edu.utez.services_clothing_shop.controller.subcategory.dto.ResponseSubcategoryNameDTO;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import mx.edu.utez.services_clothing_shop.model.subcategory.ISubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SubcategoryService {
    private final ISubCategory iSubCategory;
    public SubcategoryService(ISubCategory iSubCategory) {
        this.iSubCategory = iSubCategory;
    }

    @Transactional
    public Page<BeanSubcategory> getSubcategories(Pageable page) {
        return iSubCategory.findAll(page);
    }

    @Transactional
    public BeanSubcategory getSubcategory(UUID idSubcategory) {
        return iSubCategory.findByIdSubcategory(idSubcategory);
    }

    @Transactional
    public List<ISubCategory.SubcategoryNameProjection> getSubcategoriesByCategory(String categoryName) {
        return iSubCategory.findByCategoryName(categoryName);
    }

    @Transactional
    public Page<BeanSubcategory> getSubcategoriesBySubcategory(String subcategory, Pageable pageable) {
        return iSubCategory.findBySubcategoryStartsWithIgnoreCase(subcategory, pageable);
    }

    @Transactional
    public BeanSubcategory postSubcategory(BeanSubcategory subcategory) {
        return iSubCategory.saveAndFlush(subcategory);
    }

    @Transactional
    public BeanSubcategory putSubcategory(BeanSubcategory subcategory) {
        return iSubCategory.saveAndFlush(subcategory);
    }

    @Transactional
    public void putStatusSubcategory(UUID idSubcategory) {
        BeanSubcategory subcategory = iSubCategory.findByIdSubcategory(idSubcategory);
        subcategory.setStatus(!subcategory.isStatus());
        iSubCategory.saveAndFlush(subcategory);
    }
}
