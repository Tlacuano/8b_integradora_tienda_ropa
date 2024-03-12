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
        return iSubCategory.save(subcategory);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public BeanSubcategory putSubcategory(BeanSubcategory subcategory) {
        return iSubCategory.save(subcategory);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Boolean putStatusSubcategory(BeanSubcategory subcategory) {
        BeanSubcategory subcategory1 = iSubCategory.findByIdSubcategory(subcategory.getIdSubcategory());
        if (subcategory1 != null) {
            subcategory1.setStatus(!subcategory1.isStatus());
            iSubCategory.save(subcategory1);
            return true;
        }
        return false;
    }
}
