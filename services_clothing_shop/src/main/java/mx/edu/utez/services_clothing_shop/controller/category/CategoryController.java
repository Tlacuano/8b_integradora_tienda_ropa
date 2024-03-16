package mx.edu.utez.services_clothing_shop.controller.category;

import mx.edu.utez.services_clothing_shop.controller.category.dto.RequestCategoryDTO;
import mx.edu.utez.services_clothing_shop.controller.category.dto.RequestPutCategoryDTO;
import mx.edu.utez.services_clothing_shop.controller.category.dto.RequestCategoryByIdDTO;
import mx.edu.utez.services_clothing_shop.model.category.BeanCategory;
import mx.edu.utez.services_clothing_shop.service.category.CategoryService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("venta-ropa/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/get-categories")
    public ResponseEntity<CustomResponse<Page<BeanCategory>>> getCategories(Pageable page) {
        Page<BeanCategory> categories = categoryService.getCategories(page);
        if (categories != null) {
            return new ResponseEntity<>(new CustomResponse<>(categories, "Categorías encontradas", false, 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Categorías no encontradas", true, 404), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/get-category")
    public ResponseEntity<CustomResponse<BeanCategory>> getCategory(@RequestBody RequestCategoryByIdDTO category) {
        BeanCategory retrievedCategory = categoryService.getCategory(category.getIdCategory());
        if (retrievedCategory != null) {
            return new ResponseEntity<>(new CustomResponse<>(retrievedCategory, "Categoría encontrada", false, 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Categoría no encontrada", true, 404), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/post-category")
    public ResponseEntity<CustomResponse<BeanCategory>> postCategory(@RequestBody RequestCategoryDTO category) {
        BeanCategory newCategory = new BeanCategory();
        newCategory.setCategory(category.getCategory());
        newCategory.setImage(category.getImage());
        newCategory.setStatus(category.isStatus());
        newCategory = categoryService.postCategory(newCategory);
        if (newCategory != null) {
            return new ResponseEntity<>(new CustomResponse<>(newCategory, "Categoría guardada", false, 201), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Categoría no guardada", true, 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/put-category")
    public ResponseEntity<CustomResponse<BeanCategory>> putCategory(@RequestBody RequestPutCategoryDTO category) {
        BeanCategory updatedCategory = new BeanCategory();
        updatedCategory.setIdCategory(category.getIdCategory());
        updatedCategory.setCategory(category.getCategory());
        updatedCategory.setImage(category.getImage());
        updatedCategory = categoryService.putCategory(updatedCategory);
        if (updatedCategory != null) {
            return new ResponseEntity<>(new CustomResponse<>(updatedCategory, "Categoría actualizada", false, 201), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(null, "Categoría no actualizada", true, 400), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/put-status-category")
    public ResponseEntity<CustomResponse<Boolean>> putStatusCategory(@RequestBody RequestCategoryByIdDTO category) {
        Boolean updatedStatus = categoryService.putStatusCategory(category.getIdCategory());
        if (updatedStatus) {
            return new ResponseEntity<>(new CustomResponse<>(true, "Estado de la categoría actualizado", false, 201), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new CustomResponse<>(false, "Estado de la categoría no actualizado", true, 400), HttpStatus.BAD_REQUEST);
        }
    }
}
