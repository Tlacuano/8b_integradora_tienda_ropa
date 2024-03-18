package mx.edu.utez.services_clothing_shop.service.subcategory;

import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;
import mx.edu.utez.services_clothing_shop.model.subcategory.ISubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.UUID;

@Service
public class SubcategoryService {
    private final ISubCategory iSubCategory;
    public SubcategoryService(ISubCategory iSubCategory) {
        this.iSubCategory = iSubCategory;
    }

    @Transactional(readOnly = true)
    public Page<BeanSubcategory> getSubcategories(Pageable page) {
        return iSubCategory.findAll(page);
    }

    @Transactional(readOnly = true)
    public BeanSubcategory getSubcategory(UUID idSubcategory) {
        return iSubCategory.findByIdSubcategory(idSubcategory);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public BeanSubcategory postSubcategory(BeanSubcategory subcategory) {
        if (iSubCategory.findBySubcategory(subcategory.getSubcategory())) {
            throw new IllegalArgumentException("La subcategoría ya existe");
        }
        return iSubCategory.saveAndFlush(subcategory);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public BeanSubcategory putSubcategory(BeanSubcategory subcategory) {
        if (!iSubCategory.existsByIdSubcategory(subcategory.getIdSubcategory())) {
            throw new IllegalArgumentException("La subcategoría no existe");
        }
        return iSubCategory.saveAndFlush(subcategory);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Boolean putStatusSubcategory(UUID idSubcategory) {
        if (!iSubCategory.existsByIdSubcategory(idSubcategory)) {
            throw new IllegalArgumentException("La subcategoría no existe");
        }
        BeanSubcategory subcategory = iSubCategory.findByIdSubcategory(idSubcategory);
        subcategory.setStatus(!subcategory.isStatus());
        iSubCategory.saveAndFlush(subcategory);
        return true;
    }
}
